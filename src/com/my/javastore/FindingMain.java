package com.my.javastore;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class FindingMain extends JInternalFrame implements Runnable{
	Container c;
	FindingMain(){
		c=getContentPane();
		setLocation(0,0);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		c.setLayout(new BorderLayout());
		c.setBackground(Color.LIGHT_GRAY);
		
		JTabbedPane jtb=new JTabbedPane();
		jtb.setTabPlacement(JTabbedPane.LEFT);
		jtb.add(new Customers(),"Customer");		//customer fun. calling
		jtb.add(new Products(),"Products");			//product fun. calling
		jtb.add(new Billing(),"Billing");			//billing fun. calling
				
		c.add(jtb,BorderLayout.CENTER);
		
		setVisible(true);
		setClosable(true);
	}
	public void run(){
			new FindingMain();
		}
}
class Customers extends JPanel implements KeyListener,MouseListener{
		Container c;
		JTextField tType;
		DefaultComboBoxModel mType;
		JComboBox cbType;
		DefaultTableModel mCustomers,mMid,mBilling;
		JTable tCustomers,tMid,tBilling;
		Customers(){
			setLayout(null);
			
			mType=new DefaultComboBoxModel();
			cbType=new JComboBox(mType);
			cbType.setBounds(10,10,100,30);
			add(cbType);
			fillType();
			
			tType=new JTextField();
			tType.setBounds(120,10,100,30);
			add(tType);
			
			String header[]={"Id","Name","Gender","Address","Area","Pincode","EmailId","ContactNo"};
			mCustomers=new DefaultTableModel(header,8);
			tCustomers=new JTable(mCustomers);
			JScrollPane jspCustomers=new JScrollPane(tCustomers);
			jspCustomers.setBounds(30,50,1200,100);
			add(jspCustomers);
			clearCFrame();
			
			String headerm[]={"Bill No.","Total Amount"};
			mMid=new DefaultTableModel(headerm,2);
			tMid=new JTable(mMid);
			JScrollPane jspMid=new JScrollPane(tMid);
			jspMid.setBounds(30,160,1200,120);
			add(jspMid);
			clearMFrame();
			
			String headerb[]={"SNO","PID","PName","CID","Bill NO.","Purs_Date","Price","Pcs.","Total"};
			mBilling=new DefaultTableModel(headerb,9);
			tBilling=new JTable(mBilling);
			JScrollPane jspBilling=new JScrollPane(tBilling);
			jspBilling.setBounds(30,290,1200,120);
			add(jspBilling);
			clearBFrame();
			
			myListener();
		}
	private void fillType(){
		mType.addElement("CID");
		mType.addElement("Name");
		mType.addElement("Area");
		mType.addElement("Email-Id");
		mType.addElement("Contact-No");
	}
	private void clearCFrame(){
		while(mCustomers.getRowCount()>0)
			mCustomers.removeRow(0);
	}
	private void clearMFrame(){
		while(mMid.getRowCount()>0)
			mMid.removeRow(0);
	}
	private void clearBFrame(){
		while(mBilling.getRowCount()>0)
			mBilling.removeRow(0);
	}
	private void myListener(){
		tType.addKeyListener(this);
		tCustomers.addMouseListener(this);
		tMid.addMouseListener(this);
	}
	public void keyTyped(KeyEvent ke){
	}
	public void keyPressed(KeyEvent ke){
	}
	public void keyReleased(KeyEvent ke){
		Object o=ke.getSource();
		if(o.equals(tType)){
			try {
				String sql=null;
				if(cbType.getSelectedItem()=="CID"){
					sql="Select * from Customers where CID="+tType.getText();
				}else if(cbType.getSelectedItem()=="Name"){
					sql="Select * from Customers where Name like '"+tType.getText()+"%'";
				}else if(cbType.getSelectedItem()=="Area"){
					sql="Select * from Customers where Area like '"+tType.getText()+"%'";
				}else if(cbType.getSelectedItem()=="Email-Id"){
					sql="Select * from Customers where EmailId like '"+tType.getText()+"%'";
				}else if(cbType.getSelectedItem()=="Contact-No"){
					sql="Select * from Customers where ContactNo like '"+tType.getText()+"%'";
				}
				clearCFrame();
			    Connection con=MyConnection.getConnect();
			    PreparedStatement ps=con.prepareStatement(sql);
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
			}catch (Exception ex) {
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
			try {
				clearMFrame();
			    Connection con=MyConnection.getConnect();
			    PreparedStatement ps=con.prepareStatement("SELECT BillNo,sum(Billing.Price*Billing.Quantity) as Total from Billing where Billing.CID=? group by BillNo");
				ps.setInt(1,(Integer)mCustomers.getValueAt(tCustomers.getSelectedRow(),0));
			    ResultSet rs=ps.executeQuery();
			    Vector<Object>v;
			    while(rs.next()){
			  	  v=new Vector<Object>();
			    	v.add(rs.getInt(1));
			    	v.add(rs.getInt(2));
					mMid.addRow(v);
				}
				con.close();
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(c,ex.toString());
			}
			}else if(o.equals(tMid)){
				try {
					clearBFrame();
			    Connection con=MyConnection.getConnect();
			    PreparedStatement ps=con.prepareStatement("SELECT Billing.SNo, Billing.PID, Products.Name, Billing.CID, Billing.BillNo, Billing.PurchaseDate, Billing.Price, Billing.Quantity, Billing.Price*Billing.Quantity AS Total FROM Products INNER JOIN (Customers INNER JOIN Billing ON Customers.CID = Billing.CID) ON Products.PID = Billing.PID WHERE Billing.BillNo=? ORDER BY Billing.SNo ASC");
			    ps.setInt(1,(Integer)(mMid.getValueAt(tMid.getSelectedRow(),0)));
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
	}
	public void mouseClicked(MouseEvent me){
	}
}
class Products extends JPanel implements KeyListener,ActionListener{
	Container c;
	JTextField tType,tMRP2;
	DefaultComboBoxModel mType;
	JComboBox cbType;
	DefaultTableModel mProducts;
	JTable  tProducts;
	Products(){
		setLayout(null);
		
		mType=new DefaultComboBoxModel();
		cbType=new JComboBox(mType);
		cbType.setBounds(10,10,100,30);
		add(cbType);
		fillType();
		
		tType=new JTextField();
		tType.setBounds(120,10,100,30);
		add(tType);
		
		tMRP2=new JTextField();
		tMRP2.setBounds(230,10,100,30);
		add(tMRP2);
		tMRP2.setVisible(false);
		
		String Header[]={"PID","Name","Brand","MRP","Quantity","Unit","Discount"};
		mProducts=new DefaultTableModel(Header,7);
		tProducts=new JTable(mProducts);
		JScrollPane jspProducts=new JScrollPane(tProducts);
		jspProducts.setBounds(30,50,1200,400);
		add(jspProducts);
		clearProducts();
		
		tType.addKeyListener(this);
		tMRP2.addKeyListener(this);
		cbType.addActionListener(this);
	}
	private void fillType(){
		mType.addElement("PID");
		mType.addElement("Name");
		mType.addElement("Brand");
		mType.addElement("MRP");
	}
	private void clearProducts(){
		while(mProducts.getRowCount()>0)
			mProducts.removeRow(0);
	}
	public void actionPerformed(ActionEvent ae){
		Object o=ae.getSource();
		if(o.equals(cbType)){
			if(cbType.getSelectedItem().toString().equals("MRP"))
				tMRP2.setVisible(true);
				else
					tMRP2.setVisible(false);
		}
	}
	public void keyTyped(KeyEvent ke){
	}
	public void keyPressed(KeyEvent ke){
	}
	public void keyReleased(KeyEvent ke){
		Object o=ke.getSource();
		if(o.equals(tType)){
			try {
				String sql=null;
				if(cbType.getSelectedItem()=="PID"){
					sql="Select * from Products where PID="+tType.getText();									
				}else if(cbType.getSelectedItem()=="Name"){
					sql="Select * from Products where Name like '"+tType.getText()+"%'";
				}else if(cbType.getSelectedItem()=="Brand"){
					sql="Select * from Products where Brand like '"+tType.getText()+"%'";
				}else if(cbType.getSelectedItem()=="MRP"){
					return;		
				}
				clearProducts();
			    Connection con=MyConnection.getConnect();
			    PreparedStatement ps=con.prepareStatement(sql);
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
					mProducts.addRow(v);
				}
				con.close();
			}catch (Exception ex) {
				JOptionPane.showMessageDialog(c,ex.toString());
			}	
		}else if(o.equals(tMRP2)){
			try {
				String sql=null;
				if(cbType.getSelectedItem()=="MRP"){
					if(tType.getText().isEmpty() || tMRP2.getText().isEmpty())
						return;
					else{
						int first=Integer.parseInt(tType.getText());
						int second =Integer.parseInt(tMRP2.getText());
						if(second>first)						
							sql="Select * from Products where MRP between "+tType.getText()+" and " +tMRP2.getText();
						else
							return;			
					}
				}
				clearProducts();
			    Connection con=MyConnection.getConnect();
			    PreparedStatement ps=con.prepareStatement(sql);
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
					mProducts.addRow(v);
				}
				con.close();
			}catch (Exception ex) {
				JOptionPane.showMessageDialog(c,ex.toString());
			}
		}
	}
}
class Billing extends JPanel implements KeyListener,MouseListener,ActionListener{
	Container c;
	JTextField tType,tMRP2;
	DefaultComboBoxModel mType;
	JComboBox cbType;
	DefaultTableModel mCustomers,mProducts,mBilling;
	JTable tBilling,tCustomers,tProducts;
	JButton bPrint;
	
