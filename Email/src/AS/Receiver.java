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
        	//�������
        	ui.setText_receive("reciever��"+rmessage);
        	ui.setText_receive("socket��"+s);
        	ui.setText_receive("-------��ʼ�����-------");
        	DataStruct.Package p = AS.packAnalyse(rmessage);
        	ui.setText_receive("receivePackge��"+p.toString());
        	System.out.println("-------�������-------");
        	
        	if(p.packageOutput().equals("")) {
        		ui.setText_receive("-------client���͵İ����������·��ͣ�-------");
        		System.err.println("client���͵İ����������·��ͣ���");
        	}
        	if(p.getHead().getExistLogin().equals("1")) {
				if(p.getHead().getExistSessionKey().equals("1"))//ע��
				{
		        	ui.setText_send("-------ע��-------");
	        		try {
						smessage = AS.signin(p);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				else {//��¼
		        	ui.setText_send("-------��¼-------");
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
            	ui.setText_send("sendPacksge��"+p);
        		smessage = AS.packageToBinary(p);//���������
        	}
        	else {
        		System.err.println("client���͵İ�������鿴����");
        		//System.exit(0);
        	}
        	ui.setText_send("socket��"+s);

        	System.out.println("��Ӧ��socket"+s);
        	ui.setText_send("-------��ʼ����-------");
        	System.out.println("------��ʼ����--------");
        	ui.setText_send("send��"+smessage);
        	System.out.println("���͸�Client�İ���"+smessage);
        	
        	try {
				AS.send(s,smessage);

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
