package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Results;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Results,Long> {

}
