package arquitectura.repositorio;

import arquitectura.conexion.Database;
import arquitectura.dominio.Videojuego;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class RepositorioVideojuego implements IRepositorioExtend<Videojuego, Integer> {

    public RepositorioVideojuego() {}

    //guardar o actualizar un videojuego
    @Override
    public <S extends Videojuego> S save(S entity) {
        if (entity.getId() == 0) {
            insertar(entity);
        } else {
            actualizar(entity);
        }
        return entity;
    }

    //insertar nuevo videojuego en la base de datos
    private void insertar(Videojuego v) {
        String sql = "INSERT INTO Videojuego (titulo, categoria, año, idPersona, idPlataforma) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, v.getTitulo());
            stmt.setString(2, v.getCategoria());
            stmt.setInt(3, v.getAño());
            stmt.setInt(4, v.getIdPersona());
            stmt.setInt(5, v.getIdPlataforma());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                v.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //actualizar datos de un videojuego existente
    private void actualizar(Videojuego v) {
        String sql = "UPDATE Videojuego SET titulo = ?, categoria = ?, año = ?, idPersona = ?, idPlataforma = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, v.getTitulo());
            stmt.setString(2, v.getCategoria());
            stmt.setInt(3, v.getAño());
            stmt.setInt(4, v.getIdPersona());
            stmt.setInt(5, v.getIdPlataforma());
            stmt.setInt(6, v.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //buscar videojuego por id
    @Override
    public Videojuego findById(Integer id) {
        if (id == null) throw new IllegalArgumentException("ID no puede ser nulo");

        String sql = "SELECT * FROM Videojuego WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Videojuego(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("categoria"),
                        rs.getInt("año"),
                        rs.getInt("idPersona"),
                        rs.getInt("idPlataforma")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //buscar videojuego con optional
    @Override
    public Optional<Videojuego> findByIdOptional(Integer id) {
        return Optional.ofNullable(findById(id));
    }

    //comprobar si un videojuego existe
    @Override
    public boolean existsById(Integer id) {
        return findById(id) != null;
    }

    //obtener todos los videojuegos
    @Override
    public Iterable<Videojuego> findAll() {
        return findAllToList();
    }

    //listar todos los videojuegos en una lista
    @Override
    public List<Videojuego> findAllToList() {
        List<Videojuego> lista = new ArrayList<>();
        String sql = "SELECT * FROM Videojuego";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Videojuego(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("categoria"),
                        rs.getInt("año"),
                        rs.getInt("idPersona"),
                        rs.getInt("idPlataforma")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    //eliminar un videojuego por su id
    @Override
    public void deleteById(Integer id) {
        if (id == null) throw new IllegalArgumentException("ID no puede ser nulo");

        String sql = "DELETE FROM Videojuego WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //borrar todos los videojuegos de la tabla
    @Override
    public void deleteAll() {
        String sql = "DELETE FROM Videojuego";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //contar el total de videojuegos registrados
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Videojuego";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getLong(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //obtener lista de juegos de un usuario concreto
    public List<Videojuego> listarPorPersona(int idPersona) {
        List<Videojuego> lista = new ArrayList<>();
        String sql = "SELECT * FROM Videojuego WHERE idPersona = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Videojuego(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("categoria"),
                        rs.getInt("año"),
                        rs.getInt("idPersona"),
                        rs.getInt("idPlataforma")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    //contar cuantos juegos tiene un usuario
    public long contarPorPersona(int idPersona) {
        String sql = "SELECT COUNT(*) FROM Videojuego WHERE idPersona = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getLong(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //borrar todos los juegos de un usuario especifico
    public void eliminarBibliotecaDePersona(int idPersona) {
        String sql = "DELETE FROM Videojuego WHERE idPersona = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}