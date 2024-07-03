package com.filtrojava;

import java.util.Scanner;

import com.filtrojava.actores.adapters.in.ActoresConsoleAdapter;
import com.filtrojava.actores.adapters.out.ActoresMySQLRepository;
import com.filtrojava.actores.application.ActoresService;

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
        String url = "jdbc:mysql://localhost:3306/cinecampus";
        String user = "campus2023";
        String password = "campus2023";
        ActoresMySQLRepository actoresMySQLRepository = new ActoresMySQLRepository(url, user, password);
        ActoresService actoresService = new ActoresService(actoresMySQLRepository);
        ActoresConsoleAdapter actoresConsoleAdapter = new ActoresConsoleAdapter(actoresService);
        actoresConsoleAdapter.start();
    }
}