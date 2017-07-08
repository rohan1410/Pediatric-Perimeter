package application;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.awt.*;


import javafx.embed.swing.SwingNode;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.text.*;
import java.io.*;
import java.time.*;

public class Bangs extends Setup
{
	
	Label lsl, brl;
	Button pinfo,finish;
	public void addBangs(Group g)
	{
		Bangs bb = new Bangs();
		bb.flagAndNote(g);
		bb.ledSpeed(g);
		bb.fixBrightness(g);
		bb.patInfo(g);
		bb.finish(g);
		bb.test_det(g);
	}
	
	public void flagAndNote(Group g)
	{
		SwingNode[] fn=new SwingNode[2];
		for(int i=0;i<2;i++)
		{
			fn[i]=new SwingNode();
		}
		fan(fn[0],fn[1]);
		float xx=970,yy=420;
		for(int i=0;i<2;i++)
		{
			fn[i].setLayoutX(xx);
			fn[i].setLayoutY(yy);
			xx+=50;
			g.getChildren().add(fn[i]);
		}
	}
	
	public void fan(final SwingNode s,final SwingNode p) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	try{
            		s.setContent(new JLabel(new ImageIcon(workingDirectory+"flagB.png")));
            		ImageIcon noteB = new ImageIcon(workingDirectory+"noteB.png");
            		note.setIcon(noteB); 
            		p.setContent(note);
            		//System.out.println(workingDirectory+"flagB.png");
            		//System.out.println(workingDirectory+"noteB.png");
            	}
            	catch(Exception e)
            	{
            		System.out.println(e);
            	}
                
            }
        });
    }
	
	
	public void ledSpeed(Group g)
	{
		ls = new Slider(0,10,3);
		ls.setShowTickMarks(true);
		ls.setShowTickLabels(true);
		ls.setMajorTickUnit(0.25f);
		ls.setBlockIncrement(0.1f);
		ls.setLayoutX(840);
		ls.setLayoutY(580);
		g.getChildren().add(ls);
		lsl = new Label("LED Speed (deg/sec)");
		lsl.setLayoutX(840);
		lsl.setLayoutY(560);
		g.getChildren().add(lsl);
	}
	
	public void fixBrightness(Group g)
	{
		br = new Slider(0,10,3);
		br.setShowTickMarks(true);
		br.setShowTickLabels(true);
		br.setMajorTickUnit(0.25f);
		br.setBlockIncrement(0.1f);
		br.setLayoutX(1010);
		br.setLayoutY(580);
		g.getChildren().add(br);
		brl = new Label("FIXATION Br. (cd/sq.mt)");
		brl.setLayoutX(1010);
		brl.setLayoutY(560);
		g.getChildren().add(brl);
	}
	
	public void patInfo(Group g)
	{
		pinfo = new Button("Patient's Info");
		pinfo.setLayoutX(880);
		pinfo.setLayoutY(650);
		pinfo.setPrefSize(110, 35);
		pinfo.setOnMouseClicked(patient);
		g.getChildren().add(pinfo);
	}
	
	EventHandler<MouseEvent>patient=new EventHandler<MouseEvent>()
	{
		public void handle(MouseEvent m)
				{
					try {
						JLabel lbl1, lbl2, lbl3, lbl4, lbl5;
						JPanel labels;
						JFrame det = new JFrame();
						det.setSize(850, 900);
						JPanel panel = new JPanel();
						panel.setSize(850,640);
						panel.setLayout(new GridLayout(4, 1));
						JLabel ard = new JLabel("Arduino");
						JLabel camc = new JLabel("Camera");
						// 198-512.png

						JPanel hd = new JPanel();
						hd.setLayout(null);

						java.awt.Label cap = new java.awt.Label("Patients Info",java.awt.Label.CENTER);
						cap.setFont(new Font("Serif", Font.BOLD, 30));
						cap.setForeground(Color.BLACK);

						cap.setLocation(240, 40);
						cap.setSize(250, 90);
						String path1 = workingDirectory + "r.png";
						String path2 = workingDirectory + "g.png";
						ImageIcon red, grn;
						//System.out.println("1");
						if (output == null) {
							red = new ImageIcon(path1);
						} else {
							red = new ImageIcon(path2);
						}
						//System.out.println("2");
						if (webcam == null) {
							grn = new ImageIcon(path1);
						} else {
							grn = new ImageIcon(path2);
						}
						
						JLabel red1 = new JLabel(red);
						red1.setIcon(red);
						red1.setLocation(630, 25);
						red1.setSize(10, 10);
						JLabel grn1 = new JLabel(grn);
						grn1.setLocation(630, 40);
						grn1.setSize(10, 10);
						hd.add(red1);
						hd.add(grn1);
						hd.add(cap);
						ard.setLocation(650, 20);
						ard.setSize(160, 20);
						camc.setLocation(650, 35);
						camc.setSize(60, 20);
						hd.add(ard);
						hd.add(camc);

						panel.add(hd);

						lbl1 = new JLabel("Patient Name");
						lbl2 = new JLabel("MR Number");
						lbl3 = new JLabel("Date of Birth (dd/mm/yy)");
						lbl4 = new JLabel("Milestone details");
						lbl5 = new JLabel("Occipital Distance (0 - 28 cm)");
						
						JLabel pname = new JLabel(patient_name);
					    JLabel pMR = new JLabel(patient_MR);
					    JLabel pdob = new JLabel(patient_dob);
					    JLabel pmilestone_details = new JLabel(patient_milestone_details);
					    JLabel potc = new JLabel(patient_OTC);
					    
					    JPanel setl = new JPanel(null);

						labels = new JPanel();
						labels.setLayout(new GridLayout(5, 2));
						labels.add(lbl1);
						labels.add(pname);
						labels.add(lbl2);
						labels.add(pMR);
						labels.add(lbl3);
						labels.add(pdob);
						labels.add(lbl4);
						labels.add(pmilestone_details);
						labels.add(lbl5);
						labels.add(potc);
						labels.setSize(600, 90);
						labels.setLocation(100, 10);
						
						setl.add(labels);
						
						JPanel instr = new JPanel(new GridLayout(0, 1));
						java.awt.Label inscap = new java.awt.Label("Instructions", java.awt.Label.CENTER);
						inscap.setFont(new Font("Serif", Font.BOLD, 15));
						inscap.setForeground(Color.BLACK);
						instr.add(inscap);
						JLabel l1 = new JLabel(
								"                    1. Parents should be given informed consent for signing.");
						JLabel l2 = new JLabel(
								"	                    2. Only the parents and 3 (maximum) examiners would be allowed to stay inside the room during the testing.                   ");
						JLabel l3 = new JLabel(
								"                    3. Try re-connecting the arduino before you run the code ");
						instr.add(l1);
						instr.add(l2);
						instr.add(l3);

						panel.add(setl);
						panel.add(instr);
						
						int result = JOptionPane.showConfirmDialog(
							     det, // use your JFrame here
							     panel,
							     "Current Patient's Information",
							     JOptionPane.DEFAULT_OPTION,
							     JOptionPane.PLAIN_MESSAGE);

						// StreamGobbler.StreamGobblerLOGProcess(p);
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
	};
	
	public void finish(Group g)
	{
		finish = new Button("Finish");
		finish.setLayoutX(1010);
		finish.setLayoutY(650);
		finish.setPrefSize(110, 35);
		finish.setOnMouseClicked(finished);
		g.getChildren().add(finish);
	}
	
	EventHandler<MouseEvent> finished=new EventHandler<MouseEvent>()
	{
		public void handle(MouseEvent m)
		{
			try {
				Process p = Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /c start taskkill /IM ffmpeg.exe /f ");
				// StreamGobbler.StreamGobblerLOGProcess(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	};
	
	public void test_det(Group g)
	{
		SwingNode k=new SwingNode();
		test_details(k);
		g.getChildren().add(k);
		
	}
	
	public void test_details(final SwingNode s) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	try{
            		rt_value.setText(String.valueOf(0));
            		cur_test_value.setText("Undefined");
            		test_status_value.setText("Undefiened");
            		j.add(rt);
            		j.add(rt_value);
            		j.add(cur_test);
            		j.add(cur_test_value);
            		j.add(test_status);
            		j.add(test_status_value);
            		s.setLayoutX(570);
            		s.setLayoutY(613);
            		s.setContent(j);
            		//System.out.println("Sonu");
            	}
            	catch(Exception e)
            	{
            		System.out.println(e);
            	}
                
            }
        });
    }
}
