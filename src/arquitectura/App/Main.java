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
        Persona personaActiva = RepositorioPersona.login(rpPersona);

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
                            case 2 -> Funciones.eliminarVideojuegoId(rp, personaActiva);
                            case 3 -> Funciones.eliminarBiblioteca(rp, personaActiva);
                            case 4 -> Funciones.editarBiblioteca(rp, personaActiva);
                            case 5 -> System.out.println("Retornando... \n");
                            default -> System.out.println("Introduce un valor correcto");
                        }
                    }
                    case 2 -> Funciones.mostrarBiblioteca(rp, personaActiva);
                    case 3 -> personaActiva = rpPersona.login( rpPersona);
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
