package implementation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		//BufferedReader br = new BufferedReader(new FileReader(args[1]));

		try {
			// AICI TREBUIE SCHIMBAT in args[1]
			
			Scanner in = new Scanner(new FileReader("tree.in"));
			File file1 = new File("tree.out");
			File file2 = new File("tree2.out");
			File file3 = new File("tree3.out");
			
			// if files don't exist, then create them
			if (!file1.exists()) {
				file1.createNewFile();
			}
			if (!file2.exists()) {
				file2.createNewFile();
			}
			if (!file3.exists()) {
				file3.createNewFile();
			}
			
			FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
			FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
			FileWriter fw3 = new FileWriter(file3.getAbsoluteFile());
			BufferedWriter bw1 = new BufferedWriter(fw1);
			BufferedWriter bw2 = new BufferedWriter(fw2);
			BufferedWriter bw3 = new BufferedWriter(fw3);
			
			String value;
			Tree parseTree = null, computeTree1, computeTree2;
			String[] parts = new String[2];
			HashMap<String,Integer> myVariables = new HashMap<String,Integer>();
			int type;
			String[] words;
			int line=0,column=0;
			boolean ok = true;
			
			while(in.hasNextLine()) {
				//read the value
				value = in.nextLine();
				line++;
				ok = true;
				//split the text for analysis
				parts = value.split("=",2);
				
				if(sub(parts[0],"+-*?>:".toString()) || Character.isDigit(parts[0].charAt(0))) {
					type = 1;
				}
				else type = 2;
				
				
				for(String key : myVariables.keySet()) {
					parts[1] = parts[1].replace(key, myVariables.get(key).toString());
					
				}
			
				//TODO
				// 
				parseTree = new Tree(new Node(value));
				int max = parseTree.maxLevel(parseTree.root);
				for(int i = 0 ; i < max ; i++) {
					parseTree.toStringLvL(parseTree.root, i, 0);
					bw1.write(parseTree.line);
					parseTree.line = new String();
					bw1.newLine();
				}
				
				if(sub(parts[0],"+-*?>:".toString()) || !Character.isAlphabetic(parts[0].charAt(0))) {
					bw2.write("membrul stang nu este o variabila la linia " + line + " coloana 1");
					bw2.newLine();
					bw3.write("error");
					bw3.newLine();
					ok = false;
				}
				
				column = 0;
				if(parts[1].matches(".*[a-zA-Z]+.*") && ok) {
					words= parts[1].split("[^a-zA-Z0-9']");
					column += parts[0].length() + 2;
					for(int i = 1 ; i < words.length ; i++){
						
						if(words[i] != null && Character.isAlphabetic(words[i].charAt(0))) {
						
							column += parts[1].indexOf(words[i]) + 1;
							bw2.write(words[i] + " nedeclarata la linia " + Integer.toString(line) + " coloana " + Integer.toString(column));
							bw2.newLine();
							bw3.write("error");
							bw3.newLine();
							ok = false;
							break;
						}
					}
					
					//continue;
				}
				computeTree1 = new Tree(new Node(parts[0]));
				computeTree2 = new Tree(new Node(parts[1]));
				
				// THIS MUST BE MODIFIED
				//if(isNumeric(parts[1]))
				//	myVariables.put(parts[0], computeTree2.compute(computeTree2.root));
					
				//computeTree2.print(computeTree2.root);
				//System.out.println();
				
				
				if(ok) {
					bw2.write("Ok!");
					bw2.newLine();
					myVariables.put(parts[0], computeTree2.compute(computeTree2.root));
					bw3.write(parts[0] + "=" + myVariables.get(parts[0]).toString());
					bw3.newLine();
					//System.out.println(computeTree2.compute(computeTree2.root));
				}
				
				
					
					//computeTree2.print(computeTree2.root);
				
				
				//computeTree2.print(computeTree2.root);
				
				if(type == 1) {
					for(String key : myVariables.keySet()) {
						parts[0] = parts[0].replace(key, myVariables.get(key).toString());
					}
					//if(parts[1]
					
				}
				//TODO
				

				//System.out.println(parseTree.compute(computeTree2.root));
				//System.out.println(parseTree.maxLevel(parseTree.root));
				
				
			}
			
			in.close();
			bw1.close();
			bw2.close();
			bw3.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}

		
		
	}
	
	/**
	 * 
	 * @param string
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean isNumeric(String string) throws IllegalArgumentException {
	   boolean isnumeric = false;

	   if (string != null && !string.equals(""))
	   {
	      isnumeric = true;
	      char chars[] = string.toCharArray();

	      for(int d = 0; d < chars.length; d++)
	      {
	         isnumeric &= Character.isDigit(chars[d]);

	         if(!isnumeric)
	         break;
	      }
	   }
	   return isnumeric;
	}
	
	public static boolean sub(String string, String substring) {
	    // Keep track of our position in the string.
	    int index = 0;

	    // Iterate through all of the characters in the substring.
	    for (char character : substring.toCharArray()) {
	        // Find the current character starting from the last character we stopped on.
	        index = string.indexOf(character, index);
	        // 
	        if (index >= 0)
	            return true;
	    }

	    // If we reach this point, that means all characters were found, so the result is true.
	    return false;
	}
}
