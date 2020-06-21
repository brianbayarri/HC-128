package Entities;

import java.io.File;
import java.io.IOException;
import Util.FileManager;

public class CipherManager {
	
	private HC128 cipher;
			
	public void initializeData(String key, String iv)
	{
		cipher = new HC128(key.getBytes(), iv.getBytes());
		cipher.initialization();
	}
	
	public File encrypt(File imageFile) throws IOException
	{
		byte[] bytesArchivo = FileManager.fileToByte(imageFile);
		byte bytesHeader[] = new byte[FileManager.HEADER_SIZE];
		byte[] bytesBody = new byte[bytesArchivo.length - FileManager.HEADER_SIZE];
		
		for (int i = 0; i < bytesHeader.length; i++) 
		{
			bytesHeader[i] = bytesArchivo[i];
		}
		
		for (int i = 0, j = FileManager.HEADER_SIZE; i < bytesBody.length; i++, j++)
		{
			bytesBody[i] = bytesArchivo[j];
		}
		
		byte[] encryptedBody = cipher.encryption(bytesBody);
		
		File encryptedFile = new File("Imagenes\\temp.bmp");
		FileManager.byteToFile(bytesHeader, encryptedBody, encryptedFile);
		return encryptedFile;
	}

}
