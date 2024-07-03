package com.filtrojava.actores.infrastructure;

import java.util.List;
import java.util.Optional;

import com.filtrojava.actores.domains.models.Actores;

public interface ActoresRepository {
    void save(Actores actor);
    void update(Actores actor);
    void delete(int id);
    Optional<Actores> findById(int id);
    int getLastId();
    List<String> getTableValues(String tableName);
    List<Integer> getIDs(String tableName);
}
