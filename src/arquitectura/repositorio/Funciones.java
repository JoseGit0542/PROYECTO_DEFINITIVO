package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;
import arquitectura.dominio.Persona;

import java.util.*;

public class Funciones {

    private final Scanner reader;
    private final RepositorioVideojuego repo;
    private final RepositorioPersona repoPersona;
    private Persona personaActiva;

    public Funciones(Scanner reader, RepositorioVideojuego repo,
                     RepositorioPersona repoPersona, Persona personaActiva) {
        this.reader = reader;
        this.repo = repo;
        this.repoPersona = repoPersona;
        this.personaActiva = personaActiva;
    }

    // Permite actualizar el usuario activo tras un login
    public void setPersonaActiva(Persona personaActiva) {
        this.personaActiva = personaActiva;
    }

    public void crearVideojuego() {
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

            Videojuego v = new Videojuego(
                    titulo, categoria, plataforma, año, personaActiva.getId()
            );
            repo.save(v);

            System.out.println("Videojuego \"" + titulo + "\" añadido correctamente.\n");

        } catch (InputMismatchException e) {
            System.out.println("Introduce un tipo correcto de dato.");
            reader.nextLine();
        }
    }


    public void crearVideojuegoCase() {
        boolean estado2 = true;

        do {
            System.out.println("1: Crear videojuego | 2: salir |");
            int respuesta3 = reader.nextInt();
            reader.nextLine();

            switch (respuesta3) {
                case 1 -> crearVideojuego();
                case 2 -> {
                    estado2 = false;
                    System.out.println("Retornando... \n");
                }
                default -> System.out.println("Debes introducir un valor correcto.");
            }

        } while (estado2);
    }

    public void eliminarVideojuegoId() {

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

            System.out.print("\nIntroduce un ID [0 para salir]: ");
            int id = reader.nextInt();
            reader.nextLine();

            if (id == 0) {
                System.out.println("Retornando... \n");
                break;
            }

            if (repo.obtenerSiPertenece(id, personaActiva.getId()).isPresent()) {
                Videojuego eliminado = repo.findById(id);
                repo.deleteById(id);
                System.out.println("El videojuego \"" + eliminado.getTitulo() + "\" ha sido eliminado.\n");
            } else {
                System.out.println("ID no encontrado o no pertenece a tu cuenta.\n");
            }
        }
    }

    public void eliminarBiblioteca() {
        List<Videojuego> propios = repo.listarPorPersona(personaActiva.getId());

        if (propios.isEmpty()) {
            System.out.println("No tienes videojuegos que borrar. \n");
            return;
        }

        System.out.println("------ELIMINAR TODA TU BIBLIOTECA-------");
        System.out.println("¿Estás seguro [si/no]?");
        String seguro = reader.nextLine().toLowerCase();

        if (seguro.equals("si")) {
            repo.eliminarBibliotecaDePersona(personaActiva.getId());
            System.out.println("Tu biblioteca ha sido borrada con éxito.");
        } else {
            System.out.println("Retornando...\n");
        }
    }

    public void editarBiblioteca() {

        List<Videojuego> propios = repo.listarPorPersona(personaActiva.getId());

        if (propios.isEmpty()) {
            System.out.println("No tienes videojuegos para editar.\n");
            return;
        }

        while (true) {
            System.out.println("------EDITAR BIBLIOTECA------");
            for (Videojuego v : propios) {
                System.out.println("ID: " + v.getId() + ", título: " + v.getTitulo()
                        + ", categoría: " + v.getCategoria()
                        + ", plataforma: " + v.getPlataforma()
                        + ", año: " + v.getAño());
            }

            System.out.print("\nIntroduce un ID [0 para salir]: ");
            int id = reader.nextInt();
            reader.nextLine();

            if (id == 0) {
                System.out.println("Retornando...\n");
                return;
            }

            Optional<Videojuego> opt = repo.obtenerSiPertenece(id, personaActiva.getId());
            if (opt.isEmpty()) {
                System.out.println("El ID introducido no existe.\n");
                continue;
            }

            Videojuego v = opt.get();
            String opcionEdicion;

            do {
                Menus.menuEditar();
                System.out.print("¿Qué campo deseas editar?: ");
                opcionEdicion = reader.nextLine().trim().toLowerCase();

                switch (opcionEdicion) {
                    case "titulo" -> {
                        System.out.print("Nuevo título: ");
                        v.setTitulo(reader.nextLine());
                    }
                    case "categoria" -> {
                        System.out.print("Nueva categoría: ");
                        v.setCategoria(reader.nextLine());
                    }
                    case "plataforma" -> {
                        System.out.print("Nueva plataforma: ");
                        v.setPlataforma(reader.nextLine());
                    }
                    case "año" -> {
                        System.out.print("Nuevo año: ");
                        v.setAño(reader.nextInt());
                        reader.nextLine();
                    }
                    case "salir" -> System.out.println("Retornando...\n");
                    default -> System.out.println("Opción no válida.\n");
                }

            } while (!opcionEdicion.equals("salir"));
        }
    }

    public void mostrarBiblioteca() {

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
                                ", año: " + v.getAño());
                    }
                    System.out.println();
                }
                case 2 -> {
                    long cuenta = repo.contarPorPersona(personaActiva.getId());
                    System.out.println("Tienes " + cuenta + " juegos.\n");
                }
                case 3 -> {
                    System.out.println("IDs disponibles:");
                    for (Videojuego v : repo.listarPorPersona(personaActiva.getId())) {
                        System.out.println("ID: " + v.getId() + " - " + v.getTitulo());
                    }

                    System.out.print("Introduce un ID: ");
                    int id = reader.nextInt();
                    reader.nextLine();

                    Optional<Videojuego> opt = repo.obtenerSiPertenece(id, personaActiva.getId());
                    if (opt.isPresent()) {
                        Videojuego v = opt.get();
                        System.out.println("ID: " + v.getId() + "; título: " + v.getTitulo() +
                                ", categoría: " + v.getCategoria() + ", plataforma: " + v.getPlataforma() +
                                ", año: " + v.getAño() + "\n");
                    } else {
                        System.out.println("ID no encontrado.\n");
                    }
                }
                case 4 -> {
                    System.out.println("Retornando...\n");
                    return;
                }
                default -> System.out.println("Introduce un valor correcto...");
            }
        }
    }
}
