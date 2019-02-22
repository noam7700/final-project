// in each one of them the connection should be built at the beginning and closed at the end


server_address = 127.0.0.1; // localhost for now

public static class clientSide{
	
	private static String parseBasket(Basket basket){
		data = "basket:'\n'";
		for(product in basket.products_list){
			data += product.getID() + '\n';
		}
		return data;
	}
	
	private static ArrayList<Basket> reproduceBaskets(InputStreamReader din){
		BufferedReader basketDetails = new BufferedReader(new InputStreamReader(din));
		ArrayList<Basket> baskets;
		while(!basketDetails.end()){
			basket = new Basket();
			String line = basketDetails.readline();
			for(String cur_line = basketDetails.readline(); !cur_line.equals(line) && !basketDetails.end(); cur_line = basketDetails.readline()){ 
				basket.addProduct(getProductbyID(cur_line));
			}
			baskets.add(basket);
		}		
		
	}
	
	public static String saveBasket(Basket basket, String username, String password){ // returns success when operates properly
		try{      
			Socket s=new Socket(server_address,6666);  
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
			String basketData = parseBasket(basket);
			/*basketData can be of format:
			basket:
			product_id1
			product_id2
			..
			
			basket:
			product_id1
			product_id2
			..
			..
			*/
			dout.writeUTF("add basket to saved\n"+username+"\n"+password+"\n" + basketData);
			#read server data
			dout.flush();  
			dout.close();  
			s.close();  
			return server_massage;
		
		}
		catch(Exception e){System.out.println(e);
		}
	}
			  
		  
	
	public static ArrayList<Basket> getBaskets(String username, String password){ // return file (string) of the baskets data
		try{      
			Socket s=new Socket(server_address,6666);  
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
			dout.writeUTF("bring saved baskets");
			DataInputStream din = new DataInputStream(s.getInputStream());
			ArrayList<Basket> baskets = reproduceBaskets(din);

			dout.flush();  
			din.flush();  
			dout.close();  
			din.close();  
			s.close();
			return baskets;

		}
		catch(Exception e){System.out.println(e);
		}
	}
	
	public static String register(String username, String password){ // returns success when operates properly
		try{      
			Socket s=new Socket(server_address,6666);  
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
			dout.writeUTF("registeration\n"+username+"\n"+password);  
			DataInputStream din = new DataInputStream(s.getInputStream());
			
			dout.flush();  
			din.flush();  
			dout.close();  
			din.close();  
			s.close();  
			return din;

		}
		catch(Exception e){System.out.println(e);
		}
	}
	
	public static String log_in(String username, String password){ // returns success/fail
		try{      
			Socket s=new Socket(server_address,6666);  
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
			dout.writeUTF("log-in\n"+username+"\n"+password"); 
			DataInputStream din = new DataInputStream(s.getInputStream());
			
			dout.flush();  
			din.flush();  
			dout.close();  
			din.close();  
			s.close();  
			return din;
		}
		catch(Exception e){System.out.println(e);
		}
	}
		

}