package arquitectura.repositorio;

import arquitectura.dominio.Persona;

import java.io.*;
import java.util.*;

public class RepositorioPersona {

    private static int contadorId = 0;
    private final Map<Integer, Persona> lista = new HashMap<>();
    private File archivo; // archivo asociado al repositorio

    public RepositorioPersona() {}

    public RepositorioPersona(File archivo) {
        this.archivo = archivo;
        cargarDesdeArchivo();
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
        cargarDesdeArchivo();
    }

    private void guardar() {
        if (archivo != null) guardarEnArchivo();
    }

    public Map<Integer, Persona> getLista() {
        return lista;
    }

    public static int generarId() {
        return ++contadorId;
    }

    public void save(Persona p) {
        if (p.getId() <= 0) p.setId(generarId());
        else if (p.getId() > contadorId) contadorId = p.getId();
        lista.put(p.getId(), p);
        guardar(); // guardado automático
    }

    public Persona findById(int id) {
        return lista.get(id);
    }

    public boolean existsById(int id) {
        return lista.containsKey(id);
    }

    public List<Persona> findAll() {
        return new ArrayList<>(lista.values());
    }

    public void deleteAll() {
        lista.clear();
        guardar();
    }

    public void deleteById(int id) {
        lista.remove(id);
        guardar();
    }

    // -------------------------
    // Gestión de ficheros (CSV)
    // -------------------------
    public void cargarDesdeArchivo() {
        if (archivo == null || !archivo.exists()) {
            System.out.println("Archivo de personas no encontrado. Se creará uno nuevo al guardar.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            lista.clear();
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(";");
                if (datos.length < 2) continue;

                int id = Integer.parseInt(datos[0].trim());
                String nombre = datos[1].trim();

                Persona p = new Persona(nombre);
                p.setId(id);
                lista.put(id, p);

                if (id > contadorId) contadorId = id;
            }
        } catch (IOException e) {
            System.err.println("Error al leer personas.csv: " + e.getMessage());
        }
    }

    public void guardarEnArchivo() {
        if (archivo == null) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Persona p : lista.values()) {
                bw.write(p.getId() + ";" + p.getNombre());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error escribiendo personas.csv: " + e.getMessage());
        }
    }

    public int count() {
        return lista.size();

    }
    // Login mejorado: crear o iniciar sesión con cualquier usuario
    public static Persona login( RepositorioPersona rp) {
        Scanner reader = new Scanner(System.in);
        Persona actual = null;

        while (actual == null) {
            System.out.println("\n---- PANEL DE INICIO DE SESIÓN ----");
            System.out.println("1. Crear nuevo usuario");
            System.out.println("2. Iniciar sesión con usuario existente");
            System.out.print("Elige una opción: ");
            System.out.println("");

            try {
                int opcion = reader.nextInt();
                reader.nextLine();

                switch (opcion) {
                    case 1 -> {
                        System.out.print("Introduce tu nombre: ");
                        String nombre = reader.nextLine().trim();
                        if (!nombre.isEmpty()) {
                            Persona nuevo = new Persona(nombre);
                            rp.save(nuevo); // guardado automático
                            actual = nuevo; // inicia sesión con el usuario creado
                            System.out.println("Usuario creado con ID: " + nuevo.getId());
                        } else {
                            System.out.println("Nombre inválido.");
                        }
                    }
                    case 2 -> {
                        if (rp.count() == 0) {
                            System.out.println("No hay usuarios registrados. Crea uno primero.");
                            break;
                        }

                        System.out.println("Usuarios registrados:");
                        for (Persona p : rp.findAll()) {
                            System.out.println(p.getId() + " - " + p.getNombre());
                        }

                        System.out.println("");
                        System.out.print("Introduce tu ID: ");
                        int id = reader.nextInt();
                        reader.nextLine();

                        if (rp.existsById(id)) {
                            actual = rp.findById(id);
                            System.out.println("Has iniciado sesión como: " + actual.getNombre() + "\n");
                        } else {
                            System.out.println("ID no encontrado. Intenta de nuevo.");
                        }
                    }
                    default -> System.out.println("Opción incorrecta.");
                }

            } catch (InputMismatchException ime) {
                System.out.println("Introduce un número válido.");
                reader.nextLine();
            }
        }

        return actual;
    }
}
