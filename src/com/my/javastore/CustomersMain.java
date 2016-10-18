package com.my.javastore;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.border.*;

class CustomersMain extends JInternalFrame implements ActionListener,MouseListener,KeyListener{
	Container c;
	JLabel lCId,lName,lGender,lAddress,lArea,lPincode,lEmailId,lContactNo,lCFind;
	JTextField jCId,jName,jArea,jEmailId,jCFind;
	JRadioButton rMale,rFemale;
	ButtonGroup bg;
	JTextArea jAddress;
	JFormattedTextField jPincode,jContactNo;
	DefaultTableModel mCustomers;
	JTable tCustomers;
	JButton bAdd,bNew,bDelete,bUpdate,bFind,bClose;

	CustomersMain(){
		c=getContentPane();
		setLocation(0,0);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLayout(null);

		customersFrame();
		
		JPanel pPanel=new JPanel();
		pPanel.setBounds(10,10,1300,500);
		pPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"Customer"));
		c.add(pPanel);
		
		setVisible(true);
		setClosable(true);
	}
	private void customersFrame(){
		lCId=new JLabel("Customer ID");
		lCId.setBounds(20,40,100,30);
		c.add(lCId);
		
		jCId=new JTextField();
		jCId.setBounds(100,40,100,30);
		c.add(jCId);
		
		lName=new JLabel("Name");
		lName.setBounds(20,80,100,30);
		c.add(lName);
		
		jName=new JTextField();
		jName.setBounds(100,80,100,30);
		c.add(jName);
		
		lGender=new JLabel("Gender");
		lGender.setBounds(240,80,80,30);
		c.add(lGender);
		
		rMale=new JRadioButton("Male");
		rMale.setBounds(300,80,60,30);
		c.add(rMale);
		
		rFemale=new JRadioButton("Female");
		rFemale.setBounds(370,80,100,30);
		c.add(rFemale);
		
		bg=new ButtonGroup();
		bg.add(rMale);
		bg.add(rFemale);
		
		lAddress=new JLabel("Address");
		lAddress.setBounds(20,120,100,30);
		c.add(lAddress);
		
		jAddress=new JTextArea();
		JScrollPane jspAddress=new JScrollPane(jAddress);
		jspAddress.setBounds(100,120,120,70);
		c.add(jspAddress);
		
		lArea=new JLabel("Area");
		lArea.setBounds(20,200,100,30);
		c.add(lArea);
		
		jArea=new JTextField();
		jArea.setBounds(100,200,100,30);
		c.add(jArea);
		
		lPincode=new JLabel("Pincode");
		lPincode.setBounds(20,240,100,30);
		c.add(lPincode);
		
		try {
		    jPincode=new JFormattedTextField(new javax.swing.text.MaskFormatter("######"));
		    jPincode.setBounds(100,240,100,30);
			c.add(jPincode);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(c,ex.toString());
		}
				
		lEmailId=new JLabel("Email ID");
		lEmailId.setBounds(20,280,100,30);
		c.add(lEmailId);
		
		jEmailId=new JTextField();
		jEmailId.setBounds(100,280,120,30);
		c.add(jEmailId);
		
		lContactNo=new JLabel("Contact NO.");
		lContactNo.setBounds(20,320,100,30);
		c.add(lContactNo);
		
		try {
		    jContactNo=new JFormattedTextField(new javax.swing.text.MaskFormatter("##########"));
		    jContactNo.setBounds(100,320,100,30);
			c.add(jContactNo);
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(c,ex.toString());
		}
	
		bAdd=new JButton("add",new ImageIcon("icon/Add.png"));
		bAdd.setBounds(20,380,100,50);
		c.add(bAdd);
		
		bNew=new JButton("new",new ImageIcon("Png1/File Types/Default.png"));
		bNew.setBounds(120,380,100,50);
		c.add(bNew);
		
		bDelete=new JButton("delete",new ImageIcon("Png1/Desktop/delete.png"));
		bDelete.setBounds(220,380,100,50);
		c.add(bDelete);
		
		bUpdate=new JButton("update",new ImageIcon("PNG/refresh.png"));
		bUpdate.setBounds(20,440,100,50);
		c.add(bUpdate);
		
		bFind=new JButton("find",new ImageIcon("PNG/search.png"));
		bFind.setBounds(120,440,100,50);
		c.add(bFind);
		
		bClose=new JButton("close",new ImageIcon("icon/close.png"));
		bClose.setBounds(220,440,100,50);
		c.add(bClose);
		
		bAdd.setToolTipText("ADD");
		bNew.setToolTipText("NEW");
		bDelete.setToolTipText("DELETE");
		bUpdate.setToolTipText("UPDATE");
		bFind.setToolTipText("FIND");
		bClose.setToolTipText("CLOSE");
		
		String header[]={"Id","Name","Gender","Address","Area","Pincode","EmailId","ContactNo"};
		mCustomers=new DefaultTableModel(header,8);
		tCustomers=new JTable(mCustomers);
		JScrollPane jspCustomers=new JScrollPane(tCustomers);
		jspCustomers.setBounds(500,40,700,380);
		c.add(jspCustomers);
		clearframe();
		fillCustomer();
		tCustomers.setToolTipText("Customer Frame");
	
		lCFind=new JLabel("Sort");
		lCFind.setBounds(500,430,100,30);
		c.add(lCFind);
		
		jCFind=new JTextField();
		jCFind.setBounds(550,430,100,30);
		c.add(jCFind);
		
		myListener();	
	}
	private void myListener(){
		bAdd.addActionListener(this);
		bNew.addActionListener(this);
		bDelete.addActionListener(this);
		bUpdate.addActionListener(this);
		bFind.addActionListener(this);
		bClose.addActionListener(this);
		tCustomers.addMouseListener(this);
		jCFind.addKeyListener(this);
		
	}
	private void fillCustomer(){
		try {
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select * from Customers");
			ResultSet rs=ps.executeQuery();
			Vector<Object>v;
			while(rs.next()){
				v=new Vector<Object>();
				v.add(rs.getInt(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));
				v.add(rs.getString(5));
				v.add(rs.getInt(6));
				v.add(rs.getString(7));
				v.add(rs.getString(8));
				mCustomers.addRow(v);
			}
		}catch(Exception ex){
				JOptionPane.showMessageDialog(c,ex.toString());
		}
	}
	private void clearframe(){
		while(mCustomers.getRowCount()>0)
			mCustomers.removeRow(0);
	}
	private void clearField(){
		jName.setText("");
		jArea.setText("");
		jEmailId.setText("");
		jAddress.setText("");
		jPincode.setText("");
		jContactNo.setText("");
	}
	private void addCustomer(){
		try{
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Insert into Customers Values(?,?,?,?,?,?,?,?)");
			ps.setInt(1,Integer.parseInt(jCId.getText()));
			ps.setString(2,jName.getText());
			if(rMale.isSelected())
				ps.setString(3,"Male");
			else
				ps.setString(3,"Female");
			ps.setString(4,jAddress.getText());
			ps.setString(5,jArea.getText());
			ps.setString(6,jPincode.getText());
			ps.setString(7,jEmailId.getText());
			ps.setString(8,jContactNo.getText());
			int count=ps.executeUpdate();
			if(count>0)
				JOptionPane.showMessageDialog(c,"Saved");
				else
					JOptionPane.showMessageDialog(c,"Sorry");
		
			con.close();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(c,ex.toString());
			}
	}
	private void newCustomer(){
		try {
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select Max(CId) from Customers");
		    ResultSet rs=ps.executeQuery();
		    int cid=0;
		    if(rs.next())
		    	cid=rs.getInt(1);
			++cid;
			jCId.setText(String.valueOf(cid));
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(c,ex.toString());
		}
	
	}
	private void deleteCustomer(){
		try {
		   	Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Delete from Customers where CId=?");
			ps.setInt(1,Integer.parseInt(jCId.getText()));
			int count=ps.executeUpdate();
			if(count>0)
				JOptionPane.showMessageDialog(c,"Deleted");
			else
				JOptionPane.showMessageDialog(c,"Sorry");
		con.close();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(c,ex.toString());
		}
	}
	private void updateCustomer(){
		try{
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Update Customers set Name=?,Gender=?,Address=?,Area=?,Pincode=?,EmailId=?,ContactNo=? where CId=?");
			ps.setString(1,jName.getText());
			if(rMale.isSelected())
				ps.setString(2,"Male");
			else
				ps.setString(2,"Female");
			ps.setString(3,jAddress.getText());
			ps.setString(4,jArea.getText());
			ps.setInt(5,Integer.parseInt(jPincode.getText()));
			ps.setString(6,jEmailId.getText());
			ps.setString(7,jContactNo.getText());
			ps.setInt(8,Integer.parseInt(jCId.getText()));
			int count=ps.executeUpdate();
			con.close();
			}catch(Exception ex){
				JOptionPane.showMessageDialog(c,ex.toString());
			}
	}
	private void find(){
		try{
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select * from Customers where CId=?");
    		ps.setInt(1,Integer.parseInt(jCId.getText()));
    		ResultSet rs=ps.executeQuery();
			Vector<Object>v;
			while(rs.next()){
				v=new Vector<Object>();
				v.add(rs.getInt(1));
				v.add(rs.getString(2));
				v.add(rs.getString(3));
				v.add(rs.getString(4));
				v.add(rs.getString(5));
				v.add(rs.getInt(6));
				v.add(rs.getString(7));
				v.add(rs.getString(8));
				mCustomers.addRow(v);
				}
			con.close();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(c,ex.toString());
		}
	}
	public void actionPerformed(ActionEvent ae){
		Object o=ae.getSource();
		if(o.equals(bAdd)){
			addCustomer();
			clearframe();
			fillCustomer();
		}else if(o.equals(bNew)){
			clearField();
			newCustomer();
		}else if(o.equals(bDelete)){
			deleteCustomer();
			clearframe();
			fillCustomer();
		}else if(o.equals(bUpdate)){
			updateCustomer();
			clearframe();
			fillCustomer();
		}else if(o.equals(bFind)){
			clearframe();
			find();
		}else if(o.equals(bClose)){
			try {
			    setClosed(true);
			}
			catch (Exception ex) {
			}
		}
	}
	public void mouseEntered(MouseEvent me){
		}
	public void mouseExited(MouseEvent me){
	}
	public void mousePressed(MouseEvent me){
	}
	public void mouseReleased(MouseEvent me){
		Object o=me.getSource();
		if(o.equals(tCustomers)){
			int r=tCustomers.getSelectedRow();
			
			jCId.setText(String.valueOf((Integer)mCustomers.getValueAt(r,0)));
			jName.setText(String.valueOf(mCustomers.getValueAt(r,1)));
			String gender=String.valueOf(mCustomers.getValueAt(r,2));
			if(gender.equals("Male"))
				rMale.setSelected(true);
			else
				rFemale.setSelected(true);
			
			jAddress.setText(String.valueOf(mCustomers.getValueAt(r,3)));
			jArea.setText(String.valueOf(mCustomers.getValueAt(r,4)));
			jPincode.setText(String.valueOf((Integer)mCustomers.getValueAt(r,5)));
			jEmailId.setText(String.valueOf(mCustomers.getValueAt(r,6)));
			jContactNo.setText(String.valueOf(mCustomers.getValueAt(r,7)));
		}
	}
	public void mouseClicked(MouseEvent me){
	}
	public void keyTyped(KeyEvent ke){
	}
	public void keyPressed(KeyEvent ke){
	}
	public void keyReleased(KeyEvent ke){
		Object o=ke.getSource();
		if(o.equals(jCFind)){
			try {
			    clearframe();
			    Connection con=MyConnection.getConnect();
				PreparedStatement ps=con.prepareStatement("Select * from Customers where Name like '"+jCFind.getText()+"%'");
			    ResultSet rs=ps.executeQuery();
			    Vector<Object>v;
			    while(rs.next()){
			    	v=new Vector<Object>();
			    	v.add(rs.getInt(1));
			    	v.add(rs.getString(2));
			    	v.add(rs.getString(3));
			    	v.add(rs.getString(4));
			    	v.add(rs.getString(5));
			    	v.add(rs.getInt(6));
			    	v.add(rs.getString(7));
			    	v.add(rs.getString(8));
			    	mCustomers.addRow(v);
			    }
			    con.close();
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(c,ex.toString());
			}
		}
	}
}