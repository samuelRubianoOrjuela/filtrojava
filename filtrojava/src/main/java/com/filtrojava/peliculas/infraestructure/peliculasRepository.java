package com.filtrojava.peliculas.infraestructure;

import com.filtrojava.peliculas.domain.models.peliculas;
import java.util.List;
import java.util.Optional;

public interface peliculasRepository {
    void save (peliculas pelicula);
    void update (peliculas peliculas);
    void delete (int id);
    List<peliculas> findAll(); 
    Optional<peliculas> findById(int id); 
    List<Integer> getIDs(String tableName);
    int getLastId();
}
