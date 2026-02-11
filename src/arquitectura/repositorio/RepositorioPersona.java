package arquitectura.repositorio;

import arquitectura.conexion.Database;
import arquitectura.dominio.Persona;
import arquitectura.dominio.Videojuego;

import java.sql.*;
import java.util.*;

public class RepositorioPersona {

    public RepositorioPersona() {}

    // ---------------------- SAVE ----------------------
    public void save(Persona p) {
        if (p.getId() == 0) {
            insertar(p);
        } else {
            actualizar(p);
        }
    }

    private void insertar(Persona p) {
        String sql = "INSERT INTO Persona (nombre) VALUES (?)";

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

    private void actualizar(Persona p) {
        String sql = "UPDATE Persona SET nombre = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setInt(2, p.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------------- FIND BY ID ----------------------
    public Persona findById(int id) {
        String sql = "SELECT * FROM Persona WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Persona(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------------- EXISTS ----------------------
    public boolean existsById(int id) {
        return findById(id) != null;
    }

    // ---------------------- FIND ALL ----------------------
    public List<Persona> findAll() {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM Persona";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Persona(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ---------------------- DELETE BY ID ----------------------
    public void deleteById(int id) {
        String sql = "DELETE FROM Persona WHERE id = ?";

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
        String sql = "DELETE FROM Persona";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------------- DELETE PERSONA + JUEGOS ----------------------
    public void deletePersonaConJuegos(int idPersona, RepositorioVideojuego repoVideojuego) {

        repoVideojuego.eliminarBibliotecaDePersona(idPersona);

        deleteById(idPersona);
    }

    // ---------------------- COUNT ----------------------
    public int count() {
        String sql = "SELECT COUNT(*) FROM Persona";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ---------------------- LOGIN ----------------------
    public static Persona login(RepositorioPersona rp) {
        Scanner reader = new Scanner(System.in);
        Persona actual = null;

        while (actual == null) {
            System.out.println("\n---- PANEL DE INICIO DE SESIÓN ----");
            System.out.println("1. Crear nuevo usuario");
            System.out.println("2. Iniciar sesión con usuario existente");
            System.out.print("Elige una opción: ");

            try {
                int opcion = reader.nextInt();
                reader.nextLine();

                switch (opcion) {

                    case 1 -> {
                        System.out.print("Introduce tu nombre: ");
                        String nombre = reader.nextLine().trim();

                        if (!nombre.isEmpty()) {
                            Persona nuevo = new Persona(nombre);
                            rp.save(nuevo);
                            actual = nuevo;
                            System.out.println("Usuario creado con ID: " + nuevo.getId());
                        } else {
                            System.out.println("Nombre inválido.");
                        }
                    }

                    case 2 -> {
                        if (rp.count() == 0) {
                            System.out.println("No hay usuarios registrados. Crea uno primero.");
                            break;
                        }

                        System.out.println("Usuarios registrados:");
                        for (Persona p : rp.findAll()) {
                            System.out.println(p.getId() + " - " + p.getNombre());
                        }

                        System.out.print("Introduce tu ID: ");
                        int id = reader.nextInt();
                        reader.nextLine();

                        if (rp.existsById(id)) {
                            actual = rp.findById(id);
                            System.out.println("Has iniciado sesión como: " + actual.getNombre() + "\n");
                        } else {
                            System.out.println("ID no encontrado. Intenta de nuevo.");
                        }
                    }

                    default -> System.out.println("Opción incorrecta.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Introduce un número válido.");
                reader.nextLine();
            }
        }

        return actual;
    }
}
