package AS;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import AS.UI.AP;

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
        	ui.setText_receive("reciever："+rmessage);
        	ui.setText_receive("socket："+s);
        	ui.setText_receive("-------开始处理包-------");
        	DataStruct.Package p = AS.packAnalyse(rmessage);
        	ui.setText_receive("receivePackge："+p.toString());
        	System.out.println("-------处理完成-------");
        	
        	if(p.packageOutput().equals("")) {
        		ui.setText_receive("-------client发送的包有误，请重新发送！-------");
        		System.err.println("client发送的包有误，请重新发送！！");
        	}
        	if(p.getHead().getExistLogin().equals("1")) {
				if(p.getHead().getExistSessionKey().equals("1"))//注册
				{
		        	ui.setText_send("-------注册-------");
	        		try {
						smessage = AS.signin(p);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				else {//登录
		        	ui.setText_send("-------登录-------");
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
            	ui.setText_send("sendPacksge："+p);
        		smessage = AS.packageToBinary(p);//打包并发送
        	}
        	else {
        		System.err.println("client发送的包有误，请查看！！");
        		//System.exit(0);
        	}
        	ui.setText_send("socket："+s);

        	System.out.println("对应得socket"+s);
        	ui.setText_send("-------开始发送-------");
        	System.out.println("------开始发送--------");
        	ui.setText_send("send："+smessage);
        	System.out.println("发送给Client的包："+smessage);
        	
        	try {
				AS.send(s,smessage);

	        ui.setText_send("-------发送完成-------");
        	System.out.println("-------发送完成-------");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			public static  Runnable listener(int port,AP ui)throws IOException, InterruptedException{

				 ServerSocket server = null;
				 Socket sclast = null;
				 Socket sc =null;
			     ui.setText_receive("-------开始监听数据包-------");
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
