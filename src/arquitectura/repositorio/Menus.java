package arquitectura.repositorio;

public class Menus {

    public static void menu1() {
        System.out.println("\n------BIBLIOTECA DE VIDEOJUEGOS------");
        System.out.println("1: Gestionar biblioteca");
        System.out.println("2: Mostrar biblioteca");
        System.out.println("3: Gestionar usuario");
        System.out.println("0: Salir");
        System.out.print("Elige una opción: ");
    }

    public static void menuGestion() {
        System.out.println("\n------GESTIÓN DE BIBLIOTECA------");
        System.out.println("1: Añadir videojuego");
        System.out.println("2: Eliminar videojuego por ID");
        System.out.println("3: Eliminar todos los videojuegos");
        System.out.println("4: Editar videojuego");
        System.out.println("5: Atrás");
        System.out.print("Elige una opción: ");
    }
    public static void gestionarUsuario() {
        System.out.println("\n------ GESTIÓN DE TU USUARIO ------");
        System.out.println("1 - Editar mi usuario");
        System.out.println("2 - Eliminar mi usuario");
        System.out.println("3 - Cambiar de usuario (login)");
        System.out.println("0 - Salir");
        System.out.print("Elige una opción: ");
    }

    public static void menuMostrar() {
        System.out.println("\n-----MOSTRAR BIBLIOTECA-----");
        System.out.println("1: Mostrar lista de videojuegos");
        System.out.println("2: Mostrar cantidad de videojuegos");
        System.out.println("3: Mostrar videojuego con su nombre e identificador");
        System.out.println("4: Atrás");
        System.out.print("Elige una opción: ");
    }

    public static void menuEditar() {
        System.out.println("\n------MENÚ DE EDICIÓN------");
        System.out.println("Título");
        System.out.println("Categoría");
        System.out.println("Plataforma");
        System.out.println("Año");
        System.out.println("Introduce [titulo | categoria | año] para editar dicho campo");
        System.out.print("o escribe [salir] para salir: ");
    }
    public static void opcionesOrdenacion() {
        System.out.println("\n--- FUNCIONES DE ORDENACIÓN ---");
        System.out.println("1: Mostrar por categoría ordenado por año");
        System.out.println("2: Mostrar títulos ordenados alfabéticamente");
        System.out.println("3: Mostrar por plataforma");
        System.out.println("4: Comprobar si existe un título");
        System.out.println("5: Atrás");
        System.out.print("Elige una opción: ");
    }
}
