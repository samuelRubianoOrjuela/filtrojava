package com.filtrojava.peliculas.domain.models;


public class peliculas {
    private int id;
    private String codInterno;
    private String nombre;
    private String duracion;
    private String sinopsis;
    public peliculas(int id, String codInterno, String nombre, String duracion, String sinopsis) {
        this.id = id;
        this.codInterno = codInterno;
        this.nombre = nombre;
        this.duracion = duracion;
        this.sinopsis = sinopsis;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCodInterno() {
        return codInterno;
    }
    public void setCodInterno(String codInterno) {
        this.codInterno = codInterno;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDuracion() {
        return duracion;
    }
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    public String getSinopsis() {
        return sinopsis;
    }
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
    @Override
    public String toString() {
        return "pelicula [id=" + id + ", codInterno=" + codInterno + ", nombre=" + nombre + ", duracion=" + duracion
                + ", sinopsis=" + sinopsis + "]";
    }

    
}
