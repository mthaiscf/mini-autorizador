package mini_autorizador.com.example.mini_autorizador.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mini_autorizador.com.example.mini_autorizador.domain.Card;
import mini_autorizador.com.example.mini_autorizador.repository.CardRepository;

import mini_autorizador.com.example.mini_autorizador.exception.InsufficientBalanceException;
import mini_autorizador.com.example.mini_autorizador.exception.InvalidPasswordException;
import mini_autorizador.com.example.mini_autorizador.exception.CardNotFoundException;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    @Transactional 
    public Card createCard(String numeroCartao, String senha) {
        if (cardRepository.existsByNumeroCartao(numeroCartao)) {
            return cardRepository.findByNumeroCartao(numeroCartao).get();
        }
        Card card = new Card(numeroCartao, senha, BigDecimal.valueOf(500));
        return cardRepository.save(card);
    }

    @Transactional 
    public BigDecimal getCardBalance(String numeroCartao) {
        Card card = cardRepository.findByNumeroCartao(numeroCartao)
            .orElseThrow(() -> new CardNotFoundException("Cartão não encontrado"));
        return card.getSaldo();
    }

    @Transactional
    public void authorizeTransaction(String numeroCartao, String senha, BigDecimal valor) {
        Card card = cardRepository.findByNumeroCartao(numeroCartao)
            .orElseThrow(() -> new CardNotFoundException("Cartão não encontrado"));

        if (!card.getSenha().equals(senha)) {
            throw new InvalidPasswordException("Senha inválida");
        }
        if (card.getSaldo().compareTo(valor) < 0) {
            throw new InsufficientBalanceException("Saldo insuficiente");
        }

        card.debitarSaldo(valor);
        cardRepository.save(card);
    }
}
