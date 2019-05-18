package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
		        	//�������
		        	V v = new V();
		        	ui.setText_receive("reciever��"+rmessage);
		        	ui.setText_receive("socket��"+s);
		        	ui.setText_receive("-------��ʼ�����-------");
		        	System.out.println("-------��ʼ�����-------");
		        	DataStruct.Package p = v.packAnalyse(rmessage);
		        	ui.setText_receive("receivePackge��"+p.toString());
		        	if(p.packageOutput().equals("")) {
		        		ui.setText_receive("-------client���͵İ����������·��ͣ�-------");
		        		System.err.println("client���͵İ����������·��ͣ���");
		        	}
		        	if(v.checkIdentity(p)) {
		        		smessage = v.packData(p.getHead().getSourceID(),p.getAuth().getTimeStamp(),p.getTicket().getSessionKey());//���������
		        	}
		        	else {
		        		System.err.println("client���͵İ�������鿴����");
		        		//System.exit(0);
		        	}
		        	ui.setText_send("socket��"+s);
		        	ui.setText_send("-------��ʼ����-------");
		        	ui.setText_send("send��"+smessage);
		        	System.out.println("��Ӧ��socket"+s);
	        		System.out.println("------��ʼ����--------");
		        	System.out.println("���͸�Client�İ���"+smessage);
		        	try {
						V.send(s,smessage);
				        ui.setText_send("-------�������-------");
				        System.out.println("-------�������-------");	
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
			public static  Runnable listener(int port,AP ui)throws IOException, InterruptedException{

				 ServerSocket server = null;
				 Socket sclast = null;
				 Socket sc =null;
			     ui.setText_receive("-------��ʼ�������ݰ�-------");
 				 System.out.println("-------��ʼ�������ݰ�----------");
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
