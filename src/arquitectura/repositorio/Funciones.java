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
}
