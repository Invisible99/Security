package be.pxl.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BeginFrame {
	
	private AppFrame frame;

	private JPanel btnPnl;

	private JButton encrBtn;
	private JButton decrBtn;
	
	private Encrypt encr = new Encrypt();
	
	public BeginFrame() {
		
		encrBtn = new JButton("ENCRYPT");
		decrBtn = new JButton("DECRYPT");
		
		frame = new AppFrame();
		
		frame.setTitle("Begin");
		
		btnPnl = new JPanel();
		btnPnl.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		frame.pnl.add(btnPnl);
		/*if (shouldFill) {
			c.fill = GridBagConstraints.HORIZONTAL;
		}*/
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 70;
		c.gridy = 20;
		c.gridwidth = 500;
		c.gridheight = 210;
		btnPnl.add(encrBtn, c);

		//btnPnl.add(encrBtn);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 70;
		c.gridy = 250;
		c.gridwidth = 500;
		c.gridheight = 210;
		btnPnl.add(decrBtn, c);
		
		Handler handler = new Handler();
		
		encrBtn.addActionListener(handler);
		decrBtn.addActionListener(handler);
	}
	
	private class Handler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == encrBtn) {
				encr.generateKeys();
				EncryptFrame encrFrame = new EncryptFrame();
				frame.dispose();
				//System.out.println("Test");
				//fileChooser.showOpenDialog();
				//System.out.println("Keys generated!");
			}
		}
	}
}
