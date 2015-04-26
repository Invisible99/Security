package be.pxl.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BeginFrame {

	private AppFrame frame;

	private JPanel btnPnl;

	private JButton encrBtn;
	private JButton decrBtn;

	//private boolean isPressed = false;
	
	public boolean encrypt;

	public BeginFrame() {
		
		encrBtn = new JButton("ENCRYPT");
		decrBtn = new JButton("DECRYPT");

		frame = new AppFrame();

		frame.setTitle("Begin");

		btnPnl = new JPanel();
		// btnPnl.setLayout(new GridBagLayout());
		btnPnl.setLayout(new GridLayout(2, 1, 0, 20));
		btnPnl.setBackground(new Color(71, 62, 63));
		frame.pnl.setBackground(new Color(71, 62, 63));
		btnPnl.setBorder(new EmptyBorder(20, 0, 20, 0));

		frame.pnl.add(btnPnl);

		encrBtn.setBackground(new Color(108, 206, 203));
		encrBtn.setFocusPainted(false);
		encrBtn.setBorderPainted(false);
		encrBtn.setPreferredSize(new Dimension(500, 210));

		decrBtn.setBackground(new Color(108, 206, 203));
		decrBtn.setFocusPainted(false);
		decrBtn.setBorderPainted(false);
		decrBtn.setPreferredSize(new Dimension(500, 210));

		btnPnl.add(encrBtn);
		btnPnl.add(decrBtn);

		Handler handler = new Handler();
		MouseHover mouseHover = new MouseHover();

		encrBtn.addActionListener(handler);
		decrBtn.addActionListener(handler);

		encrBtn.addMouseListener(mouseHover);
		decrBtn.addMouseListener(mouseHover);
	}

	private class Handler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == encrBtn) {
				encrypt = true;
				new ChooseMethodFrame(true);
				frame.dispose();
			}
			
			if (e.getSource() == decrBtn) {
				encrypt = false;
				new ChooseMethodFrame(false);
				frame.dispose();
			}
		}
	}

	private class MouseHover implements MouseListener {

		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == encrBtn) {
				encrBtn.setBackground(new Color(33, 140, 141));
			}

			if (e.getSource() == decrBtn) {
				decrBtn.setBackground(new Color(33, 140, 141));
			}
		}

		public void mouseExited(MouseEvent e) {
			if (e.getSource() == encrBtn) {
				encrBtn.setBackground(new Color(108, 206, 203));
			}

			if (e.getSource() == decrBtn) {
				decrBtn.setBackground(new Color(108, 206, 203));
			}
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			
		}

		public void mouseReleased(MouseEvent e) {
		}
	}
}
