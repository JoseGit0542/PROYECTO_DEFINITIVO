package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;
import arquitectura.dominio.Persona;
import arquitectura.dominio.Plataforma;

import java.util.*;

public class Funciones {

    private final Scanner reader;
    private final RepositorioVideojuego repo;
    private final RepositorioPersona repoPersona;
    private final RepositorioPlataforma repoPlataforma;
    private Persona personaActiva;

    public Funciones(Scanner reader,
                     RepositorioVideojuego repo,
                     RepositorioPersona repoPersona,
                     RepositorioPlataforma repoPlataforma,
                     Persona personaActiva) {
        this.reader = reader;
        this.repo = repo;
        this.repoPersona = repoPersona;
        this.repoPlataforma = repoPlataforma;
        this.personaActiva = personaActiva;
    }

    public void setPersonaActiva(Persona personaActiva) {
        this.personaActiva = personaActiva;
    }

    //crear videojuego
    public void crearVideojuego() {
        try {
            System.out.print("Introduce el título del videojuego: ");
            String titulo = reader.nextLine();

            System.out.print("Introduce la categoría del videojuego: ");
            String categoria = reader.nextLine();

            System.out.println("Plataformas disponibles:");
            for (Plataforma p : repoPlataforma.findAll()) {
                System.out.println(p.getId() + " - " + p.getNombre());
            }

            System.out.print("Introduce el ID de la plataforma: ");
            int idPlataforma = reader.nextInt();
            reader.nextLine();

            if (repoPlataforma.findById(idPlataforma) == null) {
                System.out.println("Plataforma no válida.\n");
                return;
            }

            System.out.print("Introduce el año en que se lanzó: ");
            int año = reader.nextInt();
            reader.nextLine();

            Videojuego v = new Videojuego(
                    titulo, categoria, año, personaActiva.getId(), idPlataforma
            );

            repo.save(v);
            System.out.println("Videojuego añadido correctamente.\n");

        } catch (InputMismatchException e) {
            System.out.println("Error: tipo de dato incorrecto.\n");
            reader.nextLine();
        }
    }

