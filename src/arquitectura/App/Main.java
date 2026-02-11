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
                reader,
                repoVideojuego,
                repoPersona,
                repoPlataforma,
                personaActiva
        );

        boolean salir = false;

        while (!salir) {
            // Re-login si el usuario activo fue borrado
            if (funciones.getPersonaActiva() == null) {
                System.out.println("Debes iniciar sesión nuevamente.");
                personaActiva = RepositorioPersona.login(repoPersona);
                funciones.setPersonaActiva(personaActiva);
            }

            Menus.menu1();
            int opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
                // 1: Gestionar biblioteca (según tu Menus.menu1)
                case 1 -> {
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
                            case 5 -> volver = true; // Opción 5: Atrás en tu Menus
                            default -> System.out.println("Opción no válida.\n");
                        }
                    }
                }

                // 2: Mostrar biblioteca + Opciones de ordenación
                case 2 -> {
                    boolean volverMostrar = false;
                    while (!volverMostrar) {
                        Menus.menuMostrar();
                        int opMostrar = reader.nextInt();
                        reader.nextLine();

                        switch (opMostrar) {
                            case 1 -> funciones.mostrarBiblioteca(); // Lista normal
                            case 2 -> System.out.println("Total: " + repoVideojuego.contarPorPersona(personaActiva.getId()));
                            case 3 -> {
                                // Aquí llamamos al submenú de ordenación que pedías
                                boolean volverOrden = false;
                                while (!volverOrden) {
                                    Menus.opcionesOrdenacion();
                                    int opOrden = reader.nextInt();
                                    reader.nextLine();

                                    switch (opOrden) {
                                        case 1 -> funciones.mostrarPorCategoriaOrdenado();
                                        case 2 -> funciones.mostrarTitulosOrdenados();
                                        case 3 -> funciones.mostrarPorPlataforma();
                                        case 4 -> funciones.existeJuegoPorTitulo();
                                        case 5 -> volverOrden = true;
                                        default -> System.out.println("Opción no válida.\n");
                                    }
                                }
                            }
                            case 4 -> volverMostrar = true; // Opción 4: Atrás en tu Menus
                            default -> System.out.println("Opción no válida.\n");
                        }
                    }
                }

                // 3: Gestionar usuario
                case 3 -> {
                    funciones.gestionarUsuarioActivo();
                    personaActiva = funciones.getPersonaActiva();
                }

                // 0: Salir
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