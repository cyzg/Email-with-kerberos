package Server;


	import java.io.IOException;
	import java.net.ServerSocket;
	import java.net.Socket;

public class VReceiver extends Thread{

		private Socket socket;
		String rmessage;
		String smessage;
		public VReceiver(Socket server) {
			this.socket=server;
		}
		public void run() {
			  Socket s=null;       
		        	s=socket;
		        	try {
						rmessage=V.receive(s);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	//拆包调用
		        	V v = new V();
		        	System.out.println("-------开始处理包-------");
		        	DataStruct.Package p = v.packAnalyse(rmessage);
		        	if(v.checkIdentity(p)) {
		        		smessage = v.packData(p.getHead().getSourceID(),p.getAuth().getTimeStamp(),p.getTicket().getSessionKey());//打包并发送
		        	}
		        	else {
		        		System.err.println("client发送的包有误，请查看！！");
		        		//System.exit(0);
		        	}

		        	System.out.println("对应得socket"+s);
		        		System.out.println("------开始发送--------");
		        	System.out.println("发送给Client的包："+smessage);
		        	try {
						V.send(s,smessage);
		      
		        	System.out.println("-------发送完成-------");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
			public static  Runnable listener(int port)throws IOException, InterruptedException{

				 ServerSocket server = null;
				 Socket sclast = null;
				 Socket sc =null;
 				 System.out.println("-------开始监听数据包----------");
				 try {	
					 server= new ServerSocket(port);
					 while(true) {
						sc= server.accept();
						if(sclast!=sc) {	
					
							Thread thread =new VReceiver(sc);
							thread.start();
				        	try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
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
