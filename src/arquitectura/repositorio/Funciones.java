package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;
import arquitectura.dominio.Persona;

import java.io.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Funciones {

    // ------------------ CREAR VIDEOJUEGO ------------------
    public static void crearVideojuego(Scanner reader, RepositorioVideojuego repo, Persona personaActiva) {
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

            Videojuego v = new Videojuego(titulo, categoria, plataforma, año, personaActiva.getId());
            repo.save(v);

            System.out.println("Videojuego \"" + titulo + "\" añadido correctamente para " + personaActiva.getNombre() + ".\n");

        } catch (InputMismatchException e) {
            System.out.println("Introduce el tipo correcto de dato.");
            reader.nextLine(); // limpiar buffer
        }
    }

    public static void crearVideojuegoCase(Scanner reader, RepositorioVideojuego repo, Persona personaActiva, boolean estado2) {
        do {
            System.out.println("1: Crear videojuego | 2: salir |");
            int respuesta3 = reader.nextInt();
            reader.nextLine();

            switch (respuesta3) {
                case 1:
                    crearVideojuego(reader, repo, personaActiva);
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

    // ------------------ ELIMINAR VIDEOJUEGO POR ID (solo de persona activa) ------------------
    public static void eliminarVideojuegoId(RepositorioVideojuego repo, Scanner reader, Persona personaActiva) {
        if (repo.count() == 0) {
            System.out.println("La lista está vacía. Retornando...\n");
            return;
        }

        while (true) {
            System.out.println("-------Eliminar videojuego por ID------");
            List<Videojuego> lista = repo.findAll();
            boolean any = false;
            for (Videojuego v : lista) {
                if (v.getIdPersona() == personaActiva.getId()) {
                    System.out.println("ID: " + v.getId() + ", título: " + v.getTitulo());
                    any = true;
                }
            }
            if (!any) {
                System.out.println("No tienes videojuegos registrados. Retornando...\n");
                return;
            }

            System.out.print("\nIntroduce un ID del videojuego deseado [0 para salir]: ");
            int id = reader.nextInt();
            reader.nextLine();

            if (id == 0) {
                System.out.println("Retornando... \n");
                break;
            }

            if (repo.existsById(id) && repo.findById(id).getIdPersona() == personaActiva.getId()) {
                Videojuego eliminado = repo.findById(id);
                repo.deleteById(id);
                System.out.println("El videojuego \"" + eliminado.getTitulo() + "\" ha sido eliminado con éxito.\n");
            } else {
                System.out.println("No se ha encontrado el ID especificado (o no pertenece a tu cuenta).\n");
            }

            if (repo.count() == 0) {
                System.out.println("La lista global ha quedado vacía. Retornando...\n");
                break;
            }
        }
    }

    // ------------------ ELIMINAR TODA LA BIBLIOTECA DE LA PERSONA ACTIVA ------------------
    public static void eliminarBiblioteca(RepositorioVideojuego repo, Scanner reader, Persona personaActiva) {
        boolean any = false;
        for (Videojuego v : repo.findAll()) {
            if (v.getIdPersona() == personaActiva.getId()) {
                any = true;
                break;
            }
        }
        if (!any) {
            System.out.println("No tienes videojuegos que borrar. \n");
            return;
        }

        System.out.println("------ELIMINAR TODA TU BIBLIOTECA-------");
        System.out.println("¿Estás seguro [si/no]? (solo eliminará tus juegos)");
        String seguro = reader.nextLine().toLowerCase();

        if (seguro.equals("si")) {
            // eliminar solo los juegos de la persona activa
            repo.findAll().stream()
                    .filter(v -> v.getIdPersona() == personaActiva.getId())
                    .map(Videojuego::getId)
                    .toList()
                    .forEach(repo::deleteById);
            System.out.println("Tu biblioteca ha sido borrada con éxito.");
        } else if (seguro.equals("no")) {
            System.out.println("Retornando... \n");
        } else {
            System.out.println("Debes introducir un valor correcto.");
        }
    }

    // ------------------ EDITAR VIDEOJUEGO (solo de la persona) ------------------
    public static void editarBiblioteca(RepositorioVideojuego repo, Scanner reader, Persona personaActiva) {
        boolean any = false;
        for (Videojuego v : repo.findAll()) {
            if (v.getIdPersona() == personaActiva.getId()) {
                any = true;
                break;
            }
        }
        if (!any) {
            System.out.println("No tienes videojuegos para editar. \n");
            return;
        }

        while (true) {
            System.out.println("------EDITAR BIBLIOTECA------");
            System.out.println("Tu biblioteca actual:");
            for (Videojuego v : repo.findAll()) {
                if (v.getIdPersona() == personaActiva.getId()) {
                    System.out.println("ID: " + v.getId() + ", título: " + v.getTitulo()
                            + ", categoría: " + v.getCategoria()
                            + ", plataforma: " + v.getPlataforma()
                            + ", año: " + v.getAño());
                }
            }

            System.out.print("\nIntroduce un ID del videojuego deseado [0 para salir]: ");
            int id = reader.nextInt();
            reader.nextLine();

            if (id == 0) {
                System.out.println("Retornando... \n");
                break;
            }

            if (!repo.existsById(id) || repo.findById(id).getIdPersona() != personaActiva.getId()) {
                System.out.println("El ID introducido no existe en tu biblioteca.\n");
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

    // ------------------ GUARDAR CAMBIOS (delegado al repositorio) ------------------
    public static void guardarCambios(RepositorioVideojuego repo, File archivo) throws IOException {
        repo.guardarEnArchivo(archivo);
    }

    // ------------------ MOSTRAR BIBLIOTECA (solo de persona activa) ------------------
    public static void mostrarBiblioteca(Scanner reader, RepositorioVideojuego repo, File archivo, Persona personaActiva) throws IOException {
        if (!archivo.exists()) {
            System.out.println("No se encontró el archivo: " + archivo.getAbsolutePath());
            System.out.println("La biblioteca está vacía.");
            return;
        }
        do {
            Menus.menuMostrar();
            int opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
                case 1:
                    // Mostrar los juegos del archivo
                    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            if (linea.trim().isEmpty()) continue;
                            // formato: id;titulo;categoria;plataforma;año;idPersona
                            String[] datos = linea.split(";");
                            if (datos.length < 6) continue;
                            int idPersona = Integer.parseInt(datos[5].trim());
                            if (idPersona == personaActiva.getId()) {
                                System.out.println(linea);
                            }
                        }
                        System.out.println("\n");
                    }
                    break;

                case 2:
                    // Contar solo los juegos de esta persona
                    long cuenta = repo.findAll().stream().filter(v -> v.getIdPersona() == personaActiva.getId()).count();
                    System.out.println("Tienes " + cuenta + " juegos.\n");
                    break;

                case 3:
                    System.out.println("IDs disponibles en tu cuenta:");
                    for (Videojuego v : repo.findAll()) {
                        if (v.getIdPersona() == personaActiva.getId()) {
                            System.out.println("ID: " + v.getId() + " - " + v.getTitulo() + "\n");
                        }
                    }

                    System.out.print("Introduce el ID del juego que quieres mostrar: ");
                    int id = reader.nextInt();
                    reader.nextLine();

                    if (repo.existsById(id) && repo.findById(id).getIdPersona() == personaActiva.getId()) {
                        Videojuego v = repo.findById(id);
                        System.out.println("ID: " + v.getId() + "; título: " + v.getTitulo() +
                                ", categoría: " + v.getCategoria() + ", plataforma: " + v.getPlataforma() +
                                ", año: " + v.getAño() + "\n");
                    } else {
                        System.out.println("No se encontró ningún videojuego con ese ID en tu cuenta. \n");
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
