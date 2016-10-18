package com.my.javastore;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainFrame extends JFrame implements ActionListener{
	Container c;
	JButton bCustomers,bBilling,bProducts,bFinding,bProfile,bExit;
	JToolBar tBar;
	JInternalFrame iFrame;
	
	MainFrame(){
		c=getContentPane();
		setTitle("General Store");
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLayout(new BorderLayout());
		
		TopBar();
				
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void TopBar(){
		tBar=new JToolBar();
		tBar.setLayout(new GridLayout(1,6));
		
		bCustomers=new JButton("CUSTOMERS",new ImageIcon("icon/customer.png"));
		bBilling=new JButton("BILLING",new ImageIcon("icon/billing.png"));
		bProducts=new JButton("PRODUCTS",new ImageIcon("icon/product.png"));
		bFinding=new JButton("Finding",new ImageIcon("icon/Camera.png"));
		bProfile=new JButton("Profile",new ImageIcon("Png1/Extras/User.png"));
		bExit=new JButton("EXIT",new ImageIcon("icon/exit.png"));				
		
		tBar.add(bCustomers);
		tBar.add(bProducts);
		tBar.add(bBilling);
		tBar.add(bFinding);
		tBar.add(bProfile);
		tBar.add(bExit);
		
		bCustomers.setToolTipText("Customers");
		bBilling.setToolTipText("Billing");
		bProducts.setToolTipText("Products");
		bFinding.setToolTipText("Finding");
		bProfile.setToolTipText("Profile");
		bExit.setToolTipText("Exit");
		
		bCustomers.addActionListener(this);
		bBilling.addActionListener(this);
		bProducts.addActionListener(this);
		bFinding.addActionListener(this);
		bProfile.addActionListener(this);
		bExit.addActionListener(this);
		
		c.add(tBar,BorderLayout.NORTH);
		
		//default
		iFrame=new CustomersMain();
		c.add(iFrame,BorderLayout.CENTER);
	}
	public void actionPerformed(ActionEvent ae){
		Object o=ae.getSource();
		try {
		    iFrame.setClosed(true);
		}
		catch (Exception ex) {
		}
		if(o.equals(bCustomers)){
			iFrame=new CustomersMain();
			c.add(iFrame,BorderLayout.CENTER);
		}else if(o.equals(bBilling)){
			iFrame=new BillingMain();
			c.add(iFrame,BorderLayout.CENTER);
		}else if(o.equals(bProducts)){
			iFrame=new ProductsMain();
			c.add(iFrame,BorderLayout.CENTER);
		}else if(o.equals(bFinding)){
			iFrame=new FindingMain();
			c.add(iFrame,BorderLayout.CENTER);	
		}else if(o.equals(bProfile)){
			iFrame=new Profile();
			c.add(iFrame,BorderLayout.CENTER);
		}else if(o.equals(bExit)){
			System.exit(0);
		}
	}
}