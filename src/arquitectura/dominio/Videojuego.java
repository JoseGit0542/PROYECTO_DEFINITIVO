package arquitectura.dominio;

import arquitectura.repositorio.RepositorioVideojuego;

public class Videojuego {

    //le damos los atributos a videojuego
    private int id;
    private String titulo;
    private String categoria;
    private String plataforma;
    private int año;

    //Setters
    public  void setTitulo(String titulo) {
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

    //getters
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

    //Constructor con todos los parametros
    public Videojuego(String titulo, String categoria, String plataforma, int año) {
        this.id = RepositorioVideojuego.generarId();
        this.titulo = titulo;
        this.categoria = categoria;
        this.plataforma = plataforma;
        this.año = año;
    }

    //constructor con id y titulo
    public Videojuego(String titulo) {
        this.id = RepositorioVideojuego.generarId();
        this.titulo = titulo;
        this.categoria = null;
        this.plataforma = null;
        this.año = 0;
    }

    //metodo toString para mostrar los videojuegos
    public String toString() {
    return "Id: " + this.id + ", título: " + this.titulo + ", categoria: " + this.categoria + ", plataforma: " + this.plataforma + ", año: " + this.año;
    }








}
