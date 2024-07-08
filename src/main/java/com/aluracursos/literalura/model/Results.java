package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Libros")
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String autores;
    private List<String> idiomas;
    private Integer numeroDeDesacargas;
    @ManyToOne
    private Autores autorDatos;

    public Results(){}

    public Results(DatosResults datosResults){
        this.titulo = datosResults.resultados().get(0).titulo();
        this.autores = datosResults.resultados().get(0).autores().get(0).nombreAutor();
        this.idiomas = datosResults.resultados().get(0).idiomas();
        this.numeroDeDesacargas = datosResults.resultados().get(0).numeroDeDesacargas();
    }

    @Override
    public String toString() {
        return "Results{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", autores='" + autores + '\'' +
                ", idiomas=" + idiomas +
                ", numeroDeDesacargas=" + numeroDeDesacargas +
                ", autorDatos=" + autorDatos +
                '}';
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDeDesacargas() {
        return numeroDeDesacargas;
    }

    public void setNumeroDeDesacargas(Integer numeroDeDesacargas) {
        this.numeroDeDesacargas = numeroDeDesacargas;
    }

    public Autores getAutorDatos() {
        return autorDatos;
    }

    public void setAutorDatos(Autores autorDatos) {
        this.autorDatos = autorDatos;
    }

}
