package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutores(
        @JsonAlias("birth_year") Short anoNacimiento,
        @JsonAlias("death_year") Short anoMuerte,
        @JsonAlias("name") String nombreAutor

        ) {
}
