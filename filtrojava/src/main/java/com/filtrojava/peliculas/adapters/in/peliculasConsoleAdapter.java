package com.filtrojava.peliculas.adapters.in;

import com.filtrojava.Main;
import com.filtrojava.peliculas.application.peliculasService;
import com.filtrojava.peliculas.domain.models.peliculas;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class peliculasConsoleAdapter {
    private peliculasService peliculasService;

    public peliculasConsoleAdapter(peliculasService peliculasService) {
        this.peliculasService = peliculasService;
    }

     String header = """
            ------------
            | PELÍCULAS |
            ------------
            """;
    Scanner sc = new Scanner(System.in);
    String errMessage = "[¡]ERROR: El dato ingresado es incorrecto, intentelo de nuevo ";
    String rta = " ";

    public int existsId(String txt, String errMessage2,  String tableName){
        List<Integer> IDsLsit = peliculasService.getIDs(tableName);
        int fId;
        do {
            fId = Main.validInt(sc, errMessage, txt);
            if (!IDsLsit.contains(fId)) {
                System.out.println(errMessage2);
            }
        } while (!IDsLsit.contains(fId));
        return fId;
    }
 
    public void registrarPelicula(){
        while (!rta.isEmpty()) { 
            Main.clearScreen();
            System.out.println(header);
            int id = peliculasService.getLastId() + 1;
            System.out.print("Ingrese el codigo interno: ");
            String codInterno = sc.nextLine();
            System.out.print("Ingrese el nombre de la pelicula: ");
            String nombre = sc.nextLine();
            System.out.print("Ingrese la duracion de la pelicula: ");
            String duracion = sc.nextLine();
            System.out.println("Ingrese la sinopsis de la pelicula:");
            String sinopsis = sc.nextLine();
            peliculas pelicula = new peliculas(id, codInterno, nombre, duracion, sinopsis);
            peliculasService.createPelicula(pelicula);
            Optional<peliculas> createdPelicula = peliculasService.getPeliculaById(id);
            createdPelicula.ifPresentOrElse(p -> System.out.println("\nLa pelicula: " + p.toString() + " fue registrada correctamente."), 
            () -> System.out.println("La pelicula no fue registrada correctamente"));
            System.out.println("\nDesea ingresar otra pelicula? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarPelicula(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Peliculas:\n");
            int id = existsId("\nIngrese el id de la pelicula a consultar: ", "\nPelicula no encontrada, Intente de nuevo", "pelicula");             
            Optional<peliculas> selectedPelicula = peliculasService.getPeliculaById(id);
            if (selectedPelicula.isPresent()) { System.out.println(selectedPelicula.get().toString()); }
            System.out.println("\nDesea consultar otra pelicula? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void actualizarPelicula(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Peliculas:\n");
            int id = existsId("\nIngrese el id de la pelicula a consultar: ", "\nPelicula no encontrada, Intente de nuevo", "pelicula");             
            Optional<peliculas> selectedPelicula = peliculasService.getPeliculaById(id);
            if (selectedPelicula.isPresent()) {
                System.out.print("Ingrese el codigo interno: ");
                String codInterno = sc.nextLine();
                System.out.print("Ingrese el nombre de la pelicula: ");
                String nombre = sc.nextLine();
                System.out.print("Ingrese la duracion de la pelicula: ");
                String duracion = sc.nextLine();
                System.out.println("Ingrese la sinopsis de la pelicula:");
                String sinopsis = sc.nextLine();
                peliculas peliculaToUpdate = new peliculas(id, codInterno, nombre, duracion, sinopsis);
                peliculasService.updatePelicula(peliculaToUpdate);
            }
            Optional<peliculas> updatedPelicula = peliculasService.getPeliculaById(id);
            updatedPelicula.ifPresentOrElse(p -> System.out.println("La pelicula: " + p.toString() + " fue actualizada correctamente."), 
            () -> System.out.println("La pelicula no fue actualizada correctamente"));
            System.out.println("\nDesea actualizar otra pelicula? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void elimninarPelicula(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Peliculas:\n");
            int id = existsId("\nIngrese el ID de la pelicula a eliminar: ", "\nPelicula no encontrado, Intente de nuevo", "pelicula");  
            Optional<peliculas> pelicula = peliculasService.getPeliculaById(id);
            if(pelicula.isPresent()){
                System.out.println(MessageFormat.format("\nLa pelicula {0} será eliminada", pelicula.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String cnf = sc.nextLine();
                if(cnf.isEmpty()){
                    peliculasService.deletePelicula(id);
                } else {
                    System.out.println("El actor no ha sido eliminado");
                }
            }
            Optional<peliculas> deletedPelicula = peliculasService.getPeliculaById(id);
            if (deletedPelicula.isEmpty()) { System.out.println("Pelicula eliminada exitosamente"); }
            System.out.print("\nDesea eliminar otra pelicula? si/ENTER ");
            rta = sc.nextLine();
        }
    }

    public void start() {
        String[] menu = {"Registrar Pelicula","Actualizar Pelicula","Consultar Pelicula","Eliminar Pelicula","Salir"};
        boolean isActive = true;
        mainLoop:
        while (isActive) {
            Main.clearScreen();
            System.out.println(header);
            rta = " ";
            for (int i = 0; i < menu.length; i++) {
                System.out.println(MessageFormat.format("{0}. {1}.", (i+1), menu[i]));
            }
            int op = Main.validInt(sc, errMessage, "-> ");
            if(op == -1){
                continue mainLoop;
            }
            switch (op) {
                case 1:
                    registrarPelicula();
                    break;
                case 2:
                    actualizarPelicula();
                    break;
                case 3:
                    consultarPelicula();
                    break;                   
                case 4:
                    elimninarPelicula();
                    break;      
                case 5:    
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