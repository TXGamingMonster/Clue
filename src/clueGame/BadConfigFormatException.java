package clueGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception{
	String fileName = "log.txt";
	public BadConfigFormatException() {
		System.out.println("BadConfigFormatException thrown, correct the format of your configuration file to proceed.");
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
		    out.println("BadConfigFormatException thrown, correct the format of your configuration file to proceed.");
		    out.close();
		} catch (IOException e) {
		    System.out.println(e.getMessage());
		}
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
