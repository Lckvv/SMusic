package com.music.Dao;

import com.music.Entity.Genres;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Repository("mysql")
public class MySqlMusicDaoImpl implements MusicDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static class MusicRowMapper implements RowMapper<Genres>{

        @Override
        public Genres mapRow(ResultSet resultSet, int i) throws SQLException {
            Genres genres = new Genres();
            genres.setId(resultSet.getInt("id"));
            genres.setName(resultSet.getString("name"));
            return genres;
        }
        }


    @Override
    public Collection<Genres> getAllMusic() {
//      SELECT column_name(s) FROM table_name
        final String sql = "SELECT id, name FROM genres";
        List<Genres> genres = jdbcTemplate.query(sql, new MusicRowMapper());
        return genres;
    }

    @Override
    public Genres getMusicById(int id) {
//        SELECT column_name(s) FROM table_name WHERE column = value
        final String sql = "SELECT id, name FROM genres where id = ?";
        Genres genres = jdbcTemplate.queryForObject(sql, new MusicRowMapper(), id);
        return genres;
    }

    @Override
    public void removeMusicById(int id) {
     //   DELETE FROM table_name
     //   WHERE some_column = some_value
        jdbcTemplate.update("DELETE FROM musics where id = ?", id);


    }

    @Override
    public void updateMusic(Genres genres) {
//        UPDATE table_name
//        SET column1=value, column2=value2,...
//        WHERE some_column=some_value
        final String sql = "UPDATE musics SET name = ? where id = ?";
        final int id = genres.getId();
        final String name = genres.getName();
        jdbcTemplate.update(sql,new Object[] {name,id});
    }

    @Override
    public void insertMusicToDb(Genres genres) {
//        INSERT INTO table_name (column1, column2, column3,...)
//        VALUES (value1, value2, value3,...)
        final String sql = "INSERT INTO musics (name) VALUES (?)";
        final String name = genres.getName();
        jdbcTemplate.update(sql,new Object[] {name});


    }


}
