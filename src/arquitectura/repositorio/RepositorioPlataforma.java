package arquitectura.repositorio;

import arquitectura.dominio.Plataforma;
import java.util.*;

public class RepositorioPlataforma {

    private static final Map<Integer, Plataforma> lista = new HashMap<>();

    static {
        lista.put(1, new Plataforma(1, "PC"));
        lista.put(2, new Plataforma(2, "PlayStation"));
        lista.put(3, new Plataforma(3, "Xbox"));
        lista.put(4, new Plataforma(4, "Nintendo Switch"));
    }

    public Plataforma findById(int id) {
        return lista.get(id);
    }

    public List<Plataforma> findAll() {
        return new ArrayList<>(lista.values());
    }
}
