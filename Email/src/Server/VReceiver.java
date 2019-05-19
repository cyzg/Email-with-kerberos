package Server;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.sql.Statement;

import Server.DBconncet;
import Server.UI.AP;

public class VReceiver extends Thread{

		private Socket socket;
		String rmessage;
		String smessage;
		AP ui;
		public VReceiver() {
		}
		public VReceiver(Socket server,AP ui) {
			this.socket=server;
			this.ui=ui;
		}
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
        	ui.setText_receive("reciever："+rmessage);
        	ui.setText_receive("socket："+s);
        	ui.setText_receive("-------开始处理包-------");
        	System.out.println("-------开始处理包-------");
        	DataStruct.Package p = V.packAnalyse(rmessage);
        	ui.setText_receive("receivePackge："+p.toString());
	        ui.setText_receive("-------分析完成-------     -----"+s.getInetAddress()+"-----");

        	if(p.packageOutput().equals("")) {
        		ui.setText_receive("-------client发送的包有误，请重新发送！-------");
        		System.err.println("client发送的包有误，请重新发送！！");
        	}
	        else if(p.getHead().getExistTS().equals("1") &&p.getHead().getExistRequstID().equals("0")) {
        		//V发给C历史邮件
        		try {
        			String IP=s.getInetAddress().toString().replaceFirst("/", "");
        			int port=s.getPort();
					//Socket sssss = new Socket(s.getInetAddress().toString().replaceFirst("/", ""),6666);
					try {
						v.appsend(p.getID(),IP,port,s);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else if(p.getHead().getExistSessionKey().equals("1")) {
        		//收到的信件
        		DataStruct.APPPackage app=v.apppackAnalylse(p);
        		String mcc = app.getreceiveID()+app.getsendID()+app.getEmail().EmailOutput();
        		
        		System.out.println("发信人为"+mcc.substring(0,4)+"收信人为"+mcc.substring(4,8));
        		System.out.println("V储存的C1C2通信包为"+mcc.substring(8));
        		DBconncet db = new DBconncet();
        		try {
					Statement stat = db.connect().createStatement();
					db.insertData(stat,mcc,app.getHead().getExpend());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		//把mcc加入数据库 mcc=receiveid+sendid+message  4位string 4位string 密文message
        		smessage = v.requestEmail(app.getsendID(), app.getTimeStamp());
	        	ui.setText_send("socket："+s);
	        	ui.setText_send("-------开始发送-------     -----"+s.getInetAddress()+"-----");
	        	ui.setText_send("send："+smessage);
	        	System.out.println("对应得socket"+s);
        		System.out.println("------开始发送--------");
	        	System.out.println("发送给Client的包："+smessage);
	        	try {
					V.send(s,smessage);
			        ui.setText_send("-------发送完成-------     -----"+s.getInetAddress()+"-----");
			        System.out.println("-------发送完成-------");	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else if(v.checkIdentity(p)) {
	        	ui.setText_receive("-------认证成功-------     -----"+s.getInetAddress()+"-----");
	        	ui.setText_send("-------认证成功-------     -----"+s.getInetAddress()+"-----");
        		try {
					smessage = v.packData(p.getHead().getSourceID(),p.getAuth().getTimeStamp(),p.getTicket().getSessionKey());
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//打包并发送
        	 	ui.setText_send("socket："+s);
	        	ui.setText_send("-------开始发送-------     -----"+s.getInetAddress()+"-----");
	        	ui.setText_send("send："+smessage);
	        	System.out.println("对应得socket"+s);
        		System.out.println("------开始发送--------");
	        	System.out.println("发送给Client的包："+smessage);
	        	try {
					V.send(s,smessage);
			        ui.setText_send("-------发送完成-------     -----"+s.getInetAddress()+"-----");
			        System.out.println("-------发送完成-------");	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else {
        		System.err.println("client发送的包有误，请查看！！");
        		//System.exit(0);
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
					
							Thread thread =new VReceiver(sc,ui);
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
