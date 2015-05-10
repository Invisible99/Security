package be.pxl.app.keyMethod;

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

import be.pxl.app.main.AppFrame;
import be.pxl.app.main.BeginFrame;

public class KeyMethodDecryptFrame {

	private AppFrame frame;

	private JPanel btnPnl;
	private JPanel okBtnPnl;

	private JButton okBtn;
	private JButton file1ChooserBtn;
	private JButton file2ChooserBtn;
	private JButton file3ChooserBtn;
	private JButton publicKeyChooserBtn;
	private JButton privateKeyChooserBtn;

	private JLabel file1ChooserLbl;
	private JLabel file2ChooserLbl;
	private JLabel file3ChooserLbl;
	private JLabel publicKeyChooserLbl;
	private JLabel privateKeyChooserLbl;

	private JFileChooser fileChooser;

	private String file1Name;
	private String file2Name;
	private String file3Name;
	private String publicKeyName;
	private String privateKeyName;
	
	//Error Strings
	private String errorMsg;
	private String errorTitle;

	public KeyMethodDecryptFrame() {

		file1ChooserBtn = new JButton("Choose File_1");
		file2ChooserBtn = new JButton("Choose File_2");
		file3ChooserBtn = new JButton("Choose File_3");
		publicKeyChooserBtn = new JButton("Choose public key");
		privateKeyChooserBtn = new JButton("Choose private key");
		okBtn = new JButton("Decrypt");

		file1ChooserLbl = new JLabel();
		file2ChooserLbl = new JLabel();
		file3ChooserLbl = new JLabel();
		publicKeyChooserLbl = new JLabel();
		privateKeyChooserLbl = new JLabel();

		frame = new AppFrame();

		frame.pnl.setLayout(new BorderLayout());

		btnPnl = new JPanel();
		btnPnl.setLayout(new GridLayout(5, 2, 10, 4));
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

		file1ChooserBtn.setBackground(new Color(108, 206, 203));
		file1ChooserBtn.setFocusPainted(false);
		file1ChooserBtn.setBorderPainted(false);

		file2ChooserBtn.setBackground(new Color(108, 206, 203));
		file2ChooserBtn.setFocusPainted(false);
		file2ChooserBtn.setBorderPainted(false);

		file3ChooserBtn.setBackground(new Color(108, 206, 203));
		file3ChooserBtn.setFocusPainted(false);
		file3ChooserBtn.setBorderPainted(false);

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

		file1ChooserLbl.setForeground(new Color(108, 206, 203));
		file1ChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		file2ChooserLbl.setForeground(new Color(108, 206, 203));
		file2ChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		file3ChooserLbl.setForeground(new Color(108, 206, 203));
		file3ChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		publicKeyChooserLbl.setForeground(new Color(108, 206, 203));
		publicKeyChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);
		privateKeyChooserLbl.setForeground(new Color(108, 206, 203));
		privateKeyChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);

		btnPnl.add(file1ChooserBtn);
		btnPnl.add(file1ChooserLbl);
		btnPnl.add(file2ChooserBtn);
		btnPnl.add(file2ChooserLbl);
		btnPnl.add(file3ChooserBtn);
		btnPnl.add(file3ChooserLbl);
		btnPnl.add(publicKeyChooserBtn);
		btnPnl.add(publicKeyChooserLbl);
		btnPnl.add(privateKeyChooserBtn);
		btnPnl.add(privateKeyChooserLbl);

		okBtnPnl.add(okBtn);

		Handler handler = new Handler();

		file1ChooserBtn.addActionListener(handler);
		file2ChooserBtn.addActionListener(handler);
		file3ChooserBtn.addActionListener(handler);
		publicKeyChooserBtn.addActionListener(handler);
		privateKeyChooserBtn.addActionListener(handler);
		okBtn.addActionListener(handler);
	}

	private class Handler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == file1ChooserBtn) {
				fileChooser = new JFileChooser(System.getProperty("user.home") + "/documents/Security App Files/Encrypted Files");
				int returnVal = fileChooser.showOpenDialog((Component) e.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						file1Name = file.toString();
						file1ChooserLbl.setText(file.getName());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Problem accessing file 1" + file.getAbsolutePath(), "File error", JOptionPane.ERROR_MESSAGE);
						//System.out.println("problem accessing file" + file.getAbsolutePath());
					}
				}
			}

			if (e.getSource() == file2ChooserBtn) {
				fileChooser = new JFileChooser(System.getProperty("user.home") + "/documents/Security App Files/Encrypted Files");
				int returnVal = fileChooser.showOpenDialog((Component) e.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						file2Name = file.toString();
						file2ChooserLbl.setText(file.getName());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Problem accessing file 2" + file.getAbsolutePath(), "File error", JOptionPane.ERROR_MESSAGE);
						//System.out.println("problem accessing file" + file.getAbsolutePath());
					}
				}
			}

			if (e.getSource() == file3ChooserBtn) {
				fileChooser = new JFileChooser(System.getProperty("user.home") + "/documents/Security App Files/Encrypted Files");
				int returnVal = fileChooser.showOpenDialog((Component) e.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						file3Name = file.toString();
						file3ChooserLbl.setText(file.getName());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Problem accessing file 3" + file.getAbsolutePath(), "File error", JOptionPane.ERROR_MESSAGE);
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
				if (file1Name == null || file2Name == null || file3Name == null || publicKeyName == null || privateKeyName == null) {					
					//System.out.println("Add all files");
					JOptionPane.showMessageDialog(null, "Add all needed files", "File error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						Decrypt decrypt = new Decrypt(file1Name);
						
						errorTitle = "Reading keys error";
						errorMsg = "Failed to read public and private keys, make sure you have the right key files";
						decrypt.readKeys(publicKeyName, privateKeyName);
						
						errorTitle = "Decrypting AES error";
						errorMsg = "Failed to decrypt AES";
						decrypt.decryptAES(file2Name);
						
						errorTitle = "Decrypting file error";
						errorMsg = "Failed to decrypt file";
						decrypt.decryptFile();
						
						errorTitle = "Generate hash error";
						errorMsg = "Failed to generate hash from decrypted file";
						decrypt.generateHash();
						
						errorTitle = "Decrypt hash error";
						errorMsg = "Failed to decrypt hash";
						decrypt.decryptHash(file3Name, publicKeyName);
						
						errorTitle = "Compare hash error";
						errorMsg = "Failed to compare hashes";
						decrypt.compareHash();
						
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