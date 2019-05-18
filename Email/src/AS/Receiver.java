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
        	//�������
        	ui.setText_receive("���յİ���"+rmessage);
        	System.out.println("-------��ʼ�����-------");
        	DataStruct.Package p = AS.packAnalyse(rmessage);
        	if(p == null) {
        		System.err.println("client���͵İ����������·��ͣ���");
        	}
        	if(p.getHead().getExistLogin().equals("1")) {
				if(p.getHead().getExistSessionKey().equals("1"))//ע��
				{
	        		try {
						smessage = AS.signin(p);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
				else {//��¼
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
        		smessage = AS.packageToBinary(p);//���������
        	}
        	else {
        		System.err.println("client���͵İ�������鿴����");
        		//System.exit(0);
        	}

        	System.out.println("��Ӧ��socket"+s);
        		System.out.println("------��ʼ����--------");
        	System.out.println("���͸�Client�İ���"+smessage);
        	
        	try {
				AS.send(s,smessage);
      
        	System.out.println("-------�������-------");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			public static  Runnable listener(int port)throws IOException, InterruptedException{

				 ServerSocket server = null;
				 Socket sclast = null;
				 Socket sc =null;
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
