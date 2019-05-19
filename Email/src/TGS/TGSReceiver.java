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
		        	//�������
		        	TGS tgs = new TGS();
		        	ui.setText_receive("-------�յ���-------");
		        	ui.setText_receive("socket��"+s);
		        	ui.setText_receive("reciever��"+rmessage);
		        	ui.setText_receive("-------��ʼ������-------");
		        	System.out.println("-------��ʼ������-------");
		        	DataStruct.Package p = tgs.packAnalyse(rmessage);
		        	ui.setText_receive("receivePackge��"+p.toString());
			        ui.setText_receive("-------�������-------     -----"+s.getInetAddress()+"-----");
		        	if(p.packageOutput().equals("")) {
		        		ui.setText_receive("-------client���͵İ����������·��ͣ�-------");
		        		System.err.println("client���͵İ����������·��ͣ���");
		        	}
		        	if(tgs.checkIdentity(p)) {
			        	ui.setText_receive("-------��֤�ɹ�-------     -----"+s.getInetAddress()+"-----");
			        	ui.setText_send("-------��֤�ɹ�-------     -----"+s.getInetAddress()+"-----");
		        		DataStruct.Ticket TicketTgs = tgs.generateTicketV(p,s.getInetAddress());
		        		DataStruct.Package p2 = tgs.packData(p.getHead().getSourceID(),p.getRequestID(),TicketTgs);
		        		ui.setText_send("socket��"+s);
		            	ui.setText_send("sendPacksge��"+p);
		        		smessage = tgs.packageToBiarny(p2,p.getTicket().getSessionKey());//���������
		        	}
		        	else {
		        		System.err.println("client���͵İ�������鿴����");
		        		//System.exit(0);
		        	}
		        	ui.setText_send("-------��ʼ����-------     -----"+s.getInetAddress()+"-----");
		        	ui.setText_send("send��"+smessage);
		        	System.out.println("��Ӧ��socket"+s);
		        	System.out.println("------��ʼ����--------");
		        	System.out.println("���͸�Client�İ���"+smessage);
		        	try {
						TGS.send(s,smessage);
				        ui.setText_send("-------�������-------     -----"+s.getInetAddress()+"-----");
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
