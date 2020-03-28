package dns_simulator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class DnsServer  {

    final int port = 50000;
    public void run() throws IOException {
        AdminDatabase adb = new AdminDatabase();
        DnsDatabase ddb = new DnsDatabase();
        ServerSocket ss = new ServerSocket(50000);
        System.out.println("Server started listening at 50000 port");
        while(true) {
            Socket s = null;


            try {
                s = ss.accept();
                System.out.println("Client  Connected:  " + s.getInetAddress().getHostName());

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());



                Thread t = new ClientHandler(s, dis, dos,adb,ddb);
                dos.writeUTF("Welcome to AJaks's DNS Server");
                t.start();



            } catch (SocketException se){
                s.close();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

    }
}
