package view;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Media {
	private File file;
	private Clip clip;

	public void play(String path) {
		try {
			file = new File(path);
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e) {
			System.err.println(
					"Put the music.wav file in the sound folder if you want to play background music, only optional!");
		}
	}

	public void pause() {
		try {
			clip.stop();
		} catch (Exception e) {
			System.err.println(
					"Put the music.wav file in the sound folder if you want to play background music, only optional!");
		}
	}
}