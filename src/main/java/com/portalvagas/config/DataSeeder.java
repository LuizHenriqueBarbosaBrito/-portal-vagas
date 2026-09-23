package com.portalvagas.config;

import com.portalvagas.model.Vaga;
import com.portalvagas.repository.VagaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Carrega vagas de exemplo apenas na primeira execucao (quando o banco
 * ainda esta vazio). Usa o repositorio JPA para salvar, entao o Hibernate
 * cuida da geracao dos IDs automaticamente - nao ha risco de conflito
 * com vagas criadas depois pelos usuarios.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final VagaRepository vagaRepository;

    public DataSeeder(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    @Override
    public void run(String... args) {
        if (vagaRepository.count() > 0) {
            return;
        }

        List<Vaga> exemplos = List.of(
                criarVaga("Pedreiro", "Construtora Santa Fe",
                        "Contratacao para obra residencial no centro. Experiencia com alvenaria e reboco.",
                        "Construção Civil", "Centro", "Temporário", "A combinar", "(75) 99999-0001"),
                criarVaga("Auxiliar de serviços gerais", "Associação de Moradores do Alto Bonito",
                        "Limpeza e organização de espaços comunitários. Horário flexível.",
                        "Serviços Gerais", "Alto Bonito", "Diarista", "R$ 80 por dia", "(75) 99999-0002"),
                criarVaga("Vendedor(a)", "Mercadinho Bom Preço",
                        "Atendimento ao cliente e reposição de mercadorias.",
                        "Comércio", "Centro", "CLT", "R$ 1.412,00", "(75) 99999-0003"),
                criarVaga("Trabalhador rural", "Cooperativa Agrícola do Sertão",
                        "Plantio e colheita em propriedade familiar. Experiência com agricultura de sequeiro.",
                        "Agricultura", "Zona Rural", "Diarista", "R$ 70 por dia", "(75) 99999-0004"),
                criarVaga("Costureira", "Ateliê Comunitário Mãos que Criam",
                        "Confecção e reparo de peças de vestuário para o comércio local.",
                        "Artesanato e Costura", "Bairro Novo", "Autônomo", "Por produção", "(75) 99999-0005")
        );

        vagaRepository.saveAll(exemplos);
    }

    private Vaga criarVaga(String titulo, String empresa, String descricao, String categoria,
                            String localizacao, String tipoContrato, String salario, String contato) {
        Vaga vaga = new Vaga();
        vaga.setTitulo(titulo);
        vaga.setEmpresa(empresa);
        vaga.setDescricao(descricao);
        vaga.setCategoria(categoria);
        vaga.setLocalizacao(localizacao);
        vaga.setTipoContrato(tipoContrato);
        vaga.setSalario(salario);
        vaga.setContato(contato);
        vaga.setDataPublicacao(LocalDate.now());
        vaga.setAtiva(true);
        return vaga;
    }
}
