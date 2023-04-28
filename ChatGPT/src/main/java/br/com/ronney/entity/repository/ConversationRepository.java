package br.com.ronney.entity.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.ronney.entity.model.Conversation;

@Repository
public interface ConversationRepository {
    List<Conversation> findAll();
    
    Conversation findByIdconversation(Long id);
    
    Conversation save(Conversation conversation);
    
    void deleteByIdconversation(Long id);
}
