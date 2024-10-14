package mini_autorizador.com.example.mini_autorizador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf // Configuração do CSRF
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/x/**"))
            )
            .authorizeHttpRequests(authz -> authz // Usando lambda para configuração de autorização
                .anyRequest().authenticated() // Exige autenticação para todas as requisições
            )
            .httpBasic(customizer -> customizer // Habilita autenticação básica
                .realmName("Realm") // Nome do realm (opcional)
            )
            .sessionManagement(session -> session // Gerenciamento de sessões
                .sessionFixation().migrateSession() // Protege contra ataques de fixation de sessão
                .maximumSessions(1) // Limita a uma sessão por usuário
            );
    
        return http.build(); // Retorna a configuração construída
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .inMemoryAuthentication()
            .withUser("username")
            .password(passwordEncoder().encode("password"))
            .roles("USER");
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usa BCrypt para codificação de senhas
    }
}