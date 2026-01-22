package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.io.*;
import java.util.*;

public class RepositorioVideojuego {

    private static final Map<Integer, Videojuego> lista = new HashMap<>();
    private File archivo;

    public RepositorioVideojuego() {}

    public RepositorioVideojuego(File archivo) {
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

    public long count() {
        return lista.size();
    }

    public void deleteById(Integer id) {
        lista.remove(id);
        guardar();
    }

    public void deleteAll() {
        lista.clear();
        guardar();
    }

    public boolean existsById(Integer id) {
        if (id == null) throw new IllegalArgumentException("El id no debe ser nulo");
        return lista.containsKey(id);
    }

    public Videojuego findById(Integer id) {
        if (id == null) throw new IllegalArgumentException("El id no debe ser nulo");
        return lista.get(id);
    }

    public List<Videojuego> findAll() {
        return new ArrayList<>(lista.values());
    }

    public <S extends Videojuego> S save(S entity) {
        if (entity == null) throw new IllegalArgumentException("entity no debe ser nulo");
        if (entity.getId() <= 0) entity.setId(generarId());
        lista.put(entity.getId(), entity);
        guardar();
        return entity;
    }

    public static int generarId() {
        return lista.isEmpty() ? 1 : Collections.max(lista.keySet()) + 1;
    }

    public List<Videojuego> listarPorPersona(int idPersona) {
        List<Videojuego> propios = new ArrayList<>();
        for (Videojuego v : lista.values()) {
            if (v.getIdPersona() == idPersona) propios.add(v);
        }
        return propios;
    }

    public long contarPorPersona(int idPersona) {
        return listarPorPersona(idPersona).size();
    }

    public void eliminarBibliotecaDePersona(int idPersona) {
        lista.values().removeIf(v -> v.getIdPersona() == idPersona);
        guardar();
    }

    public void guardarEnArchivo() {
        if (archivo == null) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Videojuego v : lista.values()) {
                bw.write(v.getId() + ";" +
                        v.getTitulo() + ";" +
                        v.getCategoria() + ";" +
                        v.getIdPlataforma() + ";" +
                        v.getAño() + ";" +
                        v.getIdPersona());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error escribiendo archivo " + archivo.getName() + ": " + e.getMessage());
        }
    }

    public void cargarDesdeArchivo() {
        if (archivo == null || !archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            lista.clear();
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(";");
                if (datos.length < 6) continue;

                int id = Integer.parseInt(datos[0].trim());
                String titulo = datos[1].trim();
                String categoria = datos[2].trim();
                int idPlataforma = Integer.parseInt(datos[3].trim());
                int anyo = Integer.parseInt(datos[4].trim());
                int idPersona = Integer.parseInt(datos[5].trim());

                Videojuego v = new Videojuego(id, titulo, categoria, anyo, idPersona, idPlataforma);
                lista.put(id, v);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo " + archivo.getName() + ": " + e.getMessage());
        }
    }
}
