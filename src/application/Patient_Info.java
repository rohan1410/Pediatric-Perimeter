package application;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.text.*;
import java.io.*;
import java.time.*;

public class Patient_Info extends Setup
{
		public int occipitaldistance;
		// the textfiles which is used to save information to text files
		
		public Patient_Info() {
			patient_note = "";
			LocalDateTime now = LocalDateTime.now();
			int year = now.getYear();
			int month = now.getMonthValue();
			int day = now.getDayOfMonth();
			int hour = now.getHour();
			int minute = now.getMinute();
			int second = now.getSecond();
			JLabel lbl1, lbl2, lbl3, lbl4, lbl5;
			JTextField pname, pMR, pdob, pmilestone_details, potc;
			JPanel labels;
			JFrame det = new JFrame();
			det.setSize(850, 640);
			JPanel panel = new JPanel();
			panel.setSize(850,640);
			panel.setLayout(new GridLayout(4, 1));
			JLabel ard = new JLabel("Arduino");
			JLabel camc = new JLabel("Camera");
			// 198-512.png

			JPanel hd = new JPanel();
			hd.setLayout(null);

			Label cap = new Label("Patients Info", Label.CENTER);
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

			pname = new JTextField(10);
			// pMR = new JTextField(10);
			pMR = new JTextField(10);

			DateFormat df = new SimpleDateFormat("dd/MM/yy");
			pdob = new JFormattedTextField(df);

			pmilestone_details = new JTextField(10);

			NumberFormat intFormat = NumberFormat.getIntegerInstance();
			NumberFormatter numberFormatter = new NumberFormatter(intFormat);
			numberFormatter.setValueClass(Integer.class); // optional, ensures you
															// will always get a int
															// value
			numberFormatter.setAllowsInvalid(false);
			numberFormatter.setMinimum(0); // Optional
			numberFormatter.setMaximum(28); // Optional
			potc = new JFormattedTextField(numberFormatter);

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
			Label inscap = new Label("Instructions", Label.CENTER);
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
				       "Enter Correct Details",
				       JOptionPane.OK_CANCEL_OPTION,
				       JOptionPane.PLAIN_MESSAGE);

