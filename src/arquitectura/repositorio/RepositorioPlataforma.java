package arquitectura.repositorio;

import arquitectura.conexion.Database;
import arquitectura.dominio.Plataforma;

import java.sql.*;
import java.util.*;

public class RepositorioPlataforma implements IRepositorioExtend<Plataforma, Integer> {

    public RepositorioPlataforma() {}

    //decidir si insertar o actualizar plataforma
    @Override
    public <S extends Plataforma> S save(S entity) {
        if (entity.getId() == 0) {
            insertar(entity);
        } else {
            actualizar(entity);
        }
        return entity;
    }

    //insertar nueva plataforma en la base de datos
    private void insertar(Plataforma p) {
        String sql = "INSERT INTO Plataforma (nombre) VALUES (?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getNombre());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                p.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //actualizar datos de una plataforma existente
    private void actualizar(Plataforma p) {
        String sql = "UPDATE Plataforma SET nombre = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setInt(2, p.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //buscar plataforma por su identificador
    @Override
    public Plataforma findById(Integer id) {
        if (id == null) throw new IllegalArgumentException("ID no puede ser nulo");

        String sql = "SELECT * FROM Plataforma WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Plataforma(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //buscar plataforma devolviendo un optional
    @Override
    public Optional<Plataforma> findByIdOptional(Integer id) {
        return Optional.ofNullable(findById(id));
    }

    //verificar si una plataforma existe por id
    @Override
    public boolean existsById(Integer id) {
        return findById(id) != null;
    }

    //obtener todas las plataformas como iterable
    @Override
    public Iterable<Plataforma> findAll() {
        return findAllToList();
    }

    //listar todas las plataformas en una lista
    @Override
    public List<Plataforma> findAllToList() {
        List<Plataforma> lista = new ArrayList<>();
        String sql = "SELECT * FROM Plataforma ORDER BY id";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Plataforma(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    //eliminar una plataforma por su id
    @Override
    public void deleteById(Integer id) {
        if (id == null) throw new IllegalArgumentException("ID no puede ser nulo");

        String sql = "DELETE FROM Plataforma WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //borrar todas las plataformas de la tabla
    @Override
    public void deleteAll() {
        String sql = "DELETE FROM Plataforma";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //contar cuantas plataformas hay registradas
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Plataforma";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getLong(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}