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
        	//�������
        	V v = new V();
        	ui.setText_receive("reciever��"+rmessage);
        	ui.setText_receive("socket��"+s);
        	ui.setText_receive("-------��ʼ�����-------");
        	System.out.println("-------��ʼ�����-------");
        	DataStruct.Package p = V.packAnalyse(rmessage);
        	ui.setText_receive("receivePackge��"+p.toString());
	        ui.setText_receive("-------�������-------     -----"+s.getInetAddress()+"-----");

        	if(p.packageOutput().equals("")) {
        		ui.setText_receive("-------client���͵İ����������·��ͣ�-------");
        		System.err.println("client���͵İ����������·��ͣ���");
        	}
	        else if(p.getHead().getExistTS().equals("1") &&p.getHead().getExistRequstID().equals("0")) {
        		//V����C��ʷ�ʼ�
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
        		//�յ����ż�
        		DataStruct.APPPackage app=v.apppackAnalylse(p);
        		String mcc = app.getreceiveID()+app.getsendID()+app.getEmail().EmailOutput();
        		
        		System.out.println("������Ϊ"+mcc.substring(0,4)+"������Ϊ"+mcc.substring(4,8));
        		System.out.println("V�����C1C2ͨ�Ű�Ϊ"+mcc.substring(8));
        		DBconncet db = new DBconncet();
        		try {
					Statement stat = db.connect().createStatement();
					db.insertData(stat,mcc,app.getHead().getExpend());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		//��mcc�������ݿ� mcc=receiveid+sendid+message  4λstring 4λstring ����message
        		smessage = v.requestEmail(app.getsendID(), app.getTimeStamp());
	        	ui.setText_send("socket��"+s);
	        	ui.setText_send("-------��ʼ����-------     -----"+s.getInetAddress()+"-----");
	        	ui.setText_send("send��"+smessage);
	        	System.out.println("��Ӧ��socket"+s);
        		System.out.println("------��ʼ����--------");
	        	System.out.println("���͸�Client�İ���"+smessage);
	        	try {
					V.send(s,smessage);
			        ui.setText_send("-------�������-------     -----"+s.getInetAddress()+"-----");
			        System.out.println("-------�������-------");	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else if(v.checkIdentity(p)) {
	        	ui.setText_receive("-------��֤�ɹ�-------     -----"+s.getInetAddress()+"-----");
	        	ui.setText_send("-------��֤�ɹ�-------     -----"+s.getInetAddress()+"-----");
        		try {
					smessage = v.packData(p.getHead().getSourceID(),p.getAuth().getTimeStamp(),p.getTicket().getSessionKey());
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//���������
        	 	ui.setText_send("socket��"+s);
	        	ui.setText_send("-------��ʼ����-------     -----"+s.getInetAddress()+"-----");
	        	ui.setText_send("send��"+smessage);
	        	System.out.println("��Ӧ��socket"+s);
        		System.out.println("------��ʼ����--------");
	        	System.out.println("���͸�Client�İ���"+smessage);
	        	try {
					V.send(s,smessage);
			        ui.setText_send("-------�������-------     -----"+s.getInetAddress()+"-----");
			        System.out.println("-------�������-------");	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	else {
        		System.err.println("client���͵İ�������鿴����");
        		//System.exit(0);
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
