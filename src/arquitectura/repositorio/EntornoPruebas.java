package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EntornoPruebas {
    public static void main(String[] args) {

        //aqui haremos las pruebas de las funcionalidades que vayamos implementando
        Scanner reader = new Scanner(System.in);


        //creo el repositorio
        RepositorioVideojuego repo = new RepositorioVideojuego();

        //variables de las respuestas
        int respuesta1 = 0;
        int respuesta2 = 0;
        int respuesta3 = 0;
        boolean estado = true;
        boolean estado2 = true;




        //hago videojuegos en un bucle y los introduzco en el hashmap
        do {
            try {
                Menus.menu1();
                respuesta1 = reader.nextInt();
                reader.nextLine();

                //estructura switch para que el usuario elija
                switch(respuesta1) {
                    case 1:
                        Menus.menuGestion();
                        respuesta2 = reader.nextInt();
                        reader.nextLine();

                        //abro el segundo switch para el segundo menu
                        switch(respuesta2) {
                            case 1:
                                do{
                                    System.out.println("1: Crear videojuego | 2: salir |");
                                    respuesta3 = reader.nextInt();
                                    switch(respuesta3) {
                                        
                                    }

                                    Funciones.crearVideojuego(reader, repo);


                                }while(estado2);


                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("Introduce un valor correcto");

                        }


                }

            }catch(InputMismatchException e) {
                System.out.println("Debes de introducir un valor correcto.");
                reader.next();
            }
        }while(estado);
    }
}
