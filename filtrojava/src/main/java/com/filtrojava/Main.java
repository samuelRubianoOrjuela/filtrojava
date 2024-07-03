package com.filtrojava;

import java.text.MessageFormat;
import java.util.Scanner;

import com.filtrojava.actores.adapters.in.ActoresConsoleAdapter;
import com.filtrojava.actores.adapters.out.ActoresMySQLRepository;
import com.filtrojava.actores.application.ActoresService;
import com.filtrojava.peliculas.adapters.in.peliculasConsoleAdapter;
import com.filtrojava.peliculas.adapters.out.peliculasMySQLRepository;
import com.filtrojava.peliculas.application.peliculasService;

public class Main {
    
    // Codigo para limipiar consola
    public static void clearScreen() {         
        try {             
            if (System.getProperty("os.name").contains("Windows")) {                 
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();             
            } else {                 
                new ProcessBuilder("clear").inheritIO().start().waitFor();             
            }         
        } catch (Exception e) {             
            System.out.println("Error al limpiar la pantalla: " + e.getMessage());         
        }     
    }
    // Validar entradas a menu del usuario
    public static int validInt(Scanner sc, String errMesage, String txt){
        int x;
        do {
            System.out.print(txt);
            try {
                x = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(errMesage);
                sc.nextLine();
                x = -1;
                if (txt == "-> ") {
                    return x;
                }
            }
        } while (x == -1);
        return x;
    }
    
    public static void main(String[] args) {
        String header = """
            ---------------
            | CINE CAMPUS |
            ---------------
            """;
        String errMessage = "[¡]ERROR: El dato ingresado es incorrecto, intentelo de nuevo ";
        Scanner sc = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3306/cinecampus";
        String user = "root";
        String password = "123456";
        ActoresMySQLRepository actoresMySQLRepository = new ActoresMySQLRepository(url, user, password);
        ActoresService actoresService = new ActoresService(actoresMySQLRepository);
        ActoresConsoleAdapter actoresConsoleAdapter = new ActoresConsoleAdapter(actoresService);
        peliculasMySQLRepository peliculasMySQLRepository = new peliculasMySQLRepository(url, user, password);
        peliculasService peliculasService = new peliculasService(peliculasMySQLRepository);
        peliculasConsoleAdapter peliculasConsoleAdapter = new peliculasConsoleAdapter(peliculasService);
        String[] menu = {"Actores","Peliculas"};
        boolean isActive = true;
        mainLoop:
        while (isActive) {
            Main.clearScreen();
            System.out.println(header);
            for (int i = 0; i < menu.length; i++) {
                System.out.println(MessageFormat.format("{0}. {1}.", (i+1), menu[i]));
            }
            int op = Main.validInt(sc, errMessage, "-> ");
            if(op == -1){
                continue mainLoop;
            }
            switch (op) {
                case 1:
                    actoresConsoleAdapter.start();
                    break;
                case 2:
                    peliculasConsoleAdapter.start();
                    break;
                case 3:   
                    isActive = false;     
                    break; 
                default:
                    System.out.println(errMessage);
                    sc.nextLine();
                    break;
            } 
        } 
    }
}