package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Short anoNacimiento;
    private Short anoMuerte;
    @Column(unique = true)
    private String nombreAutor;
    @OneToMany(mappedBy = "autorDatos", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Results> results;

    public Autores(){}

    public Autores(DatosResults datosResults) {
        this.anoNacimiento = datosResults.resultados().get(0).autores().get(0).anoNacimiento();
        this.anoMuerte = datosResults.resultados().get(0).autores().get(0).anoMuerte();
        this.nombreAutor = datosResults.resultados().get(0).autores().get(0).nombreAutor();
    }

    public Short getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Short anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Short getAnoMuerte() {
        return anoMuerte;
    }

    public void setAnoMuerte(Short anoMuerte) {
        this.anoMuerte = anoMuerte;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        results.forEach((l) -> {
            l.setAutorDatos(this);
        });
        this.results = results;
    }
}
