package arquitectura.repositorio;

public class Menus {
    public static void menu1() {
        System.out.println("------BIBLIOTECA DE VIDEOJUEGOS------");
        System.out.println("1: Gestionar biblioteca. ");
        System.out.println("2: Mostrar biblioteca");
        System.out.println("3. Cambiar usuario / Registrar nuevo usuario");
        System.out.println("0: Salir ");
        System.out.print("Elige una opción: ");
    }
    public static void menuGestion() {
        System.out.println("------GESTION DE BIBLIOTECA------");
        System.out.println("1: Añadir videojuego. ");
        System.out.println("2: Eliminar videojuego por ID.");
        System.out.println("3: Eliminar todos ");
        System.out.println("4: Editar videojuego.");
        System.out.println("5: Atrás ");

    }
    public static void menuMostrar() {
        System.out.println("-----MOSTRAR BIBLIOTECA-----");
        System.out.println("1: Mostrar lista de videojuegos");
        System.out.println("2: Mostrar cantidad de videojuegos");
        System.out.println("3: Mostrar videojuego con su nombre e identificador");
        System.out.println("4: Atrás");
    }
    public static void menuEditar() {
        System.out.println("------MENU DE EDICIÓN------");
        System.out.println("Título.");
        System.out.println("Categoría.");
        System.out.println("Plataforma.");
        System.out.println("Año.");
        System.out.println("Introduce [titulo | categoria | plataforma | año] para editar dicho campo");
        System.out.print(" o escribe [salir] para salir.");
    }
}
