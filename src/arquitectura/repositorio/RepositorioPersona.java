package arquitectura.repositorio;

import arquitectura.dominio.Persona;

import java.io.*;
import java.util.*;

import arquitectura.dominio.Persona;

import java.io.*;
import java.util.*;

public class RepositorioPersona {

    private static int contadorId = 0;
    private final Map<Integer, Persona> lista = new HashMap<>();

    public Map<Integer, Persona> getLista() {
        return lista;
    }

    public static int generarId() {
        return ++contadorId;
    }

    public void save(Persona p) {
        if (p.getId() <= 0) {
            int id = generarId();
            p.setId(id); // asegúrate de que Persona tiene setId(int)
        } else if (p.getId() > contadorId) {
            contadorId = p.getId();
        }
        lista.put(p.getId(), p);
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

    // -------------------------
    // Gestión de ficheros (CSV)
    // -------------------------

    // Formato: id;nombre (sin encabezado)
    public void cargarDesdeArchivo(File archivo) {
        if (!archivo.exists()) {
            System.out.println("Archivo de personas no encontrado. Se creará uno nuevo al guardar.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            lista.clear();
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(";");
                if (datos.length < 2) continue;

                int id = Integer.parseInt(datos[0].trim());
                String nombre = datos[1].trim();

                Persona p = new Persona(nombre);
                p.setId(id); // aseguramos el id cargado desde CSV
                lista.put(id, p);

                if (id > contadorId) contadorId = id;
            }
        } catch (IOException e) {
            System.err.println("Error al leer personas.csv: " + e.getMessage());
        }
    }

    public void guardarEnArchivo(File archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Persona p : lista.values()) {
                bw.write(p.getId() + ";" + p.getNombre());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error escribiendo personas.csv: " + e.getMessage());
        }
    }

    public void deleteById(int id) {
        lista.remove(id);
    }
}

