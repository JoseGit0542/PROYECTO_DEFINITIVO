package arquitectura.repositorio;

import arquitectura.conexion.Database;
import arquitectura.dominio.Persona;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class RepositorioPersona implements IRepositorioExtend<Persona, Integer> {

    public RepositorioPersona() {}

    //guardar o actualizar entidad
    @Override
    public <S extends Persona> S save(S entity) {
        if (entity.getId() == 0) {
            insertar(entity);
        } else {
            actualizar(entity);
        }
        return entity;
    }

    //insertar nueva persona en base de datos
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

    //actualizar nombre de persona existente
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

    //buscar persona por id
    @Override
    public Persona findById(Integer id) {
        if (id == null) throw new IllegalArgumentException("ID no puede ser nulo");

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

    //buscar persona por id con optional
    @Override
    public Optional<Persona> findByIdOptional(Integer id) {
        return Optional.ofNullable(findById(id));
    }

    //comprobar si existe el id
    @Override
    public boolean existsById(Integer id) {
        return findById(id) != null;
    }

    //obtener todos los registros como iterable
    @Override
    public Iterable<Persona> findAll() {
        return findAllToList();
    }

    //obtener todos los registros como lista
    @Override
    public List<Persona> findAllToList() {
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

    //borrar persona por id
    @Override
    public void deleteById(Integer id) {
        if (id == null) throw new IllegalArgumentException("ID no puede ser nulo");

        String sql = "DELETE FROM Persona WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //borrar todos los registros de personas
    @Override
    public void deleteAll() {
        String sql = "DELETE FROM Persona";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //contar numero total de personas
    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Persona";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getLong(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //borrar persona y sus videojuegos vinculados
    public void deletePersonaConJuegos(int idPersona, RepositorioVideojuego repoVideojuego) {
        repoVideojuego.eliminarBibliotecaDePersona(idPersona);
        deleteById(idPersona);
    }

    //gestionar el inicio de sesion o creacion de usuario
    public static Persona login(RepositorioPersona rp) {
        Scanner reader = new Scanner(System.in);
        Persona actual = null;

        while (actual == null) {
            System.out.println("\n---- PANEL DE INICIO DE SESIÓN ----");
            System.out.println("1. Crear nuevo usuario");
            System.out.println("2. Iniciar sesión con usuario existente");
            System.out.print("Elige una opción: ");

            try {
                String linea = reader.nextLine().trim();

                if (linea.isEmpty()) {
                    System.out.println("Debes introducir un valor válido.");
                    continue;
                }

                int opcion;
                opcion = Integer.parseInt(linea);

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
                        for (Persona p : rp.findAllToList()) {
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