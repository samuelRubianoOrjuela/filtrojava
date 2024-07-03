package com.filtrojava.actores.adapters.out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.filtrojava.actores.domains.models.Actores;
import com.filtrojava.actores.infrastructure.ActoresRepository;

public class ActoresMySQLRepository implements ActoresRepository {
    private String url;
    private String user;
    private String password;
    
    public ActoresMySQLRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void save(Actores actor) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO actor (nombre, idnacionalidad, edad, idgenero) VALUES (?,?,?,?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, actor.getNombre());
                statement.setInt(2, actor.getIdNacionalidad());
                statement.setInt(3, actor.getEdad());
                statement.setInt(4, actor.getIdGenero());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Actores actor) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE actor SET nombre = ?, idnacionalidad = ?, edad = ?, idgenero = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, actor.getNombre());
                statement.setInt(2, actor.getIdNacionalidad());
                statement.setInt(3, actor.getEdad());
                statement.setInt(4, actor.getIdGenero());
                statement.setInt(5, actor.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM actor WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Actores> findById(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM actor WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Actores selectedActor = new Actores(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getInt("idnacionalidad"),
                            resultSet.getInt("edad"),
                            resultSet.getInt("idgenero")
                        );
                        return Optional.of(selectedActor);
                    }
                } 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    
    
    @Override
    public int getLastId() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT MAX(id) FROM actor";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("MAX(id)");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
    }

    @Override
    public List<String> getTableValues(String tableName) {
        List<String> values = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            switch (tableName) {
                case "actor":
                    String query1 = "SELECT id, nombre FROM " + tableName;
                    try (PreparedStatement statement = connection.prepareStatement(query1);
                         ResultSet resultSet = statement.executeQuery()) {
                            while (resultSet.next()) {
                                String id = resultSet.getString("id");
                                String nombre = resultSet.getString("nombre");
                                String value = "[id=" + id + ", nombre=" + nombre + "]";
                                values.add(value);
                            }
                        return values;
                    }
                default:
                    String query2 = "SELECT id, descripcion FROM " + tableName;
                    try (PreparedStatement statement = connection.prepareStatement(query2);
                         ResultSet resultSet = statement.executeQuery()) {
                            while (resultSet.next()) {
                                String id = resultSet.getString("id");
                                String descripcion = resultSet.getString("descripcion");
                                String value = "[id=" + id + ", descripcion=" + descripcion + "]";
                                values.add(value);
                            }
                        return values;
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Integer> getIDs(String tableName) {
        List<Integer> IDsLsit = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT id FROM " + tableName;
            try (PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        IDsLsit.add(id);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return IDsLsit;
    }
}