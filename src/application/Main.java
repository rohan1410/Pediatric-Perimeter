package application;


import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.awt.Point;




import java.io.*;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ddf.minim.*; // the audio recording library

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet; // For Importing The Data From EXcel Sheet
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import com.github.sarxos.webcam.Webcam;

public class Main extends Application {
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

	
	Group g;
	Scene scene;
	Point mouse;
	
	public Main() {
		g = new Group();
		scene = new Scene(g, 1380, 800, Color.GREY);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void start(Stage primaryStage) {
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
		Setup s = new Setup();
		s.senddata();
		new Patient_Info();
		//new Audio();
//		try {
//			//ffmpeg -video_size 1920x1080 -framerate 30 -f x11grab -i :0.0 -c:v libx264 -qp 0 -preset ultrafast capture.mkv
//		     String[] ffmpeg_command = {
//                               "C:\\Windows\\System32\\cmd.exe",
//		    	       "/c",
//		    	       "start",
//		    	       "ffmpeg",
//		    	       "-f",
//		    	       "gdigrab",
//		    	       "-framerate",
//		    	       "50",
//		    	       "-i",
//		    	       "desktop",
//		    	       "-vb",
//		    	       "48M",
//		    	       "C:\\Users\\Sonu\\Desktop\\feb.avi"
//		     };
//                     System.out.println(ffmpeg_command);  
//		     ProcessBuilder p = new ProcessBuilder(ffmpeg_command);
//		     Process pr = p.start();
//		    } catch (IOException e) {
//		     e.printStackTrace();
//		     System.exit(0);
//		}
		primaryStage.setTitle("Pediatric Perimeter");

		Rectangle r = new Rectangle();
		r.setX(100);
		r.setY(60);
        r.setWidth(1170);
        r.setHeight(680);
        r.setFill(Color.web("eeeeee", 1.0));
		
		g.getChildren().add(r);
		new Capture().capture_now(g);
		new Bangs().addBangs(g);
		GUI_Components gg=new GUI_Components();
		new Hemis().drawHemis(hemi_center, hemi_angle, hemi_diameter,g);
		new Quads().drawQuads(quad_center, quad_angle, quad_diameter,g);
		Isopter iso=new Isopter();
		iso.drawIsopter(meridians, iso_center, diameter,g);
		gg.drawPatterns(g);
		gg.mouse_pos();
		gg.heading(g);
		EventCall e = new EventCall();
		e.EventGroup(g);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}	
}