package arquitectura.dominio;

import arquitectura.repositorio.RepositorioVideojuego;

public class Videojuego {

    private int id;
    private String titulo;
    private String categoria;
    private int año;
    private int idPersona;
    private int idPlataforma;

    public Videojuego(String titulo, String categoria, int año, int idPersona, int idPlataforma) {
        this.id = RepositorioVideojuego.generarId();
        this.titulo = titulo;
        this.categoria = categoria;
        this.año = año;
        this.idPersona = idPersona;
        this.idPlataforma = idPlataforma;
    }

    public Videojuego(int id, String titulo, String categoria, int año, int idPersona, int idPlataforma) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.año = año;
        this.idPersona = idPersona;
        this.idPlataforma = idPlataforma;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getCategoria() { return categoria; }
    public int getAño() { return año; }
    public int getIdPersona() { return idPersona; }
    public int getIdPlataforma() { return idPlataforma; }

    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setAño(int año) { this.año = año; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }
    public void setIdPlataforma(int idPlataforma) { this.idPlataforma = idPlataforma; }

    @Override
    public String toString() {
        return id + ";" + titulo + ";" + categoria + ";" + año + ";" + idPersona + ";" + idPlataforma;
    }
}
