package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public static void crearVideojuegoCase(Scanner reader, RepositorioVideojuego rp, boolean estado2) {
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
                    System.out.println("Retornando... \n");
                    break;
                default:
                    System.out.println("Debes de introducir un valor correcto.");

            }

        }while(estado2);
    }
    public static void eliminarVideojuegoId(RepositorioVideojuego rp, Scanner reader) {
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
                System.out.println("Retornando... \n");
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
    public static void eliminarBiblioteca(RepositorioVideojuego rp, Scanner reader) {
        if (rp.getLista().isEmpty()) {
            System.out.println("La lista está vacia... \n");
            return;
        }
        System.out.println("------ELIMINAR TODA LA BIBLIOTECA-------");
        System.out.println("¿Estás seguro [si/no]?");
        String seguro = reader.nextLine().toLowerCase();
        if (seguro.equals("si")) {
            System.out.println("La biblioteca ha sido borrada con exito.");

            //bucle para borrar toda la biblioteca
            rp.getLista().clear();

        } else if (seguro.equals("no")) {
            System.out.println("Retornando... \n");
        } else {
            System.out.println("Debes de introducir un valor correcto");
        }
    }
    public static void editarBiblioteca(RepositorioVideojuego rp, Scanner reader) {
        int id = 0;
        do {
            //si la lista está vacía me salgo directamente
            if (rp.getLista().isEmpty()) {
                System.out.println("La lista está vacía... \n");
                break;
            }

            System.out.println("------EDITAR BIBLIOTECA------");

            //muestro todos los elementos
            System.out.println("Tu biblioteca actual: ");
            for (int key : rp.getLista().keySet()) {
                System.out.println("ID: " + rp.getLista().get(key).getId() + ", título: " + rp.getLista().get(key).getTitulo()
                        + ", categoria: " + rp.getLista().get(key).getCategoria() + ", plataforma: " + rp.getLista().get(key).getPlataforma()
                        + ", año de salida: " + rp.getLista().get(key).getAño() + "\n");
            }
            System.out.println("\n");


            //pido al usuario el id para eliminar el videojuego
            System.out.print("Introduce un ID del videojuego deseado [0 para salir]: ");
            id = reader.nextInt();
            reader.nextLine(); //limpiar el buffer que si no se repite

            if (id == 0) {
                System.out.println("Retornando... \n");
                break;
            }
            // Validar si el ID existe
            if (!rp.getLista().containsKey(id)) {
                System.out.println("El ID introducido no existe en la biblioteca.\n");
                break;
            }


            //mostramos el menu de edicion
            String respuestaEdicion = "";

            do {
                Menus.menuEditar();
                System.out.print("¿Qué campo deseas editar?: ");
                respuestaEdicion = reader.nextLine().trim().toLowerCase();

                switch (respuestaEdicion) {
                    case "titulo":
                        System.out.print("Nuevo título: ");
                        String titulo = reader.nextLine();
                        rp.getLista().get(id).setTitulo(titulo);
                        System.out.println("Título cambiado con éxito.\n");
                        break;

                    case "categoria":
                        System.out.print("Nueva categoría: ");
                        String categoria = reader.nextLine();
                        rp.getLista().get(id).setCategoria(categoria);
                        System.out.println("Categoría cambiada con éxito.\n");
                        break;

                    case "plataforma":
                        System.out.print("Nueva plataforma: ");
                        String plataforma = reader.nextLine();
                        rp.getLista().get(id).setPlataforma(plataforma);
                        System.out.println("Plataforma cambiada con éxito.\n");
                        break;

                    case "año":
                        System.out.print("Nuevo año: ");
                        int año = reader.nextInt();
                        reader.nextLine(); // limpiar buffer
                        rp.getLista().get(id).setAño(año);
                        System.out.println("Año cambiado con éxito.\n");
                        break;

                    case "salir":
                        System.out.println("Retornando al menú principal...\n");
                        break;

                    default:
                        System.out.println("Debes introducir bien el campo que deseas cambiar.\n");
                }

            } while (!respuestaEdicion.equalsIgnoreCase("salir"));
        }while(true);
    }
    public static void guardarCambios(RepositorioVideojuego rp, File archivo) throws IOException {


        //comprobamos que la lista esta vacia
        if (rp.getLista().isEmpty()) {
            System.out.println("El archivo está vacío.");
            return;
        }

        //escribimos el contenido de la lista en formato csv
        //importamos bufferedWriter
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

        String linea = "";
        for (int key : rp.getLista().keySet()) {
            linea = rp.getLista().get(key).getId() + ", " + rp.getLista().get(key).getTitulo()
                    + ", " + rp.getLista().get(key).getCategoria() + ", " + rp.getLista().get(key).getPlataforma()
                    + ", " + rp.getLista().get(key).getAño() + "\n";
            bw.write(linea);
        }
        bw.close();
    }
}
