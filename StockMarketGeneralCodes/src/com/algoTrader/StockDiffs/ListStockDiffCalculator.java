package com.algoTrader.StockDiffs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class ListStockDiffCalculator 
{

	static FileInputStream list1Fs;
	static FileInputStream list2Fs;
	static Scanner scList1;
	static Scanner scList2;
	
	public static void main(String ... args) throws Exception
	{
		list1Fs = new FileInputStream(args[0]);
		list2Fs = new FileInputStream(args[1]);
		
		scList1 = new Scanner(list1Fs);
		scList2 = new Scanner(list2Fs);
		
		Set<String> set1 = new HashSet<String>();
		Set<String> set2 = new HashSet<String>();
		
		while(scList1.hasNext())
		{
			String s = scList1.nextLine().trim();
			if(set1.contains(s)) System.err.println(s);
			set1.add(s);
		}
		
		while(scList2.hasNext())
		{
			String s = scList2.nextLine().trim();
			if(set2.contains(s)) System.err.println(s);
			set2.add(s);
		}
		
		Set<String> tempSet = new TreeSet<String>();
		tempSet.addAll(set1);
		tempSet.retainAll(set2);	
		System.out.println("Intersection Size - "+tempSet.size()+" Intersection - "+tempSet);
		
		tempSet.clear();
		tempSet.addAll(set1);
		tempSet.addAll(set2);
		System.out.println("Union Size - "+tempSet.size()+" Union - "+tempSet);
		
		tempSet.clear();
		tempSet.addAll(set1);
		tempSet.removeAll(set2);
		System.out.println("List1 - List2 Size - "+tempSet.size()+" List - "+tempSet);
		
		tempSet.clear();
		tempSet.addAll(set2);
		tempSet.removeAll(set1);
		System.out.println("List2 - List1 Size - "+tempSet.size()+" List - "+tempSet);
		
		
		
	}

}
