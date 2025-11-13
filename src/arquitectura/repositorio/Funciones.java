package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Funciones {

    // ------------------ CREAR VIDEOJUEGO ------------------
    public static void crearVideojuego(Scanner reader, RepositorioVideojuego repo) {
        try {
            System.out.println("Introduce el título del videojuego: ");
            String titulo = reader.nextLine();

            System.out.println("Introduce la categoría del videojuego: ");
            String categoria = reader.nextLine();

            System.out.println("Introduce la plataforma: ");
            String plataforma = reader.nextLine();

            System.out.println("Introduce el año en que se lanzó: ");
            int año = reader.nextInt();
            reader.nextLine();

            Videojuego v = new Videojuego(titulo, categoria, plataforma, año);
            repo.save(v);

            System.out.println("Videojuego \"" + titulo + "\" añadido correctamente.\n");

        } catch (InputMismatchException e) {
            System.out.println("Introduce el tipo correcto de dato.");
            reader.nextLine(); // limpiar buffer
        }
    }

    public static void crearVideojuegoCase(Scanner reader, RepositorioVideojuego repo, boolean estado2) {
        do {
            System.out.println("1: Crear videojuego | 2: salir |");
            int respuesta3 = reader.nextInt();
            reader.nextLine();

            switch (respuesta3) {
                case 1:
                    crearVideojuego(reader, repo);
                    break;
                case 2:
                    estado2 = false;
                    System.out.println("Retornando... \n");
                    break;
                default:
                    System.out.println("Debes introducir un valor correcto.");
            }

        } while (estado2);
    }

    // ------------------ ELIMINAR VIDEOJUEGO POR ID ------------------
    public static void eliminarVideojuegoId(RepositorioVideojuego repo, Scanner reader) {
        if (repo.count() == 0) {
            System.out.println("La lista está vacía. Retornando...\n");
            return;
        }

        while (true) {
            System.out.println("-------Eliminar videojuego por ID------");
            List<Videojuego> lista = repo.findAll();
            for (Videojuego v : lista) {
                System.out.println("ID: " + v.getId() + ", título: " + v.getTitulo());
            }

            System.out.print("\nIntroduce un ID del videojuego deseado [0 para salir]: ");
            int id = reader.nextInt();
            reader.nextLine();

            if (id == 0) {
                System.out.println("Retornando... \n");
                break;
            }

            if (repo.existsById(id)) {
                Videojuego eliminado = repo.findById(id);
                repo.deleteById(id);
                System.out.println("El videojuego \"" + eliminado.getTitulo() + "\" ha sido eliminado con éxito.\n");
            } else {
                System.out.println("No se ha encontrado el ID especificado.\n");
            }

            if (repo.count() == 0) {
                System.out.println("La lista ha quedado vacía. Retornando...\n");
                break;
            }
        }
    }

    // ------------------ ELIMINAR TODA LA BIBLIOTECA ------------------
    public static void eliminarBiblioteca(RepositorioVideojuego repo, Scanner reader) {
        if (repo.count() == 0) {
            System.out.println("La lista está vacía... \n");
            return;
        }

        System.out.println("------ELIMINAR TODA LA BIBLIOTECA-------");
        System.out.println("¿Estás seguro [si/no]?");
        String seguro = reader.nextLine().toLowerCase();

        if (seguro.equals("si")) {
            repo.deleteAll();
            System.out.println("La biblioteca ha sido borrada con éxito.");
        } else if (seguro.equals("no")) {
            System.out.println("Retornando... \n");
        } else {
            System.out.println("Debes introducir un valor correcto.");
        }
    }

    // ------------------ EDITAR VIDEOJUEGO ------------------
    public static void editarBiblioteca(RepositorioVideojuego repo, Scanner reader) {
        if (repo.count() == 0) {
            System.out.println("La lista está vacía... \n");
            return;
        }

        while (true) {
            System.out.println("------EDITAR BIBLIOTECA------");
            System.out.println("Tu biblioteca actual:");
            for (Videojuego v : repo.findAll()) {
                System.out.println("ID: " + v.getId() + ", título: " + v.getTitulo()
                        + ", categoría: " + v.getCategoria()
                        + ", plataforma: " + v.getPlataforma()
                        + ", año: " + v.getAño());
            }

            System.out.print("\nIntroduce un ID del videojuego deseado [0 para salir]: ");
            int id = reader.nextInt();
            reader.nextLine();

            if (id == 0) {
                System.out.println("Retornando... \n");
                break;
            }

            if (!repo.existsById(id)) {
                System.out.println("El ID introducido no existe en la biblioteca.\n");
                continue;
            }

            Videojuego v = repo.findById(id);

            String respuestaEdicion;
            do {
                Menus.menuEditar();
                System.out.print("¿Qué campo deseas editar?: ");
                respuestaEdicion = reader.nextLine().trim().toLowerCase();

                switch (respuestaEdicion) {
                    case "titulo":
                        System.out.print("Nuevo título: ");
                        v.setTitulo(reader.nextLine());
                        System.out.println("Título cambiado con éxito.\n");
                        break;

                    case "categoria":
                        System.out.print("Nueva categoría: ");
                        v.setCategoria(reader.nextLine());
                        System.out.println("Categoría cambiada con éxito.\n");
                        break;

                    case "plataforma":
                        System.out.print("Nueva plataforma: ");
                        v.setPlataforma(reader.nextLine());
                        System.out.println("Plataforma cambiada con éxito.\n");
                        break;

                    case "año":
                        System.out.print("Nuevo año: ");
                        v.setAño(reader.nextInt());
                        reader.nextLine();
                        System.out.println("Año cambiado con éxito.\n");
                        break;

                    case "salir":
                        System.out.println("Retornando al menú principal...\n");
                        break;

                    default:
                        System.out.println("Debes introducir un campo válido.\n");
                }

            } while (!respuestaEdicion.equalsIgnoreCase("salir"));
        }
    }

    // ------------------ GUARDAR CAMBIOS EN ARCHIVO ------------------
    public static void guardarCambios(RepositorioVideojuego repo, File archivo) throws IOException {
        List<Videojuego> lista = repo.findAll();



        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Videojuego v : lista) {
                String linea = v.getId() + ";" + v.getTitulo() + ";" + v.getCategoria() + ";" +
                        v.getPlataforma() + ";" + v.getAño();
                bw.write(linea);
                bw.newLine();
            }
        }

        System.out.println("Cambios guardados con éxito en " + archivo.getAbsolutePath() + "\n");
    }

    // ------------------ MOSTRAR BIBLIOTECA ------------------
    public static void mostrarBiblioteca(Scanner reader, RepositorioVideojuego repo, File archivo) throws IOException {
        int opcion;

        do {
            Menus.menuMostrar();
            opcion = reader.nextInt();
            reader.nextLine();


            switch (opcion) {
                case 1:
                    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                        br.lines().forEach(System.out::println);
                        if(br.readLine().isEmpty()) {
                            System.out.println("El archivo esta vacio...");
                        }
                    }
                    break;

                case 2:
                    System.out.println("Tienes " + repo.count() + " juegos.");
                    break;

                case 3:
                    System.out.println("IDs disponibles:");
                    for (Videojuego v : repo.findAll()) {
                        System.out.println("ID: " + v.getId() + " - " + v.getTitulo());
                    }

                    System.out.print("\nIntroduce el ID del juego que quieres mostrar: ");
                    int id = reader.nextInt();
                    reader.nextLine();

                    if (repo.existsById(id)) {
                        Videojuego v = repo.findById(id);
                        System.out.println("ID: " + v.getId() + ";título: " + v.getTitulo() +
                                ", categoría: " + v.getCategoria() + ", plataforma: " + v.getPlataforma() +
                                ", año: " + v.getAño());
                    } else {
                        System.out.println("No se encontró ningún videojuego con ese ID. \n");
                    }
                    break;

                case 4:
                    System.out.println("Retornando... \n");
                    return;

                default:
                    System.out.println("Introduce un valor correcto...");
            }

        } while (true);
    }
}
