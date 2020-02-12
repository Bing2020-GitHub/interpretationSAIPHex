package saipTool;


import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class mainEntry  {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel instatusLabel;
	private JLabel outstatusLabel;
	private JPanel controlPanel; 
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainEntry window = new mainEntry();
					window.showButton();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public mainEntry() {
		prepareGUI();
	}
	 private void prepareGUI(){	 	 
		 mainFrame = new JFrame("read TLV");
         //mainFrame.getContentPane().add(new readTree(data), BorderLayout.CENTER);
	     mainFrame.setSize(400,400);
	     mainFrame.setLayout(new GridLayout(4, 1));
	     mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	     });    
	     headerLabel = new JLabel("", JLabel.CENTER);        
	     instatusLabel = new JLabel("",JLabel.CENTER);  
	     outstatusLabel = new JLabel("", JLabel.CENTER);

	     instatusLabel.setSize(350,100);
	     outstatusLabel.setSize(350,100);

	     controlPanel = new JPanel();
	     controlPanel.setLayout(new FlowLayout());

	     mainFrame.add(headerLabel);
	     mainFrame.add(controlPanel);
	     mainFrame.add(instatusLabel);
	     mainFrame.add(outstatusLabel);
	     mainFrame.setVisible(true); 
	   }

	   private void showButton(){
		   headerLabel.setText("Control in action: Button"); 
           JButton hex2ASN1text_btn = new JButton("hex2ASN1Text");        
		   JButton ans1text2hex_btn = new JButton("asn1Text2hex");
		   ans1text2hex_btn.setHorizontalTextPosition(SwingConstants.LEFT);   

		   hex2ASN1text_btn.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		            SelectDBDialog dialog = new SelectDBDialog(mainFrame,"Open....");
					dialog.setVisible(true);
					String filename = dialog.getFile();
					String fileDir = dialog.getDirectory();
					instatusLabel.setText("Input file :"+fileDir+filename );
					outstatusLabel.setText("Output File :" + fileDir+filename+".out");
					new hex2ASN1Text(fileDir+filename);
					
		         }          
		      });

		     

		   ans1text2hex_btn.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		        	
			        SelectDBDialog dialog = new SelectDBDialog(mainFrame,"Open....");
					dialog.setVisible(true);
					String filename = dialog.getFile();
					String fileDir = dialog.getDirectory();
					instatusLabel.setText("Input file :"+fileDir+filename );
					outstatusLabel.setText("Output File :" + fileDir+filename+".asnhex");
					new asn1Text2Hex(fileDir+filename);
					//System.exit(0);
					
		           /*SelectDBDialog dialog = new SelectDBDialog(mainFrame,"Open....");
				   dialog.setVisible(true);
				   String filename = dialog.getFile();
					String fileDir = dialog.getDirectory();
					File folder = new File(fileDir);
					File[] listOfFiles = folder.listFiles();

				    for (int i = 0; i < listOfFiles.length; i++) {
					      if (listOfFiles[i].isFile()) {
					    	  System.out.println("File " + listOfFiles[i].getName());
					    	  if (listOfFiles[i].getName().endsWith((".asn"))) {
					    		  new asn1Text2Hex(fileDir+filename);
					    		  //System.exit(0);
					    	    }
					       
					      }
					    }
					//new asn1Text2Hex(fileDir+filename);
		            //System.exit(0);*/
		         }
		      });
		      controlPanel.add(hex2ASN1text_btn);
		      controlPanel.add(ans1text2hex_btn);       
		      mainFrame.setVisible(true);     
	   }
	   
	   



}