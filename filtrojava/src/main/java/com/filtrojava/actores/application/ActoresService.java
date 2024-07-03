package com.filtrojava.actores.application;

import java.util.List;
import java.util.Optional;

import com.filtrojava.actores.domains.models.Actores;
import com.filtrojava.actores.infrastructure.ActoresRepository;

public class ActoresService {

    ActoresRepository actoresRepository;
    public ActoresService(ActoresRepository actoresRepository) {
        this.actoresRepository = actoresRepository;
    }

    public void createActor (Actores actor){
        actoresRepository.save(actor);
    }

    public void updateActor (Actores actor){
        actoresRepository.update(actor);
    }

    public void deleteActor (int id){
        actoresRepository.delete(id);
    }

    public Optional<Actores> getActorById (int id){
        return actoresRepository.findById(id);
    }

    public int getLastId (){
        return actoresRepository.getLastId();
    }

    public List<String> getTableValues(String tableName){
        return actoresRepository.getTableValues(tableName);
    }

    public List<Integer> getIDs(String tableName){
        return actoresRepository.getIDs(tableName);
    }
}