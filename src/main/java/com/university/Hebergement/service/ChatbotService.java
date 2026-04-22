package com.university.Hebergement.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.Hebergement.entities.Bloc;
import com.university.Hebergement.repository.BlocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

@Service
public class ChatbotService {

    @Autowired
    private BlocRepository blocRepository;

    private final HttpClient   httpClient   = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String OLLAMA_URL   = "http://localhost:11434/api/generate";
    private static final String OLLAMA_MODEL = "llama3.2:1b";

    // ════════════════════════════════════════════════════════════
    //  PUBLIC ENTRY POINT
    // ════════════════════════════════════════════════════════════

    public String chat(String userMessage) {
        try {
            // ✅ Check if DB is empty — never call Ollama with no data
            List<Bloc> blocs = blocRepository.findAll();
            if (blocs == null || blocs.isEmpty()) {
                return "Aucun bloc n'est encore enregistré dans la plateforme. "
                        + "Veuillez contacter l'administration pour plus d'informations.";
            }

            Intent intent    = detectIntent(userMessage);
            String dbContext = buildContext(intent, userMessage, blocs);
            String prompt    = buildPrompt(userMessage, dbContext);
            return callOllama(prompt);

        } catch (Exception e) {
            e.printStackTrace();
            return "Désolé, une erreur est survenue : " + e.getMessage();
        }
    }

    // ════════════════════════════════════════════════════════════
    //  STEP 1 — INTENT DETECTION
    // ════════════════════════════════════════════════════════════

    private enum Intent {
        CALME,
        BRUYANT,        // ✅ NOUVEAU — sens inverse de CALME
        DISPONIBILITE,
        BLOC_INFO,
        FOYER_INFO,
        RECOMMENDATION,
        STATS,
        GENERAL
    }

    private Intent detectIntent(String message) {
        String m = message.toLowerCase();

        // ✅ BRUYANT détecté AVANT CALME pour éviter tout conflit
        if (containsAny(m, "dérangable", "derangable", "bruyant", "animé", "anime",
                "agité", "agite", "moins calme", "plus bruyant", "perturbant",
                "bruit", "agitation", "social", "vivant"))
            return Intent.BRUYANT;

        if (containsAny(m, "calme", "silencieux", "tranquille", "concentration",
                "stress", "étude", "etude", "travailler", "dormir"))
            return Intent.CALME;

        if (containsAny(m, "disponible", "disponibilité", "libre", "place", "occupé"))
            return Intent.DISPONIBILITE;

        if (containsAny(m, "combien", "nombre", "total", "statistique", "stat"))
            return Intent.STATS;

        if (containsAny(m, "recommande", "conseille", "meilleur", "suggère",
                "1ère année", "premiere année", "nouveau", "nouvelle"))
            return Intent.RECOMMENDATION;

        if (containsAny(m, "bloc"))
            return Intent.BLOC_INFO;

        if (containsAny(m, "foyer", "résidence", "residence", "hébergement"))
            return Intent.FOYER_INFO;

        return Intent.GENERAL;
    }

    // ════════════════════════════════════════════════════════════
    //  STEP 2 — CONTEXT BUILDER (RAG)
    //  blocs already fetched in chat() — passed here to avoid
    //  a second DB query
    // ════════════════════════════════════════════════════════════

