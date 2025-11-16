package arquitectura.App;

import arquitectura.dominio.Persona;
import arquitectura.repositorio.Funciones;
import arquitectura.repositorio.Menus;
import arquitectura.repositorio.RepositorioPersona;
import arquitectura.repositorio.RepositorioVideojuego;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        // Repositorios
        RepositorioVideojuego rp = new RepositorioVideojuego();
        RepositorioPersona rpPersona = new RepositorioPersona();

        File archivoVideojuegos = new File("videojuegos.csv");
        File archivoPersonas = new File("personas.csv");

        // Cargar datos desde archivos
        rpPersona.cargarDesdeArchivo(archivoPersonas);
        rp.cargarDesdeArchivo(archivoVideojuegos);

        // Login inicial o crear nuevo usuario
        Persona personaActiva = login(reader, rpPersona, archivoPersonas);

        // variables de las respuestas
        int respuesta1;
        int respuesta2;
        boolean estado = true;
        boolean estado2;

        do {
            try {
                Menus.menu1();
                respuesta1 = reader.nextInt();
                reader.nextLine();
                System.out.println("");

                switch (respuesta1) {
                    case 1:
                        Menus.menuGestion();
                        respuesta2 = reader.nextInt();
                        reader.nextLine();

                        switch (respuesta2) {
                            case 1:
                                estado2 = true;
                                Funciones.crearVideojuegoCase(reader, rp, personaActiva, estado2);
                                break;
                            case 2:
                                Funciones.eliminarVideojuegoId(rp, reader, personaActiva);
                                break;
                            case 3:
                                Funciones.eliminarBiblioteca(rp, reader, personaActiva);
                                break;
                            case 4:
                                Funciones.editarBiblioteca(rp, reader, personaActiva);
                                break;
                            case 5:
                                System.out.println("Retornando... \n");
                                break;
                            default:
                                System.out.println("Introduce un valor correcto");
                        }
                        break;

                    case 2:
                        Funciones.mostrarBiblioteca(reader, rp, archivoVideojuegos, personaActiva);
                        break;

                    case 3:
                        Funciones.guardarCambios(rp, archivoVideojuegos);
                        break;

                    case 4:
                        // Cambiar de usuario o crear uno nuevo
                        personaActiva = login(reader, rpPersona, archivoPersonas);
                        break;

                    case 0:
                        System.out.println("Saliendo del programa...");
                        estado = false;
                        break;

                    default:
                        System.out.println("Vuelve a introducir un valor.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Introduce un valor correcto. \n");
                reader.next();
            } catch (IllegalArgumentException e) {
                System.out.println("Introduce un argumento correcto. \n");
                reader.next();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (estad);

        // Guardar cambios finales al salir
        rpPersona.guardarEnArchivo(archivoPersonas);
        rp.guardarEnArchivo(archivoVideojuegos);
        reader.close();
    }

    // login mejorado: crear o iniciar sesión con cualquier usuario
    public static Persona login(Scanner reader, RepositorioPersona rp, File archivoPersonas) {
        Persona actual = null;

        while (actual == null) {
            System.out.println("\n---- PANEL DE INICIO DE SESIÓN ----");
            System.out.println("1. Crear nuevo usuario");
            System.out.println("2. Iniciar sesión con usuario existente");
            System.out.print("Elige una opción: ");
            System.out.println("");

            try {
                int opcion = reader.nextInt();
                reader.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.print("Introduce tu nombre: ");
                        String nombre = reader.nextLine().trim();
                        if (!nombre.isEmpty()) {
                            // Crear persona y guardar en repositorio
                            Persona nuevo = new Persona(nombre);
                            rp.save(nuevo);

                            // Guardado inmediato en CSV
                            rp.guardarEnArchivo(archivoPersonas);

                            System.out.println("Usuario creado con ID: " + nuevo.getId());
                        } else {
                            System.out.println("Nombre inválido.");
                        }
                        break;

                    case 2:
                        if (rp.count() == 0) {
                            System.out.println("No hay usuarios registrados. Crea uno primero.");
                            break;
                        }

                        System.out.println("Usuarios registrados:");
                        for (Persona p : rp.findAll()) {
                            System.out.println(p.getId() + " - " + p.getNombre());
                        }

                        System.out.println("");
                        System.out.print("Introduce tu ID: ");
                        int id = reader.nextInt();
                        reader.nextLine();

                        if (rp.existsById(id)) {
                            actual = rp.findById(id);
                            System.out.println("Has iniciado sesión como: " + actual.getNombre() + "\n");
                        } else {
                            System.out.println("ID no encontrado. Intenta de nuevo.");
                        }
                        break;

                    default:
                        System.out.println("Opción incorrecta.");
                }

            } catch (InputMismatchException ime) {
                System.out.println("Introduce un número válido.");
                reader.nextLine();
            }
        }

        return actual;
    }
}
