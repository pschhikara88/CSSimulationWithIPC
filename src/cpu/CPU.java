package cpu;

	import java.io.BufferedReader;
import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.OutputStream;
	import java.io.OutputStreamWriter;
	import java.io.PrintWriter;
	import java.util.Arrays;
	import java.util.Random;

	//import edu.ut.dallas.os.CPU;
import cpu.Memory;

	public class CPU {
		
		static{
			
		}
		
		{
			
		}
		
		private int pc = 0;
		private int sp = 1000;
		private int ir = 0;
		private int ac = 0;
		private int x = 0;
		private int y = 0;
		private int[] instrNeedData = {1, 2, 3, 4, 5, 7, 9, 20, 21, 22,23};
	    private int timer;
	    private int checkTimer;
	    private boolean timerFlag;
	    private boolean timerInterupt;
	    private boolean userMode=true;
	    InputStream memIs;
  	  	OutputStream memOs;
  	  	PrintWriter memPw;
  	  	BufferedReader memBr;
	    
	    
		public void defineInstruction(int instr, int data){
			switch (instr){
			   case 1:
			   ac = data;
			   pc++;
			   break;
			   
		       case 2:
		      //ac = data;
		       { 
		    	  checkAddress(data);
		    	   try {
		    		   memPw.println(data);
		    	   memPw.flush();
		    	  // System.out.println("after PC : "+pc);
					ac = Integer.parseInt(memBr.readLine());
					pc++;
					// System.out.println("after Ac : "+ac);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       break;
		       }
		       
		       case 4:
		    	   checkAddress(x + data);
		    	   memPw.println(x + data);
		    	   memPw.flush();
		    	   try {
		    	   ac = Integer.parseInt(memBr.readLine());
		    	   pc++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       break;
			
		       case 5:
		    	   checkAddress(y + data);
		    	   memPw.println(y + data);
		    	   memPw.flush();
		    	   try {
		    	   ac = Integer.parseInt(memBr.readLine());
		    	   pc++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       break;
		       
		       case 6:
		    	   checkAddress(sp + data);
		    	   memPw.println(sp + data);
		    	   memPw.flush();
		    	   try {
		    	   ac = Integer.parseInt(memBr.readLine());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       break;
			   
		      case 7:
		    	  checkAddress(data);
		       memPw.println(data+" "+ac);
		       memPw.flush();
		       pc++;
			   break;
			   
			   case 8:
					Random r = new Random();
					ac = r.nextInt(100) + 1;
					System.out.println("random generated integer is " + ac);
					break;
			   case 9:
					if (data == 1){
						System.out.print(ac);
					} else if (data ==2){
						System.out.print((char)ac);
					}
					 pc++;
					break;
			   case 10:
					ac = ac + x;
					break;
			   case 11:
					ac = ac + y;
					break;
				case 12:
					ac = ac - x;
					break;
				case 13:
					ac = ac - y;
					break;
				case 14:
					x = ac;
					break;
				case 15:
					ac = x;
					break;
				case 16:
					y = ac;
					break;
				case 17:
					ac = y;
					break;
				case 18:
					sp = ac;
					break;
				case 19:
					ac = sp;
					break;
				case 20:
					checkAddress(data);
				//ir = data;
				pc = data;
				break; 
					 //Jump addr
				case 21:
					checkAddress(data);
				if (ac == 0) {
					//ir = data;
					pc = data;				
				}
				else
					pc++;
				break;
				
					//JumpIfEqual addr   Jump to the address only if the value in the AC is zero
				case 22:
					checkAddress(data);
			    if (ac != 0) {
			    	//ir = data;
			    	pc = data;				
				}
			    else
			    	pc++;
			    break;
					 //JumpIfNotEqual addr  Jump to the address only if the value in the AC is not zero
				case 23:
					checkAddress(data);
					sp--;
				memPw.println(sp+" "+pc);
				memPw.flush();
			
				pc = data;
				//放入sp的地方
				
				break;
					// Call addr  Push return address onto stack, jump to the address
				case 24:
					
					memPw.println(sp);
			    	memPw.flush();
			    	try {
			    		pc = Integer.parseInt(memBr.readLine());
			    		sp++;
			    	} catch (Exception e) {
					e.printStackTrace();
			    	}
			    	break;
					//ret Pop return address from the stack, jump to the address
				case 25:
					x++;
					break;
				case 26:
					x--;
					break;
				case 27:
					sp--;
					memPw.println(sp+" "+ac);
					memPw.flush();
					
					break;
				case 28:
					
					memPw.println(sp);
			    	memPw.flush();
			    	try {
			    		ac = Integer.parseInt(memBr.readLine());
			    		sp++;
			    	} catch (Exception e) {
					e.printStackTrace();
			    	}
			    	break;
				case 29:
					userMode=false;
					if(!timerInterupt)
					{
						pc++;
					}
					memPw.println(1999+" "+sp);
					memPw.flush();
					memPw.println(1998+" "+pc);
					memPw.flush();
					sp=1998;
					if(timerInterupt)
					{
						//System.out.println("timer mode ");
						pc = 1000;
						
						
						//checkTimer = timer + pc+1;
					}
					else
					{
						//System.out.println("system mode ");
						pc=1500;
						//checkTimer = timer + pc+1;
					}  
					break;
				case 30:
					userMode=true;
					try {
						//System.out.println("exit mode ");
					memPw.println(sp);
			    	memPw.flush();
			    	pc = Integer.parseInt(memBr.readLine());
			    	//checkTimer = timer+pc+1;
			    	sp++;
					memPw.println(sp);
			    	memPw.flush();
			    	sp = Integer.parseInt(memBr.readLine());
					}
					 catch (Exception e) {
							e.printStackTrace();
					 }
			    	
				break;
				case 50:
					System.exit(-1);

			}
		}
		
		public void executeProgram(String inputFile){
	        try {

	        	String memoryClassName = Memory.class.getName();
	      	  	String memoryCommand = "java -cp ./bin " + memoryClassName + " " + inputFile;
	      	  	Process memoryProcess = Runtime.getRuntime().exec(memoryCommand);
	      	  	memIs = memoryProcess.getInputStream();
	      	  	memOs = memoryProcess.getOutputStream();
	      	  	memPw = new PrintWriter(new OutputStreamWriter(memOs));
	      	  	memBr = new BufferedReader(new InputStreamReader(memIs));

	      	  	String memResp=null;
	      	  	int data = 0;
	      	  	int instructionCount=0;
	      	  	//int addTimer=timer;
	            while (true){
	            	//Thread.sleep(2000);
	            	// System.out.println("Start Pc : "+pc);
	            	if(timerFlag)
	            	{
	            		if(instructionCount == (checkTimer))
	            		{
	            		if( pc<1000)
		          	  	{
	            			ir=29;
		            		timerInterupt=true;
		            		
		            		defineInstruction(ir, data);
		            		timerInterupt=false;
		            		checkTimer =checkTimer+timer;
		          	  	}
	            		//defineInstruction(ir, data);
	            		
	            		
	            		}
	            		else
	            		{
	            			memPw.flush();
		            		memPw.println(pc);
		            		memPw.flush();
		          	  		memResp = memBr.readLine();
		    
		          	  	if ("Memory started".equals(memResp)){
		          	  		System.out.println("Memory Started succesfully");
		          	  		pc++;
		          	  	} else {
		          	  	if( pc<1000)
		          	  	{
		          	  		instructionCount = instructionCount+1;
		          	  	}
		          	  	
		          	  		//System.out.println("memResp : "+memResp);
		    	            ir = Integer.parseInt(memResp);
		    	     		if (!isDataNeeded(ir)){
		    	     			//defineInstruction(ir, data);
		    	     			if(ir!=29)
		    	     			{
		    	     				defineInstruction(ir, data);
		    	     				if(ir!=30 )
			    	     			{
			    	     				pc++;
			    	     			}
		    	     			}
		    	     			else
		    	     			{
		    	     				if(ir==29 && pc<1000)
		    	     				{
		    	     					defineInstruction(ir, data);
		    	     				}
		    	     			}
		    	     			
		          	  		} else {
		          	  			pc++;
		          	  			memPw.println(pc); 
		          	  			memPw.flush();
		          	  		 // System.out.println(" Pc : "+pc);
		          	  			data = Integer.parseInt(memBr.readLine());
		          	  			defineInstruction(ir, data);
		          	  		
		          	  		}
		    	     		
		    	     		
			            }
	            		}
	            	}
	            	else
	            	{
	            		memPw.flush();
	            		memPw.println(pc);
	            		memPw.flush();
	          	  		memResp = memBr.readLine();
	    
	          	  	if ("Memory started".equals(memResp)){
	          	  		System.out.println("Memory Started succesfully");
	          	  		pc++;
	          	  	} else {
	          	  		//System.out.println("memResp : "+memResp);
	    	            ir = Integer.parseInt(memResp);
	    	     		if (!isDataNeeded(ir)){
	    	     			//defineInstruction(ir, data);
	    	     			if(ir!=29)
	    	     			{
	    	     				defineInstruction(ir, data);
	    	     				if(ir!=30 )
		    	     			{
		    	     				pc++;
		    	     			}
	    	     			}
	    	     			else
	    	     			{
	    	     				if(ir==29 && pc<1000)
	    	     				{
	    	     					defineInstruction(ir, data);
	    	     					
	    	     				}
	    	     			}
	    	     			/*if(ir!=30)
	    	     			{
	    	     				pc++;
	    	     			}*/
	          	  		} else {
	          	  			pc++;
	          	  			memPw.println(pc); 
	          	  			memPw.flush();
	          	  		 // System.out.println(" Pc : "+pc);
	          	  			data = Integer.parseInt(memBr.readLine());
	          	  			defineInstruction(ir, data);
	          	  		
	          	  		}
	    	     		
		            }
	            	}
	          	  /*	if( pc<1000)
	          	  	{
	          	  		instructionCount = instructionCount+1;
	          	  	}*/
	            }    	      
	        }catch (Exception e) {
	        	e.printStackTrace();
	          System.out.println(e);
	       } 
	        finally
	        {
	        	try {
					memIs.close();
					memOs.close();
		      	  	memPw.close();
		      	  	memBr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      	  	
	        }
		}
		
		public boolean isDataNeeded(int instruction){
			boolean result = false;
			for (int i = 0; i< instrNeedData.length; i++){
				if (instruction == instrNeedData[i]){
					result = true;
				} 
			}
			return result;
		}
		
		
	    public static void main (String[] args){
	    	if (args.length <1 ){
	    		System.out.println("Missing input file");
	    	}
	    	CPU cpu = new CPU();
	    	if(args.length>=2)
	    	{
	    		cpu.timer = Integer.valueOf(args[1]);
	    		cpu.checkTimer = Integer.valueOf(args[1]);
	    		cpu.timerFlag=true;
	    	}
	    	
	    	cpu.executeProgram(args[0]);
	    }
	    
	    public  void checkAddress(int Address)
	    {
	    	if(userMode && (Address>=1000))
        	{
        		System.out.println("Invalid memory access( Address =  "+ Address + ", SP = "+ sp +" )");
        		System.exit(-1);
        	}
	    }
	}