    private String buildContext(Intent intent, String userMessage, List<Bloc> blocs) {
        StringBuilder ctx = new StringBuilder();

        switch (intent) {

            case CALME -> {
                ctx.append("CLASSEMENT DES BLOCS DU PLUS CALME AU PLUS BRUYANT :\n");
                ctx.append("(Règle : plus il y a de chambres SIMPLE, plus le bloc est calme)\n\n");
                blocs.stream()
                        .map(b -> new AbstractMap.SimpleEntry<>(b, countType(b, "SIMPLE")))
                        .sorted((a, z) -> Integer.compare(z.getValue(), a.getValue()))
                        .forEach(e -> ctx.append(String.format(
                                "- Bloc %-12s | Simples: %d | Doubles: %d | Triples: %d | Total: %d\n",
                                e.getKey().getNomBloc(),
                                countType(e.getKey(), "SIMPLE"),
                                countType(e.getKey(), "DOUBLE"),
                                countType(e.getKey(), "TRIPLE"),
                                e.getKey().getChambres().size()
                        )));
            }

            // ✅ NOUVEAU CAS — BRUYANT
            case BRUYANT -> {
                ctx.append("CLASSEMENT DES BLOCS DU PLUS BRUYANT AU PLUS CALME :\n");
                ctx.append("(Règle : plus il y a de chambres TRIPLE, plus le bloc est bruyant/dérangable)\n\n");
                blocs.stream()
                        .map(b -> new AbstractMap.SimpleEntry<>(b, countType(b, "TRIPLE")))
                        .sorted((a, z) -> Integer.compare(z.getValue(), a.getValue()))
                        .forEach(e -> ctx.append(String.format(
                                "- Bloc %-12s | Triples: %d | Doubles: %d | Simples: %d | Total: %d\n",
                                e.getKey().getNomBloc(),
                                countType(e.getKey(), "TRIPLE"),
                                countType(e.getKey(), "DOUBLE"),
                                countType(e.getKey(), "SIMPLE"),
                                e.getKey().getChambres().size()
                        )));
            }

            case DISPONIBILITE -> {
                ctx.append("DISPONIBILITÉ PAR BLOC :\n\n");
                for (Bloc b : blocs) {
                    ctx.append(String.format(
                            "- Bloc %-12s | Total: %d | Simples: %d | Doubles: %d | Triples: %d\n",
                            b.getNomBloc(),
                            b.getChambres().size(),
                            countType(b, "SIMPLE"),
                            countType(b, "DOUBLE"),
                            countType(b, "TRIPLE")
                    ));
                }
            }

            case STATS -> {
                int totalSimple = 0, totalDouble = 0, totalTriple = 0;
                for (Bloc b : blocs) {
                    totalSimple += countType(b, "SIMPLE");
                    totalDouble += countType(b, "DOUBLE");
                    totalTriple += countType(b, "TRIPLE");
                }
                ctx.append("STATISTIQUES GLOBALES DE LA PLATEFORME :\n\n");
                ctx.append(String.format("- Nombre de blocs      : %d\n", blocs.size()));
                ctx.append(String.format("- Chambres simples     : %d\n", totalSimple));
                ctx.append(String.format("- Chambres doubles     : %d\n", totalDouble));
                ctx.append(String.format("- Chambres triples     : %d\n", totalTriple));
                ctx.append(String.format("- Total des chambres   : %d\n",
                        totalSimple + totalDouble + totalTriple));
            }

            case BLOC_INFO -> {
                String lower = userMessage.toLowerCase();
                boolean found = false;
                for (Bloc b : blocs) {
                    if (lower.contains(b.getNomBloc().toLowerCase())) {
                        ctx.append("DÉTAILS DU BLOC DEMANDÉ :\n\n");
                        ctx.append(buildBlocDetail(b));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    ctx.append("LISTE COMPLÈTE DES BLOCS :\n\n");
                    blocs.forEach(b -> ctx.append(buildBlocDetail(b)));
                }
            }

            case RECOMMENDATION -> {
                ctx.append("DONNÉES COMPLÈTES POUR RECOMMANDATION :\n");
                ctx.append("Règle métier : SIMPLE = environnement calme idéal pour étudier,\n");
                ctx.append("               DOUBLE = modéré, TRIPLE = animé/social.\n\n");
                blocs.forEach(b -> ctx.append(buildBlocDetail(b)));
            }

            default -> {
                ctx.append("RÉSUMÉ GÉNÉRAL DE LA PLATEFORME PGHU :\n\n");
                ctx.append(String.format("Nombre total de blocs : %d\n\n", blocs.size()));
                blocs.forEach(b -> ctx.append(String.format(
                        "- Bloc %-12s : %d chambres "
                                + "(Simples: %d, Doubles: %d, Triples: %d)\n",
                        b.getNomBloc(),
                        b.getChambres().size(),
                        countType(b, "SIMPLE"),
                        countType(b, "DOUBLE"),
                        countType(b, "TRIPLE")
                )));
            }
        }

        return ctx.toString();
    }

    // ════════════════════════════════════════════════════════════
    //  STEP 3 — PROMPT ENGINEERING
    // ════════════════════════════════════════════════════════════

    private String buildPrompt(String userMessage, String dbContext) {
        return "Tu es l'assistant officiel de la Plateforme de Gestion "
                + "d'Hébergement Universitaire (PGHU).\n\n"
                + "RÈGLES STRICTES — OBLIGATOIRES :\n"
                + "1. Tu réponds TOUJOURS en français.\n"
                + "2. Tu utilises UNIQUEMENT les informations entre [DONNÉES] et [/DONNÉES].\n"
                + "3. Tu ne mentionnes JAMAIS un bloc, foyer ou chambre absent des [DONNÉES].\n"
                + "4. Si les [DONNÉES] sont vides ou insuffisantes, réponds UNIQUEMENT : "
                + "\"Je n'ai pas cette information dans la base de données.\"\n"
                + "5. Tu n'inventes RIEN. Zéro hallucination.\n"
                + "6. Ta réponse est courte : 3 à 5 phrases maximum.\n"
                + "7. Sois chaleureux et professionnel.\n\n"
                + "[DONNÉES]\n"
                + dbContext
                + "[/DONNÉES]\n\n"
                + "Question : " + userMessage + "\n\n"
                + "Réponse (basée UNIQUEMENT sur [DONNÉES]) :";
    }

    // ════════════════════════════════════════════════════════════
    //  STEP 4 — OLLAMA CALL
    // ════════════════════════════════════════════════════════════

    private String callOllama(String prompt) throws Exception {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("model",  OLLAMA_MODEL);
        body.put("prompt", prompt);
        body.put("stream", false);
        body.put("options", Map.of(
                "temperature", 0.1,   // LOW = colle aux faits, pas d'invention
                "num_predict", 300,
                "top_p",       0.8
        ));

        String requestBody = objectMapper.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(OLLAMA_URL))
                .timeout(Duration.ofSeconds(90))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Ollama error " + response.statusCode()
                    + " : " + response.body());
        }

        JsonNode json   = objectMapper.readTree(response.body());
        String   result = json.path("response").asText("").trim();

        if (result.isEmpty()) {
            return "Je n'ai pas pu générer une réponse. Veuillez réessayer.";
        }
        return result;
    }

    // ════════════════════════════════════════════════════════════
    //  HELPERS
    // ════════════════════════════════════════════════════════════

    private int countType(Bloc bloc, String typeName) {
        if (bloc.getChambres() == null) return 0;
        return (int) bloc.getChambres().stream()
                .filter(c -> c.getType() != null
                        && c.getType().name().equalsIgnoreCase(typeName))
                .count();
    }

    private String buildBlocDetail(Bloc bloc) {
        return String.format(
                "Bloc %-12s | Simples: %d | Doubles: %d | Triples: %d | Total: %d\n",
                bloc.getNomBloc(),
                countType(bloc, "SIMPLE"),
                countType(bloc, "DOUBLE"),
                countType(bloc, "TRIPLE"),
                bloc.getChambres() == null ? 0 : bloc.getChambres().size()
        );
    }

    private boolean containsAny(String input, String... keywords) {
        for (String kw : keywords) {
            if (input.contains(kw)) return true;
        }
        return false;
    }
}