package com.music.Controller;

import com.music.Dao.MySqlMusicDaoImpl;
import com.music.Entity.Genres;
import com.music.Service.MusicService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/musics")
public class GenresController {

    @FXML private ListView<Genres> missionsList;

    public void initialize() {
        

        Collection<Genres> missions = FXCollections.observableArrayList(getAllMusic());
        missionsList.setItems((ObservableList<Genres>) missions);
    }



    @Autowired
    private MusicService musicService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Genres> getAllMusic(){
        return musicService.getAllMusic();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Genres getMusicById(@PathVariable("id") int id){
        return musicService.getMusicById(id);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void deleteMusicById(@PathVariable("id") int id){
        musicService.removeMusicById(id);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateMusic(@RequestBody Genres genres){
        musicService.updateMusic(genres);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertMusic(@RequestBody Genres genres){
        musicService.insertMusic(genres);
    }

}
