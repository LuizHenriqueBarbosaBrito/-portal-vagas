package com.portalvagas.controller;

import com.portalvagas.exception.RecursoNaoEncontradoException;
import com.portalvagas.model.Vaga;
import com.portalvagas.repository.VagaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    private final VagaRepository vagaRepository;

    public VagaController(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }

    // GET /api/vagas?categoria=&localizacao=&busca=
    @GetMapping
    public List<Vaga> listar(@RequestParam(required = false) String categoria,
                              @RequestParam(required = false) String localizacao,
                              @RequestParam(required = false) String busca) {
        String cat = (categoria == null || categoria.isBlank()) ? null : categoria;
        String loc = (localizacao == null || localizacao.isBlank()) ? null : localizacao;
        String txt = (busca == null || busca.isBlank()) ? null : busca;
        return vagaRepository.buscarVagas(cat, loc, txt);
    }

    @GetMapping("/{id}")
    public Vaga buscarPorId(@PathVariable Long id) {
        return vagaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vaga nao encontrada: " + id));
    }

    // GET /api/vagas/todas - lista todas as vagas (ativas e inativas), para o painel administrativo
    @GetMapping("/todas")
    public List<Vaga> listarTodas() {
        return vagaRepository.findAllByOrderByDataPublicacaoDesc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vaga publicar(@Valid @RequestBody Vaga vaga) {
        vaga.setId(null);
        vaga.setAtiva(true);
        return vagaRepository.save(vaga);
    }

    @PutMapping("/{id}")
    public Vaga atualizar(@PathVariable Long id, @Valid @RequestBody Vaga dadosAtualizados) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vaga nao encontrada: " + id));

        vaga.setTitulo(dadosAtualizados.getTitulo());
        vaga.setEmpresa(dadosAtualizados.getEmpresa());
        vaga.setDescricao(dadosAtualizados.getDescricao());
        vaga.setCategoria(dadosAtualizados.getCategoria());
        vaga.setLocalizacao(dadosAtualizados.getLocalizacao());
        vaga.setTipoContrato(dadosAtualizados.getTipoContrato());
        vaga.setSalario(dadosAtualizados.getSalario());
        vaga.setContato(dadosAtualizados.getContato());
        vaga.setAtiva(dadosAtualizados.isAtiva());

        return vagaRepository.save(vaga);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void encerrar(@PathVariable Long id) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vaga nao encontrada: " + id));
        vaga.setAtiva(false);
        vagaRepository.save(vaga);
    }

    // PATCH /api/vagas/{id}/status?ativa=true|false - usado pelo painel administrativo
    @PatchMapping("/{id}/status")
    public Vaga alterarStatus(@PathVariable Long id, @RequestParam boolean ativa) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vaga nao encontrada: " + id));
        vaga.setAtiva(ativa);
        return vagaRepository.save(vaga);
    }
}
