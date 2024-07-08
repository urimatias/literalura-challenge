package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autores,Long> {
    @Query("SELECT a FROM Autores a WHERE a.anoMuerte > :opcionFecha")
    List<Autores> autoresVivosEnLaFecha(int opcionFecha);
}
