package ru.alishev.springcourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MusicPlayer {
    private Music music;

    //ioc
    @Autowired
    public MusicPlayer(
            @Qualifier("classicalMusic")Music music) {
        this.music = music;
    }
    public void playMusic(){
        System.out.println("playing: "+music.getSong());
    }
}
