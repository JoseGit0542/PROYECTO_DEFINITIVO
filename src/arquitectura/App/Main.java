package arquitectura.App;

import arquitectura.conexion.Database;
import arquitectura.dominio.InicializadorPlataforma;
import arquitectura.dominio.Persona;
import arquitectura.dominio.Plataforma;
import arquitectura.repositorio.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {

        // Inicializa la BD y crea tablas
        Database.getConnection();

        Scanner reader = new Scanner(System.in);

        // Repositorios SQL
        RepositorioPersona repoPersona = new RepositorioPersona();
        RepositorioVideojuego repoVideojuego = new RepositorioVideojuego();
        RepositorioPlataforma repoPlataforma = new RepositorioPlataforma();

        InicializadorPlataforma.inicializar();

        // Login
        Persona personaActiva = RepositorioPersona.login(repoPersona);

        // Funciones del programa
        Funciones funciones = new Funciones(
                reader,
                repoVideojuego,
                repoPersona,
                repoPlataforma,
                personaActiva
        );

        // Menú principal
        boolean salir = false;

        while (!salir) {
            Menus.menu1();
            int opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
                case 1 -> { // Gestionar biblioteca
                    boolean volver = false;
                    while (!volver) {
                        Menus.menuGestion();
                        int op = reader.nextInt();
                        reader.nextLine();

                        switch (op) {
                            case 1 -> funciones.crearVideojuegoCase();
                            case 2 -> funciones.eliminarVideojuegoId();
                            case 3 -> funciones.eliminarBiblioteca();
                            case 4 -> funciones.editarBiblioteca();
                            case 5 -> volver = true;
                            default -> System.out.println("Opción no válida.\n");
                        }
                    }
                }

                case 2 -> funciones.mostrarBiblioteca();

                case 3 -> funciones.eliminarPersona();

                case 0 -> {
                    salir = true;
                    System.out.println("Saliendo del programa...");
                }

                default -> System.out.println("Opción no válida.\n");
            }
        }

        reader.close();
    }
}
