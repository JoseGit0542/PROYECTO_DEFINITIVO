package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RepositorioVideojuego implements IRepositorioExtend<Videojuego,  Integer> {

    //atributos del repositorio
    Map<Integer, Videojuego> lista = new HashMap<>();



    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Videojuego findById(Integer integer) {
        return null;
    }

    @Override
    public Optional<Videojuego> findByIdOptional(Integer integer) {
        return Optional.empty();
    }

    @Override
    public List<Videojuego> findAll() {
        return null;
    }

    @Override
    public <S extends Videojuego> S save(S entity) {
        return null;
    }
}
