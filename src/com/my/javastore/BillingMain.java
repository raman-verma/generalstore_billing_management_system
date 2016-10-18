package com.my.javastore;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.border.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Formatter;
import java.util.Calendar;

class BillingMain extends JInternalFrame implements ActionListener,MouseListener,KeyListener{
	Container c;
	JLabel lCID,lCName,lPID,lPName,lPBrand;
	JLabel lBillNo,lDated,lSNO,lBPID,lBPName,lBCID,lBCName,lBPrice,lBQuantity,lCal;
	JTextField jCID,jCName,jPID,jPName,jPBrand;
	JTextField jBillNo,jSNO,jBPID,jBPName,jBCID,jBCName,jBPrice,jBQuantity,jCal;
	DefaultTableModel mBCustomers,mBProducts,mBilling;
	JTable tBCustomers,tBProducts,tBilling;
	DefaultComboBoxModel mPBrand;
	JComboBox cbPBrand;
	JFormattedTextField tDated;
	JButton bNewBill,bSNO,bAdd,bDelete,bUpdate,bFind,bPrint,bExit,bCal;
	
	BillingMain(){
		c=getContentPane();
		setLocation(0,0);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLayout(null);
		
		billingFrame();
		
		JPanel pPane2=new JPanel();
		pPane2.setBounds(10,15,550,230);
		pPane2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"Customers"));
		c.add(pPane2);
		
		JPanel pPane3=new JPanel();
		pPane3.setBounds(10,245,550,280);
		pPane3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"Products"));
		c.add(pPane3);

		JPanel pPanel=new JPanel();
		pPanel.setBounds(0,0,1350,530);
		pPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"Billing"));
		c.add(pPanel);
		
		setVisible(true);
		setClosable(true);
	}
	private void billingFrame(){
		lCID=new JLabel("CID");
		lCID.setBounds(15,30,30,30);
		c.add(lCID);
		
		jCID=new JTextField();
		jCID.setBounds(50,30,50,30);
		c.add(jCID);
		
		lCName=new JLabel("Name");
		lCName.setBounds(110,30,50,30);
		c.add(lCName);
		
		jCName=new JTextField();
		jCName.setBounds(160,30,100,30);
		c.add(jCName);
		
		lPID=new JLabel("PID");
		lPID.setBounds(15,260,30,30);
		c.add(lPID);
		
		jPID=new JTextField();
		jPID.setBounds(50,260,50,30);
		c.add(jPID);
		
		lPName=new JLabel("Name");
		lPName.setBounds(110,260,50,30);
		c.add(lPName);
		
		jPName=new JTextField();
		jPName.setBounds(160,260,100,30);
		c.add(jPName);
		
		lPBrand=new JLabel("Brand");
		lPBrand.setBounds(270,260,50,30);
		c.add(lPBrand);
		
		mPBrand=new DefaultComboBoxModel();
		cbPBrand=new JComboBox(mPBrand);
		cbPBrand.setBounds(320,260,120,30);
		c.add(cbPBrand);
		MyConnection.fillPBrand(mPBrand);
		
		bNewBill=new JButton("New Bill",new ImageIcon("Png1/File Types/new bill.png"));
		bNewBill.setBounds(570,10,60,60);
		c.add(bNewBill);
		bNewBill.setToolTipText("New Bill");
		
		lBillNo=new JLabel("Bill No.");
		lBillNo.setBounds(640,10,50,30);
		c.add(lBillNo);
		
		jBillNo=new JTextField();
		jBillNo.setBounds(690,10,50,30);
		c.add(jBillNo);
		
		lDated=new JLabel("Dated");
		lDated.setBounds(790,10,50,30);
		c.add(lDated);
		
		try {
		    tDated=new JFormattedTextField(new javax.swing.text.MaskFormatter("##-AAA-####"));
			tDated.setBounds(840,10,100,30);
			c.add(tDated);
			myDate();
		}
		catch (Exception ex) {
		}
		
		lSNO=new JLabel("SNO.");
		lSNO.setBounds(640,50,50,30);
		c.add(lSNO);
		
		jSNO=new JTextField();
		jSNO.setBounds(690,50,100,30);
		c.add(jSNO);
		
		lBPID=new JLabel("PID");
		lBPID.setBounds(640,80,50,30);
		c.add(lBPID);
		
		jBPID=new JTextField();
		jBPID.setBounds(690,80,100,30);
		c.add(jBPID);
		
		lBPName=new JLabel("PName");
		lBPName.setBounds(640,110,50,30);
		c.add(lBPName);
		
		jBPName=new JTextField();
		jBPName.setBounds(690,110,100,30);
		c.add(jBPName);
		
		lBPrice=new JLabel("Price");
		lBPrice.setBounds(640,140,50,30);
		c.add(lBPrice);
		
		jBPrice=new JTextField();
		jBPrice.setBounds(690,140,100,30);
		c.add(jBPrice);
	
		lBQuantity=new JLabel("Quantity");
		lBQuantity.setBounds(640,170,50,30);
		c.add(lBQuantity);
		
		jBQuantity=new JTextField();
		jBQuantity.setBounds(690,170,100,30);
		c.add(jBQuantity);
		
		lBCID=new JLabel("CID");
		lBCID.setBounds(800,50,50,30);
		c.add(lBCID);
		
		jBCID=new JTextField();
		jBCID.setBounds(850,50,100,30);
		c.add(jBCID);
		
		lBCName=new JLabel("CName");
		lBCName.setBounds(800,80,50,30);
		c.add(lBCName);
		
		jBCName=new JTextField();
		jBCName.setBounds(850,80,100,30);
		c.add(jBCName);
		
		bSNO=new JButton("SNO",new ImageIcon("PNG/notebook.png"));
		bSNO.setBounds(1120,40,100,40);
		c.add(bSNO);
		
		bAdd=new JButton("Add",new ImageIcon("PNG/plus.png"));
		bAdd.setBounds(1070,80,110,40);
		c.add(bAdd);
		
		bDelete=new JButton("Delete",new ImageIcon("PNG/trash.png"));
		bDelete.setBounds(1180,80,110,40);
		c.add(bDelete);
		
		bUpdate=new JButton("Update",new ImageIcon("PNG/Podcast.png"));
		bUpdate.setBounds(1120,120,110,40);
		c.add(bUpdate);
		
		bFind=new JButton("Find",new ImageIcon("PNG/search.png"));
		bFind.setBounds(745,10,30,30);
		c.add(bFind);
		
		bExit=new JButton("Exit",new ImageIcon("PNG/stop.png"));
		bExit.setBounds(1090,161,180,51);
		c.add(bExit);
		
		bPrint=new JButton("Print",new ImageIcon("icon/printer.png"));
		bPrint.setBounds(1200,430,110,80);
		c.add(bPrint);
				
		lCal=new JLabel("Cal. Bill");
		lCal.setBounds(930,495,50,30);
		c.add(lCal);
		
		jCal=new JTextField();
		jCal.setEditable(false);
		jCal.setBounds(980,495,80,30);
		c.add(jCal);
		
		bCal=new JButton("Calculate");
		bCal.setBounds(1080,495,100,30);
		c.add(bCal);
		
		String header[]={"CID","Name","ContactNo"};
		mBCustomers=new DefaultTableModel(header,3);
		tBCustomers=new JTable(mBCustomers);
		JScrollPane jspBCustomers=new JScrollPane(tBCustomers);
		jspBCustomers.setBounds(15,70,540,170);
		c.add(jspBCustomers);
		clearCFrame();
		fillBCustomer();
		tBCustomers.setToolTipText("Customer Frame");
		
		String headerp[]={"PID","Name","Brand","MRP","Qty.","Unit","Dis."};
		mBProducts=new DefaultTableModel(headerp,7);
		tBProducts=new JTable(mBProducts);
		JScrollPane jspBProducts=new JScrollPane(tBProducts);
		jspBProducts.setBounds(15,300,540,220);
		c.add(jspBProducts);
		clearPFrame();
		fillBProduct();
		tBProducts.setToolTipText("Products Frame");
		
		String headerb[]={"SNO","PID","PName","CID","Bill NO.","Purs_Date","Price","Pcs.","Total"};
		mBilling=new DefaultTableModel(headerb,9);
		tBilling=new JTable(mBilling);
		JScrollPane jspBilling=new JScrollPane(tBilling);
		jspBilling.setBounds(570,220,610,270);
		c.add(jspBilling);
		clearBFrame();
		tBilling.setToolTipText("Billing Frame");
		
		myListener();
	}
	private void myListener(){
		tBCustomers.addMouseListener(this);
		tBProducts.addMouseListener(this);
		tBilling.addMouseListener(this);
		jCID.addKeyListener(this);
		jCName.addKeyListener(this);
		jPID.addKeyListener(this);
		jPName.addKeyListener(this);
		cbPBrand.addActionListener(this);
		bNewBill.addActionListener(this);
		bSNO.addActionListener(this);
		bAdd.addActionListener(this);
		bDelete.addActionListener(this);
		bUpdate.addActionListener(this);
		bFind.addActionListener(this);
		bPrint.addActionListener(this);
		bExit.addActionListener(this);
		jCID.addKeyListener(this);
		jCName.addKeyListener(this);
		jPID.addKeyListener(this);
		jPName.addKeyListener(this);
		bCal.addActionListener(this);
		
	}
	private void clearCFrame(){
		while(mBCustomers.getRowCount()>0)
			mBCustomers.removeRow(0);
	}
	private void clearPFrame(){
		while(mBProducts.getRowCount()>0)
			mBProducts.removeRow(0);
	}
	private void clearBFrame(){
		while(mBilling.getRowCount()>0)
			mBilling.removeRow(0);
	}
	private void fillBCustomer(){
		try {
		    Connection con=MyConnection.getConnect();
		    PreparedStatement ps=con.prepareStatement("Select CID,Name,ContactNo from Customers");
		    ResultSet rs=ps.executeQuery();
		    Vector<Object>v;
		    while(rs.next()){
		    	v=new Vector<Object>();
		    	v.add(rs.getInt(1));
		    	v.add(rs.getString(2));
		    	v.add(rs.getString(3));
		    	mBCustomers.addRow(v);
		    }
		}
		catch (Exception ex) {
		}
	}
	private void fillBProduct(){
		try {
		    Connection con=MyConnection.getConnect();
		    PreparedStatement ps=con.prepareStatement("Select PID,Name,Brand,MRP,Quantity,Unit,Discount from Products");
		    ResultSet rs=ps.executeQuery();
		    Vector<Object>v;
		    while(rs.next()){
		    	v=new Vector<Object>();
		    	v.add(rs.getInt(1));
		    	v.add(rs.getString(2));
		    	v.add(rs.getString(3));
		    	v.add(rs.getInt(4));
		    	v.add(rs.getInt(5));
		    	v.add(rs.getString(6));
		    	v.add(rs.getInt(7));
		    	mBProducts.addRow(v);
		    }
		}
		catch (Exception ex) {
		}
	}
	private void fillBilling(){
		try {
		    Connection con=MyConnection.getConnect();
		    PreparedStatement ps=con.prepareStatement("SELECT Billing.SNo, Billing.PID, Products.Name, Billing.CID, Billing.BillNo, Billing.PurchaseDate, Billing.Price, Billing.Quantity, Billing.Price*Billing.Quantity AS Total FROM Products INNER JOIN (Customers INNER JOIN Billing ON Customers.CID = Billing.CID) ON Products.PID = Billing.PID WHERE Billing.BillNo=? ORDER BY Billing.SNo ASC");
		    ps.setInt(1,Integer.parseInt(jBillNo.getText()));
		    ResultSet rs=ps.executeQuery();
		    Vector<Object>v;
		    while(rs.next()){
		    	v=new Vector<Object>();
		    	v.add(rs.getInt(1));
		    	v.add(rs.getInt(2));
		    	v.add(rs.getString(3));
		    	v.add(rs.getInt(4));
		    	v.add(rs.getInt(5));
		    	java.util.Date d=rs.getDate(6);
		    	v.add(String.format("%td-%tb-%tY",d,d,d));
		    	v.add(rs.getInt(7));
		    	v.add(rs.getInt(8));
		    	v.add(rs.getInt(9));
		    	mBilling.addRow(v);
		    }
		}
		catch (Exception ex) {
		}
	}
	public void myDate(){
		Calendar cal=Calendar.getInstance();
		tDated.setText(String.format("%td-%tb-%tY",cal,cal,cal));
	}
	private void printBill(){
		try {
			 java.io.PrintWriter out=new java.io.PrintWriter(new java.io.File("Bill.html"));
			 out.println("<html>");
			 out.println("<body>");
			 out.println("<table border='1' width='600'>");
			 out.println("<tr>");
			 out.println("<td>SNO</td>");
			 out.println("<td>PID</td>");
			 out.println("<td>PName</td>");
			 out.println("<td>CID</td>");
			 out.println("<td>Bill No</td>");
			 out.println("<td>Price</td>");
			 out.println("<td>Qty.</td>");
			 out.println("<td>Total</td>");
			 out.println("</tr>");
			 for (int i = 0; i<mBilling.getRowCount(); i++){
			 	out.println("\t<tr>");
			 	for(int j=0;j<8;++j)
			 		out.println("\t\t<td>"+mBilling.getValueAt(i,j)+"</td>");
			 	out.println("\t</tr>");
			 }
			 out.println("</table>");
			 out.println("</body>");
			 out.println("</html>");
			 out.close();

			 Desktop.getDesktop().open(new java.io.File("Bill.html"));   
		}
		catch (Exception ex) {
		}
	}
	private void newBilling(){
		try {
			Connection con=MyConnection.getConnect();
		PreparedStatement ps=con.prepareStatement("Select Max(BillNo) from Billing");
		ResultSet rs=ps.executeQuery();
		int billno=0;
		if(rs.next()){
			billno=rs.getInt(1);
			++billno;
		}
			jBillNo.setText(String.valueOf(billno));
		 con.close();   
		}
		catch (Exception ex) {
		}
		
		myDate();
		
		try {
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select Max(SNo) from Billing");
			ResultSet rs=ps.executeQuery();
			int sno=0;
			if(rs.next()){
				sno=rs.getInt(1);
				++sno;
			}
				jSNO.setText(String.valueOf(sno));
			 con.close();   
		}
		catch (Exception ex) {
		}
			jBPID.setText("");
			jBPName.setText("");
			jBCID.setText("");
			jBCName.setText("");
			jBPrice.setText("");
			jBQuantity.setText("");
			clearBFrame();
		}
	private void newSNO(){
		try {
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select Max(SNo) from Billing");
			ResultSet rs=ps.executeQuery();
			int sno=0;
			if(rs.next()){
				sno=rs.getInt(1);
				++sno;
			}
				jSNO.setText(String.valueOf(sno));
			 con.close();   
		}
		catch (Exception ex) {
		}
			jBPID.setText("");
			jBPName.setText("");
			jBPrice.setText("");
			jBQuantity.setText("");
	}
	private void addBilling(){
		try {
		    Connection con=MyConnection.getConnect();
		    PreparedStatement ps=con.prepareStatement("Insert into Billing Values(?,?,?,?,?,?,?)");
			ps.setInt(1,Integer.parseInt(jSNO.getText()));
			ps.setInt(2,Integer.parseInt(jBPID.getText()));
			ps.setInt(3,Integer.parseInt(jBCID.getText()));
			ps.setInt(4,Integer.parseInt(jBillNo.getText()));
			ps.setString(5,tDated.getText());
			ps.setInt(6,Integer.parseInt(jBPrice.getText()));
			ps.setInt(7,Integer.parseInt(jBQuantity.getText()));
			int count=ps.executeUpdate();
			if(count>0)
				JOptionPane.showMessageDialog(c,"Saved","Added Button",1,new ImageIcon("PNG/happy.png"));
				else 
					JOptionPane.showMessageDialog(c,"Sorry");
		}
		catch (Exception ex) {
		}
	}
	private void deleteBilling(){
		try {
		    Connection con=MyConnection.getConnect();
		    PreparedStatement ps=con.prepareStatement("Delete from Billing where SNo=?");
		    ps.setInt(1,Integer.parseInt(jSNO.getText()));
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
	private void updateBilling(){
		try {
		    Connection con=MyConnection.getConnect();
		    PreparedStatement ps=con.prepareStatement("Update Billing set PID=?,CID=?,BillNo=?,PurchaseDate=?,Price=?,Quantity=? where SNo=?");
			ps.setInt(1,Integer.parseInt(jBPID.getText()));
			ps.setInt(2,Integer.parseInt(jBCID.getText()));
			ps.setInt(3,Integer.parseInt(jBillNo.getText()));
			ps.setString(4,tDated.getText());
			ps.setInt(5,Integer.parseInt(jBPrice.getText()));
			ps.setInt(6,Integer.parseInt(jBQuantity.getText()));
			ps.setInt(7,Integer.parseInt(jSNO.getText()));
			int count=ps.executeUpdate();
			if(count>0)
				JOptionPane.showMessageDialog(c,"Update","Added Button",1,new ImageIcon("PNG/happy.png"));
				else 
					JOptionPane.showMessageDialog(c,"Sorry");
		}
		catch (Exception ex) {
		}
	}
	private void findBilling(){
		try {
		    Connection con=MyConnection.getConnect();
		    PreparedStatement ps=con.prepareStatement("SELECT Billing.SNo, Billing.PID, Products.Name, Billing.CID, Billing.BillNo, Billing.PurchaseDate, Billing.Price, Billing.Quantity, Billing.Price*Billing.Quantity AS Total FROM Products INNER JOIN (Customers INNER JOIN Billing ON Customers.CID = Billing.CID) ON Products.PID = Billing.PID WHERE Billing.BillNo=? ORDER BY Billing.SNo ASC");
		    ps.setInt(1,Integer.parseInt(jBillNo.getText()));
		    ResultSet rs=ps.executeQuery();
		    Vector<Object>v;
		    while(rs.next()){
		    	v=new Vector<Object>();
		    	v.add(rs.getInt(1));
		    	v.add(rs.getInt(2));
		    	v.add(rs.getString(3));
		    	v.add(rs.getInt(4));
		    	v.add(rs.getInt(5));
		    	java.util.Date d=rs.getDate(6);
		    	v.add(String.format("%td-%tb-%tY",d,d,d));
		    	v.add(rs.getInt(7));
		    	v.add(rs.getInt(8));
		    	v.add(rs.getInt(9));
		    	mBilling.addRow(v);
		    }
		}catch(Exception ex){
				JOptionPane.showMessageDialog(c,ex.toString());
		}
	}
	private void keyCustomers(KeyEvent ke){
		Object o=ke.getSource();
		if(o.equals(jCID)){
			try {
			    clearCFrame();
			    Connection con=MyConnection.getConnect();
				PreparedStatement ps=con.prepareStatement("Select * from Customers where CID="+jCID.getText());
			    ResultSet rs=ps.executeQuery();
			    Vector<Object>v;
			    while(rs.next()){
			    	v=new Vector<Object>();
			    	v.add(rs.getInt(1));
			    	v.add(rs.getString(2));
			    	v.add(rs.getString(3));
			    	mBCustomers.addRow(v);
				    }
			    con.close();
			    
			}
			catch (Exception ex) {
				
			}
		}else if(o.equals(jCName)){
			try {
			    clearCFrame();
			    Connection con=MyConnection.getConnect();
				PreparedStatement ps=con.prepareStatement("Select * from Customers where Name like '"+jCName.getText()+"%'");
			    ResultSet rs=ps.executeQuery();
			    Vector<Object>v;
			    while(rs.next()){
			    	v=new Vector<Object>();
			    	v.add(rs.getInt(1));
			    	v.add(rs.getString(2));
			    	v.add(rs.getString(3));
			    	mBCustomers.addRow(v);
				    }
			    con.close();
			}
			catch (Exception ex) {
				
			}
		}
	}
	private void keyProducts(KeyEvent ke){
		Object o=ke.getSource();
		if(o.equals(jPID)){
			try {
			    clearPFrame();
			    Connection con=MyConnection.getConnect();
				PreparedStatement ps=con.prepareStatement("Select * from Products where PID="+jPID.getText());
			    ResultSet rs=ps.executeQuery();
			    Vector<Object>v;
			    while(rs.next()){
			    	v=new Vector<Object>();
			    	v.add(rs.getInt(1));
			    	v.add(rs.getString(2));
			    	v.add(rs.getString(3));
			    	v.add(rs.getInt(4));
			    	v.add(rs.getInt(5));
			    	v.add(rs.getString(6));
			    	v.add(rs.getInt(7));
			    	mBProducts.addRow(v);
				    }
			    con.close();
			}
			catch (Exception ex) {
				
			}
		}else if(o.equals(jPName)){
			try {
			    clearPFrame();
			    Connection con=MyConnection.getConnect();
				PreparedStatement ps=con.prepareStatement("Select * from Products where Name like '"+jPName.getText()+"%'");
			    ResultSet rs=ps.executeQuery();
			    Vector<Object>v;
			    while(rs.next()){
			    	v=new Vector<Object>();
			    	v.add(rs.getInt(1));
			    	v.add(rs.getString(2));
			    	v.add(rs.getString(3));
			    	v.add(rs.getInt(4));
			    	v.add(rs.getInt(5));
			    	v.add(rs.getString(6));
			    	v.add(rs.getInt(7));
			    	mBProducts.addRow(v);
				    }
			    con.close();
			}
			catch (Exception ex) {	
			}
		}
	}
	private void calBilling(){
		int sum=0;
		for (int i = 0; i<mBilling.getRowCount(); i++) {
			sum+=(Integer)mBilling.getValueAt(i,8);	
		}
		jCal.setText(String.valueOf(sum));
	}
	public void actionPerformed(ActionEvent ae){
		Object o=ae.getSource();
		if(o.equals(bNewBill)){
			newBilling();
		}else if(o.equals(bSNO)){
			newSNO();
		}else if(o.equals(bAdd)){
			addBilling();
			clearBFrame();
			fillBilling();
			newSNO();
		}else if(o.equals(bDelete)){
			deleteBilling();
			clearBFrame();
			fillBilling();
		}else if(o.equals(bUpdate)){
			updateBilling();
			clearBFrame();
			fillBilling();
		}else if(o.equals(bFind)){
			clearBFrame();
			findBilling();
		}else if(o.equals(bPrint)){
			printBill();
		}else if(o.equals(bCal)){
			calBilling();
		}else if(o.equals(bExit)){
			try {
			    setClosed(true);
			}
			catch (Exception ex) {
			}
		}
		else if(o.equals(cbPBrand)){
			if(cbPBrand.getSelectedItem()=="Choose")
				fillBProduct();
			else if(cbPBrand.getSelectedItem()!="Choose"){
			
				try {
				    clearPFrame();
				    Connection con=MyConnection.getConnect();
					PreparedStatement ps=con.prepareStatement("Select * from Products where Brand='"+cbPBrand.getSelectedItem()+"'");
				    ResultSet rs=ps.executeQuery();
				    Vector<Object>v;
				    while(rs.next()){
				    	v=new Vector<Object>();
				    	v.add(rs.getInt(1));
				    	v.add(rs.getString(2));
				    	v.add(rs.getString(3));
				    	v.add(rs.getInt(4));
				    	v.add(rs.getInt(5));
				    	v.add(rs.getString(6));
				    	v.add(rs.getInt(7));
				    	mBProducts.addRow(v);
					    }
				    con.close();
				}
				catch (Exception ex) {
				}
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
		if(o.equals(tBCustomers)){
			int r=tBCustomers.getSelectedRow();
			jBCID.setText(String.valueOf((Integer)mBCustomers.getValueAt(r,0)));
			jBCName.setText(String.valueOf(mBCustomers.getValueAt(r,1)));
		}else if(o.equals(tBProducts)){
			int r=tBProducts.getSelectedRow();
			jBPID.setText(String.valueOf((Integer)mBProducts.getValueAt(r,0)));
			jBPName.setText(String.valueOf(mBProducts.getValueAt(r,1)));
			int m,d,p;
			m=(Integer)mBProducts.getValueAt(r,3);
			d=(Integer)mBProducts.getValueAt(r,6);
			p=(m-(m*d/100));
			jBPrice.setText(String.valueOf(p));
		}else if(o.equals(tBilling)){
			int r=tBilling.getSelectedRow();
			jSNO.setText(String.valueOf((Integer)mBilling.getValueAt(r,0)));
			jBPID.setText(String.valueOf((Integer)mBilling.getValueAt(r,1)));
			jBPName.setText(String.valueOf(mBilling.getValueAt(r,2)));
			jBCID.setText(String.valueOf((Integer)mBilling.getValueAt(r,3)));
			jBillNo.setText(String.valueOf((Integer)mBilling.getValueAt(r,4)));
			tDated.setText(String.valueOf(mBilling.getValueAt(r,5)));
			jBPrice.setText(String.valueOf((Integer)mBilling.getValueAt(r,6)));
			jBQuantity.setText(String.valueOf((Integer)mBilling.getValueAt(r,7)));
				try {
				    Connection con=MyConnection.getConnect();
				    PreparedStatement ps=con.prepareStatement("SELECT Customers.Name FROM Products INNER JOIN (Customers INNER JOIN Billing ON Customers.CID = Billing.CID) ON Products.PID = Billing.PID WHERE Billing.BillNo=?");
				    ps.setInt(1,Integer.parseInt(jBillNo.getText()));
				    ResultSet rs=ps.executeQuery();
					while(rs.next()){
					    jBCName.setText(String.valueOf(rs.getString(1)));
				    }	
					con.close();
				}catch(Exception ex){
				}
		}
	}
	public void mouseClicked(MouseEvent me){	
	}
	public void keyTyped(KeyEvent ke){
	}
	public void keyPressed(KeyEvent ke){
	}
	public void keyReleased(KeyEvent ke){
		keyCustomers(ke);
		keyProducts(ke);
	}
}