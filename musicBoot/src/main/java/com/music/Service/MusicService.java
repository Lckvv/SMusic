package com.music.Service;

import com.music.Dao.MusicDao;
import com.music.Entity.Genres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MusicService {

    @Autowired
    @Qualifier("mysql")
    private MusicDao musicDao;

    public Collection<Genres> getAllMusic(){
        return this.musicDao.getAllMusic();
    }

    public Genres getMusicById(int id){
        return this.musicDao.getMusicById(id);
    }

    public void removeMusicById(int id) {
        this.musicDao.removeMusicById(id);
    }

    public void updateMusic(Genres genres){
        this.musicDao.updateMusic(genres);
    }

    public void insertMusic(Genres genres) {
        musicDao.insertMusicToDb(genres);
    }
}
