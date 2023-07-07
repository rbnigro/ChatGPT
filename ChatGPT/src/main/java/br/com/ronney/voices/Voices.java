package br.com.ronney.voices;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

import com.google.cloud.texttospeech.v1beta1.AudioConfig;
import com.google.cloud.texttospeech.v1beta1.AudioEncoding;
import com.google.cloud.texttospeech.v1beta1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1beta1.SynthesisInput;
import com.google.cloud.texttospeech.v1beta1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1beta1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1beta1.VoiceSelectionParams;
import com.google.protobuf.ByteString;

public class Voices {

	public Voices() {

	}

	public void AnswerGoogle(String pTexto) {
		String outputAudioFilePath = "/home/support/Documents/output.mp3";
		
		try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder().setText(pTexto).build();
 
            // Build the voice request; languageCode = "en_us"
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder().setLanguageCode("en-US")
                    .setSsmlGender(SsmlVoiceGender.FEMALE)
                    .build();
 
            // Select the type of audio file you want returned
            AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3) // MP3 audio.
                    .build();
 
            // Perform the text-to-speech request
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
 
            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();
 
            // Write the response to the output file.
            try (OutputStream out = new FileOutputStream(outputAudioFilePath)) {
                out.write(audioContents.toByteArray());
                System.out.println("Audio content written to file \"output.mp3\"");
            }
        } catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void ResponderGoogle(String pTexto) {

	    try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
	      // Set the text input to be synthesized
	      SynthesisInput input = SynthesisInput.newBuilder().setText(pTexto).build();

	      // Build the voice request, select the language code ("en-US") and the ssml voice gender ("neutral")
	      VoiceSelectionParams voice =
	          VoiceSelectionParams.newBuilder()
	              .setLanguageCode("en-US")
	              .setSsmlGender(SsmlVoiceGender.NEUTRAL)
	              .build();
	      
	      // Select the type of audio file you want returned
	      AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();

	      // Perform the text-to-speech request on the text input with the selected voice parameters and audio file type
	      SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

	      // Get the audio contents from the response
	      ByteString audioContents = response.getAudioContent();

	      // Write the response to the output file.
	      try (OutputStream out = new FileOutputStream("output.mp3")) {
	        out.write(audioContents.toByteArray());
	        System.out.println("Audio content written to file \"output.mp3\"");
	      }
	    } catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	  }

	public void AnswerFreeTTS(String pTexto) {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		try {
			Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
			Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
			synthesizer.allocate();
			synthesizer.resume();
			synthesizer.speakPlainText(pTexto, null);
			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
			synthesizer.deallocate();

		} catch (EngineException e) {
			e.printStackTrace();
		} catch (AudioException e) {
			e.printStackTrace();
		} catch (EngineStateError e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
