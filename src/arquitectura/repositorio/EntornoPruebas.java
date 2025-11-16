package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class EntornoPruebas {
    public static void main(String[] args) {

        //aqui haremos las pruebas de las funcionalidades que vayamos implementando
        Scanner reader = new Scanner(System.in);


        //creo el repositorio
        RepositorioVideojuego rp = new RepositorioVideojuego();
        File archivo = new File("archivo.csv");

        cargarDesdeArchivo(rp, archivo);

        //variables de las respuestas
        int respuesta1 = 0;
        int respuesta2 = 0;
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
                        Funciones.mostrarBiblioteca(reader, rp, archivo);
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
    public static void cargarDesdeArchivo(RepositorioVideojuego rp, File archivo) {
        if (!archivo.exists()) {
            System.out.println("No se encontró el archivo: " + archivo.getAbsolutePath());
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int contador = 0;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] datos = linea.split(";");
                if (datos.length < 5) {
                    System.err.println("Línea inválida: " + linea);
                    continue;
                }

                try {
                    int id = Integer.parseInt(datos[0].trim());
                    String titulo = datos[1].trim();
                    String categoria = datos[2].trim();
                    String plataforma = datos[3].trim();
                    int año = Integer.parseInt(datos[4].trim());

                    Videojuego v = new Videojuego(titulo, categoria, plataforma, año);
                    v.setId(id);
                    rp.getLista().put(id, v);

                    contador++;
                } catch (NumberFormatException e) {
                    System.err.println("Error de formato numérico en línea: " + linea);
                }
            }

            System.out.println("Se han cargado " + contador + " videojuegos en memoria.");
            System.out.println("Total en repositorio: " + rp.count());

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }







}


