package AS;


	import java.io.IOException;
	import java.net.ServerSocket;
	import java.net.Socket;
import java.sql.SQLException;

import AS.UI.AP;
import DataStruct.Package;

public class Receiver extends Thread{

		private Socket socket;
		String rmessage;
		String smessage;
		static AP ui;
		public Receiver() {
		}
		public Receiver(AP ui) {
			this.ui=ui;
		}
		public Receiver(Socket server,AP ui) {
			this.socket=server;
			this.ui=ui;
		}
		public void run() {
			Socket s=null;      
        	s=socket;
        	try {
				rmessage=AS.receive(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	//拆包调用
        	ui.setText_receive("接收的包："+rmessage);
        	System.out.println("-------开始处理包-------");
        	DataStruct.Package p = AS.packAnalyse(rmessage);
        	if(p == null) {
        		System.err.println("client发送的包有误，请重新发送！！");
        	}
        	if(p.getHead().getExistLogin().equals("1")) {
				if(p.getHead().getExistSessionKey().equals("1"))//注册
				{
	        		try {
						smessage = AS.signin(p);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				else {//登录
	        		try {
						smessage = AS.login(p);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
        	}
        	else if(AS.verifyPackage(p)) {
        		DataStruct.Ticket TicketTgs = AS.generateTicketTGS(p,s.getInetAddress());
        		p = AS.packData(p.getHead().getSourceID(),p.getRequestID(),TicketTgs);
        		smessage = AS.packageToBinary(p);//打包并发送
        	}
        	else {
        		System.err.println("client发送的包有误，请查看！！");
        		//System.exit(0);
        	}

        	System.out.println("对应得socket"+s);
        		System.out.println("------开始发送--------");
        	System.out.println("发送给Client的包："+smessage);
        	
        	try {
				AS.send(s,smessage);
      
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
					
							Thread thread =new Receiver(sc,ui);
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
