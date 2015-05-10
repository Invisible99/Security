package be.pxl.app.imageStega;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
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

public class StegaImgMethodDecryptFrame {

	private AppFrame frame;

	private JPanel btnPnl;
	private JPanel okBtnPnl;

	private JButton okBtn;
	private JButton imageChooserBtn;

	private JLabel imageChooserLbl;

	private JFileChooser fileChooser;

	private String imageName;

	public StegaImgMethodDecryptFrame() {

		imageChooserBtn = new JButton("Choose image with message");
		okBtn = new JButton("Decrypt with image steganography");

		imageChooserLbl = new JLabel();

		frame = new AppFrame();

		frame.pnl.setLayout(new BorderLayout());

		btnPnl = new JPanel();
		btnPnl.setLayout(new GridLayout(1, 2, 10, 10));
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

		imageChooserBtn.setBackground(new Color(108, 206, 203));
		imageChooserBtn.setFocusPainted(false);
		imageChooserBtn.setBorderPainted(false);

		okBtn.setBackground(new Color(108, 206, 203));
		okBtn.setFocusPainted(false);
		okBtn.setBorderPainted(false);
		okBtn.setPreferredSize(new Dimension(640, 100));

		imageChooserLbl.setForeground(new Color(108, 206, 203));
		imageChooserLbl.setHorizontalAlignment(SwingConstants.CENTER);

		btnPnl.add(imageChooserBtn);
		btnPnl.add(imageChooserLbl);

		okBtnPnl.add(okBtn);

		Handler handler = new Handler();

		imageChooserBtn.addActionListener(handler);
		okBtn.addActionListener(handler);
	}

	private class Handler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == imageChooserBtn) {
				fileChooser = new JFileChooser(System.getProperty("user.home") + "/documents/Security App Files/Encrypted Steganography Files/");
				FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
				fileChooser.setFileFilter(imageFilter);
				int returnVal = fileChooser.showOpenDialog((Component) e.getSource());
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						imageName = file.toString();
						imageChooserLbl.setText(file.getName());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Problem accessing image file" + file.getAbsolutePath(), "Image error", JOptionPane.ERROR_MESSAGE);
						// System.out.println("problem accessing file" + file.getAbsolutePath());
					}
				}
			}

			if (e.getSource() == okBtn) {
				if (imageName == null) {
					// System.out.println("Add all files");
					JOptionPane.showMessageDialog(null, "Add all needed files", "File error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						Steganography stega = new Steganography();
						if (stega.isImage(imageName)) {
							stega.reveal(imageName);

							JOptionPane.showMessageDialog(null, "Revealing text from image successful", "Decryption message", JOptionPane.PLAIN_MESSAGE);
							frame.dispose();
							new BeginFrame();
						} else {
							JOptionPane.showMessageDialog(null, "File chosen is not an image with extension '.png, .gif or .bmp'", "Image error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Failed to decrypt message from image", "Decryption error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
}