	Billing(){
		setLayout(null);
		
		mType=new DefaultComboBoxModel();
		cbType=new JComboBox(mType);
		cbType.setBounds(10,10,100,30);
		add(cbType);
		fillType();
		
		tType=new JTextField();
		tType.setBounds(120,10,100,30);
		add(tType);
		
		bPrint=new JButton("Print",new ImageIcon("icon/printer.png"));
		bPrint.setBounds(1010,420,210,100);
		add(bPrint);
		
		String headerb[]={"Bill NO.","Customer_Name","ContactNo","Purs_Date"};
		mBilling=new DefaultTableModel(headerb,9);
		tBilling=new JTable(mBilling);
		JScrollPane jspBilling=new JScrollPane(tBilling);
		jspBilling.setBounds(30,50,1200,100);
		add(jspBilling);
		clearBFrame();
		
		String header[]={"Id","Name","Gender","Address","Area","Pincode","EmailId","Total Amount"};
		mCustomers=new DefaultTableModel(header,8);
		tCustomers=new JTable(mCustomers);
		JScrollPane jspCustomers=new JScrollPane(tCustomers);
		jspCustomers.setBounds(30,160,1200,120);
		add(jspCustomers);
		clearCFrame();
		
		String headerp[]={"SNO","PID","PName","Brand","CID","Price","Pcs.","Net"};
		mProducts=new DefaultTableModel(headerp,8);
		tProducts=new JTable(mProducts);
		JScrollPane jspProducts=new JScrollPane(tProducts);
		jspProducts.setBounds(30,290,1200,120);
		add(jspProducts);
		clearPFrame();
		
		tType.addKeyListener(this);
		tBilling.addMouseListener(this);
		bPrint.addActionListener(this);
	}
	private void fillType(){
		mType.addElement("BillNo");
		mType.addElement("Customer_Name");
		mType.addElement("ContactNo");
		mType.addElement("PurchaseDate");
	}
	private void clearBFrame(){
		while(mBilling.getRowCount()>0)
			mBilling.removeRow(0);
	}
	private void clearCFrame(){
		while(mCustomers.getRowCount()>0)
			mCustomers.removeRow(0);
	}
	private void clearPFrame(){
		while(mProducts.getRowCount()>0)
			mProducts.removeRow(0);
	}
	public void actionPerformed(ActionEvent ae){
		Object o=ae.getSource();
		if(o.equals(bPrint)){
			if(mBilling.getRowCount()>0){
				System.out.println ("pppp");
				int billNo=(Integer)mBilling.getValueAt(0,0);
				printBill(billNo);
			}
		}
	}
	public void keyTyped(KeyEvent ke){
	}
	public void keyPressed(KeyEvent ke){
	}
	public void keyReleased(KeyEvent ke){
		Object o=ke.getSource();
		if(o.equals(tType)){
			try {
				String sql=null;
				if(cbType.getSelectedItem()=="BillNo"){
					String billno=tType.getText();
					if(billno.isEmpty())
						return;
						else
						sql="SELECT Billing.BillNo, Customers.Name,Customers.ContactNo, Billing.PurchaseDate FROM Products INNER JOIN (Customers INNER JOIN Billing ON Customers.CID = Billing.CID) ON Products.PID = Billing.PID where BillNO='"+billno+"' group by BillNo,Customers.Name,Customers.ContactNo,Billing.PurchaseDate";
				}else if(cbType.getSelectedItem()=="Customer_Name"){
					sql="Select Billing.BillNo, Customers.Name,Customers.ContactNo, Billing.PurchaseDate FROM Products INNER JOIN (Customers INNER JOIN Billing ON Customers.CID = Billing.CID) ON Products.PID = Billing.PID where Customers.Name like '"+tType.getText()+"%' group by BillNo,Customers.Name,Customers.ContactNo,Billing.PurchaseDate";
				}else if(cbType.getSelectedItem()=="ContactNo"){
					sql="Select Billing.BillNo, Customers.Name,Customers.ContactNo, Billing.PurchaseDate FROM Products INNER JOIN (Customers INNER JOIN Billing ON Customers.CID = Billing.CID) ON Products.PID = Billing.PID where Customers.ContactNo like '"+tType.getText()+"%' group by BillNo,Customers.Name,Customers.ContactNo,Billing.PurchaseDate";
				}else if(cbType.getSelectedItem()=="PurchaseDate"){
					if(tType.getText().length()==11)
						sql="Select Billing.BillNo, Customers.Name,Customers.ContactNo, Billing.PurchaseDate FROM Products INNER JOIN (Customers INNER JOIN Billing ON Customers.CID = Billing.CID) ON Products.PID = Billing.PID where Billing.PurchaseDate='"+tType.getText()+"' group by BillNo,Customers.Name,Customers.ContactNo,Billing.PurchaseDate";
					else
						return;	
				}
				clearBFrame();
			    Connection con=MyConnection.getConnect();
			    PreparedStatement ps=con.prepareStatement(sql);
			    ResultSet rs=ps.executeQuery();
			    Vector<Object>v;
			    while(rs.next()){
			    	v=new Vector<Object>();
					v.add(rs.getInt(1));
					v.add(rs.getString(2));
					v.add(rs.getString(3));
					java.util.Date d=rs.getDate(4);
		    		v.add(String.format("%td-%tb-%tY",d,d,d));
					mBilling.addRow(v);
				}
				con.close();
			}catch (Exception ex) {
				JOptionPane.showMessageDialog(c,ex.toString());
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
		if(o.equals(tBilling)){
			try {
				clearCFrame();
			    Connection con=MyConnection.getConnect();
			    PreparedStatement ps=con.prepareStatement("SELECT Customers.CID, Customers.Name, Customers.Gender, Customers.Address, Customers.Area, Customers.Pincode, Customers.EmailId, sum(Billing.Price*Billing.Quantity) as Total FROM Products INNER JOIN (Customers INNER JOIN Billing ON Customers.CID = Billing.CID) ON Products.PID = Billing.PID where Billing.BillNo=? group by Customers.CID, Customers.Name, Customers.Gender, Customers.Address, Customers.Area, Customers.Pincode, Customers.EmailId,Billing.BillNo");
				ps.setInt(1,(Integer)mBilling.getValueAt(tBilling.getSelectedRow(),0));
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
			    	v.add(rs.getInt(8));
					mCustomers.addRow(v);
				}
				con.close();
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(c,ex.toString());
			}
			}if(o.equals(tBilling)){
				try {
				clearPFrame();
			    Connection con=MyConnection.getConnect();
			    PreparedStatement ps=con.prepareStatement("SELECT Billing.SNo, Billing.PID, Products.Name, Products.Brand,Billing.CID, Billing.Price, Billing.Quantity, Billing.Price*Billing.Quantity AS Net FROM Products INNER JOIN (Customers INNER JOIN Billing ON Customers.CID = Billing.CID) ON Products.PID = Billing.PID WHERE Billing.BillNo=? ORDER BY Billing.SNo ASC");
			    ps.setInt(1,(Integer)mBilling.getValueAt(tBilling.getSelectedRow(),0));
			    ResultSet rs=ps.executeQuery();
			    Vector<Object>v;
			    	while(rs.next()){
				    	v=new Vector<Object>();
				    	v.add(rs.getInt(1));
				    	v.add(rs.getInt(2));
				    	v.add(rs.getString(3));
				    	v.add(rs.getString(4));
				    	v.add(rs.getInt(5));
				    	v.add(rs.getInt(6));
				    	v.add(rs.getInt(7));
				    	v.add(rs.getInt(8));
				    	mProducts.addRow(v);
			    	}
				}
				catch (Exception ex) {
				}
		}
	}
	public void mouseClicked(MouseEvent me){
	}
	String getCustomerName(int billNo){
		String name=null;
		try {
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select Name from Customers where CID=(select distinct CID from Billing where BillNo="+billNo+")");
			ResultSet rs=ps.executeQuery();
			if(rs.next())
				name=rs.getString(1);
			
			con.close();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(c,ex.toString());
		}
		return name;
	}
	String getBillDate(int billNo){
		String purchasedate=null;
		try {
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select PurchaseDate from Billing where CID=(select distinct  CID from Billing where BillNo="+billNo+")");
			ResultSet rs=ps.executeQuery();
			if(rs.next())
				purchasedate=rs.getString(1);
			con.close();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(c,ex.toString());
		}
		return purchasedate;
	}
	String getCustomersID(int billNo){
		String CustomersID=null;
		System.out.println (billNo);
		try {
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select CID from Billing where CID=(select distinct  CID from Billing where BillNo="+billNo+")");
			ResultSet rs=ps.executeQuery();
			if(rs.next())
					CustomersID=rs.getString(1);
			con.close();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(c,"cid"+ex.toString());
		}
		return CustomersID;
	}
	String getCustomersContactNo(int billNo){
		String contactNo=null;
		try {
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select ContactNo from Customers where CID=(select distinct  CID from Billing where BillNo="+billNo+")");
			ResultSet rs=ps.executeQuery();
			if(rs.next())
				contactNo=rs.getString(1);
			con.close();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(c,"contact"+ex.toString());
		}
		return contactNo;
	}
	
	private void printBill(int billNo){
		try {
		    java.io.PrintWriter out=new java.io.PrintWriter(new java.io.File("Total Bill.html"));
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Customer_Bill</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<DIV ALIGN='CENTER'>");
			out.println("<font face='Corbel' COLOR='blue' SIZE=18><B>BILLING FORM</B></FONT><BR>");
			out.println("<table border='4' WIDTH='70%' celpadding='2' celspacing='5'>");
			out.println("<tr>");
			out.println("<td><table width='100%'><tr><td rowspan='4' align='center'><img src='C:/db/my general store project/StoreMain/classes/Png1/Start Menu/Favorite.png' alt='Favorite' width='70%' /></td>");
			
			out.println("<td>R.K</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td>General Store<td><table border='1' width='75%'><tr><td>Bill Number</td><td>"+billNo+"</td></tr></table></td></td>");
			out.println("</tr>");
			
			out.println("<tr><td>Shop No.27,Panipat,Haryana.</td></tr>");
			out.println("<tr><td>PH.8950000895</td></tr>");
			out.println("</table></td>");
			out.println("</tr><tr>");
			out.println("<td><table width='100%'><tr><td>Bill To Customer: </td>");
			out.println("<td>"+getCustomersID(billNo)+"</td><td>Date:</td><td>"+getBillDate(billNo)+"</td></tr><tr><td>Bill To: </td><td>"+getCustomerName(billNo)+"</td><td>Mode of Pay:</td><td>CASH</td></tr><tr><td>PH.No: </td><td>"+getCustomersContactNo(billNo)+"</td>");
			out.println("</tr></table></td>");
			out.println("</tr>");
			
			out.println("<tr><td>");
			out.println("<table border='1' width='100%'>");
			out.println("<tr><th>SNO.</th><th>PID</th><th>P_BRAND</th><th>P_NAME</th><th>WEIGHT</th><th>UNIT</th><th>DISCOUNT</th><th>MRP</th><th>Dis.% Price</th><th>QUANTITY</th><th>TOTAL</th></tr>");
			
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("SELECT Billing.SNo, Billing.PID, Products.Brand, Products.Name, Products.Quantity, Products.Unit, Products.Discount, Products.MRP, Billing.Price, Billing.Quantity, Billing.Price*Billing.Quantity FROM Products INNER JOIN (Customers INNER JOIN Billing ON Customers.CID = Billing.CID) ON Products.PID = Billing.PID where BillNo=? group by Billing.SNo, Billing.PID, Products.Brand, Products.Name, Products.Quantity, Products.Unit, Products.MRP, Products.Discount, Billing.Price, Billing.Quantity");
			ps.setInt(1,billNo);
			int s=0;
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				out.println("<tr>");
				int total=rs.getInt(11);
				s=s+total;
				out.println("<td>"+rs.getInt(1)+"</td><td>"+rs.getInt(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getInt(5)+"</td><td>"+rs.getString(6)+"</td><td>"+rs.getInt(7)+"</td><td>"+rs.getInt(8)+"</td><td>"+rs.getInt(9)+"</td><td>"+rs.getInt(10)+"</td><td>"+total+"</td>");
				out.println("</tr>");
			}
			out.print("<tr>");
			out.print("<td>&nbsp;</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>");
			out.print("</tr>");
			
			out.print("<tr>");
			out.print("<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>Net Total Amout.</td><td>"+s+"</td>");
			out.print("</tr>");
			
				con.close();
			
			out.println("</table></td></tr>");
			out.println("<td>");
			out.println("<table><tr><td><font size='6'>Terms and condition</td></tr>");
			out.println("<ul><tr><td><li>Fixed price shop</li></td></tr><tr><td><li>Item brought not return</li></td></tr></ul>");
			out.println("</table>");
			out.println("</td>");
			
			out.println("</table>");
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
			
			out.close();

		 	Desktop.getDesktop().open(new java.io.File("Total Bill.html"));   
		
		}
		catch (Exception ex) {
		}
	}
}