package application;

import java.awt.Point;
import java.io.IOException;
import java.time.temporal.ChronoField;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Hemis extends Setup
{
	int clicked_hemi;
	float[] hemi_center = { 900.0f, 450.0f };
	float[] hemi_angle = { 90.0f, 270.0f };
	float hemi_diameter = 45.0f;
	Button buttonh;
	int done1;
	
	public Hemis() {
		h = new Arc[4];
		buttonh=new Button("hemi");
		flagh=new int[4];
	}
	
	public void drawHemis(float[] hemi_center, float[] hemi_angle,
			float hemi_diameter, Group g) {

		h[0] = new Arc(hemi_center[0] - 4, hemi_center[1], hemi_diameter,
				hemi_diameter, hemi_angle[0], 180.0f);
		h[1] = new Arc(hemi_center[0], hemi_center[1], hemi_diameter,
				hemi_diameter, hemi_angle[1], 180.0f);

		hemi_diameter = hemi_diameter - 15;

		h[2] = new Arc(hemi_center[0] - 4, hemi_center[1], hemi_diameter,
				hemi_diameter, hemi_angle[0], 180.0f);
		h[3] = new Arc(hemi_center[0], hemi_center[1], hemi_diameter,
				hemi_diameter, hemi_angle[1], 180.0f);

		for (int i = 0; i < 4; i++) {
			new GUI_Components().colorQuadsHemis(h[i], g);
			h[i].setOnMouseEntered(mouseHandler_enter_hemi);
			h[i].setOnMouseExited(mouseHandler_exit_hemi);
			h[i].setOnMouseClicked(mouseHandler_click_hemi);
			flagh[i]=0;
		}
//		g.setOnKeyPressed(keyHandler_press_hemi);
		//g.getChildren().add(buttonh);
	}
	EventHandler<KeyEvent> keyHandler_press_hemi=new EventHandler<KeyEvent>()
	{
		public void handle(KeyEvent keyEvent)
		{
			if(keyEvent.getCode().equals(KeyCode.SPACE))
			{
				for(int i=0;i<4;i++)
				{
					if(flagh[i] == 1)
					{
						System.out.println("Space pressed");
						h[i].setFill(Color.BLUE);//h[clicked_hemi].setFill(Color.BLUE);
						flagh[i]=3;
						//clicked_mer=28;
					}
				}
			}

			flagged_test = false;
		    System.out.println("Space Bar Pressed Now");
		    try {
				output.write("x\n".getBytes());
				new Stop();
				System.out.println("Stop");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	EventHandler<MouseEvent> mouseHandler_click_hemi=new EventHandler<MouseEvent>()
	{
		public void handle(MouseEvent mouseEvent)
		{
			Arc a=(Arc) mouseEvent.getSource();
			for(int i=0;i<4;i++)
			{
				if(h[i]==a)
				{
					flagh[i]=1;
					clicked_hemi=i;
					System.out.println(i);
				}
			}
			a.setFill(Color.GREEN);
			try {
				String value = hovered_object + "," + hovered_count + "\n";
		    	System.out.println("Hemis...");
		    	System.out.println(value);
				output.write(value.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     lastTest_Hobject = hovered_object;
		     lastTest_Hcount = hovered_count;
		     previousMillis = now.get(ChronoField.MILLI_OF_SECOND); // start the timer from now
		     status = "hemi";
		     current_gross_test = hovered_count;
		}
		
	};
	
	EventHandler<MouseEvent> mouseHandler_enter_hemi = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Arc a = (Arc) mouseEvent.getSource();
			hovered_object='h';
			for(int i=1;i<4;i++)
			{
				if(h[i]==a){
					System.out.println(i);
					hovered_count = i;
				}
			}
			if(a.getFill()==Color.WHITE)
				a.setFill(Color.CYAN);
			else if(a.getFill()==Color.BLUE)
			{
				a.setFill(Color.CYAN);
				done1=1;
			}
		}
	};

	EventHandler<MouseEvent> mouseHandler_exit_hemi = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			Arc a = (Arc) mouseEvent.getSource();
			for(int i=0;i<4;i++)
			{
				if(h[i]==a)
					System.out.println(i);
			}
			if(a.getFill()==Color.CYAN && done1==1)
			{
				a.setFill(Color.BLUE);
				done1=0;
			}
			else if(a.getFill()==Color.CYAN)
				a.setFill(Color.WHITE);
		}
	};
}
