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
        int id = 0;
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
                                    reader.nextLine();

                                    switch(respuesta3) {
                                        case 1:
                                            Funciones.crearVideojuego(reader, repo);
                                            break;
                                        case 2:
                                            estado2 = false;
                                            System.out.println("Retornando...");
                                            break;
                                        default:
                                            System.out.println("Debes de introducir un valor correcto.");

                                    }

                                }while(estado2);
                                break;
                            case 2:
                                if(repo.getLista().isEmpty()) {
                                    System.out.println("La lista está vacia. Retornando... \n");
                                    continue;
                                }

                                System.out.println("-------Eliminar videojuego por ID------");
                                //busco el videojuego por id y lo elimino
                                System.out.println("Los ids y titulo son los siguientes: ");
                                for (int key : repo.getLista().keySet()) {
                                    System.out.println("ID: " + repo.getLista().get(key).getId() + ", título: " + repo.getLista().get(key).getTitulo() + "\n");
                                }

                                //pido al usuario el id para eliminar el videojuego
                                System.out.print("Introduce un ID del videojuego deseado [0 para salir]: ");
                                id = reader.nextInt();
                                reader.nextLine(); //limpiar el buffer que si no se repite

                                //paro el bucle si id == 0
                                if(id == 0) {
                                    System.out.println("Retornando...");
                                    break;
                                }

                                //busco el elemento con id introducida y lo borro
                                boolean encontrado = false;
                                for(int key : repo.getLista().keySet()) {
                                    if(id == key) {
                                        System.out.println("El videojuego " + repo.getLista().get(id).getTitulo() + " ha sido eliminado con éxito. \n");
                                        repo.getLista().remove(id);
                                        encontrado = true;
                                        break;
                                    }
                                }

                                //si no lo encuentra da mensaje
                                if (!encontrado) {
                                    System.out.println("No se ha encontrado el id especificado.");
                                }

                                break;
                            case 3:
                                System.out.println("------ELIMINAR TODA LA BIBLIOTECA-------");
                                System.out.println("¿Estás seguro [si/no]?");
                                String seguro = reader.nextLine().toLowerCase();
                                if (seguro.equals("si")) {
                                    System.out.println("La biblioteca ha sido borrada con exito.");

                                    //bucle para borrar toda la biblioteca
                                    repo.getLista().clear();

                                } else if (seguro.equals("no")) {
                                    System.out.println("Retornando...");
                                } else {
                                    System.out.println("Debes de introducir un valor correcto");
                                }

                                break;
                            case 4:
                                System.out.println("------EDITAR BIBLIOTECA------");

                                //muestro todos los elementos
                                System.out.println("Tu biblioteca actual: ");
                                for (int key : repo.getLista().keySet()) {
                                    System.out.println("ID: " + repo.getLista().get(key).getId() + ", título: " + repo.getLista().get(key).getTitulo()
                                    + ", categoria: " + repo.getLista().get(key).getCategoria() + ", plataforma: " + repo.getLista().get(key).getPlataforma()
                                    + ", año de salida: " + repo.getLista().get(key).getAño() + "\n");
                                }

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