package application;

import java.awt.Point;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;


public class Isopter extends Setup
{
	LocalDateTime now = LocalDateTime.now();
	int done2=0;
	float dx = 2.5f, dy = 2.5f;
	float diameter = 300,angle,xm,ym;
	int[] iso_center = { 800, 200 };
	Point mouse;
	Text[] ind;
	float[][] endpoint;
	Button button;
	public float HALF_PI = 1.5707964f;
	public float PI = 3.1415927f;
	public Isopter() {
		ln = new Line[24];
		ind = new Text[24];
		endpoint = new float[24][2];
		button=new Button();
	}
	
	void sendTimeIntervals(int chosenStrip) {
	    System.out.println("Sending data.."); 
	    try {
		    System.out.println("bottomMostAngle[chosenStrip - 1] " + bottomMostAngle[chosenStrip - 1] + " Sweep value " + ls.getValue() + " Quotient " + bottomMostAngle[chosenStrip - 1] / ls.getValue());
		    long valueToBeSent = Math.round(((bottomMostAngle[chosenStrip - 1] / ls.getValue()) / numberOfLEDs[chosenStrip - 1]) * 1000);
		    System.out.println(valueToBeSent);
		    String value = "t,"+valueToBeSent+"\n";
		    System.out.println("Sending Time Intervals...");
		    System.out.println(value);
		    output.write(value.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	
	// QUICK FUNCTION TO CALCULATE THE ANGLE SUBTENDED
	 float angleSubtended(float x, float y, int c1, int c2) {
	    // angle subtended by (x,y) to fixed point (c1,c2) 
	      if(x == c2) {
	        return HALF_PI;  // To avoid INFINITY
	      }
	      float angle = (float) Math.atan(Math.abs(y - c2) / Math.abs(x - c1));
	      if(x <= c1 && y < c2) {
	        angle = PI-angle;   // Quadrant 2
	      }
	      if(x < c1 && y >= c2) {
	        angle = PI+angle;   // Quadrant 3
	      }
	      if(x >= c1 && y > c2) {
	        angle = (2*PI) - angle;  // Quadrant 4
	      }
	      return angle;
	   }
	
	public void drawIsopter(int[] meridians, int[] iso_center, float diameter,
			Group g) {

		Ellipse el;

		// fill(255);
		for (int i = 7; i >= 1; i--) {
			float r_IsopterRange = (float) (diameter * ((i * 15.0) / 210));

			el = new Ellipse(iso_center[0], iso_center[1], r_IsopterRange,
					r_IsopterRange);
			el.setFill(Color.WHITE);
			el.setStroke(Color.GREY);
			g.getChildren().add(el);
			// fill(#bbbbbb);
			// ind[i] = new Text(iso_center[0] - 5, (int) yc, (i * 15) + "");
			// ind[i].setFill(Color.BLACK);
			// System.out.println((i * 15));
			// g.getChildren().add(ind);

		}
		for (int i = 0; i < 24; i++) {
			xm = (float) (Math.cos(Math.toRadians(-i * 15)) * diameter
					/ 2 + iso_center[0]);
			ym = (float) (Math.sin(Math.toRadians(-i * 15)) * diameter
					/ 2 + iso_center[1]);
			for (int j = 0; j < 2; j++) {
				endpoint[i][j] = xm;
				endpoint[i][j] = ym;
			}
			ln[i] = new Line(iso_center[0], iso_center[1], xm, ym);
			ln[i].setStrokeWidth(2.5);
			ln[i].setOnMouseMoved(mouseHandler_enter_meridian);
			ln[i].setOnMouseExited(mouseHandler_exit_meridian);
			ln[i].setOnMouseClicked(mouseHandler_press_meridian);
			
			//g.getChildren().add(button);
			/*ln[i].setOnKeyPressed(//keyHandler_press_meridian);
					new EventHandler<KeyEvent>() {

			            @Override
			            public void handle(KeyEvent event) {
			                if (event.getCode() == KeyCode.ENTER) {
			                    System.out.println("Enter Pressed");
			                }
			            }
			        });
			*/
			
			// ln[i].setOnKeyPressed(mouseHandler_keypress_meridian);
			g.getChildren().add(ln[i]);
			float xt = (float) (Math.cos(Math.toRadians(-i * 15))
					* (diameter + 30) / 2 + iso_center[0] - 10);
			float yt = (float) (Math.sin(Math.toRadians(-i * 15))
					* (diameter + 20) / 2 + iso_center[1] + 5);
			ind[i] = new Text(xt, yt, (i * 15) + ""); // draw the label of the
														// meridian (in degrees)
			ind[i].setOnMouseEntered(mouseHandler_enter_sweep);
			ind[i].setOnMousePressed(mouseHandler_press_sweep);
			ind[i].setOnMouseExited(mouseHandler_exit_sweep);
			g.getChildren().add(ind[i]);
		}
		//System.out.println("HIIIII");
//		g.setOnKeyPressed(keyHandler_press_meridian);
//		g.getChildren().add(button);
	}

	EventHandler<KeyEvent> keyHandler_press_meridian =new EventHandler<KeyEvent>()
	{
		@Override
		public void handle(KeyEvent keyEvent)
		{
			if(keyEvent.getCode().equals(KeyCode.SPACE))
			{
				if(flagm == 1)
				{
				System.out.println("Space pressed");
				ln[clicked_mer].setStroke(Color.BLUE);
				flagm=0;
				//clicked_mer=28;
				}
			}
			//System.out.println(keyEvent.getCode());
			flagged_test = false;
		    System.out.println("Space Bar Pressed Now Isopter..");
		    try {
				output.write("x\n".getBytes());
				new Stop();
				System.out.println("Stop");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("err6");
				e.printStackTrace();
			}
		}
	};
	
	EventHandler<MouseEvent> mouseHandler_enter_meridian = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Line l = (Line) mouseEvent.getSource();
			for (int i = 0; i < 24; i++) {
				if (l == ln[i])
				{
					//System.out.println(i);
				}
			}
			hovered_object='m';
			meridians[hovered_count]=Math.abs(meridians[hovered_count]);
			angle = (float)Math.toDegrees(angleSubtended(xm, ym, iso_center[0], iso_center[1]));
			hovered_count = (int)((angle + 5) / 15) % 24;
			// System.out.println(l);
			if(l.getStroke() == Color.BLUE){
				l.setStroke(Color.CYAN);
				done2=1;
			}
			else if(l.getStroke() == Color.BLACK)
				l.setStroke(Color.CYAN);
		}
	};
	
	EventHandler<MouseEvent> mouseHandler_exit_meridian = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Line l = (Line) mouseEvent.getSource();
			for (int i = 0; i < 24; i++) {
				if (l == ln[i]){}
					//System.out.println(i);
			}
			if(done2==1 && l.getStroke() == Color.CYAN){
				done2=0;
				l.setStroke(Color.BLUE);
			}
			else if(l.getStroke() == Color.CYAN)
				l.setStroke(Color.BLACK);
		}
	};
	
	EventHandler<MouseEvent> mouseHandler_press_meridian = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Line l = (Line) mouseEvent.getSource();
			for (int i = 0; i < 24; i++) {
				if (l == ln[i])
				{
					//System.out.println(i);
					clicked_mer=i;
					flagm=1;
				}
			}
			l.setStroke(Color.GREEN);
			flagged_test = true;
			try {
				System.out.println("Sending data to arduino..");
				System.out.println(hovered_count);
				System.out.println(((24 - hovered_count) % 24 + 1));
				String s="m,"+((24 - hovered_count) % 24 + 1)+"\n";
				System.out.println((char)((24 - hovered_count) % 24 + 1));
				System.out.println(s);
//				output.write(s.getBytes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Kya kar raha hai??");
				e.printStackTrace();
			}
			lastTest_Hobject = 'm';
		    lastTest_Hcount = hovered_count;
		    previousMillis = now.get(ChronoField.MILLI_OF_SECOND); // start the timer from now
		    status = "Meridian";
		    current_sweep_meridian = hovered_count; // this needs to be stored in a seperate variable    
			//l.removeEventFilter(MouseEvent.MOUSE_EXITED,mouseHandler_exit_meridian);
		}
	};
	
	EventHandler<MouseEvent> mouseHandler_enter_sweep = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Text l = (Text) mouseEvent.getSource();
			l.setFill(Color.CYAN);
			System.out.println("Enter Sweep..");
			flags=0;
			hovered_object = 's';
			meridians[hovered_count] = Math.abs(meridians[hovered_count]);
			angle = (float)Math.toDegrees(angleSubtended(xm, ym, iso_center[0], iso_center[1]));
			hovered_count = (int)((angle + 5) / 15) % 24;
			meridians[hovered_count] = -1 * Math.abs(meridians[hovered_count]);
		}
	};
	
	EventHandler<MouseEvent> mouseHandler_exit_sweep = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Text l = (Text) mouseEvent.getSource();
			if(flags==0)
			l.setFill(Color.BLACK);
			System.out.println("Exit Sweep..");
		}
	};
	
	EventHandler<MouseEvent> mouseHandler_press_sweep = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Text l = (Text) mouseEvent.getSource();
			l.setFill(Color.GREEN);
			flags=1;
			String x = l.toString();
			for (int i = 0; i < 24; i++) {
				if (x == (i*15) + "") {
					//for (int j = 0; j < 2; j++) {
						//System.out.print(endpoint[i][j]);
						Arc s=new Arc((double)endpoint[i][0],(double)endpoint[i][1],5.0f,5.0f,0.0f,360.0f);
						Group gg=new Main().g;
						gg.getChildren().add(s);
					//} 	
					System.out.println();
				}
			}
			sendTimeIntervals((24 - hovered_count) % 24 + 1);
			try{
			String s= hovered_object+","+((24 - hovered_count) % 24 + 1)+"\n";
			output.write(s.getBytes());
			}
			catch(IOException e){
				e.printStackTrace();
			}
		    lastTest_Hobject = hovered_object;
		    lastTest_Hcount = hovered_count;
		    previousMillis = now.get(ChronoField.MILLI_OF_SECOND); // start the timer from now
		    status = "sweep";
		    current_sweep_meridian = hovered_count; // this needs to be stored in a seperate variable
		}
	};

}
