package com.filtrojava.actores.adapters.in;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.filtrojava.Main;
import com.filtrojava.actores.application.ActoresService;
import com.filtrojava.actores.domains.models.Actores;

public class ActoresConsoleAdapter {

    private final ActoresService actoresService;
    
    public ActoresConsoleAdapter(ActoresService actoresService) {
        this.actoresService = actoresService;
    }

    String header = """
        -----------
        | ACTORES |
        -----------
        """;
    String errMessage = "[¡]ERROR: El dato ingresado es incorrecto, intentelo de nuevo ";
    Scanner sc = new Scanner(System.in);
    String rta = " ";

    public void printAllValues(String tableName) {
        List<String> valuesList = actoresService.getTableValues(tableName);
        for (String value : valuesList) {
            System.out.println(value);
        }
    }

    public int existsId(String txt, String errMessage, String tableName){
        List<Integer> IDsLsit = actoresService.getIDs(tableName);
        printAllValues(tableName);
        int fId;
        do {
            fId = Main.validInt(sc, errMessage, txt);
            if (!IDsLsit.contains(fId)) { System.out.println(errMessage); }
        } while (!IDsLsit.contains(fId));
        return fId;
    }

    public void registrarActor(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            int id = actoresService.getLastId() + 1;
            System.out.print("Ingrese el nombre del actor: ");
            String nombre = sc.nextLine();
            System.out.println("\nPaises:");
            int idNac = existsId("\nIngrese el id del pais de la nacionalidad del actor: ", 
            "Pais no encontrado, Intente de nuevo", "pais");
            int edad = Main.validInt(sc, errMessage, "\nIngrese la edad del actor: ");
            System.out.println("\nGeneros:");
            int idGen = existsId("\nIngrese el id del genero del actor: ", "Genero no encontrado, Intente de nuevo", "genero");
            Actores newActor = new Actores(id, nombre, idNac, edad, idGen);
            actoresService.createActor(newActor);
            Optional<Actores> createdActor = actoresService.getActorById(id);
            createdActor.ifPresentOrElse(a -> System.out.println("\nEl actor: " + a.toString() + " fue registrado correctamente."), 
            () -> System.out.println("El actor no fue registrado correctamente"));
            System.out.print("\nDesea ingresar otro actor? si/ENTER ");
            rta = sc.nextLine();
        }
    }
    
    public void actualizarActor(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Actores:\n");
            int id = existsId("\nIngrese el id del actor a actualizar: ", 
            "Actor no encontrado, Intente de nuevo","actor");
            Optional<Actores> selectedActor = actoresService.getActorById(id);
            if(selectedActor.isPresent()){
                System.out.println("\nActor seleccionado: \n" + selectedActor.get().toString());
                System.out.print("\nIngrese el nuevo nombre del actor: ");
                String nombre = sc.nextLine();
                System.out.println("\nPaises:");
                int idNac = existsId("\nIngrese el id del pais de la nueva nacionalidad del actor: ", 
                "Pais no encontrado, Intente de nuevo", "pais");
                int edad = Main.validInt(sc, errMessage, "\nIngrese la nueva edad del actor: ");
                System.out.println("\nGeneros:");
                int idGen = existsId("\nIngrese el id del nuevo genero del actor: ", "Genero no encontrado, Intente de nuevo", "genero");
                Actores actorToUpdate = new Actores(id, nombre, idNac, edad, idGen);
                actoresService.updateActor(actorToUpdate);
            }
            Main.clearScreen();
            System.out.println(header);
            Optional<Actores> updatedActor = actoresService.getActorById(id);
            updatedActor.ifPresentOrElse(a -> System.out.println("El actor: " + a.toString() + " fue actualizado correctamente."), 
            () -> System.out.println("El actor no fue actualizado correctamente"));
            System.out.println("\nDesea actualizar otro actor? si/ENTER");
            rta = sc.nextLine();
        }
    }

    public void consultarActor(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Actores:\n");
            int id = existsId("\nIngrese el ID del actor a consultar: ", 
            "Actor no encontrado, Intente de nuevo", "actor"); 
            Optional<Actores> selectedActor = actoresService.getActorById(id);
            if (selectedActor.isPresent()) { System.out.println(selectedActor.get().toString()); }
            System.out.print("\nDesea consultar otro actor? si/ENTER ");
            rta = sc.nextLine();
        }
    }

    public void elimninarActor(){
        while (!rta.isEmpty()) {
            Main.clearScreen();
            System.out.println(header);
            System.out.println("Actores:\n");
            int id = existsId("\nIngrese el ID del actor a eliminar: ", 
            "\nActor no encontrado, Intente de nuevo", "actor");  
            Optional<Actores> actor = actoresService.getActorById(id);
            if(actor.isPresent()){
                System.out.println(MessageFormat.format("\nEl actor {0} será eliminado", actor.get().toString()));
                System.out.println("\n¿Desea continuar? \npresione ENTER para si o cualquier tecla para no");
                String cnf = sc.nextLine();
                if(cnf.isEmpty()){
                    actoresService.deleteActor(id);
                } else {
                    System.out.println("El actor no ha sido eliminado");
                }
            }
            Optional<Actores> deletedActor = actoresService.getActorById(id);
            if (deletedActor.isEmpty()) { System.out.println("Actor eliminado exitosamente"); }
            System.out.print("\nDesea eliminar otro actor? si/ENTER ");
            rta = sc.nextLine();
        }
    }

    
    public void start() {
        String[] menu = {"Registrar Actor","Actualizar Actor","Consultar Actor","Eliminar Actor","Salir"};
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
                    registrarActor();
                    break;
                case 2:
                    actualizarActor();
                    break;
                case 3:
                    consultarActor();
                    break;                   
                case 4:
                    elimninarActor();
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