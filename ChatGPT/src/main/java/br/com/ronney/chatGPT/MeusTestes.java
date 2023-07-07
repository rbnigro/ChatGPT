package br.com.ronney.chatGPT;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1beta1.AudioConfig;
import com.google.cloud.texttospeech.v1beta1.AudioEncoding;
import com.google.cloud.texttospeech.v1beta1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1beta1.SynthesisInput;
import com.google.cloud.texttospeech.v1beta1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1beta1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1beta1.TextToSpeechSettings;
import com.google.cloud.texttospeech.v1beta1.VoiceSelectionParams;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;

public class MeusTestes {

	public static void main(String[] args) {

		String text = "Hello World! How are you doing today? This is Google Cloud Text-to-Speech Demo!";
        String outputAudioFilePath = "/home/support/Documents/output.mp3";
        String jsonFilePath = "C:\\Users\\ronne\\git\\ChatGPT\\ChatGPT\\src\\main\\resources\\client_secret_apps.json";
        
        try {
			TextToSpeechSettings settings = TextToSpeechSettings.newBuilder()
			        .setCredentialsProvider(FixedCredentialsProvider.create(authExplicit(jsonFilePath)))
			        .build();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
        
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
 
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder().setLanguageCode("en-US")
                    .setSsmlGender(SsmlVoiceGender.FEMALE)
                    .build();
 
            AudioConfig audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();
 
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
 
            ByteString audioContents = response.getAudioContent();
 
            try (OutputStream out = new FileOutputStream(outputAudioFilePath)) {
                out.write(audioContents.toByteArray());
                System.out.println("Audio content written to file \"output.mp3\"");
            } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public static GoogleCredentials authExplicit(String jsonPath) throws IOException {
		final FileInputStream fileInputStream = new FileInputStream(jsonPath);
		GoogleCredentials credentials = GoogleCredentials.fromStream(fileInputStream);
		
	  //  GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
	  //      .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

	    return credentials;
	}
}
