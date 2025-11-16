package arquitectura.repositorio;

import arquitectura.dominio.Persona;

import java.io.*;
import java.util.*;

public class RepositorioPersona {

    private static int contadorId = 0;
    private Map<Integer, Persona> lista = new HashMap<>();

    public Map<Integer, Persona> getLista() {
        return lista;
    }

    // Generador de IDs automáticos
    public static int generarId() {
        return ++contadorId;
    }

    public void save(Persona p) {
        lista.put(p.getId(), p);
        if (p.getId() > contadorId) contadorId = p.getId();
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
    }

    public int count() {
        return lista.size();
    }



    // Cargar desde CSV (formato: id;nombre) sin encabezado
    public void cargarDesdeArchivo(File archivo) {
        if (!archivo.exists()) {
            System.out.println("Archivo de personas no encontrado. Se creará uno nuevo al guardar.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(";");
                if (datos.length < 2) continue;
                try {
                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();
                    Persona p = new Persona(nombre);
                    lista.put(id, p);
                    if (id > contadorId) contadorId = id;
                } catch (NumberFormatException e) {
                    System.err.println("Línea inválida en personas.csv: " + linea);
                }
            }
            System.out.println("Personas cargadas: " + lista.size());
        } catch (IOException e) {
            System.err.println("Error al leer personas.csv: " + e.getMessage());
        }
    }

    // Guardar en CSV (sin encabezado)
    public void guardarEnArchivo(File archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Persona p : lista.values()) {
                bw.write(p.getId() + ";" + p.getNombre());
                bw.newLine();
            }
            System.out.println("Personas guardadas en " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error escribiendo personas.csv: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        if (lista.containsKey(id)) {
            lista.remove(id);
            System.out.println("Persona con ID " + id + " eliminada.");
        } else {
            System.out.println("No existe ninguna persona con ese ID.");
        }
    }


}
