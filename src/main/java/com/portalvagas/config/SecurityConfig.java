package com.portalvagas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Regras de acesso do site:
 * - Buscar vagas, publicar vaga e cadastrar currículo continuam publicos,
 *   sem necessidade de login (qualquer morador ou empresa pode usar).
 * - O painel administrativo (listar todas as vagas, editar, ativar/desativar,
 *   ver curriculos cadastrados) exige login, configurado em application.properties.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // necessario para o console do H2
            .authorizeHttpRequests(auth -> auth
                // paginas e recursos publicos
                .requestMatchers(HttpMethod.GET, "/", "/index.html", "/publicar-vaga.html",
                        "/cadastro-curriculo.html", "/favicon.ico").permitAll()
                .requestMatchers("/css/**", "/js/**").permitAll()

                // API publica: buscar vagas, ver detalhe de uma vaga, publicar vaga e cadastrar curriculo
                .requestMatchers(HttpMethod.GET, "/api/vagas").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/vagas/{id:[0-9]+}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/vagas").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/curriculos").permitAll()

                // console do H2, uso apenas em desenvolvimento local
                .requestMatchers("/h2-console/**").permitAll()

                // tudo o mais (painel.html, editar-vaga.html, listar todas as vagas,
                // editar/ativar/desativar vaga, listar/remover curriculos) exige login
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
