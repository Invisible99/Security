package be.pxl.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StegaSoundMethodDecryptFrame {

	private AppFrame frame;

	private JPanel btnPnl;
	private JPanel okBtnPnl;

	private JButton okBtn;
	private JButton soundChooserBtn;

	private JLabel soundChooserLbl;
	private JLabel passwdLbl;

	private JTextField passwdTxtField;

	private JFileChooser fileChooser;

	private String soundName;
	private String outputDirName = System.getProperty("user.home") + "/documents/Security App Files/Decrypted Steganography Files/";

	public StegaSoundMethodDecryptFrame() {

		soundChooserBtn = new JButton("Choose sound(.au) to store message");
		okBtn = new JButton("Encrypt with sound steganography");

		soundChooserLbl = new JLabel();
		passwdLbl = new JLabel("Choose a password");

		passwdTxtField = new JTextField();

		frame = new AppFrame();

		frame.pnl.setLayout(new BorderLayout());

		btnPnl = new JPanel();
		btnPnl.setLayout(new GridLayout(2, 2, 10, 10));
		btnPnl.setBackground(new Color(71, 62, 63));

		okBtnPnl = new JPanel();
		okBtnPnl.setLayout(new GridLayout(1, 1, 0, 0));
		okBtnPnl.setBackground(new Color(71, 62, 63));

		frame.pnl.setBackground(new Color(71, 62, 63));

		frame.pnl.add(btnPnl, BorderLayout.CENTER);
		frame.pnl.add(okBtnPnl, BorderLayout.SOUTH);

		frame.pnl.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnPnl.setBorder(new EmptyBorder(0, 0, 0, 0));
		okBtnPnl.setBorder(new EmptyBorder(10, 0, 0, 0));
		btnPnl.setPreferredSize(new Dimension(210, 300));
		okBtnPnl.setPreferredSize(new Dimension(640, 100));

		soundChooserBtn.setBackground(new Color(108, 206, 203));
		soundChooserBtn.setFocusPainted(false);
		soundChooserBtn.setBorderPainted(false);

		okBtn.setBackground(new Color(108, 206, 203));
		okBtn.setFocusPainted(false);
		okBtn.setBorderPainted(false);
		okBtn.setPreferredSize(new Dimension(640, 100));

		soundChooserLbl.setForeground(new Color(108, 206, 203));
		soundChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		passwdLbl.setForeground(new Color(108, 206, 203));
		passwdLbl.setHorizontalAlignment(SwingConstants.CENTER);

		passwdTxtField.setBorder(BorderFactory.createLineBorder(new Color(108, 206, 203)));
		passwdTxtField.setBackground(new Color(71, 62, 63));
		passwdTxtField.setHorizontalAlignment(SwingConstants.CENTER);
		passwdTxtField.setForeground(new Color(108, 206, 203));

		btnPnl.add(soundChooserBtn);
		btnPnl.add(soundChooserLbl);
		btnPnl.add(passwdLbl);
		btnPnl.add(passwdTxtField);

		okBtnPnl.add(okBtn);

		Handler handler = new Handler();

		soundChooserBtn.addActionListener(handler);
		okBtn.addActionListener(handler);
	}

	private class Handler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == soundChooserBtn) {
				fileChooser = new JFileChooser();
				FileFilter imageFilter = new FileNameExtensionFilter("Audio files", "au");
				fileChooser.setFileFilter(imageFilter);
				int returnVal = fileChooser.showOpenDialog((Component) e.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						soundName = file.toString();
						soundChooserLbl.setText(file.getName());
						
						int dot = file.getName().lastIndexOf(".");
						String fnm = file.getName().substring(0, dot);
						outputDirName += fnm + ".txt";
					} catch (Exception ex) {
						System.out.println("problem accessing file" + file.getAbsolutePath());
					}
				}
			}

			if (e.getSource() == okBtn) {
				if (soundName == null) {
					System.out.println("Add all files");
				} else {
					SoundStega soundStega = new SoundStega(soundName, outputDirName, passwdTxtField.getText().toCharArray());
					soundStega.decode();
					frame.dispose();
					new BeginFrame();
				}
			}
		}
	}
}