package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
	
	public static Integer HEADER_SIZE = 54;
	
	public static byte[] fileToByte(File imageFile) throws IOException{
		FileInputStream fis = new FileInputStream(imageFile);
		byte[] bytesOfImage = new byte[(int) imageFile.length()];
		fis.read(bytesOfImage);
		fis.close();
		return bytesOfImage;
	}
	
	public static void byteToFile(byte[] bytesHeader, byte[] bytesBody, File pathFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(pathFile);
		fos.write(bytesHeader);
		fos.write(bytesBody);
		fos.close();	
	}

}
