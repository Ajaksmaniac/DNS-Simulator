

package  dns_simulator;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.WeakHashMap;


public class ClientHandler extends Thread {


    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    private AdminDatabase adb;
    private DnsDatabase ddb;
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos,AdminDatabase adb, DnsDatabase ddb) {
        this.dis = dis;
        this.dos = dos;
        this.s = s;
        this.adb = adb;
        this.ddb = ddb;
    }

    // Example: ADDwww,google.com123.123.123.123111 => 1808ea6083345697951ff4124b0e799af8497a1c11155490909a47a18b06d507
    //Command Ajaks REMOVE aaaa  + HASH(REMOVEaaaa123TestPass) => ada83f88d94e585502a07aebb084925621097a8cf189be370669b3865662f1db
    //Command Ajaks ADD singidunum.ac.rs 78.44.104.51 + HASH(ADDsingidunum.ac.rs78.44.104.51TestPass) => 302a45bbf2d509adf8e0376d43b667b2aa00e8b284b85115008a6be13c722a9c
    //Converts String into SHA256 Encoding
   public String hash(String s) throws NoSuchAlgorithmException { try{
       MessageDigest digest = MessageDigest.getInstance("SHA-256");
       byte[] hash = digest.digest(s.getBytes("UTF-8"));
       StringBuffer hexString = new StringBuffer();

       for (int i = 0; i < hash.length; i++) {
           String hex = Integer.toHexString(0xff & hash[i]);
           if(hex.length() == 1) hexString.append('0');
           hexString.append(hex);
       }
       System.out.println(hexString.toString());
       return hexString.toString();
   } catch(Exception ex){
       throw new RuntimeException(ex);
   }

    }
    @Override
    public void run() {

        while(true) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(dis));
                BufferedWriter writer=  new BufferedWriter(new OutputStreamWriter(dos));

                String recieved;
                while((recieved = in.readLine()) != null){



                    String[] args = recieved.split(" ");


                    if(args[0].equals("LOOKUP")){
                        String domain = args[1];
                        DnsRecord record = ddb.lookup(domain);
                        if(record != null){
                            writer.write("Ip for " + domain + " is " + record.getIpAddress());
                        }else{
                            writer.write("Record not found!");
                        }
                    }else if(args.length == 5 && args[1].equals("ADD")){
                        String  adminId = args[0];
                        String adminKey = adb.getSecretKey(adminId);
                        String domain = args[2];
                        String ip = args[3];

                        StringBuilder sb = new StringBuilder();

                        sb.append("ADD");
                        sb.append(domain);
                        sb.append(ip);
                        sb.append(adminKey);

                        String Hash = args[4];

                        if(hash(sb.toString()).equals(Hash)){

                            writer.write("SUCCES");
                            ddb.add(domain,ip,writer);

                        }else{

                            writer.write("Task failed successfuly :)");

                        }

                    }else if(args.length == 4 && args[1].equals("REMOVE")){
                        String  adminId = args[0];
                        String domain = args[2];
                        DnsRecord record = ddb.lookup(domain);
                        String ip = record.getIpAddress();
                        StringBuilder sb = new StringBuilder();
                        String adminKey = adb.getSecretKey(adminId);
                        sb.append("REMOVE");
                        sb.append(domain);
                        sb.append(ip);
                        sb.append(adminKey);
                        String Hash = args[3];
                        if(hash(sb.toString()).equals(Hash)){

                            writer.write(domain + " Removed to the Database!");
                            ddb.remove(domain);

                        }else{

                            writer.write("Task failed successfuly :)");

                        }
                    }else{
                        writer.write("Invalid '" + recieved+"' command");
                    }

                    writer.newLine();
                    writer.flush();
                }
                s.close();
                System.out.println("Client Disconected");
                break;
            } catch (IOException | NoSuchAlgorithmException | NullPointerException e) {

                e.printStackTrace();
            }
        }
    }



}
