package mini_autorizador.com.example.mini_autorizador.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mini_autorizador.com.example.mini_autorizador.service.CardService;
import mini_autorizador.com.example.mini_autorizador.dto.TransactionDTO;
import mini_autorizador.com.example.mini_autorizador.exception.InsufficientBalanceException;
import mini_autorizador.com.example.mini_autorizador.exception.InvalidPasswordException;
import mini_autorizador.com.example.mini_autorizador.exception.CardNotFoundException;


@RestController
@RequestMapping("/transacoes")
public class TransactionController {
    
        @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<String> authorizeTransaction(@RequestBody TransactionDTO transactionDTO) {
        try {

            cardService.authorizeTransaction(transactionDTO.getNumeroCartao(),
                                             transactionDTO.getSenhaCartao(),
                                             BigDecimal.valueOf(transactionDTO.getValor()));
                                             
            return new ResponseEntity<>("OK", HttpStatus.CREATED);
        } catch (InsufficientBalanceException e) {
            return new ResponseEntity<>("SALDO_INSUFICIENTE", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>("SENHA_INVALIDA", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (CardNotFoundException e) {
            return new ResponseEntity<>("CARTAO_INEXISTENTE", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
