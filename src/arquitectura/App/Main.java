package arquitectura.App;

import arquitectura.conexion.Database;
import arquitectura.dominio.InicializadorPlataforma;
import arquitectura.dominio.Persona;
import arquitectura.repositorio.*;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Database.getConnection();
        Scanner reader = new Scanner(System.in);

        RepositorioPersona repoPersona = new RepositorioPersona();
        RepositorioVideojuego repoVideojuego = new RepositorioVideojuego();
        RepositorioPlataforma repoPlataforma = new RepositorioPlataforma();

        InicializadorPlataforma.inicializar();
        Persona personaActiva = RepositorioPersona.login(repoPersona);

        Funciones funciones = new Funciones(
                reader, repoVideojuego, repoPersona, repoPlataforma, personaActiva
        );

        boolean salir = false;
        while (!salir) {
            if (funciones.getPersonaActiva() == null) {
                System.out.println("Debes iniciar sesión nuevamente.");
                personaActiva = RepositorioPersona.login(repoPersona);
                funciones.setPersonaActiva(personaActiva);
            }

            Menus.menu1();
            int opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
                //llamamos directamente a los menus internos de la clase Funciones
                case 1 -> funciones.menuGestionBiblioteca();
                case 2 -> funciones.mostrarBiblioteca();
                case 3 -> {
                    funciones.gestionarUsuarioActivo();
                    personaActiva = funciones.getPersonaActiva();
                }
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