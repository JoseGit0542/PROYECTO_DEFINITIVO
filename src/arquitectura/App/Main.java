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

        // Repositorios inicializados con sus archivos
        RepositorioVideojuego rp = new RepositorioVideojuego(archivoVideojuegos);
        RepositorioPersona rpPersona = new RepositorioPersona(archivoPersonas);

        // Gestión de usuarios al comienzo del programa
        Persona personaActiva = RepositorioPersona.login(rpPersona);

        // Creamos objeto Funciones con variables internas
        Funciones funciones = new Funciones(reader, rp, rpPersona, personaActiva);

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
                            case 5 -> System.out.println("Retornando... \n");
                            default -> System.out.println("Introduce un valor correcto");
                        }
                    }
                    case 2 -> funciones.mostrarBiblioteca();
                    case 3 -> {
                        personaActiva = rpPersona.login(rpPersona);
                        funciones.setPersonaActiva(personaActiva);
                    }
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
}