				    boolean firstTime = true;            //For validation message
				    if (result == JOptionPane.OK_OPTION) {

				     //This While loop is used for validation of form
				     while (pname.getText().length() == 0 || pMR.getText().length() == 0 || pdob.getText().length() == 0 || pmilestone_details.getText().length() == 0 || potc.getText().length() == 0) {
				      if (firstTime) {
				       JLabel l4 = new JLabel("Please fill the details correctly.");
				       l4.setForeground(Color.RED);
				       l4.setFont(new Font("Serif", Font.BOLD, 18));
				       instr.add(l4);
				       panel.add(instr);
				       firstTime = false;
				      }
				      result = JOptionPane.showConfirmDialog(
				    	       det, // use your JFrame here
				    	       panel,
				    	       "Enter Correct Details",
				    	       JOptionPane.OK_CANCEL_OPTION,
				    	JOptionPane.PLAIN_MESSAGE);
				      if (result == JOptionPane.CANCEL_OPTION) {
				       //System.out.println("HI");
				       System.exit(0);
				       break;
				      }
				}

			//det.getContentPane().add(panel);
			//det.setVisible(true);
			// Validation Ends here
			patient_name = pname.getText();
			patient_MR = pMR.getText();
			patient_dob = pdob.getText();
			patient_milestone_details = pmilestone_details.getText();
			patient_OTC = potc.getText();
			
			base_folder = year + "/" + month + "/" + day + "/" + patient_name + "_" + hour + "_" + minute + "_hrs";
			
			occipitalDistance = Integer.parseInt(patient_OTC.trim());
			File f= new File(workingDirectory+base_folder + "/" + patient_name + "_isopter.txt");
			f.getParentFile().mkdirs();
			try {
				isopter_text = new PrintWriter(f, "UTF-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		    isopter_text.println("Isopter angles for patient " + patient_name);
		    isopter_text.println("MR No : " + patient_MR);
		    isopter_text.println("Date of Birth : " + patient_dob);
		    isopter_text.println("Milestone Details : " + patient_milestone_details);
		    isopter_text.println("Occipital to Corneal Distance (cm) : " + patient_OTC);
		    isopter_text.println("Timestamp : " + hour + ":" + minute + ":" + second);
		    isopter_text.println("Timestamp\t|Meridian\t|Angle\t|Reaction Time (ms)\t|Flag\t");
		    isopter_text.flush();
//		    System.out.println("1");
		    f= new File(workingDirectory+base_folder + "/" + patient_name + "_quadHemi.txt");
			f.getParentFile().mkdirs();
		    try {
				quadHemi_text = new PrintWriter(f, "UTF-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		     quadHemi_text.println("Meridian and Quad tests for patient " + patient_name);
		     quadHemi_text.println("MR No : " + patient_MR);
		     quadHemi_text.println("Date of Birth : " + patient_dob);
		     quadHemi_text.println("Milestone Details : " + patient_milestone_details);
		     quadHemi_text.println("Occipital to Corneal Distance (cm) : " + patient_OTC);
		     quadHemi_text.println("Timestamp : " + hour + ":" + minute + ":" + second);
		     quadHemi_text.println("Timestamp\t|Test done\t|Reaction Time\t|Flag\t");
		     quadHemi_text.flush();
			}
//				    System.out.println("2");
			if (result == JOptionPane.CANCEL_OPTION) {
				//System.out.println("HI");
				System.exit(0);
	 		 }
			// Writing data into file	
			try{
				FileInputStream fs = new FileInputStream(new File(workingDirectory+"AngleData.xlsx"));
				//Create Workbook instance holding reference to .xlsx file
				workbook = new XSSFWorkbook(fs);
		        //Get first/desired sheet from the workbook
		        XSSFSheet sheet = workbook.getSheetAt(0);
		        //Iterate through each rows one by one
		        int numberOfRows = sheet.getLastRowNum();
		        //System.out.println("OccipitalDistance: " + occipitalDistance);
		        for (int rowNumber = 1; rowNumber <= numberOfRows; rowNumber++) {
		            Row row = sheet.getRow(rowNumber);
		            //System.out.println(rowNumber);
		            Cell cell = row.getCell(0);
		            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		            int meridianNumber = (int)(cell.getNumericCellValue());
		            cell = row.getCell(2);
		            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		            int pixelNumber = (int)(cell.getNumericCellValue());
		            cell = row.getCell(3);
		            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		            float angleValue = (float)(cell.getNumericCellValue());

		            cell = row.getCell(10);
		            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		            float x = (float)(cell.getNumericCellValue());

		            cell = row.getCell(11);
		            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		            float y = (float)(cell.getNumericCellValue());

		            cell = row.getCell(12);
		            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		            float z = (float)(cell.getNumericCellValue());

		            float zdash = z - (7 + occipitalDistance);
		            boolean flag = (zdash < 0) ? true : false;
		            zdash = Math.abs(zdash);
		            float finalAngleValue = (float) Math.toDegrees(Math.atan(zdash / ((float) Math.sqrt(x * x + y * y))));
		            if (flag) {
		             finalAngleValue += 90;
		            } else {
		             finalAngleValue = 90 - finalAngleValue;
		            }
		            if (pixelNumber == 1) {
		             bottomMostAngle[meridianNumber - 1] = finalAngleValue;
		            // System.out.println((meridianNumber - 1) + " " + bottomMostAngle[meridianNumber - 1]);
		            }
		            angleData[meridianNumber - 1][pixelNumber - 1] = finalAngleValue;
		           }
		        fs.close();
			}
			catch(IOException e){
				System.out.println("Error while importing file");
			}
			/*for(int i=1;i<30;i++){
				for(int j=0;j<30;j++){
					System.out.print(angleData[i][j]+"\t");
				}
				System.out.println("");
			}*/
		}
	}

