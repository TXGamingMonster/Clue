package clueGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception{
	String fileName = "log.txt";
	public BadConfigFormatException() {
		// TODO Auto-generated constructor stub
	}
	public BadConfigFormatException(String errorMessage) {
		// TODO Auto-generated constructor stub
		super(errorMessage);
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
		    out.println(errorMessage);
		    out.close();
		} catch (IOException e) {
		    System.out.println(e.getMessage());
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
