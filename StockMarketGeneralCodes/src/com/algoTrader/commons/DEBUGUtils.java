package com.algoTrader.commons;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class DEBUGUtils 
{
	public static void printStream(InputStream is)
	{
		Scanner sc = new Scanner(is);
		while(sc.hasNext())
		{
			System.out.println(sc.nextLine());
		}
	}

	public static void printToFile(String s) throws FileNotFoundException
	{
		PrintWriter pw = new PrintWriter("report.html");
		pw.println(s);
		pw.flush();
		pw.close();
	}

	public static void main(String[] args) 
	{

	}
}
