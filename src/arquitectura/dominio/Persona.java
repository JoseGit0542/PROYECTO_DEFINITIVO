package arquitectura.dominio;

public class Persona {

    //atributos
    private int id;
    private String nombre;
    private String apellido;

    //getters y setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    //metodo toString
    public String toString() {
        return "ID: " + this.getId() + ", nombre: " + this.getNombre() + ", apellido: " + this.getApellido();
    }

    //clase para generar los ides de las personas
    public static int generarId() {

        return 0;
    }

}
