package com.music.Dao;

import com.music.Entity.Genres;

import java.util.Collection;

public interface MusicDao {
    Collection<Genres> getAllMusic();

    Genres getMusicById(int id);

    void removeMusicById(int id);

    void updateMusic(Genres genres);

    void insertMusicToDb(Genres genres);
}
