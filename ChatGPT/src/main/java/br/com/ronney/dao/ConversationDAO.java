package br.com.ronney.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import br.com.ronney.model.Conversation;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ConversationDAO {
	
	private SessionFactory sessionFactory;

    public ConversationDAO() {
        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }
	 
    public void insertConversation(Conversation conversation) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(conversation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public List<Conversation> getAllConversations() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Conversation", Conversation.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
