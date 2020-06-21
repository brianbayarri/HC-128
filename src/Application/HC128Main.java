package Application;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Entities.CipherManager;
import Util.DataGenerator;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class HC128Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_Key;
	private JTextField txt_iv;
	private JTextField txt_Image;
	private File imageSelectedFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HC128Main frame = new HC128Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HC128Main() {
		setResizable(false);
		setTitle("Algoritmo HC-128");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 526, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txt_Key = new JTextField();
		txt_Key.setBounds(118, 35, 180, 20);
		contentPane.add(txt_Key);
		txt_Key.setColumns(10);
		
		JButton btn_GenerateKey = new JButton("Generar");
		btn_GenerateKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_Key.setText(DataGenerator.generateData());
			}
		});
		btn_GenerateKey.setBounds(308, 34, 118, 23);
		contentPane.add(btn_GenerateKey);
		
		JLabel lbl_Key = new JLabel("Key:");
		lbl_Key.setBounds(62, 38, 61, 14);
		contentPane.add(lbl_Key);
		
		JLabel lbl_iv = new JLabel("IV:");
		lbl_iv.setBounds(62, 69, 46, 14);
		contentPane.add(lbl_iv);
		
		txt_iv = new JTextField();
		txt_iv.setBounds(118, 66, 180, 20);
		contentPane.add(txt_iv);
		txt_iv.setColumns(10);
		
		JButton btn_GenerateIv = new JButton("Generar");
		btn_GenerateIv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_iv.setText(DataGenerator.generateData());
			}
		});
		btn_GenerateIv.setBounds(308, 68, 118, 23);
		contentPane.add(btn_GenerateIv);
		
		JLabel lbl_Image = new JLabel("Imagen:");
		lbl_Image.setBounds(62, 108, 46, 14);
		contentPane.add(lbl_Image);
		
		txt_Image = new JTextField();
		txt_Image.setBounds(118, 105, 180, 20);
		contentPane.add(txt_Image);
		txt_Image.setColumns(10);
		
		JLabel lbl_OriginalImage = new JLabel("");
		lbl_OriginalImage.setBounds(62, 199, 174, 172);
		contentPane.add(lbl_OriginalImage);
		
		JLabel lbl_Original = new JLabel("Imagen original");
		lbl_Original.setBounds(102, 174, 112, 14);
		contentPane.add(lbl_Original);
		
		JLabel lbl_EncryptedImage = new JLabel("");
		lbl_EncryptedImage.setBounds(304, 199, 174, 172);
		contentPane.add(lbl_EncryptedImage);
		
		JLabel lbl_Encrypted = new JLabel("Imagen encriptada");
		lbl_Encrypted.setBounds(308, 174, 118, 14);
		contentPane.add(lbl_Encrypted);
		
		JButton btn_SelectImage = new JButton("Seleccionar");
		btn_SelectImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser();
				int option = jf.showOpenDialog(txt_Image);
				if (option == JFileChooser.APPROVE_OPTION) 
				{
					imageSelectedFile = jf.getSelectedFile();
					txt_Image.setText(imageSelectedFile.getName());
					BufferedImage imageSelected;
					try 
					{
						imageSelected = ImageIO.read(imageSelectedFile);
						ImageIcon imageLabel = new ImageIcon(imageSelected.getScaledInstance(lbl_OriginalImage.getWidth(), lbl_OriginalImage.getHeight(), Image.SCALE_DEFAULT));
						lbl_OriginalImage.setIcon(imageLabel);
					} 
					catch (Exception e1) 
					{
						JOptionPane.showMessageDialog(null, "Error cargando la imagen, por favor intente nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
						txt_Image.setText(null);
					}
					
				}
				
			}
		});
		btn_SelectImage.setBounds(308, 104, 118, 23);
		contentPane.add(btn_SelectImage);
		
		JButton btn_Encrypt = new JButton("Encriptar");
		btn_Encrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txt_Key.getText().length() == 16) 
				{
					if(txt_iv.getText().length() == 16) 
					{
						if (imageSelectedFile != null) 
						{
							try 
							{
								CipherManager cm = new CipherManager();
								cm.initializeData(txt_Key.getText(), txt_iv.getText());
								File encryptedFile = cm.encrypt(imageSelectedFile);
								BufferedImage imageEncrypted;
								imageEncrypted = ImageIO.read(encryptedFile);
								ImageIcon imageLabel = new ImageIcon(imageEncrypted.getScaledInstance(lbl_OriginalImage.getWidth(), lbl_OriginalImage.getHeight(), Image.SCALE_DEFAULT));
								lbl_EncryptedImage.setIcon(imageLabel);
							}
							catch(Exception ex) 
							{
								JOptionPane.showMessageDialog(null, "Error encriptando la imagen, por favor intentelo de nuevo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						else 
						{
							JOptionPane.showMessageDialog(null, "Por favor seleccione una imagen", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "El tamaño del iv debe ser de 16 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "El tamaño de la key debe ser de 16 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
				}		
			}
		});
		btn_Encrypt.setBounds(165, 136, 89, 23);
		contentPane.add(btn_Encrypt);
	}
}
