import java.io.*;
import java.util.Random;
import sun.audio.*;

/**
 * Class for initializing sounds
 * @author Ronald Elzen
 *
 */
public class Sound {
public static void play()
			  throws Exception
			  {
			    // open the sound file as a Java input stream
	            
	            InputStream in1 = new FileInputStream("/sound1.wav");
	            InputStream in2 = new FileInputStream("/sound2.wav");
	            InputStream in3 = new FileInputStream("/sound3.wav");
	            AudioStream audioStream1 = new AudioStream(in1);
				AudioStream as1 = audioStream1;
			    AudioStream audioStream2 = new AudioStream(in2);
				AudioStream as2 = audioStream2;
				AudioStream audioStream3 = new AudioStream(in3);
				AudioStream as3 = audioStream3;
				Random rand = new Random(); 
                int value = rand.nextInt(4); 
                //Plays random sound
                if (value == 1)
                {
                    AudioPlayer.player.start(as1);
                }
                if (value==2)
                {
	             AudioPlayer.player.start(as2); 
	            }
	
	             if (value==3)
                {
	             AudioPlayer.player.start(as3); 
	            }
	             }
}