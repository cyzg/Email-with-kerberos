package Client;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Client.UI.receive_email;
import DataStruct.Package;
import Server.UI.AP;

public class CReceiver extends Thread{

		private String message;
		private Socket socket;
		receive_email ui;
		public CReceiver(Socket socket) {
			this.socket=socket;
		}

		public CReceiver(Socket server,receive_email ui) {
			this.socket=server;
			this.ui=ui;
		}
		public CReceiver(receive_email ui) {
			this.ui=ui;
		}
		public void run() {
			Socket s=null;       
        	s=socket;
        	message=Client.receive(s);
        	System.out.println("-------��ʼ�����ʼ�-------");
        	
        	//�������
        	DataStruct.APPPackage p = Client.apppackageAnalyse(message);
        	ui.getEmail("sender��"+p.getAuth().getsendID());
        	ui.getEmail("Email��"+p.getEmail().getcontent());
        	System.out.println("�ʼ������ˣ�"+p.getAuth().getsendID());	
        	System.out.println("�ʼ����ݣ�"+p.getEmail().getcontent());	
        }
		
			public static  Runnable listener(int port)throws IOException, InterruptedException{
 				 System.out.println("-------��ʼ�����ʼ�----------");
 				 ServerSocket server = null;
				 Socket sclast = null;
				 Socket sc =null;
				 try {	
					 server= new ServerSocket(port);
					 while(true) {
						sc= server.accept();
						if(sclast!=sc) {	
							Thread thread =new CReceiver(sc);
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
			            //5.�ر���Ӧ�����Լ�Socket,ServerSocket�Ķ���
			            
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
