package arquitectura.dominio;

import arquitectura.repositorio.RepositorioVideojuego;

public class Videojuego {

    private int id;
    private String titulo;
    private String categoria;
    private String plataforma;
    private int año;
    private int idPersona;

    public Videojuego(String titulo, String categoria, String plataforma, int año, int idPersona) {
        this.id = RepositorioVideojuego.generarId();
        this.titulo = titulo;
        this.categoria = categoria;
        this.plataforma = plataforma;
        this.año = año;
        this.idPersona = idPersona;
    }

    public Videojuego(int id, String titulo, String categoria, String plataforma, int año, int idPersona) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.plataforma = plataforma;
        this.año = año;
        this.idPersona = idPersona;
    }

    public Videojuego(String titulo) {
        this.id = RepositorioVideojuego.generarId();
        this.titulo = titulo;
        this.categoria = null;
        this.plataforma = null;
        this.año = 0;
        this.idPersona = 0;
    }

    public int getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getCategoria() {
        return categoria;
    }
    public String getPlataforma() {
        return plataforma;
    }
    public int getAño() {
        return año;
    }
    public int getIdPersona() {
        return idPersona;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
    public void setAño(int año) {
        this.año = año;
    }
    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + ";" + titulo + ";" + categoria + ";" + plataforma + ";" + año + ";" + idPersona;
    }
}
