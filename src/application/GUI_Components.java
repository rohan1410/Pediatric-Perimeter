package application;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GUI_Components extends Setup{

	int meridians[] = { 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 28,
			28, 28, 28, 28, 28, 28, 28, 28, 28, 28 }; // negative value means
														// its being hovered
														// over
	int[] flagm,flagh,flagq;
	int clicked_mer,clicked_quad,clicked_hemi;
	Arc[] q, h, p;
	float[] quad_angle = { 0.0f, 90.0f, 180.0f, 270.0f };
	float[] quad_center = { 740.0f, 560.0f };
	float dx = 2.5f, dy = 2.5f;
	float diameter = 300;
	int[] iso_center = { 830, 230 };
	float[] hemi_center = { 880.0f, 560.0f };
	float[] hemi_angle = { 90.0f, 270.0f };
	float quad_diameter = 45.0f;
	float hemi_diameter = 45.0f;
	Point mouse;
	Line[] ln;
	Text[] ind;
	float[][] endpoint;
	Button button,buttonh,buttonq;
	int done1;
	
	public GUI_Components() {
		h = new Arc[4];
		q = new Arc[8];
		ln = new Line[24];
		ind = new Text[24];
		p = new Arc[3];
		endpoint = new float[24][2];
		button=new Button();
		buttonh=new Button("hemi");
		flagh=new int[4];
	}

	public void mouse_pos() {
		// Thread, because the main process shouldn't pause
		Thread t = new Thread(new Runnable() {
			public void run() {
				mouse = java.awt.MouseInfo.getPointerInfo().getLocation();
				System.out.println(mouse.x + " " + mouse.y);
			}
		});
		t.start();
	}

	public void heading(Group g)
	{
		Text hd = new Text("Pediatric Perimetry");
		hd.setFont(Font.font("Verdana",FontWeight.BOLD,30));
		hd.setLayoutX(500);
		hd.setLayoutY(30);
		g.getChildren().add(hd);
	}
	// h1 and h2 are outer hemis and h3 and h4 are inner hemis
	
	public void colorQuadsHemis(Arc c, Group g) {
		c.setFill(Color.WHITE);
		c.setStroke(Color.BLACK);
		c.setType(ArcType.ROUND);
		g.getChildren().add(c);
	}

	
	
	public void drawPatterns(Group g) {
		float x = 170.0f;
		float y = 650.0f;
		for (int i = 0; i < 3; i++) {
			p[i] = new Arc(x, y, 5.0f, 5.0f, 0.0f, 360.0f);
			x += 130;
			//JLabel j=new JLabel(new ImageIcon("Images/pattern1B.png"));
			/*Collection<? extends Node> k=(Collection<? extends Node>) new File("Images/pattern1B.png");
			g.getChildren().addAll(k);*/
			//iv=new ImageView();
			//im=new Image("file:pattern1B.png");
			
			g.getChildren().add(p[i]);
		}
		SwingNode[] p=new SwingNode[3];
		for(int i=0;i<3;i++)
		{
			p[i]=new SwingNode();
		}
		drawk(p[0],p[1],p[2]);
		float xx=180,yy=580;
		for(int i=0;i<3;i++)
		{
			p[i].setLayoutX(xx);
			p[i].setLayoutY(yy);
			xx+=130;
			g.getChildren().add(p[i]);
		}
	}
	
	public void drawk(final SwingNode s,final SwingNode p,final SwingNode r) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	try{
            		ImageIcon img1 = new ImageIcon(workingDirectory+"pattern1B.png");
            		b1.setIcon(img1);
            		b1.setBackground(java.awt.Color.white);
            		s.setContent(b1);
            		ImageIcon img2 = new ImageIcon(workingDirectory+"pattern2B.png");
            		b2.setIcon(img2);
            		b2.setBackground(java.awt.Color.white);
            		p.setContent(b2);
            		ImageIcon img3 = new ImageIcon(workingDirectory+"pattern3B.png");
            		b3.setIcon(img3);
            		b3.setBackground(java.awt.Color.white);
            		r.setContent(b3);
            	}
            	catch(Exception e)
            	{
            		System.out.println(e);
            	}
            }
        });
    }
}
