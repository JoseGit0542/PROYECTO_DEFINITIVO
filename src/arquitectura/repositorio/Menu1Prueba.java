package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu1Prueba {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        RepositorioVideojuego rp = new RepositorioVideojuego();
        Videojuego vd = new Videojuego("God of war", "hack & slash", "PLayStation", 2018);
        rp.save(vd);
        Videojuego vd2 = new Videojuego("BattleField 6", "Shooter", "Pc/PlayStation/Xbox", 2025);
        rp.save(vd2);

        int opcion = 0;
        do {

            try {
                System.out.println("");
                Menus.menuMostrar();
                opcion = sc.nextInt();
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


                        int id = sc.nextInt();
                        sc.nextLine();

                        if(rp.existsById(id)) {
                            System.out.println(rp.findById(id));
                        } else {
                            throw new IllegalArgumentException("Debes introducir un id que exista");
                        }


                        break;

                    case 4:
                        Menus.menu1();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Introduce el tipo correcto de dato.");
                sc.nextLine();
            } catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        } while (opcion != 4);


    }


}

