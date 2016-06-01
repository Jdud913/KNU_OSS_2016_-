package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

	ServerSocket serverSocket = null;//������ ��� ����
	Thread[] threadArr;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Server server = new Server(1);
		server.start();
		
	}
	
	public Server(int num){
		try{
			//���� ������ �����Ͽ� �ش���Ʈ�� ���ε�
			serverSocket = new ServerSocket(7777);
			threadArr = new Thread[num];
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void start(){
		for(int i=0; i<threadArr.length; i++){
			threadArr[i] = new Thread(this);
			threadArr[i].start();
		}
	}

	@Override
	public void run() {
		
		while(true){
			
			int count = 0;
			
			try {
				System.out.println("server�� ���� ��û�� ��ٸ��ϴ�.");
				Socket socket = serverSocket.accept();
				System.out.println("" + socket.getInetAddress() + "�κ��� �����û�� ���Խ��ϴ�.");
				
				String[] beacon = new String[3]; 
				
				InputStream in = socket.getInputStream();
//				OutputStream out = socket.getOutputStream();
				
				DataInputStream dataInputStream = new DataInputStream(in);
//				DataOutputStream dataOutputStream = new DataOutputStream(out);
				
				
				
				while(true){
					
					if(count == 3){
						System.out.println("mac : " +  beacon[0] + ", major : " + beacon[1] + ", minor : " + beacon[2]);
						dataInputStream.close();
						socket.close();
						count = 0;
						break;
					}
					
					String input = dataInputStream.readUTF();
						
//					System.out.println("Ŭ���̾�Ʈ�κ��� ���� �޼��� : " + input);	
					
					if(count == 0){
						beacon[0] = input;
					}else if(count == 1){
						beacon[1] = input;
					}else if(count == 2){
						beacon[2] = input;
					}
					
					count++;
				}
							
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //������ ��ȯ ����			
		}
		
		
	}

}
