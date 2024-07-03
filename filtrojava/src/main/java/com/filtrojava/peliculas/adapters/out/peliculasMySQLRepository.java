package com.filtrojava.peliculas.adapters.out;

import com.filtrojava.peliculas.infraestructure.peliculasRepository;
import com.filtrojava.peliculas.domain.models.peliculas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class peliculasMySQLRepository implements peliculasRepository {
    private final String url;
    private final String user;
    private final String password;

    public peliculasMySQLRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void save(peliculas pelicula) {
        try (Connection connection = DriverManager.getConnection(url, user, password)){
            String query = "INSERT INTO pelicula (codInterno, nombre, duracion ,sinopsis) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1, pelicula.getCodInterno());
                statement.setString(2, pelicula.getNombre());
                statement.setString(3, pelicula.getDuracion());
                statement.setString(4, pelicula.getSinopsis());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }

    @Override
    public void update(peliculas pelicula) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE pelicula SET codInterno = ?, nombre = ?, duracion = ?, sinopsis = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, pelicula.getCodInterno());
                statement.setString(2, pelicula.getNombre());
                statement.setString(3, pelicula.getDuracion());
                statement.setString(4, pelicula.getSinopsis());
                statement.setInt(5, pelicula.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM pelicula WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<peliculas> findAll() {
        List<peliculas> PeliculasList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM pelicula";
            try (PreparedStatement statement = connection.prepareStatement(query); 
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    peliculas peliculas = new peliculas(
                        resultSet.getInt("id"),
                        resultSet.getString("codInterno"),
                        resultSet.getString("nombre"),
                        resultSet.getString("duracion"),
                        resultSet.getString("sinopsis")

                    );
                    PeliculasList.add(peliculas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PeliculasList;
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

    @Override
    public int getLastId() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT MAX(id) FROM pelicula";
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
    public Optional<peliculas> findById(int id) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM pelicula WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        peliculas selectedPelicula = new peliculas(
                            resultSet.getInt("id"),
                            resultSet.getString("codInterno"),
                            resultSet.getString("nombre"),
                            resultSet.getString("duracion"),
                            resultSet.getString("sinopsis")
                        );
                        return Optional.of(selectedPelicula);
                    }
                } 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    

}
