package com.portalvagas.controller;

import com.portalvagas.exception.RecursoNaoEncontradoException;
import com.portalvagas.model.Curriculo;
import com.portalvagas.repository.CurriculoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/curriculos")
public class CurriculoController {

    private final CurriculoRepository curriculoRepository;

    public CurriculoController(CurriculoRepository curriculoRepository) {
        this.curriculoRepository = curriculoRepository;
    }

    @GetMapping
    public List<Curriculo> listar() {
        return curriculoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Curriculo buscarPorId(@PathVariable Long id) {
        return curriculoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curriculo nao encontrado: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Curriculo cadastrar(@Valid @RequestBody Curriculo curriculo) {
        curriculo.setId(null);
        return curriculoRepository.save(curriculo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        if (!curriculoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Curriculo nao encontrado: " + id);
        }
        curriculoRepository.deleteById(id);
    }
}
