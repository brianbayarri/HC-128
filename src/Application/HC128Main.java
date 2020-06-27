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
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JSeparator;

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
	private final JSeparator separator = new JSeparator();

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
	 * @throws IOException 
	 */
	public HC128Main() throws IOException {
		setResizable(false);
		setTitle("Algoritmo HC-128");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 526, 538);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txt_Key = new JTextField();
		txt_Key.setBounds(96, 74, 262, 20);
		contentPane.add(txt_Key);
		txt_Key.setColumns(10);
		
		Button btn_GenerateKey = new Button("Generar");
		btn_GenerateKey.setBackground(new Color(0, 128, 128));
		btn_GenerateKey.setForeground(new Color(255, 255, 255));
		btn_GenerateKey.setFont(new Font("Dialog", Font.BOLD, 12));
		btn_GenerateKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_Key.setText(DataGenerator.generateData());
			}
		});
		btn_GenerateKey.setBounds(377, 71, 118, 23);
		contentPane.add(btn_GenerateKey);
		
		JLabel lbl_Key = new JLabel("Key:");
		lbl_Key.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_Key.setBounds(26, 77, 75, 14);
		contentPane.add(lbl_Key);
		
		JLabel lbl_iv = new JLabel("IV:");
		lbl_iv.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_iv.setBounds(26, 117, 60, 14);
		contentPane.add(lbl_iv);
		
		txt_iv = new JTextField();
		txt_iv.setBounds(96, 114, 262, 20);
		contentPane.add(txt_iv);
		txt_iv.setColumns(10);
		
		Button btn_GenerateIv = new Button("Generar");
		btn_GenerateIv.setBackground(new Color(0, 128, 128));
		btn_GenerateIv.setFont(new Font("Dialog", Font.BOLD, 12));
		btn_GenerateIv.setForeground(new Color(255, 255, 255));
		btn_GenerateIv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_iv.setText(DataGenerator.generateData());
			}
		});
		btn_GenerateIv.setBounds(377, 111, 118, 23);
		contentPane.add(btn_GenerateIv);
		
		JLabel lbl_Image = new JLabel("Imagen:");
		lbl_Image.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_Image.setBounds(26, 156, 60, 14);
		contentPane.add(lbl_Image);
		
		txt_Image = new JTextField();
		txt_Image.setBounds(96, 153, 262, 20);
		contentPane.add(txt_Image);
		txt_Image.setColumns(10);
		
		JLabel lbl_OriginalImage = new JLabel("");
		lbl_OriginalImage.setBounds(56, 306, 174, 172);
		contentPane.add(lbl_OriginalImage);

		JLabel lbl_UnlamImage = new JLabel("");
		lbl_UnlamImage.setBounds(26, 11, 43, 42);
		File unlamIcon = new File ("unlam.png");
		BufferedImage unlamSelected = ImageIO.read(unlamIcon);
		ImageIcon unlamLabel = new ImageIcon(unlamSelected.getScaledInstance(lbl_UnlamImage.getWidth(), lbl_UnlamImage.getHeight(), Image.SCALE_DEFAULT));
		lbl_UnlamImage.setIcon(unlamLabel);
		contentPane.add(lbl_UnlamImage);
		
		JLabel lbl_Original = new JLabel("Imagen original");
		lbl_Original.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_Original.setBounds(85, 270, 112, 14);
		contentPane.add(lbl_Original);
		
		JLabel lbl_EncryptedImage = new JLabel("");
		lbl_EncryptedImage.setBounds(282, 295, 174, 172);
		contentPane.add(lbl_EncryptedImage);
		
		JLabel lbl_Encrypted = new JLabel("Imagen encriptada");
		lbl_Encrypted.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_Encrypted.setBounds(319, 270, 118, 14);
		contentPane.add(lbl_Encrypted);
		
		Button btn_SelectImage = new Button("Seleccionar");
		btn_SelectImage.setBackground(new Color(0, 128, 128));
		btn_SelectImage.setForeground(new Color(255, 255, 255));
		btn_SelectImage.setFont(new Font("Dialog", Font.BOLD, 12));
		btn_SelectImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser();
				int option = jf.showOpenDialog(txt_Image);
				if (option == JFileChooser.APPROVE_OPTION) 
				{
					imageSelectedFile = jf.getSelectedFile();
					if (!imageSelectedFile.getName().endsWith(".bmp"))
					{
						JOptionPane.showMessageDialog(null, "La extension de la imagen debe ser \".bmp\"", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
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
		btn_SelectImage.setBounds(377, 150, 118, 23);
		contentPane.add(btn_SelectImage);
		
		Button btn_Encrypt = new Button("Encriptar / Desencriptar");
		btn_Encrypt.setFont(new Font("Dialog", Font.BOLD, 12));
		btn_Encrypt.setForeground( new Color(255, 255, 255) );
		btn_Encrypt.setBackground(new Color(0, 128, 128));
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
		btn_Encrypt.setBounds(130, 191, 180, 23);
		contentPane.add(btn_Encrypt);
		separator.setBounds(0, 239, 520, 31);
		contentPane.add(separator);
		
		JLabel lblCriptografa = new JLabel(" Criptograf\u00EDa");
		lblCriptografa.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCriptografa.setBounds(79, 11, 106, 20);
		contentPane.add(lblCriptografa);
		
		JLabel lblAlgoritmoHc = new JLabel(" Algoritmo HC-128");
		lblAlgoritmoHc.setBounds(79, 30, 134, 14);
		contentPane.add(lblAlgoritmoHc);
	}
}
