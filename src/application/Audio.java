package application;

import ddf.minim.*;// the audio recording library
import java.io.*;

public class Audio extends Setup{

	/**
	 * @param args
	 */
	public Audio(){
		minim = new Minim(this);
		mic_input = minim.getLineIn(); // keep this ready. This is the line-in.

	    // CREATE A NEW AUDIO OBJECT
	    sound_recording = minim.createRecorder(mic_input, base_folder + "/recording.wav", false); // the false means that it would save directly to disc rather than in a buffer
	    sound_recording.beginRecord();
	    
	    String video_folder = workingDirectory + "/" + base_folder + "/video.mpg";
	     try {
	      String[] ffmpeg_command = {
	       "C:\\Windows\\System32\\cmd.exe",
	       "/c",
	       "start",
	       "ffmpeg",
	       "-f",
	       "gdigrab",
	       "-framerate",
	       "50",
	       "-i",
	       "desktop",
	       "-vb",
	       "48M",
	       video_folder
	      };
	      ProcessBuilder p = new ProcessBuilder(ffmpeg_command);
	      Process pr = p.start();
	     } catch (IOException e) {
	      e.printStackTrace();
	      System.exit(0);
	     }


	     // angleDadta stores the values according to device numbering
	     // Initialize the pattern_state 
	     pattern_state[0] = 1;
	     pattern_state[1] = 1;
	     pattern_state[2] = 1;
	    }
}