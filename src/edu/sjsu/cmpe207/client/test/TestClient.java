package edu.sjsu.cmpe207.client.test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class TestClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String prevDir = "";
		String path = "C:\\Users\\admin\\0009310028\\2";
		File destinationFile = new File(path);
		for (File f : destinationFile.listFiles()) {
			System.out.println(f.getName());
		}
		System.exit(0);
		for (int i = path.length() - 1; i >= 0; i--) {
			char atI = path.charAt(i);
			if (path.charAt(i) == File.separatorChar) {
				prevDir = path.substring(0, i);
				break;
			}
		}
		System.out.println(prevDir);
		System.exit(0);
		System.out.println("100093100282".substring(1, 11));
		char c = '1';
		System.out.println( c - '0');
		File f = new File("C:\\Users\\admin\\Documents\\MyDirectory\\myfile.txt");
		System.out.println(f.exists());
		System.out.println(f.isFile());
		System.out.println(f.isDirectory());
		try {
			FileOutputStream fos = new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] b = new byte[1024];
			b[0] = 'a';
			b[1] = 'b';
			bos.write(b);
			bos.close();
//			String path = "C:\\Users\\admin\\Documents\\MyDirectory\\myfile.txt";
//			BufferedReader br = new BufferedReader(new FileReader(path));
//			String line = "";
//			
//			while ((line = br.readLine()) != null) {
//				System.out.println(line);
//			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
