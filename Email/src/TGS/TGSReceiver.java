package TGS;


	import java.io.IOException;
	import java.net.ServerSocket;
	import java.net.Socket;

public class TGSReceiver extends Thread{

		private Socket socket;
		String rmessage;
		String smessage;
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
		        	System.out.println("-------��ʼ������-------");
		        	DataStruct.Package p = tgs.packAnalyse(rmessage);
		        	if(tgs.checkIdentity(p)) {
		        		DataStruct.Ticket TicketTgs = tgs.generateTicketV(p,s.getInetAddress());
		        		DataStruct.Package p2 = tgs.packData(p.getHead().getSourceID(),p.getRequestID(),TicketTgs);
		        		smessage = tgs.packageToBiarny(p2,p.getTicket().getSessionKey());//���������
		        	}
		        	else {
		        		System.err.println("client���͵İ�������鿴����");
		        		//System.exit(0);
		        	}

		        	System.out.println("��Ӧ��socket"+s);
		        		System.out.println("------��ʼ����--------");
		        	System.out.println("���͸�Client�İ���"+smessage);
		        	try {
						TGS.send(s,smessage);
		      
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
					
							Thread thread =new TGSReceiver(sc);
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