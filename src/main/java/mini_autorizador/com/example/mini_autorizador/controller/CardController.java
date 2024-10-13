package mini_autorizador.com.example.mini_autorizador.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mini_autorizador.com.example.mini_autorizador.domain.Card;
import mini_autorizador.com.example.mini_autorizador.dto.CardDTO;
import mini_autorizador.com.example.mini_autorizador.service.CardService;

@RestController
@RequestMapping("/cartoes")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO cardDTO) {
        Card card = cardService.createCard(cardDTO.getNumeroCartao(), cardDTO.getSenha());
        return new ResponseEntity<>(new CardDTO(card), HttpStatus.CREATED);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> getSaldo(@PathVariable String numeroCartao) {
        BigDecimal saldo = cardService.getCardBalance(numeroCartao);
        return new ResponseEntity<>(saldo, HttpStatus.OK);
    }
    
}
