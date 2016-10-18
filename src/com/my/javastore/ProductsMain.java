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
class ProductsMain extends JInternalFrame implements ActionListener,MouseListener,KeyListener{
	Container c;
	JLabel lPID,lName,lBrand,lMRP,lQuantity,lDiscount,lPer,lCFind;
	JTextField jPID,jName,jMRP,jQuantity,jCFind;
	DefaultComboBoxModel mUnit;
	JComboBox cbUnit;
	DefaultListModel mBrand;
	JList jlBrand;
	DefaultTableModel mProducts;
	JTable tProducts;
	JButton bAdd,bNew,bDelete,bUpdate,bFind,bClose,bPrint;
	JTextField jDiscount;
	
	ProductsMain(){
		c=getContentPane();
		setLocation(0,0);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLayout(null);
		
		productsFrame();
		
		JPanel pPanel=new JPanel();
		pPanel.setBounds(10,10,1300,500);
		pPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"Products"));
		c.add(pPanel);
		
		setVisible(true);
		setClosable(true);
	}
	private void productsFrame(){
		lPID=new JLabel("Product ID");
		lPID.setBounds(20,40,100,30);
		c.add(lPID);
			
		jPID=new JTextField();
		jPID.setBounds(100,40,100,30);
		c.add(jPID);
		
		lName=new JLabel("Name");
		lName.setBounds(20,80,100,30);
		c.add(lName);
		
		jName=new JTextField();
		jName.setBounds(100,80,100,30);
		c.add(jName);
		
		lBrand=new JLabel("Brand");
		lBrand.setBounds(220,80,100,30);
		c.add(lBrand);
		
		mBrand=new DefaultListModel();
		jlBrand=new JList(mBrand);
		JScrollPane jspBrand=new JScrollPane(jlBrand);
		jspBrand.setBounds(280,40,110,120);
		c.add(jspBrand);
		MyConnection.fillBrand(mBrand);
		
		lMRP=new JLabel("MRP");
		lMRP.setBounds(20,130,100,30);
		c.add(lMRP);
		
		jMRP=new JTextField();
		jMRP.setBounds(100,130,100,30);
		c.add(jMRP);
		
		lQuantity=new JLabel("Net Wt.");
		lQuantity.setBounds(20,170,100,30);
		c.add(lQuantity);
		
		jQuantity=new JTextField();
		jQuantity.setBounds(100,170,90,30);
		c.add(jQuantity);
		
		mUnit=new DefaultComboBoxModel();
		cbUnit=new JComboBox(mUnit);
		cbUnit.setBounds(200,170,70,30);
		c.add(cbUnit);
		fillUnit();
				
		lDiscount=new JLabel("Discount");
		lDiscount.setBounds(20,210,100,30);
		c.add(lDiscount);
			
		jDiscount=new JTextField();
		jDiscount.setBounds(100,210,50,30);
		c.add(jDiscount);
		
		lPer=new JLabel("%");
		lPer.setBounds(160,210,30,30);
		c.add(lPer);
		
		bAdd=new JButton("add",new ImageIcon("icon/Add.png"));
		bAdd.setBounds(20,270,100,60);
		c.add(bAdd);
		
		bNew=new JButton("new",new ImageIcon("Png1/File Types/Default.png"));
		bNew.setBounds(110,270,100,60);
		c.add(bNew);
		
		bDelete=new JButton("delete",new ImageIcon("Png1/Desktop/delete.png"));
		bDelete.setBounds(210,270,100,60);
		c.add(bDelete);
		
		bUpdate=new JButton("update",new ImageIcon("PNG/refresh.png"));
		bUpdate.setBounds(20,340,100,60);
		c.add(bUpdate);
		
		bFind=new JButton("find",new ImageIcon("PNG/search.png"));
		bFind.setBounds(110,340,100,60);
		c.add(bFind);
		
		bClose=new JButton("close",new ImageIcon("icon/close.png"));
		bClose.setBounds(210,340,100,60);
		c.add(bClose);
		
		bPrint=new JButton("print",new ImageIcon("icon/printer.png"));
		bPrint.setBounds(1150,390,150,115);
		c.add(bPrint);
		
		bAdd.setToolTipText("ADD");
		bNew.setToolTipText("NEW");
		bDelete.setToolTipText("DELETE");
		bUpdate.setToolTipText("UPDATE");
		bFind.setToolTipText("FIND");
		bClose.setToolTipText("CLOSE");
		bPrint.setToolTipText("Print");
		
		String header[]={"PID","Name","Brand","MRP","Net Wt.","Unit","Discount(%)"};
		mProducts=new DefaultTableModel(header,7);
		tProducts=new JTable(mProducts);
		JScrollPane jspProducts=new JScrollPane(tProducts);
		jspProducts.setBounds(600,20,700,360);
		c.add(jspProducts);
		clearframe();
		fillProducts();
		tProducts.setToolTipText("Products Frame");
		
		lCFind=new JLabel("Sort");
		lCFind.setBounds(600,390,60,30);
		c.add(lCFind);
		
		jCFind=new JTextField();
		jCFind.setBounds(650,390,100,30);
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
		bPrint.addActionListener(this);
		tProducts.addMouseListener(this);
		jCFind.addKeyListener(this);
		
	}
	private void fillProducts(){
		try {
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select * from Products");
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
		}catch(Exception ex){
				JOptionPane.showMessageDialog(c,ex.toString());
		}
	}
	private void clearframe(){
		while(mProducts.getRowCount()>0)
			mProducts.removeRow(0);
	}
	private void clearField(){
		jPID.setText("");
		jName.setText("");
		jMRP.setText("");
		jQuantity.setText("");
	}
	private void fillUnit(){
		mUnit.addElement("Choose");
		mUnit.addElement("Kg");
		mUnit.addElement("g");
		mUnit.addElement("L");
		mUnit.addElement("ml");
		mUnit.addElement("Pound");
		mUnit.addElement("Pcs.");
		
	}
	private void printProducts(){
		try {
			 java.io.PrintWriter out=new java.io.PrintWriter(new java.io.File("print.html"));
			 out.println("<html>");
			 out.println("<body>");
			 out.println("<table border='1' width='600'>");
			 out.println("<tr>");
			 out.println("<td>PID</td>");
			 out.println("<td>Name</td>");
			 out.println("<td>Brand</td>");
			 out.println("<td>MRP</td>");
			 out.println("<td>Net Wt.</td>");
			 out.println("<td>Unit</td>");
			 out.println("<td>Discount (%)</td>");
			 out.println("</tr>");
			 for (int i = 0; i<mProducts.getRowCount(); i++){
			 	out.println("\t<tr>");
			 	for(int j=0;j<7;++j)
			 		out.println("\t\t<td>"+mProducts.getValueAt(i,j)+"</td>");
			 	out.println("\t</tr>");
			 }
			 out.println("</table>");
			 out.println("</body>");
			 out.println("</html>");
			 out.close();
			 
			 Desktop.getDesktop().open(new java.io.File("print.html"));   
		}
		catch (Exception ex) {
		}
	}
	private void addProduct(){
		try{
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Insert into Products Values(?,?,?,?,?,?,?)");
			ps.setInt(1,Integer.parseInt(jPID.getText()));
			ps.setString(2,jName.getText());
			ps.setString(3,jlBrand.getSelectedValue().toString());
			ps.setInt(4,Integer.parseInt(jMRP.getText()));
			ps.setInt(5,Integer.parseInt(jQuantity.getText()));
			ps.setString(6,cbUnit.getSelectedItem().toString());
			ps.setInt(7,Integer.parseInt(jDiscount.getText()));
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
	private void newProducts(){
		try {
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select Max(PID) from Products");
		    ResultSet rs=ps.executeQuery();
		    int pid=0;
		    if(rs.next())
		    	pid=rs.getInt(1);
			++pid;
			jPID.setText(String.valueOf(pid));
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(c,ex.toString());
		}
	
	}
	private void deleteCustomer(){
		try {
		   	Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Delete from Products where PID=?");
			ps.setInt(1,Integer.parseInt(jPID.getText()));
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
	private void updateproducts(){
		try{
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Update Products set Name=?,Brand=?,MRP=?,Quantity=?,Unit=?,Discount=? where PID=?");
			ps.setString(1,jName.getText());
			ps.setString(2,jlBrand.getSelectedValue().toString());
			ps.setInt(3,Integer.parseInt(jMRP.getText()));
			ps.setInt(4,Integer.parseInt(jQuantity.getText()));
			ps.setString(5,cbUnit.getSelectedItem().toString());
			ps.setInt(6,Integer.parseInt(jDiscount.getText()));
			ps.setInt(7,Integer.parseInt(jPID.getText()));
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
	private void findProducts(){
		try {
			Connection con=MyConnection.getConnect();
			PreparedStatement ps=con.prepareStatement("Select * from Products where PID=?");
			ps.setInt(1,Integer.parseInt(jPID.getText()));  		
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
		}catch(Exception ex){
				JOptionPane.showMessageDialog(c,ex.toString());
		}
	}
	public void actionPerformed(ActionEvent ae){
		Object o=ae.getSource();
		if(o.equals(bAdd)){
			addProduct();
			clearframe();
			fillProducts();
		}else if(o.equals(bNew)){
			clearField();;
			newProducts();
		}else if(o.equals(bDelete)){
			deleteCustomer();
			clearframe();
			fillProducts();
		}else if(o.equals(bUpdate)){
			updateproducts();
			clearframe();
			fillProducts();
		}else if(o.equals(bFind)){
			clearframe();
			findProducts();
		}else if(o.equals(bPrint)){
			printProducts();
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
		if(o.equals(tProducts)){
			int r=tProducts.getSelectedRow();
			jPID.setText(String.valueOf((Integer)mProducts.getValueAt(r,0)));
			jName.setText(String.valueOf(mProducts.getValueAt(r,1)));
			jlBrand.setSelectedValue(mProducts.getValueAt(r,2),true);
			jMRP.setText(String.valueOf((Integer)mProducts.getValueAt(r,3)));
			jQuantity.setText(String.valueOf((Integer)mProducts.getValueAt(r,4)));
			cbUnit.setSelectedItem(mProducts.getValueAt(r,5));
			jDiscount.setText(String.valueOf((Integer)mProducts.getValueAt(r,6)));	
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
				PreparedStatement ps=con.prepareStatement("Select * from Products where Name like '"+jCFind.getText()+"%'");
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
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(c,ex.toString());
			}
		}
	}
}