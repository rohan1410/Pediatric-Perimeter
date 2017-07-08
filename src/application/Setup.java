package application;

import java.awt.Button;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import gnu.io.CommPortIdentifier; 
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import gnu.io.UnsupportedCommOperationException;

import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.sarxos.webcam.Webcam;

import ddf.minim.AudioInput;
import ddf.minim.AudioRecorder;
import ddf.minim.Minim;

import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;

public class Setup{
	//Bangs
	public static JButton b1 = new JButton();
	public static JButton b2 = new JButton();
	public static JButton b3 = new JButton();
	public static JButton note = new JButton();
	public static JLabel rt_value=new JLabel();
	public static JLabel cur_test_value=new JLabel();
	public static JLabel test_status_value=new JLabel();
	public static JPanel j=new JPanel(new GridLayout(3,2,10,10));
	public static JLabel rt = new JLabel("Reaction Time : ");
	public static JLabel cur_test=new JLabel("Previous Test : ");
	public static JLabel test_status=new JLabel("Test Status : ");
	
	//Quads
	public static int[] flagq;
	public static int clicked_quad;
	public static Arc[] q;
	
	//Isopter
	public static int clicked_mer;
	public static Line[] ln;
	public static int flagm=0,flags=0;
	
	//Hemis
	public static int[] flagh;
	public static Arc[] h;
	
	//Setup
	public static SerialPort serialPort;
	public static OutputStream output;
	private static final String PORT_NAMES[] = { 
		"/dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyACM0",
        "/dev/ttyACM1",// Raspberry Pi
		"/dev/ttyUSB0", // Linux
		"COM3", // Windows
		"/dev/ttyS32", //ACM1
		"/dev/ttyS33", //ACM0
	};
	public static Enumeration portList;
	public static CommPortIdentifier portId;
	public static InputStream inputStream;
	public static BufferedReader input;
		
	//Bangs
	Slider ls, br;
	
	//Patient Info
	public static String patient_name, patient_MR, patient_dob,patient_milestone_details, patient_OTC,patient_note;
	public static  String workingDirectory = "/home/rohan/files/";
	
	//Capture
	Webcam webcam = null;
	
	//Audio
	public static Minim minim;
	public static AudioInput mic_input;
	public static AudioRecorder sound_recording;
	public static String base_folder;
	public static int pattern_state[] = { 1, 1, 1};
	
	// Variables For Excel Sheet Importing
	public static XSSFWorkbook workbook = null;
	public static Sheet sh = null;
	public static InputStream inp = null;
	public static Workbook wb = null;
	public static float[][] angleData = new float[500][500];
	public static float[] bottomMostAngle = new float[30];
	public static int lowLimit,upperLimit;
	
