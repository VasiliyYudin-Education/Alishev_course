package ru.alishev.springcourse;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext("applicationContext.xml");
//        Music tb=context.getBean("musicBean",ClassicalMusic.class);
//        MusicPlayer mp=new MusicPlayer(tb);
        context.getBean("musicPlayer",MusicPlayer.class).playMusic();
        context.close();
    }
}
