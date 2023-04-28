package br.com.ronney.entity.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idconversation;
    
    @Column(name = "ask", nullable = false, length = 500)
    private String ask;
 
    @Column(name = "answer", nullable = false, length = 500)
    private String answer;
 
    @Column(name = "momento", nullable = false)
    private LocalDateTime momento;
}
