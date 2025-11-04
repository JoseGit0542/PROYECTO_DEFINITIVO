package arquitectura.repositorio;

import arquitectura.dominio.Videojuego;

import java.util.Scanner;

public class Menu1Prueba {
    public static void main (String[] args){

        Scanner sc = new Scanner(System.in);

        RepositorioVideojuego rp = new RepositorioVideojuego();
        Videojuego vd = new Videojuego("God of war", "hack & slash", "PLayStation", 2018);
        rp.save(vd);
        Videojuego vd2 = new Videojuego("BattleField 6", "Shooter", "Pc/PlayStation/Xbox", 2025);
        rp.save(vd2);

        int opcion;

        do{
            System.out.println("");
            Menus.menuMostrar();
            opcion = sc.nextInt();
                switch (opcion){
                    case 1:
                        System.out.println(rp.getLista());
                    break;

                    case 2:
                        long contador = rp.count();
                        System.out.println("tienes " + contador + " juegos");
                    break;

                    case 3:
                        System.out.println("introduce el id del juego que quieres introducir: ");
                        int id = sc.nextInt();
                        rp.existsById(id);
                        System.out.println(rp.findById(id));
                    break;

                    case 4:
                        Menus.menu1();
                    break;
                }
        }while(opcion != 5);


    }
}
