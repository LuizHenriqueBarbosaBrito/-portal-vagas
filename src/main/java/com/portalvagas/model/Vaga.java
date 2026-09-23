package com.portalvagas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "vagas")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O titulo da vaga e obrigatorio")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "O nome da empresa ou associacao e obrigatorio")
    @Column(nullable = false)
    private String empresa;

    @NotBlank(message = "A descricao da vaga e obrigatoria")
    @Column(nullable = false, length = 2000)
    private String descricao;

    @NotBlank(message = "A categoria e obrigatoria")
    @Column(nullable = false)
    private String categoria;

    @NotBlank(message = "A localizacao e obrigatoria")
    @Column(nullable = false)
    private String localizacao;

    @NotBlank(message = "O tipo de contrato e obrigatorio")
    @Column(nullable = false)
    private String tipoContrato;

    private String salario;

    @NotBlank(message = "Um contato para candidatura e obrigatorio")
    @Column(nullable = false)
    private String contato;

    @Column(nullable = false)
    private LocalDate dataPublicacao = LocalDate.now();

    @Column(nullable = false)
    private boolean ativa = true;

    public Vaga() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}
