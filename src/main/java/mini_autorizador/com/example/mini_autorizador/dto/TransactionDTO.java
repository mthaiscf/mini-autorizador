package mini_autorizador.com.example.mini_autorizador.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransactionDTO {
    
    @NotBlank(message = "O número do cartão é obrigatório")
    private String numeroCartao;

    @NotBlank(message = "A senha do cartão é obrigatória")
    private String senhaCartao;

    @NotNull(message = "O valor da transação é obrigatório")
    @Min(value = 0, message = "O valor da transação deve ser maior que zero")
    private Double valor;

    // Construtores
    public TransactionDTO() {}

    public TransactionDTO(String numeroCartao, String senhaCartao, Double valor) {
        this.numeroCartao = numeroCartao;
        this.senhaCartao = senhaCartao;
        this.valor = valor;
    }

    // Getters e Setters
    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getSenhaCartao() {
        return senhaCartao;
    }

    public void setSenhaCartao(String senhaCartao) {
        this.senhaCartao = senhaCartao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
