package be.pxl.app;

import java.awt.BorderLayout;
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

public class ChooseMethodFrame {

	private AppFrame frame;

	private JPanel btnPnl;
	private JPanel stegaBtnPnl;

	private JButton keyBtn;
	private JButton stegaImageBtn;
	private JButton stegaSoundBtn;
	
	private boolean encrypt;

	public ChooseMethodFrame(boolean encrypt) {
		this.encrypt = encrypt;
		if (encrypt == true) {
			keyBtn = new JButton("Encrypt with keys");
			stegaImageBtn = new JButton("Encrypt with steganography(Image)");
			stegaSoundBtn = new JButton("Encrypt with steganography(Sound)");
		} else {
			keyBtn = new JButton("Decrypt with keys");
			stegaImageBtn = new JButton("Decrypt with steganography(Image)");
			stegaSoundBtn = new JButton("Decrypt with steganography(Sound)");
		}

		frame = new AppFrame();

		frame.setTitle("Choose method");

		btnPnl = new JPanel();
		stegaBtnPnl = new JPanel();
		
		btnPnl.setLayout(new GridLayout(1, 1, 20, 20));
		btnPnl.setBackground(new Color(71, 62, 63));
		stegaBtnPnl.setLayout(new GridLayout(1, 2, 20, 0));
		stegaBtnPnl.setBackground(new Color(71, 62, 63));
		
		frame.pnl.setBackground(new Color(71, 62, 63));
		btnPnl.setBorder(new EmptyBorder(20, 0, 20, 0));
		//stegaBtnPnl.setBorder(new EmptyBorder(20, 0, 20, 0));

		frame.pnl.add(btnPnl, BorderLayout.CENTER);
		frame.pnl.add(stegaBtnPnl, BorderLayout.CENTER);

		keyBtn.setPreferredSize(new Dimension(500, 210));
		stegaImageBtn.setPreferredSize(new Dimension(240, 210));
		stegaSoundBtn.setPreferredSize(new Dimension(240, 210));

		keyBtn.setBackground(new Color(108, 206, 203));
		keyBtn.setFocusPainted(false);
		keyBtn.setBorderPainted(false);
		stegaImageBtn.setBackground(new Color(108, 206, 203));
		stegaImageBtn.setFocusPainted(false);
		stegaImageBtn.setBorderPainted(false);
		stegaSoundBtn.setBackground(new Color(108, 206, 203));
		stegaSoundBtn.setFocusPainted(false);
		stegaSoundBtn.setBorderPainted(false);

		btnPnl.add(keyBtn);
		stegaBtnPnl.add(stegaImageBtn);
		stegaBtnPnl.add(stegaSoundBtn);

		Handler handler = new Handler();
		MouseHover mouseHover = new MouseHover();

		keyBtn.addActionListener(handler);
		stegaImageBtn.addActionListener(handler);
		stegaSoundBtn.addActionListener(handler);
		
		keyBtn.addMouseListener(mouseHover);
		stegaImageBtn.addMouseListener(mouseHover);
		stegaSoundBtn.addMouseListener(mouseHover);
	}

	private class Handler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == keyBtn && encrypt == true) {
				new KeyMethodEncryptFrame();
				frame.dispose();
			}
			
			if (e.getSource() == keyBtn && encrypt == false) {
				new KeyMethodDecryptFrame();
				frame.dispose();
			}
			
			if (e.getSource() == stegaImageBtn && encrypt == true) {
				new StegaImgMethodEncryptFrame();
				frame.dispose();
			}
			
			if (e.getSource() == stegaImageBtn && encrypt == false) {
				new StegaImgMethodDecryptFrame();
				frame.dispose();
			}
			
			if (e.getSource() == stegaSoundBtn && encrypt == true) {
				new StegaSoundMethodEncryptFrame();
				frame.dispose();
			}
			
			if (e.getSource() == stegaSoundBtn && encrypt == false) {
				new StegaSoundMethodDecryptFrame();
				frame.dispose();
			}
		}
	}

	private class MouseHover implements MouseListener {

		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == keyBtn) {
				keyBtn.setBackground(new Color(33, 140, 141));
			}

			if (e.getSource() == stegaImageBtn) {
				stegaImageBtn.setBackground(new Color(33, 140, 141));
			}

			if (e.getSource() == stegaSoundBtn) {
				stegaSoundBtn.setBackground(new Color(33, 140, 141));
			}
		}

		public void mouseExited(MouseEvent e) {
			if (e.getSource() == keyBtn) {
				keyBtn.setBackground(new Color(108, 206, 203));
			}

			if (e.getSource() == stegaImageBtn) {
				stegaImageBtn.setBackground(new Color(108, 206, 203));
			}

			if (e.getSource() == stegaSoundBtn) {
				stegaSoundBtn.setBackground(new Color(108, 206, 203));
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
