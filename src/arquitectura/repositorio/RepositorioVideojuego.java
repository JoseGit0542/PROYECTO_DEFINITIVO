package arquitectura.repositorio;

import arquitectura.conexion.Database;
import arquitectura.dominio.Videojuego;

import java.sql.*;
import java.util.*;

public class RepositorioVideojuego {

    public RepositorioVideojuego() {}

    // ---------------------- SAVE ----------------------
    public <S extends Videojuego> S save(S entity) {
        if (entity.getId() == 0) {
            insertar(entity);
        } else {
            actualizar(entity);
        }
        return entity;
    }

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

    // ---------------------- FIND BY ID ----------------------
    public Videojuego findById(Integer id) {
        String sql = "SELECT * FROM Videojuego WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Videojuego(
                        rs.getInt(1),      // id
                        rs.getString(2),   // titulo
                        rs.getString(3),   // categoria
                        rs.getInt(4),      // año
                        rs.getInt(5),      // idPersona
                        rs.getInt(6)       // idPlataforma
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ---------------------- EXISTS ----------------------
    public boolean existsById(Integer id) {
        return findById(id) != null;
    }

    // ---------------------- FIND ALL ----------------------
    public List<Videojuego> findAll() {
        List<Videojuego> lista = new ArrayList<>();
        String sql = "SELECT * FROM Videojuego";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Videojuego(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ---------------------- DELETE BY ID ----------------------
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Videojuego WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------------- DELETE ALL ----------------------
    public void deleteAll() {
        String sql = "DELETE FROM Videojuego";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------------- LISTAR POR PERSONA ----------------------
    public List<Videojuego> listarPorPersona(int idPersona) {
        List<Videojuego> lista = new ArrayList<>();
        String sql = "SELECT * FROM Videojuego WHERE idPersona = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Videojuego(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ---------------------- CONTAR POR PERSONA ----------------------
    public long contarPorPersona(int idPersona) {
        String sql = "SELECT COUNT(*) FROM Videojuego WHERE idPersona = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPersona);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ---------------------- ELIMINAR BIBLIOTECA ----------------------
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
