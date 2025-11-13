package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Menu1Prueba {
    public static void main(String[] args) throws IOException {

        Scanner reader = new Scanner(System.in);
        RepositorioVideojuego rp = new RepositorioVideojuego();

        int opcion = 0;
        String[] lista;

        do {
            try {
                System.out.println("");
                Menus.menuMostrar();
                opcion = reader.nextInt();
                reader.nextLine(); // limpiar buffer

                File archivo = new File("archivo.csv");

                switch (opcion) {
                    case 1: {
                        BufferedReader br = new BufferedReader(new FileReader(archivo));
                        String lineaLeida;
                        while ((lineaLeida = br.readLine()) != null) {
                            System.out.println(lineaLeida);
                        }
                        br.close();
                        break;
                    }

                    case 2: {
                        BufferedReader br = new BufferedReader(new FileReader(archivo));
                        int contador = 0;
                        while (br.readLine() != null) {
                            contador++;
                        }
                        br.close();
                        System.out.println("Tienes " + contador + " juegos");
                        break;
                    }

                    case 3: {
                        System.out.println("Tu biblioteca es la siguiente: ");
                        BufferedReader br = new BufferedReader(new FileReader(archivo));

                        String linea;
                        // Mostramos los IDs y nombres
                        while ((linea = br.readLine()) != null) {
                            lista = linea.split(",");
                            System.out.print("ID: " + lista[0] + ", ");
                        }
                        br.close();

                        System.out.println("\n\nIntroduce el ID del juego que quieres mostrar: ");
                        int id = reader.nextInt();
                        reader.nextLine();

                        // Volvemos a abrir el archivo para buscar el juego
                        BufferedReader br2 = new BufferedReader(new FileReader(archivo));
                        Videojuego v = null;

                        while ((linea = br2.readLine()) != null) {
                            lista = linea.split(",");
                            if (id == parseInt(lista[0])) {
                                v = new Videojuego(lista[1].trim(), lista[2].trim(), lista[3].trim(), parseInt(lista[4].trim()));
                                v.setId(id);
                                break;
                            }
                        }
                        br2.close();

                        if (v != null) {
                            System.out.println("ID: " + v.getId() + ", título: " + v.getTitulo());
                        } else {
                            System.out.println("No se encontró ningún videojuego con ese ID.");
                        }
                        break;
                    }

                    case 4:
                        System.out.println("Retornando... \n");
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } while (opcion != 5);

        System.out.println("Programa finalizado.");
    }
}
