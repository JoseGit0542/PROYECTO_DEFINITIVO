package arquitectura.repositorio;

import arquitectura.conexion.Database;
import arquitectura.dominio.Plataforma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPlataforma {

    public RepositorioPlataforma() {}

    // ---------------------- FIND BY ID ----------------------
    public Plataforma findById(int id) {
        String sql = "SELECT * FROM Plataforma WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Plataforma(
                        rs.getInt(1),      // id
                        rs.getString(2)    // nombre
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ---------------------- FIND ALL ----------------------
    public List<Plataforma> findAll() {
        List<Plataforma> lista = new ArrayList<>();
        String sql = "SELECT * FROM Plataforma ORDER BY id";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Plataforma(
                        rs.getInt(1),
                        rs.getString(2)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ---------------------- INSERTAR (si quieres añadir más) ----------------------
    public void save(Plataforma p) {
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
}
