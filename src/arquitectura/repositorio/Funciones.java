package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Funciones {
    public static void crearVideojuego(Scanner reader, RepositorioVideojuego repo) {
        //le pido al usuario que introduzca los datos
        try {
            System.out.println("Introduce el título del videojuego: ");
            String titulo = reader.nextLine();


            System.out.println("Introduce la categoria del videojuego: ");
            String categoria = reader.nextLine();


            System.out.println("Introduce la plataforma: ");
            String plataforma = reader.nextLine();


            System.out.println("Introduce el año en que se lanzo: ");
            int año = reader.nextInt();
            reader.nextLine();

            Videojuego v = new Videojuego(titulo, categoria, plataforma, año);

            //añado a la lista el videojuego
            repo.getLista().put(RepositorioVideojuego.generarId(), v);


        }catch(InputMismatchException e) {
            System.out.println("Introduce el tipo correcto");
        }



    }
    public static void CrearVideojuego(Scanner reader, RepositorioVideojuego rp, boolean estado2) {
        do{
            System.out.println("1: Crear videojuego | 2: salir |");
            int respuesta3 = reader.nextInt();
            reader.nextLine();

            switch(respuesta3) {
                case 1:
                    Funciones.crearVideojuego(reader, rp);
                    break;
                case 2:
                    estado2 = false;
                    System.out.println("Retornando...");
                    break;
                default:
                    System.out.println("Debes de introducir un valor correcto.");

            }

        }while(estado2);
    }
    public static void EliminarVideojuegoId(RepositorioVideojuego rp, Scanner reader) {
        if (rp.getLista().isEmpty()) {
            System.out.println("La lista está vacía. Retornando...\n");
            return;
        }

        int id; // definimos fuera del bucle para usarlo en la condición

        do {
            System.out.println("-------Eliminar videojuego por ID------");
            System.out.println("Los ids y títulos son los siguientes: ");
            for (int key : rp.getLista().keySet()) {
                System.out.println("ID: " + rp.getLista().get(key).getId() +
                        ", título: " + rp.getLista().get(key).getTitulo());
            }

            System.out.print("\nIntroduce un ID del videojuego deseado [0 para salir]: ");
            id = reader.nextInt();
            reader.nextLine(); // limpiar el buffer

            if (id == 0) {
                System.out.println("Retornando...");
                break;
            }

            // Buscar y eliminar el videojuego
            if (rp.getLista().containsKey(id)) {
                System.out.println("El videojuego \"" + rp.getLista().get(id).getTitulo() +
                        "\" ha sido eliminado con éxito.\n");
                rp.getLista().remove(id);
            } else {
                System.out.println("No se ha encontrado el id especificado.\n");
            }

            // Si la lista se vacía, salimos automáticamente
            if (rp.getLista().isEmpty()) {
                System.out.println("La lista ha quedado vacía. Retornando...\n");
                break;
            }

        } while (true);
    }
}
