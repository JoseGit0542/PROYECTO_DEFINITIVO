package arquitectura.dominio;

public class Persona {

    private int id;
    private String nombre;

    // Constructor para crear nuevas personas (sin ID)
    public Persona(String nombre) {
        this.nombre = nombre;
    }

    // Constructor para cargar desde BD
    public Persona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return "ID: " + id + ", nombre: " + nombre;
    }
}
