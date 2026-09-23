package com.portalvagas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Plataforma web para centralizacao de vagas de emprego locais.
 * Conecta comercios, associacoes e moradores a oportunidades de trabalho
 * na comunidade, e permite que candidatos cadastrem seus curriculos.
 */
@SpringBootApplication
public class PlataformaVagasApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlataformaVagasApplication.class, args);
    }
}