	LocalDateTime now = LocalDateTime.now();
	public static boolean startRecording = false;
	public static char hovered_object,lastTest_Hobject;
	public static int hovered_count, hovered_subcount,lastTes_Hsubcount,lastTest_Hcount; // the current meridian which has been hovered over
	public static int hemi_state[][] = { {  1,  1 }, {  1,  1 }, {  1,  1 }, {  1,  1 }};
	public static int hemi_hover_code[][] = { {  0,  3 }, {  1,  2 }};
	public static int meridian_label[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
	// Device Numbering 
	public static int numberOfLEDs[] = {25, 25, 25, 25, 21, 14, 13, 14, 21, 25, 25, 25, 25, 25, 25, 25, 14, 12, 12, 12, 14, 26, 25, 25}; 
	//including Daisy Disc for time interval calculation according to the device numbering
	public static int quad_state[][] = {{  1,  1 }, {  1,  1 }, {  1,  1 }, {  1,  1 }}; 
	// 1 means the quad has not been done yet, 2 means it has already been done, 3 means it is presently going on, negative means it is being hovered upon
	// ISOPTER VARIABLES [SWEEP]
	public static PrintWriter isopter_text,quadHemi_text,note_text;
	public static int meridian_state[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
	public static int[][] section_state = {{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1},{1,1,1}};
	public static int posPatternImage[][] = { {  105,  540 }, {  255,  540 }, {  385,  540 }};
	public static int previousMillis = 0,
			   currentMillis = 0,
			   initialMillis,
			   finalMillis,
			   Sent_Time = 0,
			   time_taken,
			   prev_time,
			   Recieve_Time = 0,
			   z = 0,
			   millis = 0; // initial and final are used to calculate the FPS for the video at the verry end
	public static int previousTime,currentTime;
	public static int reaction_time ,occipitalDistance; // intialize reaction_time to 0 otherwise it gets a weird value which will confuse the clinicians
	public static boolean flagged_test = false;
	public static int current_sweep_meridian,current_gross_test,imageFrameCounter;
	public static String status;
	public static String last_tested;
	public static int Arduino_Response;
	public static boolean serialEventFlag = false,allDataSentFlag = false;
	public static int SpaceKey_State = 0; // 0 means it is not pressed , 1 means it is pressed
	
	public static Image subjectIsopter;
	
	public static String daisy_On_Off = "OFF";
	public static String ledCouplet = "OFF";
	
	public static int imageNumber;
	public static int count = 0;
	public static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	public static final int DATA_RATE = 115200;
	
	public static int meridians[] = { 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28,
		28, 28, 28, 28, 28, 28, 28, 28, 28, 28 }; // negative value means its being hovered over
	public static Arc h1, h2, h3, h4, q1, q2, q3, q4, q5, q6, q7, q8;
	public static float[] quad_angle = { 0.0f, 90.0f, 180.0f, 270.0f };
	public static float[] quad_center = { 910.0f, 500.0f };
	public static float dx = 2.5f, dy = 2.5f;
	public static float diameter = 300;
	public static int[] iso_center = { 1000, 250 };
	public static float[] hemi_center = { 1100.0f, 500.0f };
	public static float[] hemi_angle = { 90.0f, 270.0f };
	public static float quad_diameter = 45.0f;
	public static float hemi_diameter = 45.0f;
	
	public void senddata(){
		System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
		//It lists out the given ports
		System.out.println(System.getProperty("gnu.io.rxtx.SerialPorts"));
		System.out.println(System.getProperty("java.library.path"));
		System.out.println(this.getClass().getName());
	    portList = CommPortIdentifier.getPortIdentifiers();

	    boolean flag=false;
	    while (portList.hasMoreElements()) {
	        portId = (CommPortIdentifier) portList.nextElement();
	        System.out.println(portId.getPortType());
	        System.out.println(CommPortIdentifier.PORT_SERIAL);
	        System.out.println(portId);
	        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

	             if (portId.getName().equals("/dev/ttyACM0")) {
	                try {
	                    	serialPort = (SerialPort)
	                        portId.open(this.getClass().getName(), 2000);
	                } catch (PortInUseException e) {System.out.println("err");}
	                try {
	                    output = serialPort.getOutputStream();
	                } catch (IOException e) {System.out.println("err1");}
	                try {
	                    serialPort.setSerialPortParams(115200,
	                        SerialPort.DATABITS_8,
	                        SerialPort.STOPBITS_1,
	                        SerialPort.PARITY_NONE);
	                } catch (UnsupportedCommOperationException e) {System.out.println("err2");}
	                try {
	                	flag=true;
	                	//Writing the data
	                	long startTime = System.currentTimeMillis();
	                	System.out.println(startTime);
	                	try{
	                	TimeUnit.SECONDS.sleep(4);
	                	}
	                	catch(Exception e){}
	                	//t.start();
	                	long stopTime = System.currentTimeMillis();
	                	System.out.println(stopTime);
	                    long elapsedTime = stopTime - startTime;
	                    System.out.println(elapsedTime);
	                    output.write("m,2\n".getBytes());
	                    input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
//	                    outputStream.write("m,2\n".getBytes());
//	                  	serialPort.addEventListener(this);
//	                 	serialPort.notifyOnDataAvailable(true);
//	        			System.out.println(SerialPortEvent.DATA_AVAILABLE);
//	                  	outputStream.write('');
//	                  	System.out.println('a');
//	        			String inputLine=input.readLine();
//	        			System.out.println(inputLine);
//	                    System.out.println("Arduino has sent this code...");
//	                    //System.out.println(input);
//	                    String thisLine = null;
//	                    while((thisLine = input.readLine())!=null)
//	        			System.out.println(thisLine);
	                    output.write("x\n".getBytes());
	                } catch (Exception e) {
	                	System.out.println(e.getStackTrace());
	                	System.out.println("erra");
	                	}
	            }
	        }
	    }
	    if(flag)
	    	System.out.println("There!");
	    else
	    	System.out.println("Not There!");
	}
	
	public Setup() {}
}
