package com.filtrojava.peliculas.application;

import java.util.List;
import java.util.Optional;

import com.filtrojava.peliculas.domain.models.peliculas;
import com.filtrojava.peliculas.infraestructure.peliculasRepository;

public class peliculasService {
    private final peliculasRepository peliculasRepository;

    public peliculasService(peliculasRepository peliculasRepository) {
        this.peliculasRepository = peliculasRepository;
    }
    
    public void createPelicula(peliculas pelicula){
        peliculasRepository.save(pelicula);
    }

    public void updatePelicula(peliculas pelicula){
        peliculasRepository.update(pelicula);
    }

    public void deletePelicula(int id){
        peliculasRepository.delete(id);
    }

    public Optional<peliculas> getPeliculaById (int id){
        return peliculasRepository.findById(id);
    }

    public int getLastId (){
        return peliculasRepository.getLastId();
    }

    public List<Integer> getIDs(String tableName) {
        return peliculasRepository.getIDs(tableName);
    }

}
