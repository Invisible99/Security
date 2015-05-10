package be.pxl.app.soundStega;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import be.pxl.app.main.AppFrame;
import be.pxl.app.main.BeginFrame;

public class StegaSoundMethodEncryptFrame {

	private AppFrame frame;

	private JPanel btnPnl;
	private JPanel okBtnPnl;

	private JButton okBtn;
	private JButton soundChooserBtn;
	private JButton textFileChooserBtn;

	private JLabel soundChooserLbl;
	private JLabel textFileChooserLbl;

	private JFileChooser fileChooser;

	private String soundName;
	private String textFileName;
	private String outputDirName;

	public StegaSoundMethodEncryptFrame() {

		soundChooserBtn = new JButton("Choose sound(.au) to store message");
		textFileChooserBtn = new JButton("Choose text file");
		okBtn = new JButton("Encrypt with sound steganography");

		soundChooserLbl = new JLabel();
		textFileChooserLbl = new JLabel();

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

		// btnPnl.add(fileChooser);
		btnPnl.add(soundChooserBtn);
		btnPnl.add(soundChooserLbl);
		// btnPnl.add(publicKeyChooser);
		btnPnl.add(textFileChooserBtn);
		btnPnl.add(textFileChooserLbl);
		// btnPnl.add(privateKeyChooser);

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
					outputDirName = System.getProperty("user.home") + "/documents/Security App Files/Encrypted Steganography Files/";
					try {
						soundName = file.toString();
						soundChooserLbl.setText(file.getName());
						outputDirName += file.getName();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Problem accessing audio file" + file.getAbsolutePath(), "Audio file error", JOptionPane.ERROR_MESSAGE);
						// System.out.println("problem accessing file" + file.getAbsolutePath());
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
						JOptionPane.showMessageDialog(null, "Problem accessing text file" + file.getAbsolutePath(), "Text file error", JOptionPane.ERROR_MESSAGE);
						// System.out.println("problem accessing file" + file.getAbsolutePath());
					}
				}
			}

			if (e.getSource() == okBtn) {
				if (soundName == null || textFileName == null) {
					// System.out.println("Add all files");
					JOptionPane.showMessageDialog(null, "Add all needed files", "File error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						SoundStega soundStega = new SoundStega(soundName, textFileName, outputDirName);
						if (soundStega.isAu(soundName)) {
							if (soundStega.isTxt(textFileName)) {
								soundStega.encode();

								JOptionPane.showMessageDialog(null, "Hiding text in audio successful", "Encryption message", JOptionPane.PLAIN_MESSAGE);
								frame.dispose();
								new BeginFrame();
							} else {
								JOptionPane.showMessageDialog(null, "File chosen is not a text with extension '.txt'", "Text file error", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "File chosen is not an audio with extension '.au'", "Audio file error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Failed to encrypt message from audio file", "Encryption error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
}