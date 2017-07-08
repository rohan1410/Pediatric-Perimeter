package application;

import javafx.event.EventHandler;
import javafx.scene.Group;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.temporal.ChronoField;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class EventCall extends Setup{
	
	public EventCall(){}	
	
	public void EventGroup(Group g){
		g.setOnKeyPressed(keyHandler_press);
		b1.addActionListener(new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("B1 was pressed..");
		        String value = "p,1\n";
		        try {
					output.write(value.getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        pattern_state[0]=2;
		    }
		});
		b2.addActionListener(new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("B2 was pressed..");
		        String value = "p,2\n";
		        try {
					output.write(value.getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        pattern_state[1]=2;
		    }
		});
		b3.addActionListener(new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("B3 was pressed..");
		        String value = "p,3\n";
		        try {
					output.write(value.getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        pattern_state[2]=2;
		    }
		});
		note.addActionListener(new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        System.out.println("Add a note..");
		        addnote();
		    }
		});
	}
	
	public void addnote(){
		Thread t = new Thread(new Runnable() {
		     public void run() {
		      addanote();
		     }
		    });
		t.start();
	}
	
	public void addanote(){
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(2, 1));
	    JLabel lbl1 = new JLabel("Add Note");

	    JFrame addnoteb = new JFrame();
	    addnoteb.setSize(100,100);
	    
	    final JTextField pnote = new JTextField(50);
	    pnote.setEditable(true);
	    JPanel labels = new JPanel();
	    labels.setLayout(new GridLayout(2, 1));
	    labels.add(lbl1);
	    labels.add(pnote);

	    panel.add(labels);
	    panel.add(pnote);
	    addnoteb.add(panel);
	    addnoteb.setVisible(true);
	    pnote.requestFocusInWindow();
	    int result = JOptionPane.showConfirmDialog(
	     addnoteb, // use your JFrame here
	     panel,
	     "Special Note for " + patient_name,
	     JOptionPane.OK_CANCEL_OPTION,
	     JOptionPane.PLAIN_MESSAGE);

	    if (result == JOptionPane.OK_OPTION) {
	      //Add only if not empty
	     if (pnote.getText().length() != 0) {
	       //This if condition works only for the first time
	      if (patient_note.length() == 0) {
	       File f= new File(workingDirectory+base_folder + "/" + patient_name + "_note.txt");
	       f.getParentFile().mkdirs();
	       try {
				note_text = new PrintWriter(f, "UTF-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	       note_text.println("Note for patient " + patient_name);
	       note_text.println("MR No : " + patient_MR);
	       note_text.println("Milestone Details : " + patient_milestone_details);
	       note_text.println("Note - ");
	      }
	      //Appends everytime
	      note_text.println();
	      note_text.println("Timestamp : " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());
	      patient_note = pnote.getText();
	      note_text.println("" + patient_note);
	      note_text.println("________________\n");
	      note_text.flush();
	     }
	    }
	}
	
	EventHandler<KeyEvent> keyHandler_press=new EventHandler<KeyEvent>()
	{
		@Override
		public void handle(KeyEvent keyEvent)
		{
			if(keyEvent.getCode().equals(KeyCode.SPACE))
			{
				System.out.println("Space bar is pressed");
				for(int i=0;i<8;i++)
				{
					if(flagq[i] == 1)
					{
						System.out.println("Space pressed");
						q[i].setFill(Color.BLUE);//q[clicked_quad].setFill(Color.BLUE);
						flagq[i]=3;
						//clicked_mer=28;
						//System.out.println(keyEvent.getCode());
						flagged_test = false;
					    System.out.println("Space Bar Pressed Now Quads..");
					    try {
							//output.write("x\n".getBytes());
							new Stop();
							System.out.println("Stop");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if(flagm == 1)
				{
				System.out.println("Space pressed");
				ln[clicked_mer].setStroke(Color.BLUE);
				flagm=0;
				//clicked_mer=28;
				flagged_test = false;
			    System.out.println("Space Bar Pressed Now Isopter..");
			    try {
//					output.write("x\n".getBytes());
					new Stop();
					System.out.println("Stop");	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("err6");
					e.printStackTrace();
					}
				}
				for(int i=0;i<4;i++)
				{
					if(flagh[i] == 1)
					{
						System.out.println("Space pressed");
						h[i].setFill(Color.BLUE);//h[clicked_hemi].setFill(Color.BLUE);
						flagh[i]=3;
						//clicked_mer=28;
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
				}
				if(pattern_state[0]==2 || pattern_state[1]==2 || pattern_state[2]==2)
				{
					try {
						output.write("x\n".getBytes());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pattern_state[0]=1;
					pattern_state[1]=1;
					pattern_state[2]=1;
				}
			}
		}
	};
	
}
