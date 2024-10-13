package mini_autorizador.com.example.mini_autorizador.dto;

import javax.validation.constraints.NotBlank;
import mini_autorizador.com.example.mini_autorizador.domain.Card;

public class CardDTO {

    @NotBlank(message = "O número do cartão é obrigatório")
    private String numeroCartao;

    @NotBlank(message = "A senha do cartão é obrigatória")
    private String senha;

    // Construtores
    public CardDTO() {}

    public CardDTO(Card card) {
        this.numeroCartao = card.getNumeroCartao();
        this.senha = card.getSenha();
    }

    public CardDTO(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }

    // Getters e Setters
    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
}
