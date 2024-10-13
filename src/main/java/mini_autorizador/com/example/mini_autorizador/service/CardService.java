package mini_autorizador.com.example.mini_autorizador.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mini_autorizador.com.example.mini_autorizador.domain.Card;
import mini_autorizador.com.example.mini_autorizador.repository.CardRepository;

import mini_autorizador.com.example.mini_autorizador.exception.InsufficientBalanceException;
import mini_autorizador.com.example.mini_autorizador.exception.InvalidPasswordException;
import mini_autorizador.com.example.mini_autorizador.exception.CardNotFoundException;
import mini_autorizador.com.example.mini_autorizador.exception.CardAlreadyExistsException;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public Card createCard(String numeroCartao, String senha) {
        if (cardRepository.existsByNumeroCartao(numeroCartao)) {
            throw new CardAlreadyExistsException("Cartão já existe");
        }
        Card card = new Card(numeroCartao, senha, BigDecimal.valueOf(500));
        return cardRepository.save(card);
    }

    public BigDecimal getCardBalance(String numeroCartao) {
        Card card = cardRepository.findByNumeroCartao(numeroCartao)
            .orElseThrow(() -> new CardNotFoundException("Cartão não encontrado"));
        return card.getSaldo();
    }

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
