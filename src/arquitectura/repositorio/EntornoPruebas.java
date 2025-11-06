package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
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
                                Funciones.CrearVideojuego(reader, rp, estado2);
                                break;
                            case 2:
                                Funciones.EliminarVideojuegoId(rp, reader);
                                break;
                            case 3:
                                System.out.println("------ELIMINAR TODA LA BIBLIOTECA-------");
                                System.out.println("¿Estás seguro [si/no]?");
                                String seguro = reader.nextLine().toLowerCase();
                                if (seguro.equals("si")) {
                                    System.out.println("La biblioteca ha sido borrada con exito.");

                                    //bucle para borrar toda la biblioteca
                                    rp.getLista().clear();

                                } else if (seguro.equals("no")) {
                                    System.out.println("Retornando...");
                                } else {
                                    System.out.println("Debes de introducir un valor correcto");
                                }

                                break;
                            case 4:
                                //si la lista está vacía me salgo directamente
                                if(rp.getLista().isEmpty()) {
                                    System.out.println("La lista está vacía... \n");
                                    break;
                                }

                                System.out.println("------EDITAR BIBLIOTECA------");

                                //muestro todos los elementos
                                System.out.println("Tu biblioteca actual: ");
                                for (int key : rp.getLista().keySet()) {
                                    System.out.println("ID: " + rp.getLista().get(key).getId() + ", título: " + rp.getLista().get(key).getTitulo()
                                    + ", categoria: " + rp.getLista().get(key).getCategoria() + ", plataforma: " + rp.getLista().get(key).getPlataforma()
                                    + ", año de salida: " + rp.getLista().get(key).getAño() + "\n");
                                }
                                System.out.println("\n");


                                //pido al usuario el id para eliminar el videojuego
                                System.out.print("Introduce un ID del videojuego deseado [0 para salir]: ");
                                id = reader.nextInt();
                                reader.nextLine(); //limpiar el buffer que si no se repite

                                if(id == 0) {
                                    System.out.println("Retornando...");
                                    System.out.println("\n");
                                    break;
                                }
                                // Validar si el ID existe
                                if (!rp.getLista().containsKey(id)) {
                                    System.out.println("El ID introducido no existe en la biblioteca.\n");
                                    break;
                                }



                                //mostramos el menu de edicion
                                String respuestaEdicion = "";

                                do {
                                    Menus.menuEditar();
                                    System.out.print("¿Qué campo deseas editar?: ");
                                    respuestaEdicion = reader.nextLine().trim().toLowerCase();

                                    switch (respuestaEdicion) {
                                        case "titulo":
                                            System.out.print("Nuevo título: ");
                                            String titulo = reader.nextLine();
                                            rp.getLista().get(id).setTitulo(titulo);
                                            System.out.println("Título cambiado con éxito.\n");
                                            break;

                                        case "categoria":
                                            System.out.print("Nueva categoría: ");
                                            String categoria = reader.nextLine();
                                            rp.getLista().get(id).setCategoria(categoria);
                                            System.out.println("Categoría cambiada con éxito.\n");
                                            break;

                                        case "plataforma":
                                            System.out.print("Nueva plataforma: ");
                                            String plataforma = reader.nextLine();
                                            rp.getLista().get(id).setPlataforma(plataforma);
                                            System.out.println("Plataforma cambiada con éxito.\n");
                                            break;

                                        case "año":
                                            System.out.print("Nuevo año: ");
                                            int año = reader.nextInt();
                                            reader.nextLine(); // limpiar buffer
                                            rp.getLista().get(id).setAño(año);
                                            System.out.println("Año cambiado con éxito.\n");
                                            break;

                                        case "salir":
                                            System.out.println("Retornando al menú principal...\n");
                                            break;

                                        default:
                                            System.out.println("Debes introducir bien el campo que deseas cambiar.\n");
                                    }

                                } while (!respuestaEdicion.equalsIgnoreCase("salir"));
                                break;
                            case 5:

                                break;
                            default:
                                System.out.println("Introduce un valor correcto");

                        }

                    break;
                    case 2:
                        //compruebo que la lista esta vacía
                        if(rp.getLista().isEmpty()) {
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
                                        for(int key : rp.getLista().keySet()) {
                                            System.out.print("[ " + rp.getLista().get(key).getId() + " ], ");
                                        }
                                        System.out.println("\n");
                                        System.out.println("introduce el id del juego que quieres mostrar: ");


                                        id = reader.nextInt();
                                        reader.nextLine();

                                        if(rp.existsById(id)) {
                                            System.out.println(rp.findById(id));
                                        } else {
                                            throw new IllegalArgumentException("Debes introducir un id que exista");
                                        }


                                        break;

                                    case 4:
                                        break;
                                }


                        } while (opcion != 4);
                        break;
                    case 3:

                        //creamos el archivo
                        File archivo = new File("archivo.txt");
                        archivo.createNewFile();


                        //comprobamos que la lista esta vacia
                        if(rp.getLista().isEmpty()) {
                            System.out.println("El archivo está vacío.");
                            return;
                        }

                        //escribimos el contenido de la lista en formato csv
                        //importamos bufferedWriter
                        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

                        String linea = "";
                        for(int key : rp.getLista().keySet()) {
                            linea = rp.getLista().get(key).getId() + ", " + rp.getLista().get(key).getTitulo()
                                    + ", " + rp.getLista().get(key).getCategoria() + ", " + rp.getLista().get(key).getPlataforma()
                                    + ", " + rp.getLista().get(key).getAño() + "\n";
                            bw.write(linea);
                        }
                        bw.close();


                        break;
                    case 0:
                        //salir del programa
                        System.out.println("Saliendo del programa...");
                        estado = false;
                        break;
                    default:
                        System.out.println("Vuelve a introducir un valor.");
                }

            }catch(InputMismatchException e) {
                System.out.println("Introduce un valor correcto. \n");
                reader.next();
            } catch(IllegalArgumentException e) {
                System.out.println("Introduce un argumento correcto. \n");
                reader.next();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }while(estado);
    }


}