package arquitectura.dominio;

import arquitectura.repositorio.RepositorioPersona;

public class Persona {

    //atributos
    private int id;
    private String nombre;

    //constructor
    public Persona(String nombre) {
        this.id = RepositorioPersona.generarId();
        this.nombre = nombre;
    }

    //getters y setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }


    //metodo toString
    public String toString() {
        return "ID: " + this.getId() + ", nombre: " + this.getNombre();
    }



}

