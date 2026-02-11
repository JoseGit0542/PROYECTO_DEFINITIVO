package arquitectura.dominio;

import arquitectura.conexion.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializadorPlataforma {
    public static void inicializar() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {

            // Crear tabla Persona
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Persona (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "nombre VARCHAR(100) NOT NULL" +
                            ")"
            );

            // Crear tabla Plataforma
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Plataforma (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "nombre VARCHAR(50) NOT NULL" +
                            ")"
            );

            // Crear tabla Videojuego
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Videojuego (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "titulo VARCHAR(100) NOT NULL, " +
                            "categoria VARCHAR(50), " +
                            "año INT, " +
                            "idPersona INT, " +
                            "idPlataforma INT, " +
                            "FOREIGN KEY (idPersona) REFERENCES Persona(id), " +
                            "FOREIGN KEY (idPlataforma) REFERENCES Plataforma(id)" +
                            ")"
            );

            System.out.println("Tablas inicializadas correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
