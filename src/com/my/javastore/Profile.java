package com.my.javastore;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

class MyPanel extends JPanel{
	Image image;
	void setImage(String im){
		image=new ImageIcon(im).getImage();
		repaint();
	}
	public void paintComponent(Graphics g){
		Image image=new ImageIcon("C:/db/my general store project/StoreMain/classes/images/mine1.png").getImage();
		g.drawImage(image,0,0,200,200,this);	
	}
}

class Profile extends JInternalFrame{
	Container c;
	JLabel jDeveloper,jName,jDOB,jSchool,jCollege,jCourse,jBatch,jProject,jLanguage,jSoftware,jAbout;
	JTextField tName,tDOB,tSchool,tCollege,tCourse,tBatch,tProject,tLanguage,tSoftware;
	JTextArea tAbout;
	MyPanel mp;
	
	Profile(){
		c=getContentPane();
		setLocation(0,0);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLayout(null);
		
		profileFrame();
		
		mp=new MyPanel();
		mp.setBounds(980,40,250,250);
		c.add(mp);
		
		JPanel profilePanel=new JPanel();
		profilePanel.setBounds(10,10,1300,500);
		profilePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"About Developer"));
		c.add(profilePanel);
		
		setVisible(true);
		setClosable(true);
	}
	private void profileFrame(){
		jDeveloper=new JLabel("Developer of the Program");
		jDeveloper.setBounds(1008,250,140,15);
		c.add(jDeveloper);
		
		jName=new JLabel("Name");
		jName.setBounds(20,40,100,30);
		c.add(jName);
		
		tName=new JTextField();
		tName.setText("Raman Verma");
		tName.setBounds(130,40,100,30);
		tName.setEditable(false);
		c.add(tName);
		
		jDOB=new JLabel("DOB");
		jDOB.setBounds(20,80,100,30);
		c.add(jDOB);
		
		tDOB=new JTextField();
		tDOB.setText("07-July-1995");
		tDOB.setEditable(false);
		tDOB.setBounds(130,80,100,30);
		c.add(tDOB);
		
		jSchool=new JLabel("School(+12)");
		jSchool.setBounds(20,120,100,30);
		c.add(jSchool);
		
		tSchool=new JTextField();
		tSchool.setText("Bal Vikas School");
		tSchool.setEditable(false);
		tSchool.setBounds(130,120,120,30);
		c.add(tSchool);
		
		jCollege=new JLabel("College");
		jCollege.setBounds(20,160,100,30);
		c.add(jCollege);
		
		tCollege=new JTextField();
		tCollege.setText("MMEC");
		tCollege.setEditable(false);
		tCollege.setBounds(130,160,100,30);
		c.add(tCollege);
		
		jBatch=new JLabel("Batch");
		jBatch.setBounds(20,200,100,30);
		c.add(jBatch);
		
		tBatch=new JTextField();
		tBatch.setText("2013-2017");
		tBatch.setEditable(false);
		tBatch.setBounds(130,200,100,30);
		c.add(tBatch);
		
		jProject=new JLabel("Java Project");
		jProject.setBounds(20,240,100,30);
		c.add(jProject);
		
		tProject=new JTextField();
		tProject.setText("General Store");
		tProject.setEditable(false);
		tProject.setBounds(130,240,100,30);
		c.add(tProject);
		
		jLanguage=new JLabel("Language Use");
		jLanguage.setBounds(20,280,100,30);
		c.add(jLanguage);
		
		tLanguage=new JTextField();
		tLanguage.setText("Core Java,HTML");
		tLanguage.setEditable(false);
		tLanguage.setBounds(130,280,120,30);
		c.add(tLanguage);
		
		jSoftware=new JLabel("Software Use");
		jSoftware.setBounds(20,320,100,30);
		c.add(jSoftware);
		
		tSoftware=new JTextField();
		tSoftware.setText("JCreator-Pro,Notepad,MSAccess");
		tSoftware.setEditable(false);
		tSoftware.setBounds(130,320,200,30);
		c.add(tSoftware);
		
		jAbout=new JLabel("About me");
		jAbout.setBounds(340,40,100,30);
		c.add(jAbout);
		
		tAbout=new JTextArea();
		String a="Welcome to Raman's Program About me page,and I love to program in Java."+
			"I am Raman Verma and I am pursing Bachlor Degree in Computer Science at Maharishi Markandeshwar University,Mullana."+
			"I am a learning software developing and a technology enthusiast."+
			"\nYes I did learn to program at a very early age, but the point of my story is not to brag about myself. "+
			"My point is that with the right learning tool ANYONE can learn to program a computer. "+
			"The Java programming language is a beginner friendly language, and one that you can learn quickly. "+
			"With proper wording and extremely clear explanations that do not assume you know how to program, you can learn Java. "+
			"I don't believe computer programming is out of anyone's grasp, not even to you all who think you have no computer skills."+
			"Again, you do NOT need to know everything there is to know about computers in order to learn programming! "+
			"What you do need is focus and to apply a small amount of effort and you'll be coding up your first programs in no time.\n"+
			"I believe everyone has a story to tell and you can know my story and everything about me Personally ;)";
		tAbout.append(a);
		tAbout.setEditable(false);
		tAbout.setLineWrap(true);
		tAbout.setWrapStyleWord(true);
		tAbout.setBounds(440,40,380,330);
		c.add(tAbout);
	}
}