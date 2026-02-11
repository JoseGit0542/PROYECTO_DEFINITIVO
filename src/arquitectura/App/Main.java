package arquitectura.App;

import arquitectura.conexion.Database;
import arquitectura.dominio.InicializadorPlataforma;
import arquitectura.dominio.Persona;
import arquitectura.repositorio.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {

        //inicializa la base de datos y crea tablas
        Database.getConnection();

        //crea scanner para leer consola
        Scanner reader = new Scanner(System.in);

        //repositorio de usuarios
        RepositorioPersona repoPersona = new RepositorioPersona();

        //repositorio de videojuegos
        RepositorioVideojuego repoVideojuego = new RepositorioVideojuego();

        //repositorio de plataformas
        RepositorioPlataforma repoPlataforma = new RepositorioPlataforma();

        //inicializa plataformas iniciales
        InicializadorPlataforma.inicializar();

        //login usuario activo
        Persona personaActiva = RepositorioPersona.login(repoPersona);

        //instancia funciones con repositorios y usuario activo
        Funciones funciones = new Funciones(
                reader,
                repoVideojuego,
                repoPersona,
                repoPlataforma,
                personaActiva
        );

        //menu principal
        boolean salir = false;

        while (!salir) {

            //muestra menu principal
            Menus.menu1();
            int opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {

                //gestionar biblioteca
                case 1 -> {
                    boolean volver = false;
                    while (!volver) {

                        //menu de gestion de biblioteca
                        Menus.menuGestion();
                        int op = reader.nextInt();
                        reader.nextLine();

                        switch (op) {
                            case 1 -> funciones.crearVideojuegoCase();
                            case 2 -> funciones.eliminarVideojuegoId();
                            case 3 -> funciones.eliminarBiblioteca();
                            case 4 -> funciones.editarBiblioteca();
                            case 5 -> volver = true;
                            default -> System.out.println("Opcion no valida\n");
                        }
                    }
                }

                //mostrar biblioteca
                case 2 -> funciones.mostrarBiblioteca();

                //gestionar usuario activo
                case 3 -> funciones.gestionarUsuarioActivo();

                //salir del programa
                case 0 -> {
                    salir = true;
                    System.out.println("Saliendo del programa...");
                }

                default -> System.out.println("Opcion no valida\n");
            }
        }

        //cierra scanner
        reader.close();
    }
}
