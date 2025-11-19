package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.io.*;
import java.util.*;


import arquitectura.dominio.Videojuego;

import java.io.*;
import java.util.*;

public class RepositorioVideojuego implements IRepositorioExtend<Videojuego, Integer> {

    private static final Map<Integer, Videojuego> lista = new HashMap<>();

    @Override
    public long count() {
        return lista.size();
    }

    @Override
    public void deleteById(Integer id) {
        lista.remove(id);
    }

    @Override
    public void deleteAll() {
        lista.clear();
    }

    @Override
    public boolean existsById(Integer id) {
        if (id == null) throw new IllegalArgumentException("El id no debe ser nulo");
        return lista.containsKey(id);
    }

    @Override
    public Videojuego findById(Integer id) {
        if (id == null) throw new IllegalArgumentException("El id no debe ser nulo");
        return lista.get(id);
    }

    @Override
    public Optional<Videojuego> findByIdOptional(Integer id) {
        if (id == null) throw new IllegalArgumentException("El id no debe ser nulo");
        return Optional.ofNullable(lista.get(id));
    }

    @Override
    public List<Videojuego> findAll() {
        return new ArrayList<>(lista.values());
    }

    @Override
    public <S extends Videojuego> S save(S entity) {
        if (entity == null) throw new IllegalArgumentException("entity no debe ser nulo");
        int id = generarId();
        entity.setId(id); // asegúrate de que Videojuego tiene setId(int)
        lista.put(id, entity);
        return entity;
    }

    public static int generarId() {
        return lista.isEmpty() ? 1 : Collections.max(lista.keySet()) + 1;
    }

    // -------------------------
    // Métodos de dominio útiles
    // -------------------------

    public List<Videojuego> listarPorPersona(int idPersona) {
        List<Videojuego> propios = new ArrayList<>();
        for (Videojuego v : lista.values()) {
            if (v.getIdPersona() == idPersona) {
                propios.add(v);
            }
        }
        return propios;
    }

    public long contarPorPersona(int idPersona) {
        long count = 0;
        for (Videojuego v : lista.values()) {
            if (v.getIdPersona() == idPersona) count++;
        }
        return count;
    }

    public Optional<Videojuego> obtenerSiPertenece(int id, int idPersona) {
        Videojuego v = lista.get(id);
        if (v != null && v.getIdPersona() == idPersona) return Optional.of(v);
        return Optional.empty();
    }

    public void eliminarBibliotecaDePersona(int idPersona) {
        // recopilamos IDs para evitar ConcurrentModification
        List<Integer> ids = new ArrayList<>();
        for (Map.Entry<Integer, Videojuego> e : lista.entrySet()) {
            if (e.getValue().getIdPersona() == idPersona) {
                ids.add(e.getKey());
            }
        }
        for (int id : ids) {
            lista.remove(id);
        }
    }

    // -------------------------
    // Gestión de ficheros (CSV)
    // -------------------------

    public void guardarEnArchivo(File archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Videojuego v : lista.values()) {
                bw.write(
                        v.getId() + ";" +
                                v.getTitulo() + ";" +
                                v.getCategoria() + ";" +
                                v.getPlataforma() + ";" +
                                v.getAño() + ";" +
                                v.getIdPersona()
                );
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error escribiendo archivo " + archivo.getName() + ": " + e.getMessage());
        }
    }

    public void cargarDesdeArchivo(File archivo) {
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            lista.clear();
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(";");
                if (datos.length < 6) continue;

                int id = Integer.parseInt(datos[0].trim());
                String titulo = datos[1].trim();
                String categoria = datos[2].trim();
                String plataforma = datos[3].trim();
                int anyo = Integer.parseInt(datos[4].trim());
                int idPersona = Integer.parseInt(datos[5].trim());

                Videojuego v = new Videojuego(id, titulo, categoria, plataforma, anyo, idPersona);
                lista.put(id, v);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo " + archivo.getName() + ": " + e.getMessage());
        }
    }
}
