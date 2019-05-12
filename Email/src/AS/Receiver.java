package AS;


	import java.io.IOException;
	import java.net.ServerSocket;
	import java.net.Socket;

public class Receiver extends Thread{

		private Socket socket;
		String rmessage;
		String smessage;
		public Receiver(Socket server) {
			this.socket=server;
		}
		public void run() {
			  Socket s=null;
		      System.out.println("receive1");		       
		        	s=socket;
		        	try {
						rmessage=AS.receive(s);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	//拆包调用
		        	System.out.println(s);
		        	smessage="aaaa";
		        	System.out.println(smessage);
		      
		        	try {
						AS.send(s,smessage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		
		
			public static  Runnable listener(int port)throws IOException, InterruptedException{

				 ServerSocket server = null;
				 Socket sclast = null;
				 Socket sc =null;
 				 System.out.println("listen");
				 try {	
					 server= new ServerSocket(port);
					 while(true) {
						sc= server.accept();
						if(sclast!=sc) {	
						//	System.out.println(server);
							Thread thread =new Receiver(sc);
							thread.start();
				        	try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        	System.out.println(sc.isClosed());
							sclast=sc;
						}
						Thread.sleep(100);
						}
				
				 }catch (IOException e){
			            // TODO Auto-generated catch block
			            
			        }finally{
			            //5.关闭相应的流以及Socket,ServerSocket的对象
			            
			            if(server!=null){
			                try {
			                    server.close();
			                } catch (IOException e) {
			                    // TODO Auto-generated catch block
			                    e.printStackTrace();
			                }
			            }   
			   
			            if(sc!=null){
			                try {
			                    sc.close();
			                } catch (IOException e) {
			                    // TODO Auto-generated catch block
			                    e.printStackTrace();
			                }
			            }   
			            if(sclast!=null){
			                try {
			                    sclast.close();
			                } catch (IOException e) {
			                    // TODO Auto-generated catch block
			                    e.printStackTrace();
			                }
			            }   
			            }
			return null;
			}
			
		}
