package arquitectura.App;

import arquitectura.dominio.Persona;
import arquitectura.repositorio.Funciones;
import arquitectura.repositorio.Menus;
import arquitectura.repositorio.RepositorioPersona;
import arquitectura.repositorio.RepositorioVideojuego;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        // Archivos CSV
        File archivoVideojuegos = new File("videojuegos.csv");
        File archivoPersonas = new File("personas.csv");

        // Repositorios inicializados con sus archivos
        RepositorioVideojuego rp = new RepositorioVideojuego(archivoVideojuegos);
        RepositorioPersona rpPersona = new RepositorioPersona(archivoPersonas);

        // Gestión de usuarios al comienzo del programa
        Persona personaActiva = login(reader, rpPersona);

        boolean estado = true;

        do {
            try {
                Menus.menu1();
                int respuesta1 = reader.nextInt();
                reader.nextLine();
                System.out.println("");

                switch (respuesta1) {
                    case 1 -> {
                        Menus.menuGestion();
                        int respuesta2 = reader.nextInt();
                        reader.nextLine();

                        switch (respuesta2) {
                            case 1 -> Funciones.crearVideojuegoCase(reader, rp, personaActiva);
                            case 2 -> Funciones.eliminarVideojuegoId(rp, reader, personaActiva);
                            case 3 -> Funciones.eliminarBiblioteca(rp, reader, personaActiva);
                            case 4 -> Funciones.editarBiblioteca(rp, reader, personaActiva);
                            case 5 -> System.out.println("Retornando... \n");
                            default -> System.out.println("Introduce un valor correcto");
                        }
                    }
                    case 2 -> Funciones.mostrarBiblioteca(reader, rp, personaActiva);
                    case 4 -> personaActiva = login(reader, rpPersona);
                    case 0 -> {
                        System.out.println("Saliendo del programa...");
                        estado = false;
                    }
                    default -> System.out.println("Vuelve a introducir un valor.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Introduce un valor correcto. \n");
                reader.next();
            } catch (IllegalArgumentException e) {
                System.out.println("Introduce un argumento correcto. \n");
                reader.next();
            }
        } while (estado);

        // No es necesario guardar manualmente; repositorios lo hacen automáticamente
        reader.close();
    }

    // Login mejorado: crear o iniciar sesión con cualquier usuario
    public static Persona login(Scanner reader, RepositorioPersona rp) {
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
                    case 1 -> {
                        System.out.print("Introduce tu nombre: ");
                        String nombre = reader.nextLine().trim();
                        if (!nombre.isEmpty()) {
                            Persona nuevo = new Persona(nombre);
                            rp.save(nuevo); // guardado automático
                            actual = nuevo; // inicia sesión con el usuario creado
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
                    }
                    default -> System.out.println("Opción incorrecta.");
                }

            } catch (InputMismatchException ime) {
                System.out.println("Introduce un número válido.");
                reader.nextLine();
            }
        }

        return actual;
    }
}
