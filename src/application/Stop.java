package application;

import java.time.temporal.ChronoField;

public class Stop extends Setup {
	
	public Stop(){
		currentMillis = now.get(ChronoField.MILLI_OF_SECOND);
		reaction_time = currentMillis - previousMillis;
	    System.out.println("Reaction time is " + (reaction_time) + "ms");

	    // SAVE QUADS AND HEMIS TO TEXT FILE IN PROPER FORMAT
	    if (status == "quadrant") {
	     quadHemi_text.println();
	     quadHemi_text.print(now.getHour() + ":" + now.getMinute() + ":");
	     int s = now.getSecond();
	     if (s < 10) {
	      quadHemi_text.print("0" + (s) + "\t"); // so that the text formatting is proper
	     } else {
	      quadHemi_text.print((s) + "\t\t");
	     }
	     switch (current_gross_test) {
	      case 1:
	       quadHemi_text.print("TR Quad Outer");
	       break;

	      case 2:
	       quadHemi_text.print("TL Quad Outer");
	       break;

	      case 3:
	       quadHemi_text.print("BL Quad Outer");
	       break;

	      case 4:
	       quadHemi_text.print("BR Quad Outer");
	       break;

	      case 5:
	       quadHemi_text.print("TR Quad Full");
	       break;

	      case 6:
	       quadHemi_text.print("TL Quad Full");
	       break;

	      case 7:
	       quadHemi_text.print("BL Quad Full");
	       break;

	      case 8:
	       quadHemi_text.print("BR Quad Full");
	       break;
	     }
	     quadHemi_text.print("\t" + (reaction_time) + "\t");
	     quadHemi_text.flush();

	     // Check if this is flagged to discard it


	    }

	    if (status == "hemi") {
	     quadHemi_text.println();
	     quadHemi_text.print(now.getHour() + ":" + now.getMinute() + ":");
	     int s = now.getSecond();
	     if (s < 10) {
	      quadHemi_text.print("0" + (s) + "\t"); // so that the text formatting is proper
	     } else {
	      quadHemi_text.print((s) + "\t\t");
	     }
	     switch (current_gross_test) {
	      case 0:
	       quadHemi_text.print("R Hemi Outer");
	       break;
	      case 1:
	       quadHemi_text.print("L Hemi Outer");
	       break;
	      case 2:
	       quadHemi_text.print("R Hemi Full");
	       break;
	      case 3:
	       quadHemi_text.print("L Hemi Full");
	       break;
	     }
	     quadHemi_text.print("\t" + (reaction_time) + "\t");
	     quadHemi_text.flush();


	    }

	    //Save Meridians to a Text File in a Proper Format
	    if (status == "Meridian") {
	     quadHemi_text.println();
	     quadHemi_text.print(now.getHour() + ":" + now.getMinute() + ":");
	     int s = now.getSecond();
	     if (s < 10) {
	      quadHemi_text.print("0" + (s) + "\t"); // so that the text formatting is proper
	     } else {
	      quadHemi_text.print((s) + "\t\t");
	     }

	     quadHemi_text.print("Meridian " + "\t" + (current_sweep_meridian) * 15);
	     quadHemi_text.print("\t" + (reaction_time) + "\t");
	     quadHemi_text.flush();

	    }

	 //For Section on a Merdidan
	 if (status == "Section") {
	   
	    quadHemi_text.println();
	     quadHemi_text.print(now.getHour() + ":" + now.getMinute() + ":");
	     int s = now.getSecond();
	     if (s < 10) {
	      quadHemi_text.print("0" + (s) + "\t"); // so that the text formatting is proper
	     } else {
	      quadHemi_text.print((s) + "\t\t");
	     }

	     quadHemi_text.print("Section " +(lastTes_Hsubcount )*30+" - "+(lastTes_Hsubcount + 1)*30+ "\t" + (current_sweep_meridian) * 15);
	     quadHemi_text.print("\t" + (reaction_time) + "\t");
	     quadHemi_text.flush();
	   
	 }


	    // REDRAW AND SAVE THE ISOPTER TO FILE  
	    if (status == "sweep") {
	     // redraw isopter image to file

	     // write this to the isopter text file
	     isopter_text.println();
	     isopter_text.print(now.getHour() + ":" + now.getMinute() + ":");
	     int s = now.getSecond();
	     if (s < 10) {
	      isopter_text.print("0" + s + "\t"); // so that the text formatting is proper
	     } else {
	      isopter_text.print(s + "\t");
	     }

	     System.out.println("Stopped Meridian :" + current_sweep_meridian);
	     isopter_text.print((current_sweep_meridian) * 15 + "\t\t");
	     //CKR
	     if (Math.abs(meridians[current_sweep_meridian]) > 0 && Math.abs(meridians[current_sweep_meridian]) <= numberOfLEDs[(24 - current_sweep_meridian) % 24]) {
	      if (Math.abs(meridians[current_sweep_meridian]) > 3) {
	       isopter_text.print((angleData[(24 - current_sweep_meridian) % 24][numberOfLEDs[(24 - current_sweep_meridian) % 24] - Math.abs(meridians[current_sweep_meridian])])+ "(" + meridians[current_sweep_meridian]+ ")" + "\t");
	      } else if (Math.abs(meridians[current_sweep_meridian]) <= 3) {
	       isopter_text.print((angleData[(24 - current_sweep_meridian) % 24][numberOfLEDs[(24 - current_sweep_meridian) % 24] - Math.abs(meridians[current_sweep_meridian])]) + "(" + meridians[current_sweep_meridian] + ")" + "\t");
	      }else {
	        isopter_text.print("No Response" + "\t");}
	     }
	     //CKR
	     isopter_text.print((reaction_time) + "\t\t\t");
	     isopter_text.flush();

	     // Check if the the test is flagged or not to discard the test on GUI 
	     if (flagged_test == true) {
	      meridians[current_sweep_meridian] = 28;
	     }
	    }

	    // UPDATE STATUS VARIABLES
	    last_tested = status; // last tested thing becomes the previous value of status
	    status = "Test stopped. idle";
	    startRecording = false; // go back to low quality recording
	    rt_value.setText(String.valueOf(reaction_time));
	    cur_test_value.setText(last_tested);
	    test_status_value.setText(status);
	}

}
