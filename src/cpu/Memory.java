package cpu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Memory {
	
	static int[] mem = new int[2000];
	
	public void readInputFile(String inputFile) {	
		BufferedReader bReader = null;
		FileReader fReader = null;
		String inputLine = null;
		int i=0;
		try {
			fReader = new FileReader(new File(inputFile));
			bReader = new BufferedReader(fReader);
			
			while ((inputLine = bReader.readLine()) != null) {
				int index;
				if (inputLine.trim().length() > 0){
					if ((index = inputLine.indexOf(" ") ) >= 0 ){
						if (index > 0 && inputLine.substring(0, index).trim().length()>0){
							mem[i] = Integer.parseInt(inputLine.substring(0, index).trim());
							i++;
						}
					} else if (inputLine.startsWith(".")) {
						i = Integer.parseInt(inputLine.substring(1).trim());
					} else {
						mem[i] = Integer.parseInt(inputLine);
						i++;
					}
				}

			}
			//System.out.println(Arrays.toString(mem));
			fReader.close();
			bReader.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			System.out.println("File error in read input file " + fnfe.getMessage());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("IO error in read input file " + ioe.getMessage());
		}
	}
	
	public void write(int address, int data){
		mem[address] = data;
	}
	
	public int read(int address){
		return mem[address];
	}
	
	public static void main(String[] args) {
		Memory memory = new Memory();
		memory.readInputFile(args[0]);
		BufferedReader br=null;
		try {
	            br = new BufferedReader(new InputStreamReader(System.in));
	           String line = null;
        	 //  System.out.println("Memory started");
	           while ((line = br.readLine()) != null) {
	        	   
	        	   if(line .contains(" ")){
	        		   String[] input = line.split(" ");
	        		   memory.write(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
	        		   } /*
	        	   if (line .contains("*")) {
	        		   String[] output = line.split(" ");
				}*/else {
	        	   int address = Integer.parseInt(line);
	        	   System.out.println(memory.read(address));
	        	System.out.flush();
	           }
	           }
	           }catch (IOException e) {
	        	   e.printStackTrace();
			System.err.println("IOException:  " + e);
		}
	           finally
	           {
	        	   try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
	}
}

