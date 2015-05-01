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

public class StegaSoundMethodEncryptFrame {

	private AppFrame frame;

	private JPanel btnPnl;
	private JPanel okBtnPnl;

	private JButton okBtn;
	private JButton soundChooserBtn;
	private JButton textFileChooserBtn;

	private JLabel soundChooserLbl;
	private JLabel textFileChooserLbl;
	private JLabel passwdLbl;
	
	private JTextField passwdTxtField;

	private JFileChooser fileChooser;

	private String soundName;
	private String textFileName;
	private String outputDirName = System.getProperty("user.home") + "/documents/Security App Files/Encrypted Steganography Files/";

	public StegaSoundMethodEncryptFrame() {

		soundChooserBtn = new JButton("Choose sound(.au) to store message");
		textFileChooserBtn = new JButton("Choose text file");
		okBtn = new JButton("Encrypt with sound steganography");

		soundChooserLbl = new JLabel();
		textFileChooserLbl = new JLabel();
		passwdLbl = new JLabel("Choose a password");
		
		passwdTxtField = new JTextField();

		frame = new AppFrame();

		frame.pnl.setLayout(new BorderLayout());

		btnPnl = new JPanel();
		btnPnl.setLayout(new GridLayout(3, 3, 10, 10));
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

		textFileChooserBtn.setBackground(new Color(108, 206, 203));
		textFileChooserBtn.setFocusPainted(false);
		textFileChooserBtn.setBorderPainted(false);

		textFileChooserBtn.setBorderPainted(false);

		okBtn.setBackground(new Color(108, 206, 203));
		okBtn.setFocusPainted(false);
		okBtn.setBorderPainted(false);
		okBtn.setPreferredSize(new Dimension(640, 100));

		soundChooserLbl.setForeground(new Color(108, 206, 203));
		soundChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		textFileChooserLbl.setForeground(new Color(108, 206, 203));
		textFileChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		passwdLbl.setForeground(new Color(108, 206, 203));
		passwdLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		//passwdTxtField.setBorder(BorderFactory.createEmptyBorder());
		passwdTxtField.setBorder(BorderFactory.createLineBorder(new Color(108, 206, 203)));
		passwdTxtField.setBackground(new Color(71, 62, 63));
		passwdTxtField.setHorizontalAlignment(SwingConstants.CENTER);
		passwdTxtField.setForeground(new Color(108, 206, 203));

		// btnPnl.add(fileChooser);
		btnPnl.add(soundChooserBtn);
		btnPnl.add(soundChooserLbl);
		// btnPnl.add(publicKeyChooser);
		btnPnl.add(textFileChooserBtn);
		btnPnl.add(textFileChooserLbl);
		// btnPnl.add(privateKeyChooser);
		btnPnl.add(passwdLbl);
		btnPnl.add(passwdTxtField);

		okBtnPnl.add(okBtn);

		Handler handler = new Handler();

		soundChooserBtn.addActionListener(handler);
		textFileChooserBtn.addActionListener(handler);
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
						outputDirName += file.getName(); 
					} catch (Exception ex) {
						System.out.println("problem accessing file" + file.getAbsolutePath());
					}
				}
			} 
			
			if (e.getSource() == textFileChooserBtn) {
				fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				fileChooser.setFileFilter(filter);
				int returnVal = fileChooser.showOpenDialog((Component) e.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						textFileName = file.toString();
						textFileChooserLbl.setText(file.getName());
					} catch (Exception ex) {
						System.out.println("problem accessing file" + file.getAbsolutePath());
					}
				}
			} 
			
			if (e.getSource() == okBtn) {
				if (soundName == null || textFileName == null) {					
					System.out.println("Add all files");
				} else {
					SoundStega soundStega = new SoundStega(soundName, textFileName, outputDirName, passwdTxtField.getText().toCharArray());
					soundStega.encode();
					frame.dispose();
					new BeginFrame();
				}
			}
		}
	}
}