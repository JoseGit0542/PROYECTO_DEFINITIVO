package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.io.*;
import java.util.*;

public class RepositorioVideojuego implements IRepositorioExtend<Videojuego,  Integer> {

    //atributos del repositorio
    private static Map<Integer, Videojuego> lista = new HashMap<>();

    /**
     * Devuelve el número de entidades.
     * @return  número de entidades
     */
    @Override
    public long count() {
        long contador = 0;
        for(int key: lista.keySet()) contador++;
        return contador;
    }

    /**
     * Borra la entidad con identificador id.
     * Si no se encuentra no realiza ninguna acción
     *
     * @param integer de la entidad. No debe ser nulo
     *
     * @throws IllegalArgumentException En caso de ser id nulo
     */
    @Override
    public void deleteById(Integer integer) {
        for(int key: lista.keySet()) {
            if (integer == key) {
                lista.remove(key);
                break;
            }
        }
    }

    /**
     * Borra todas las entidades del arquitectura.repositorio.
     */
    @Override
    public void deleteAll() {
        //quito todos los campos del hashmap
        lista.clear();
    }

    /**
     * Devuelve true si existe la entidad con identificador id.
     * @param id    Identificador de la entidad
     * @return      true si existe la entidad con el identificador id
     *
     * @throws IllegalArgumentException En caso de ser id nulo
     */
    @Override
    public boolean existsById(Integer id) {
        if (id == null) throw new IllegalArgumentException("El id no debe ser nulo");
        return lista.containsKey(id);
    }

    /**
     * Devuelve la entidad T con identificador id.
     * @param integer  Identificador de la entidad
     * @return      Entidad que tiene como identificador id.
     *
     * @throws IllegalArgumentException En caso de ser id nulo
     */
    @Override
    public Videojuego findById(Integer integer) {
        if (integer == null) throw new IllegalArgumentException("el id no debe de ser nulo");
        return lista.get(integer);
    }

    /**
     * Devuelve la entidad T con identificador id.
     *
     * @param integer    Identificador de la entidad
     * @return      Entidad que tiene como identificador id u Optional#empty() si no se encuentra
     *
     * @throws IllegalArgumentException En caso de ser id nulo
     */
    @Override
    public Optional<Videojuego> findByIdOptional(Integer integer) {
        if(integer == null) throw new IllegalArgumentException("el id no debe de ser nulo");
        return Optional.empty();
    }

    /**
     * Devuelve todas las instancias
     * @return  Todas las instancias
     */
    @Override
    public List<Videojuego> findAll() {
        return new ArrayList<>(lista.values());
    }

    /**
     * Guarda la entidad entity.
     * @param entity    entidad a guardar. No debe ser nulo
     * @return          entidad guardada
     *
     * @throws IllegalArgumentException En caso de ser entity nulo
     */
    @Override
    public <S extends Videojuego> S save(S entity) {
        if(entity == null) throw new IllegalArgumentException("entity no debe de ser nulo");
        int id = generarId();
        lista.put(id, entity);
        return entity;
    }

    //clase para generar los ides de los videojuegos
    public static int generarId() {
        if (lista.isEmpty()) return 1;
        int maxId = 0;
        for (int id : lista.keySet()) {
            if (id > maxId) maxId = id;
        }
        return maxId + 1;
    }

    //getter para la lista
    public Map<Integer, Videojuego> getLista() {
        return lista;
    }

    /**
     * Guarda los videojuegos en un archivo CSV, añadiendo el idPersona al final de cada línea
     * @param archivo Archivo CSV donde se guardarán los videojuegos
     */
    public void guardarEnArchivo(File archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Videojuego v : lista.values()) {
                bw.write(
                        v.getId() + ";" +
                                v.getTitulo() + ";" +
                                v.getCategoria() + ";" +
                                v.getPlataforma() + ";" +
                                v.getAño() + ";" +
                                v.getIdPersona()   // <-- nuevo campo
                );
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error escribiendo archivo videojuegos.csv");
        }
    }

    /**
     * Carga los videojuegos desde un archivo CSV, incluyendo el idPersona
     * @param archivo Archivo CSV de donde se leerán los videojuegos
     */
    public void cargarDesdeArchivo(File archivo) {
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(";");
                int id = Integer.parseInt(datos[0]);
                String titulo = datos[1];
                String categoria = datos[2];
                String plataforma = datos[3];
                int anyo = Integer.parseInt(datos[4]);
                int idPersona = Integer.parseInt(datos[5]);

                // Constructor nuevo en Videojuego con idPersona
                Videojuego v = new Videojuego(id, titulo, categoria, plataforma, anyo, idPersona);
                lista.put(id, v);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo videojuegos.csv");
        }
    }
}
