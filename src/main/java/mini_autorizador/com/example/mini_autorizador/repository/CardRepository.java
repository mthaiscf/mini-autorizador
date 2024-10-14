package mini_autorizador.com.example.mini_autorizador.repository;

import mini_autorizador.com.example.mini_autorizador.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    
    boolean existsByNumeroCartao(String numeroCartao);

    Optional<Card> findByNumeroCartao(String numeroCartao);
}