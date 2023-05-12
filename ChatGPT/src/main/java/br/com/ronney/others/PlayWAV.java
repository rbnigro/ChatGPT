package br.com.ronney.others;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class PlayWAV {
	
	public static void executarWAV(String sPath) throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {

		Clip clip = AudioSystem.getClip();
    	
        try {
        	File diretorio = new File(sPath);
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
