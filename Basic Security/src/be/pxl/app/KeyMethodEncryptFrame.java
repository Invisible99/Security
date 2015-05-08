package be.pxl.app;

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

public class KeyMethodEncryptFrame {

	private AppFrame frame;

	private JPanel btnPnl;
	private JPanel okBtnPnl;

	private JButton okBtn;
	private JButton fileChooserBtn;
	private JButton publicKeyChooserBtn;
	private JButton privateKeyChooserBtn;

	private JLabel fileChooserLbl;
	private JLabel publicKeyChooserLbl;
	private JLabel privateKeyChooserLbl;

	private JFileChooser fileChooser;

	private String fileName;
	private String publicKeyName;
	private String privateKeyName;
	
	//Error Strings
	private String errorMsg;
	private String errorTitle;

	public KeyMethodEncryptFrame() {

		fileChooserBtn = new JButton("Choose file");
		publicKeyChooserBtn = new JButton("Choose public key");
		privateKeyChooserBtn = new JButton("Choose private key");
		okBtn = new JButton("Encrypt");

		fileChooserLbl = new JLabel();
		publicKeyChooserLbl = new JLabel();
		privateKeyChooserLbl = new JLabel();

		frame = new AppFrame();

		frame.pnl.setLayout(new BorderLayout());

		btnPnl = new JPanel();
		btnPnl.setLayout(new GridLayout(3, 2, 10, 4));
		btnPnl.setBackground(new Color(71, 62, 63));

		okBtnPnl = new JPanel();
		okBtnPnl.setLayout(new GridLayout(1, 1, 0, 0));
		okBtnPnl.setBackground(new Color(71, 62, 63));

		frame.pnl.setBackground(new Color(71, 62, 63));

		frame.pnl.add(btnPnl, BorderLayout.CENTER);
		frame.pnl.add(okBtnPnl, BorderLayout.SOUTH);

		frame.pnl.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnPnl.setBorder(new EmptyBorder(0, 0, 0, 0));
		okBtnPnl.setBorder(new EmptyBorder(4, 0, 0, 0));
		btnPnl.setPreferredSize(new Dimension(210, 300));
		okBtnPnl.setPreferredSize(new Dimension(640, 100));

		fileChooserBtn.setBackground(new Color(108, 206, 203));
		fileChooserBtn.setFocusPainted(false);
		fileChooserBtn.setBorderPainted(false);

		publicKeyChooserBtn.setBackground(new Color(108, 206, 203));
		publicKeyChooserBtn.setFocusPainted(false);
		publicKeyChooserBtn.setBorderPainted(false);

		privateKeyChooserBtn.setBackground(new Color(108, 206, 203));
		privateKeyChooserBtn.setFocusPainted(false);
		privateKeyChooserBtn.setBorderPainted(false);

		okBtn.setBackground(new Color(108, 206, 203));
		okBtn.setFocusPainted(false);
		okBtn.setBorderPainted(false);
		okBtn.setPreferredSize(new Dimension(640, 100));

		fileChooserLbl.setForeground(new Color(108, 206, 203));
		fileChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		publicKeyChooserLbl.setForeground(new Color(108, 206, 203));
		publicKeyChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		privateKeyChooserLbl.setForeground(new Color(108, 206, 203));
		privateKeyChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);

		// btnPnl.add(fileChooser);
		btnPnl.add(fileChooserBtn);
		btnPnl.add(fileChooserLbl);
		// btnPnl.add(publicKeyChooser);
		btnPnl.add(publicKeyChooserBtn);
		btnPnl.add(publicKeyChooserLbl);
		// btnPnl.add(privateKeyChooser);
		btnPnl.add(privateKeyChooserBtn);
		btnPnl.add(privateKeyChooserLbl);

		okBtnPnl.add(okBtn);

		Handler handler = new Handler();

		fileChooserBtn.addActionListener(handler);
		publicKeyChooserBtn.addActionListener(handler);
		privateKeyChooserBtn.addActionListener(handler);
		okBtn.addActionListener(handler);
	}

	private class Handler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == fileChooserBtn) {
				fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog((Component) e.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						fileName = file.toString();
						fileChooserLbl.setText(file.getName());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Problem accessing file" + file.getAbsolutePath(), "File error", JOptionPane.ERROR_MESSAGE);
						//System.out.println("problem accessing file" + file.getAbsolutePath());
					}
				}
			}

			if (e.getSource() == publicKeyChooserBtn) {
				fileChooser = new JFileChooser(System.getProperty("user.home") + "/documents/Security App Files/Keys");
				int returnVal = fileChooser.showOpenDialog((Component) e.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						publicKeyName = file.toString();
						publicKeyChooserLbl.setText(file.getName());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Problem accessing public key file" + file.getAbsolutePath(), "File error", JOptionPane.ERROR_MESSAGE);
						//System.out.println("problem accessing file" + file.getAbsolutePath());
					}
				}
			}

			if (e.getSource() == privateKeyChooserBtn) {
				fileChooser = new JFileChooser(System.getProperty("user.home") + "/documents/Security App Files/Keys");
				int returnVal = fileChooser.showOpenDialog((Component) e.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						privateKeyName = file.toString();
						privateKeyChooserLbl.setText(file.getName());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Problem accessing private key file" + file.getAbsolutePath(), "File error", JOptionPane.ERROR_MESSAGE);
						//System.out.println("problem accessing file" + file.getAbsolutePath());
					}
				}
			}

			if (e.getSource() == okBtn) {
				if (fileName == null || publicKeyName == null || privateKeyName == null) {
					//System.out.println("Add all files");
					JOptionPane.showMessageDialog(null, "Add all needed files", "File error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						Encrypt encrypt  = new Encrypt(fileName);
						errorTitle = "Reading keys error";
						errorMsg = "Failed to read public and private keys, make sure you have the right key files";
						encrypt.readKeys(publicKeyName, privateKeyName);
						
						errorTitle = "Generating AES key error";
						errorMsg = "Failed to generate an AES key";
						encrypt.generateAES();
						
						errorTitle = "Encrypting file error";
						errorMsg = "Failed to encrypt file";
						encrypt.encryptFile();
						
						errorTitle = "Encrypting AES error";
						errorMsg = "Failed to encrypt AES key";
						encrypt.encryptAES();
						
						errorTitle = "Encrypting hash error";
						errorMsg = "Failed to encrypt hash";
						encrypt.encryptHash(encrypt.generateHash(fileName), privateKeyName);

						JOptionPane.showMessageDialog(null, "Encrypting successful", "Encrypt message", JOptionPane.PLAIN_MESSAGE);
						frame.dispose();
						new BeginFrame();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, errorMsg, errorTitle, JOptionPane.ERROR_MESSAGE);
						//e1.printStackTrace();
					}
				}
			}
		}
	}
}