package be.pxl.app;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EncryptFrame {

	private AppFrame frame;

	private JPanel btnPnl;

	private JButton keyBtn;
	private JButton stegaPhotoBtn;
	private JButton stegaMovieBtn;
	private JButton stegaSoundBtn;

	public EncryptFrame() {
		keyBtn = new JButton("Encrypt with keys");
		stegaPhotoBtn = new JButton("Encrypt with steganography(Photo)");
		stegaMovieBtn = new JButton("Encrypt with steganography(Movie)");
		stegaSoundBtn = new JButton("Encrypt with steganography(Sound)");

		frame = new AppFrame();

		frame.setTitle("Begin");

		btnPnl = new JPanel();
		btnPnl.setLayout(new GridLayout(2, 2));
		frame.pnl.add(btnPnl, BorderLayout.CENTER);

		btnPnl.add(keyBtn);
		btnPnl.add(stegaPhotoBtn);
		btnPnl.add(stegaMovieBtn);
		btnPnl.add(stegaSoundBtn);

		Handler handler = new Handler();

		keyBtn.addActionListener(handler);
		stegaPhotoBtn.addActionListener(handler);
		stegaMovieBtn.addActionListener(handler);
		stegaSoundBtn.addActionListener(handler);
	}

	private class Handler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == keyBtn) {
				frame.dispose();
			}
		}
	}
}
