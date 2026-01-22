package arquitectura.App;

import arquitectura.dominio.Persona;
import arquitectura.repositorio.*;

import java.io.File;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        // Archivos CSV
        File archivoVideojuegos = new File("videojuegos.csv");
        File archivoPersonas = new File("personas.csv");

        // Repositorios
        RepositorioVideojuego rp = new RepositorioVideojuego(archivoVideojuegos);
        RepositorioPersona rpPersona = new RepositorioPersona(archivoPersonas);
        RepositorioPlataforma rpPlataforma = new RepositorioPlataforma();

        // Login inicial
        Persona personaActiva = RepositorioPersona.login(rpPersona);

        // Funciones (constructor actualizado)
        Funciones funciones = new Funciones(
                reader,
                rp,
                rpPersona,
                rpPlataforma,
                personaActiva
        );

        boolean estado = true;

        do {
            try {
                Menus.menu1();
                int respuesta1 = reader.nextInt();
                reader.nextLine();
                System.out.println();

                switch (respuesta1) {
                    case 1 -> {
                        Menus.menuGestion();
                        int respuesta2 = reader.nextInt();
                        reader.nextLine();

                        switch (respuesta2) {
                            case 1 -> funciones.crearVideojuegoCase();
                            case 2 -> funciones.eliminarVideojuegoId();
                            case 3 -> funciones.eliminarBiblioteca();
                            case 4 -> funciones.editarBiblioteca();
                            case 5 -> System.out.println("Retornando...\n");
                            default -> System.out.println("Introduce un valor correcto");
                        }
                    }
                    case 2 -> funciones.mostrarBiblioteca();
                    case 3 -> {
                        // Menu de usuario
                        System.out.println("1: Cambiar usuario actual");
                        System.out.println("2: Eliminar usuario");
                        System.out.print("Elige una opción: ");
                        int opcionUsuario = reader.nextInt();
                        reader.nextLine();

                        switch (opcionUsuario) {
                            case 1 -> {
                                personaActiva = rpPersona.login(rpPersona);
                                funciones.setPersonaActiva(personaActiva);
                            }
                            case 2 -> funciones.eliminarPersona();
                            default -> System.out.println("Opción incorrecta.");
                        }
                    }
                    case 0 -> {
                        System.out.println("Saliendo del programa...");
                        estado = false;
                    }
                    default -> System.out.println("Vuelve a introducir un valor.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Introduce un valor correcto.\n");
                reader.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Introduce un argumento correcto.\n");
                reader.nextLine();
            }
        } while (estado);

        reader.close();
    }
}
