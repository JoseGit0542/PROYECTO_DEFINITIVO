package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EntornoPruebas {
    public static void main(String[] args) {

        //aqui haremos las pruebas de las funcionalidades que vayamos implementando
        Scanner reader = new Scanner(System.in);
        boolean estado = true;


        //creo el repositorio
        RepositorioVideojuego repo = new RepositorioVideojuego();


        //hago videojuegos en un bucle y los introduzco en el hashmap
        do {
            System.out.println("1: Crear un videojuego | 2: Salir ");
            int respuesta = reader.nextInt();
            reader.nextLine();

            switch(respuesta) {
                case 1:

                    try {
                        //llamo a la funcion pa que no sea un lio en el main
                        crearVideojuego(reader, repo);

                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    estado = false;
                    System.out.println("Saliendo...");

                    //muestro la lista
                    System.out.println("Su biblioteca es la siguiente: ");
                    for (Videojuego v : repo.getLista().values()) {
                        System.out.println(v);
                    }

                    break;
                default:
                    System.out.println("Debes de introducir un valor valido.");

            }


        }while(estado);

        //despues los muestro para comprobar que todo funciona correctamente

    }
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
}
