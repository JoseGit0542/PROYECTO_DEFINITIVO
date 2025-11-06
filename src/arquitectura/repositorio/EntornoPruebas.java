package arquitectura.repositorio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EntornoPruebas {
    public static void main(String[] args) {

        //aqui haremos las pruebas de las funcionalidades que vayamos implementando
        Scanner reader = new Scanner(System.in);


        //creo el repositorio
        RepositorioVideojuego rp = new RepositorioVideojuego();

        //variables de las respuestas
        int respuesta1 = 0;
        int respuesta2 = 0;
        int id = 0;
        boolean estado = true;
        boolean estado2 = true;
        File archivo = new File("archivo.txt");



        //hago videojuegos en un bucle y los introduzco en el hashmap
        do {
            try {
                Menus.menu1();
                respuesta1 = reader.nextInt();
                reader.nextLine();

                //estructura switch para que el usuario elija
                switch (respuesta1) {
                    case 1:
                        Menus.menuGestion();
                        respuesta2 = reader.nextInt();
                        reader.nextLine();

                        //abro el segundo switch para el segundo menu
                        switch (respuesta2) {
                            case 1:
                                Funciones.crearVideojuegoCase(reader, rp, estado2);
                                break;
                            case 2:
                                Funciones.eliminarVideojuegoId(rp, reader);
                                break;
                            case 3:
                                Funciones.eliminarBiblioteca(rp, reader);

                                break;
                            case 4:
                                Funciones.editarBiblioteca(rp, reader);
                                break;
                            case 5:
                                System.out.println("Retornando... \n");
                                break;
                            default:
                                System.out.println("Introduce un valor correcto");

                        }

                        break;
                    case 2:
                        //compruebo que la lista esta vacía
                        if (rp.getLista().isEmpty()) {
                            System.out.println("La lista está vacía...\n");
                            break;
                        }

                        int opcion = 0;
                        do {


                            System.out.println("");
                            Menus.menuMostrar();
                            opcion = reader.nextInt();
                            switch (opcion) {
                                case 1:
                                    System.out.println("Tu biblioteca es la siguiente: \n");
                                    for (int key : rp.getLista().keySet()) {
                                        System.out.println("Id: " + rp.getLista().get(key).getId() + " título: " + rp.getLista().get(key).getTitulo() + ", categoría: " + rp.getLista().get(key).getCategoria() +
                                                ", plataforma: " + rp.getLista().get(key).getPlataforma() + ", año: " + rp.getLista().get(key).getAño());
                                    }
                                    break;

                                case 2:
                                    long contador = rp.count();
                                    System.out.println("Tamaño de la biblioteca: " + contador);
                                    break;

                                case 3:
                                    System.out.println("Lista de IDS: ");
                                    for (int key : rp.getLista().keySet()) {
                                        System.out.print("[ " + rp.getLista().get(key).getId() + " ], ");
                                    }
                                    System.out.println("\n");
                                    System.out.println("introduce el id del juego que quieres mostrar: ");


                                    id = reader.nextInt();
                                    reader.nextLine();

                                    if (rp.existsById(id)) {
                                        System.out.println(rp.findById(id));
                                    } else {
                                        throw new IllegalArgumentException("Debes introducir un id que exista");
                                    }


                                    break;

                                case 4:
                                    System.out.println("Retornando...\n");
                                    break;
                            }


                        } while (opcion != 4);
                        break;
                    case 3:
                        Funciones.guardarCambios(rp, archivo);
                        break;
                    case 0:
                        //salir del programa
                        System.out.println("Saliendo del programa...");
                        estado = false;
                        break;
                    default:
                        System.out.println("Vuelve a introducir un valor.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Introduce un valor correcto. \n");
                reader.next();
            } catch (IllegalArgumentException e) {
                System.out.println("Introduce un argumento correcto. \n");
                reader.next();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (estado);
    }



}


