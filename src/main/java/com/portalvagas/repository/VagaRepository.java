package com.portalvagas.repository;

import com.portalvagas.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VagaRepository extends JpaRepository<Vaga, Long> {

    @Query("SELECT v FROM Vaga v WHERE v.ativa = true " +
           "AND (:categoria IS NULL OR LOWER(v.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))) " +
           "AND (:localizacao IS NULL OR LOWER(v.localizacao) LIKE LOWER(CONCAT('%', :localizacao, '%'))) " +
           "AND (:busca IS NULL OR LOWER(v.titulo) LIKE LOWER(CONCAT('%', :busca, '%')) " +
           "     OR LOWER(v.descricao) LIKE LOWER(CONCAT('%', :busca, '%')) " +
           "     OR LOWER(v.empresa) LIKE LOWER(CONCAT('%', :busca, '%'))) " +
           "ORDER BY v.dataPublicacao DESC")
    List<Vaga> buscarVagas(@Param("categoria") String categoria,
                            @Param("localizacao") String localizacao,
                            @Param("busca") String busca);

    List<Vaga> findAllByOrderByDataPublicacaoDesc();
}
