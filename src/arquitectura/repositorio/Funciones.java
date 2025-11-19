package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;
import arquitectura.dominio.Persona;

import java.io.*;
import java.util.*;

import arquitectura.dominio.Videojuego;
import arquitectura.dominio.Persona;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Funciones {

    public static void crearVideojuego(Scanner reader, RepositorioVideojuego repo, Persona personaActiva) {
        try {
            System.out.print("Introduce el título del videojuego: ");
            String titulo = reader.nextLine();

            System.out.print("Introduce la categoría del videojuego: ");
            String categoria = reader.nextLine();

            System.out.print("Introduce la plataforma: ");
            String plataforma = reader.nextLine();

            System.out.print("Introduce el año en que se lanzó: ");
            int año = reader.nextInt();
            reader.nextLine();

            Videojuego v = new Videojuego(titulo, categoria, plataforma, año, personaActiva.getId());
            repo.save(v);

            System.out.println("Videojuego \"" + titulo + "\" añadido correctamente para " + personaActiva.getNombre() + ".\n");

        } catch (InputMismatchException e) {
            System.out.println("Introduce el tipo correcto de dato.");
            reader.nextLine();
        }
    }

    public static void crearVideojuegoCase(Scanner reader, RepositorioVideojuego repo, Persona personaActiva) {
        boolean estado2 = true;
        do {
            System.out.println("1: Crear videojuego | 2: salir |");
            int respuesta3 = reader.nextInt();
            reader.nextLine();

            switch (respuesta3) {
                case 1 -> crearVideojuego(reader, repo, personaActiva);
                case 2 -> {
                    estado2 = false;
                    System.out.println("Retornando... \n");
                }
                default -> System.out.println("Debes introducir un valor correcto.");
            }

        } while (estado2);
    }

    public static void eliminarVideojuegoId(RepositorioVideojuego repo, Scanner reader, Persona personaActiva) {
        if (repo.count() == 0) {
            System.out.println("La lista está vacía. Retornando...\n");
            return;
        }

        while (true) {
            System.out.println("-------Eliminar videojuego por ID------");
            List<Videojuego> lista = repo.listarPorPersona(personaActiva.getId());
            if (lista.isEmpty()) {
                System.out.println("No tienes videojuegos registrados. Retornando...\n");
                return;
            }
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

            if (repo.obtenerSiPertenece(id, personaActiva.getId()).isPresent()) {
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

    public static void eliminarBiblioteca(RepositorioVideojuego repo, Scanner reader, Persona personaActiva) {
        List<Videojuego> propios = repo.listarPorPersona(personaActiva.getId());
        if (propios.isEmpty()) {
            System.out.println("No tienes videojuegos que borrar. \n");
            return;
        }

        System.out.println("------ELIMINAR TODA TU BIBLIOTECA-------");
        System.out.println("¿Estás seguro [si/no]? (solo eliminará tus juegos)");
        String seguro = reader.nextLine().toLowerCase();

        if (seguro.equals("si")) {
            repo.eliminarBibliotecaDePersona(personaActiva.getId());
            System.out.println("Tu biblioteca ha sido borrada con éxito.");
        } else if (seguro.equals("no")) {
            System.out.println("Retornando... \n");
        } else {
            System.out.println("Debes introducir un valor correcto.");
        }
    }

    public static void editarBiblioteca(RepositorioVideojuego repo, Scanner reader, Persona personaActiva) {
        List<Videojuego> propios = repo.listarPorPersona(personaActiva.getId());
        if (propios.isEmpty()) {
            System.out.println("No tienes videojuegos para editar. \n");
            return;
        }

        while (true) {
            System.out.println("------EDITAR BIBLIOTECA------");
            System.out.println("Tu biblioteca actual:");
            for (Videojuego v : propios) {
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

            if (repo.obtenerSiPertenece(id, personaActiva.getId()).isEmpty()) {
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
                    case "titulo" -> {
                        System.out.print("Nuevo título: ");
                        v.setTitulo(reader.nextLine());
                        System.out.println("Título cambiado con éxito.\n");
                    }
                    case "categoria" -> {
                        System.out.print("Nueva categoría: ");
                        v.setCategoria(reader.nextLine());
                        System.out.println("Categoría cambiada con éxito.\n");
                    }
                    case "plataforma" -> {
                        System.out.print("Nueva plataforma: ");
                        v.setPlataforma(reader.nextLine());
                        System.out.println("Plataforma cambiada con éxito.\n");
                    }
                    case "año" -> {
                        System.out.print("Nuevo año: ");
                        v.setAño(reader.nextInt());
                        reader.nextLine();
                        System.out.println("Año cambiado con éxito.\n");
                    }
                    case "salir" -> System.out.println("Retornando al menú principal...\n");
                    default -> System.out.println("Debes introducir un campo válido.\n");
                }

            } while (!respuestaEdicion.equalsIgnoreCase("salir"));
        }
    }

    // Mostrar sin tocar archivos: todo desde el repositorio
    public static void mostrarBiblioteca(Scanner reader, RepositorioVideojuego repo, Persona personaActiva) {
        if (repo.contarPorPersona(personaActiva.getId()) == 0) {
            System.out.println("La biblioteca está vacía.");
            return;
        }

        while (true) {
            Menus.menuMostrar();
            int opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
                case 1 -> {
                    for (Videojuego v : repo.listarPorPersona(personaActiva.getId())) {
                        System.out.println("ID: " + v.getId() + "; título: " + v.getTitulo() +
                                ", categoría: " + v.getCategoria() + ", plataforma: " + v.getPlataforma() +
                                ", año: " + v.getAño() + "; idPersona: " + v.getIdPersona());
                    }
                    System.out.println();
                }
                case 2 -> {
                    long cuenta = repo.contarPorPersona(personaActiva.getId());
                    System.out.println("Tienes " + cuenta + " juegos.\n");
                }
                case 3 -> {
                    System.out.println("IDs disponibles en tu cuenta:");
                    for (Videojuego v : repo.listarPorPersona(personaActiva.getId())) {
                        System.out.println("ID: " + v.getId() + " - " + v.getTitulo() + "\n");
                    }
                    System.out.print("Introduce el ID del juego que quieres mostrar: ");
                    int id = reader.nextInt();
                    reader.nextLine();

                    Optional<Videojuego> opt = repo.obtenerSiPertenece(id, personaActiva.getId());
                    if (opt.isPresent()) {
                        Videojuego v = opt.get();
                        System.out.println("ID: " + v.getId() + "; título: " + v.getTitulo() +
                                ", categoría: " + v.getCategoria() + ", plataforma: " + v.getPlataforma() +
                                ", año: " + v.getAño() + "\n");
                    } else {
                        System.out.println("No se encontró ningún videojuego con ese ID en tu cuenta. \n");
                    }
                }
                case 4 -> {
                    System.out.println("Retornando... \n");
                    return;
                }
                default -> System.out.println("Introduce un valor correcto...");
            }
        }
    }
}


