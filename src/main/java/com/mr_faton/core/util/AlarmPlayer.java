package com.mr_faton.core.util;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.IOException;
import java.io.InputStream;

public class AlarmPlayer {
    private static final String FILE = "Alarm.wav";
    private final AudioStream audioStream;

    public AlarmPlayer() throws IOException {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(FILE);
        audioStream = new AudioStream(inputStream);
    }

    public void play() {
        AudioPlayer.player.start(audioStream);
    }

    public void stop() {
        AudioPlayer.player.stop(audioStream);
    }
}
