package br.com.ronney.entity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ronney.model.Conversation;
import br.com.ronney.repository.ConversationRepository;

@Service
@Transactional
public class ConversationService {
	
    @Autowired
    private ConversationRepository conversationRepository;
    
    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }
    
    
    public Conversation getConversationById(Long id) {
        return conversationRepository.findByIdconversation(id);
    }

    public Conversation saveConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }
 
    public void deleteConversationById(Long id) {
        conversationRepository.deleteByIdconversation(id);
    }
}
