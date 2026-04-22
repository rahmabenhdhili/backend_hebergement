package com.university.Hebergement.controller;

import com.university.Hebergement.dto.ChatRequestDTO;
import com.university.Hebergement.dto.ChatResponseDTO;
import com.university.Hebergement.service.ChatbotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatbot")
@CrossOrigin(origins = "*")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping
    public ResponseEntity<ChatResponseDTO> chat(@RequestBody ChatRequestDTO request) {
        try {
            String aiResponse = chatbotService.chat(request.getMessage());
            return ResponseEntity.ok(new ChatResponseDTO(aiResponse));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ChatResponseDTO("Erreur IA : " + e.getMessage()));
        }
    }
}