    //menu para crear videojuego
    public void crearVideojuegoCase() {
        boolean estado = true;

        do {
            System.out.println("1: Crear videojuego | 2: Salir");
            int opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
                case 1 -> crearVideojuego();
                case 2 -> {
                    estado = false;
                    System.out.println("Retornando...\n");
                }
                default -> System.out.println("Opción no válida.\n");
            }
        } while (estado);
    }

    //eliminar persona
    public void eliminarPersona() {
        System.out.println("------ ELIMINAR USUARIO ------");
        System.out.println("Usuarios registrados:");

        for (Persona p : repoPersona.findAll()) {
            System.out.println(p.getId() + " - " + p.getNombre());
        }

        System.out.print("Introduce el ID del usuario a eliminar [0 para salir]: ");
        int id = reader.nextInt();
        reader.nextLine();

        if (id == 0) {
            System.out.println("Retornando...\n");
            return;
        }

        if (!repoPersona.existsById(id)) {
            System.out.println("El usuario no existe.\n");
            return;
        }

        System.out.print("¿Seguro que deseas eliminar este usuario y TODOS sus juegos? [si/no]: ");
        String confirmacion = reader.nextLine().toLowerCase();

        if (!confirmacion.equals("si")) {
            System.out.println("Operación cancelada.\n");
            return;
        }

        repoPersona.deletePersonaConJuegos(id, repo);

        if (personaActiva != null && personaActiva.getId() == id) {
            personaActiva = null;
            System.out.println("Usuario activo eliminado. Debes iniciar sesión de nuevo.\n");
        } else {
            System.out.println("Usuario eliminado correctamente.\n");
        }
    }

    //eliminar videojuego por id
    public void eliminarVideojuegoId() {
        List<Videojuego> propios = repo.listarPorPersona(personaActiva.getId());

        if (propios.isEmpty()) {
            System.out.println("No tienes videojuegos registrados.\n");
            return;
        }

        while (true) {
            System.out.println("-------Eliminar videojuego por ID------");
            for (Videojuego v : propios) {
                System.out.println("ID: " + v.getId() + " - " + v.getTitulo());
            }

            System.out.print("Introduce un ID [0 para salir]: ");
            int id = reader.nextInt();
            reader.nextLine();

            if (id == 0) return;

            Videojuego objetivo = propios.stream()
                    .filter(j -> j.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (objetivo != null) {
                repo.deleteById(id);
                propios.remove(objetivo);
                System.out.println("Videojuego eliminado.\n");
            } else {
                System.out.println("ID no encontrado.\n");
            }
        }
    }

    //eliminar biblioteca completa
    public void eliminarBiblioteca() {
        List<Videojuego> propios = repo.listarPorPersona(personaActiva.getId());

        if (propios.isEmpty()) {
            System.out.println("No tienes videojuegos que borrar.\n");
            return;
        }

        System.out.print("¿Estás seguro de borrar toda tu biblioteca? [si/no]: ");
        String seguro = reader.nextLine().toLowerCase();

        if (seguro.equals("si")) {
            repo.eliminarBibliotecaDePersona(personaActiva.getId());
            System.out.println("Biblioteca eliminada.\n");
        } else {
            System.out.println("Operación cancelada.\n");
        }
    }

    //editar biblioteca
    public void editarBiblioteca() {
        List<Videojuego> propios = repo.listarPorPersona(personaActiva.getId());

        if (propios.isEmpty()) {
            System.out.println("No tienes videojuegos para editar.\n");
            return;
        }

        while (true) {
            System.out.println("------EDITAR BIBLIOTECA------");
            for (Videojuego v : propios) {
                System.out.println("ID: " + v.getId() + ", título: " + v.getTitulo() +
                        ", categoría: " + v.getCategoria() + ", año: " + v.getAño());
            }

            System.out.print("Introduce un ID [0 para salir]: ");
            int id = reader.nextInt();
            reader.nextLine();

            if (id == 0) return;

            Videojuego v = propios.stream()
                    .filter(j -> j.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (v == null) {
                System.out.println("ID no válido.\n");
                continue;
            }

            String opcion;
            do {
                Menus.menuEditar();
                opcion = reader.nextLine().toLowerCase();

                switch (opcion) {
                    case "titulo" -> {
                        System.out.print("Nuevo título: ");
                        v.setTitulo(reader.nextLine());
                        repo.save(v);
                    }
                    case "categoria" -> {
                        System.out.print("Nueva categoría: ");
                        v.setCategoria(reader.nextLine());
                        repo.save(v);
                    }
                    case "año" -> {
                        System.out.print("Nuevo año: ");
                        v.setAño(reader.nextInt());
                        reader.nextLine();
                        repo.save(v);
                    }
                    case "salir" -> System.out.println("Retornando...\n");
                    default -> System.out.println("Opción no válida.\n");
                }
            } while (!opcion.equals("salir"));
        }
    }

    //mostrar biblioteca
    public void mostrarBiblioteca() {
        List<Videojuego> propios = repo.listarPorPersona(personaActiva.getId());

        if (propios.isEmpty()) {
            System.out.println("La biblioteca está vacía.\n");
            return;
        }

        while (true) {
            Menus.menuMostrar();
            int opcion = reader.nextInt();
            reader.nextLine();

            switch (opcion) {
                case 1 -> {
                    for (Videojuego v : propios) {
                        Plataforma p = repoPlataforma.findById(v.getIdPlataforma());
                        System.out.println(
                                "ID: " + v.getId() +
                                        ", título: " + v.getTitulo() +
                                        ", categoría: " + v.getCategoria() +
                                        ", plataforma: " + p.getNombre() +
                                        ", año: " + v.getAño()
                        );
                    }
                    System.out.println();
                }
                case 2 -> System.out.println("Tienes " + propios.size() + " juegos.\n");
                case 3 -> {
                    System.out.print("Introduce un ID: ");
                    int id = reader.nextInt();
                    reader.nextLine();

                    Videojuego v = propios.stream()
                            .filter(j -> j.getId() == id)
                            .findFirst()
                            .orElse(null);

                    if (v != null) {
                        Plataforma p = repoPlataforma.findById(v.getIdPlataforma());
                        System.out.println(
                                "ID: " + v.getId() +
                                        ", título: " + v.getTitulo() +
                                        ", categoría: " + v.getCategoria() +
                                        ", plataforma: " + p.getNombre() +
                                        ", año: " + v.getAño() + "\n"
                        );
                    } else {
                        System.out.println("ID no encontrado.\n");
                    }
                }
                case 4 -> {
                    System.out.println("Retornando...\n");
                    return;
                }
                default -> System.out.println("Opción no válida.\n");
            }
        }
    }

    //crear usuario
    public void crearUsuario() {
        System.out.print("Introduce el nombre del nuevo usuario: ");
        String nombre = reader.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("Nombre inválido.\n");
            return;
        }

        Persona nuevo = new Persona(nombre);
        repoPersona.save(nuevo);
        System.out.println("Usuario creado con ID: " + nuevo.getId() + "\n");
    }

    //gestionar usuario activo
    public void gestionarUsuarioActivo() {
        if (personaActiva == null) {
            System.out.println("No hay usuario activo. Debes iniciar sesión primero.\n");
            return;
        }

        boolean salir = false;

        while (!salir) {
            Menus.gestionarUsuario();

            String linea = reader.nextLine().trim();
            if (linea.isEmpty()) {
                System.out.println("Debes introducir un número válido.\n");
                continue;
            }

            int opcion;
            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Debes introducir un número válido.\n");
                continue;
            }

            switch (opcion) {
                case 1 -> editarUsuarioActivo();
                case 2 -> eliminarUsuarioActivo();
                case 3 -> cambiarUsuario();
                case 0 -> salir = true;
                default -> System.out.println("Opción no válida.\n");
            }
        }
    }

    //editar usuario activo
    private void editarUsuarioActivo() {
        System.out.print("Introduce tu nuevo nombre: ");
        String nuevoNombre = reader.nextLine().trim();

        if (!nuevoNombre.isEmpty()) {
            personaActiva.setNombre(nuevoNombre);
            repoPersona.save(personaActiva);
            System.out.println("Nombre actualizado correctamente.\n");
        } else {
            System.out.println("Nombre inválido, operación cancelada.\n");
        }
    }

    //eliminar usuario activo
    private void eliminarUsuarioActivo() {
        System.out.print("¿Seguro que deseas eliminar tu usuario y todos tus juegos? [si/no]: ");
        String confirm = reader.nextLine().toLowerCase();

        if (confirm.equals("si")) {
            repoPersona.deletePersonaConJuegos(personaActiva.getId(), repo);
            personaActiva = null;
            System.out.println("Usuario eliminado correctamente. Debes iniciar sesión de nuevo.\n");
        } else {
            System.out.println("Operación cancelada.\n");
        }
    }

    //cambiar de usuario
    private void cambiarUsuario() {
        System.out.println("---- Cambiar de usuario ----");
        personaActiva = RepositorioPersona.login(repoPersona);
    }
}