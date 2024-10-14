package mini_autorizador.com.example.mini_autorizador.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Card {

    @Id
    private String numeroCartao;

    private String senha;

    private BigDecimal saldo;

    @Version
    private Long version; // campo de versão

    public Card() {}

    public Card(String numeroCartao, String senha, BigDecimal saldo) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = saldo;
    }

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

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void debitarSaldo(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }
}
