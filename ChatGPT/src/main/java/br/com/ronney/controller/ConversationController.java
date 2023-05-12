package br.com.ronney.controller;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ronney.model.Conversation;
import br.com.ronney.service.ConversationService;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;
    
    @GetMapping("/all")
    public List<Conversation> getAllConversations() {
        return conversationService.getAllConversations();
    }
    
    @GetMapping("/{id}")
    public Conversation getConversationById(@PathVariable Long id) {
        return conversationService.getConversationById(id);
    }
 
    @PostMapping("/save/{id}")
    public Conversation createConversation(@RequestBody Conversation conversation) {
        return conversationService.saveConversation(conversation);
    }
    
    @PutMapping("/update/{id}")
    public Conversation updateConversation(@PathVariable Long id, @RequestBody Conversation conversation) {
        Conversation existingConversation = conversationService.getConversationById(id);
        BeanUtils.copyProperties(conversation, existingConversation, "idconversation");
        return conversationService.saveConversation(existingConversation);
    }
 
    @DeleteMapping("/delete/{id}")
    public void deleteConversation(@PathVariable Long id) {
        conversationService.deleteConversationById(id);
    }
}
