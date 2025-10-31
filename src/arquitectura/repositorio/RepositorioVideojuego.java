package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

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
        for(int key: lista.keySet()) {
            contador++;
        }
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
        for(int key : lista.keySet()) {
            lista.remove(key);
        }
    }
    /**
     *
     * Devuelve true si existe la entidad con identificador id.
     * @param integer    Identificador de la entidad
     * @return      true si existe la entidad con el identificador id
     *
     * @throws IllegalArgumentException En caso de ser id nulo
     */
    @Override
    public boolean existsById(Integer integer) {
        if(integer == null){
            throw new IllegalArgumentException("el id no debe de ser nulo");
        }
        return true;
    }
    /**
     * Devuelve la entidad T con identificador id.
     * @param id    Identificador de la entidad
     * @return      Entidad que tiene como identificador id.
     *
     * @throws IllegalArgumentException En caso de ser id nulo
     */
    @Override
    public Videojuego findById(Integer integer) {
        if (integer == null){
            throw new IllegalArgumentException("el id no debe de ser nulo");
        }
        return lista.get(integer);
    }
    /**
     * Devuelve la entidad T con identificador id.
     *
     * @param id    Identificador de la entidad
     * @return      Entidad que tiene como identificador id u Optional#empty() si no se encuentra
     *
     * @throws IllegalArgumentException En caso de ser id nulo
     */
    @Override
    public Optional<Videojuego> findByIdOptional(Integer integer) {
        if(integer == null){
            throw new IllegalArgumentException("el id no debe de ser nulo");
        }
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
     *
     * Guarda la entidad entity.
     * @param entity    entidad a guar dar. No debe ser nulo
     * @return          entidad guardada
     *
     * @throws IllegalArgumentException En caso de ser entity nulo
     */
    @Override
    public <S extends Videojuego> S save(S entity) {
        if(entity == null){
            throw new IllegalArgumentException("entity no debe de ser nulo");
        }
        return entity;
    }

    //clase para generar los ides de los videojuegos
    public static int generarId() {
        int id = lista.size()+1;
        return id;
    }

    //getter para la lista
    public Map<Integer, Videojuego> getLista() {
        return lista;
    }
}
