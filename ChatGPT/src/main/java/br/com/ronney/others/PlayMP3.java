package br.com.ronney.others;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class PlayMP3 {
	
	public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {

		String sCaminho = "C:/Users/ronne/OneDrive/Documentos/ChatGPT/chat01.wav";
		Clip clip = AudioSystem.getClip();
    	
        try {
        	File diretorio = new File(sCaminho);
            AudioInputStream oStream = AudioSystem.getAudioInputStream(diretorio);
            clip.open(oStream);
            clip.start();
            clip.loop(0); 
            
            Thread.sleep(10000);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
        	ex.printStackTrace();
        	clip.flush(); 	
        }
        
	}
}
