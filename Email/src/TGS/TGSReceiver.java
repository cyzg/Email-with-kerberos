package TGS;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import TGS.UI.AP;

public class TGSReceiver extends Thread{

		private Socket socket;
		String rmessage;
		String smessage;
		AP ui;
		public TGSReceiver() {
		}
		public TGSReceiver(Socket server,AP ui) {
			this.socket=server;
			this.ui=ui;
		}
		public TGSReceiver(Socket server) {
			this.socket=server;
		}
		public void run() {
			  Socket s=null;       
		        	s=socket;
		        	try {
						rmessage=TGS.receive(s);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	//拆包调用
		        	TGS tgs = new TGS();
		        	ui.setText_receive("-------收到包-------");
		        	ui.setText_receive("socket："+s);
		        	ui.setText_receive("reciever："+rmessage);
		        	ui.setText_receive("-------开始分析包-------");
		        	System.out.println("-------开始分析包-------");
		        	DataStruct.Package p = tgs.packAnalyse(rmessage);
		        	ui.setText_receive("receivePackge："+p.toString());
			        ui.setText_receive("-------分析完成-------     -----"+s.getInetAddress()+"-----");
		        	if(p.packageOutput().equals("")) {
		        		ui.setText_receive("-------client发送的包有误，请重新发送！-------");
		        		System.err.println("client发送的包有误，请重新发送！！");
		        	}
		        	if(tgs.checkIdentity(p)) {
			        	ui.setText_receive("-------认证成功-------     -----"+s.getInetAddress()+"-----");
			        	ui.setText_send("-------认证成功-------     -----"+s.getInetAddress()+"-----");
		        		DataStruct.Ticket TicketTgs = tgs.generateTicketV(p,s.getInetAddress());
		        		DataStruct.Package p2 = tgs.packData(p.getHead().getSourceID(),p.getRequestID(),TicketTgs);
		        		ui.setText_send("socket："+s);
		            	ui.setText_send("sendPacksge："+p);
		        		smessage = tgs.packageToBiarny(p2,p.getTicket().getSessionKey());//打包并发送
		        	}
		        	else {
		        		System.err.println("client发送的包有误，请查看！！");
		        		//System.exit(0);
		        	}
		        	ui.setText_send("-------开始发送-------     -----"+s.getInetAddress()+"-----");
		        	ui.setText_send("send："+smessage);
		        	System.out.println("对应得socket"+s);
		        	System.out.println("------开始发送--------");
		        	System.out.println("发送给Client的包："+smessage);
		        	try {
						TGS.send(s,smessage);
				        ui.setText_send("-------发送完成-------     -----"+s.getInetAddress()+"-----");
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
					
							Thread thread =new TGSReceiver(sc,ui);
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
