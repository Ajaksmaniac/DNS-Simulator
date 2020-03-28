package dns_simulator;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
//CLient Lookup

//Admin
//Add,remove,Lookup
public class DnsDatabase {
    private File file =  new File(System.getProperty("user.dir")+"//src//dns_simulator//DNSDB.txt");
    private Scanner sc = null;
    private Map<String,String> records = new HashMap<>();

   /* public void add(DnsRecord record) throws IOException {
        records.put(record.getDomain(),record.getIpAddress());
      /*  PrintWriter pw = new PrintWriter(new FileWriter(file));
        pw.println(record.getDomain() + " " + record.getIpAddress());
        pw.flush();
    }*/

    public DnsDatabase() throws IOException {

        refresh();
    }


    //Appends current lines of records into records map;
    public void refresh() throws IOException {
        file =  new File(System.getProperty("user.dir")+"//src//dns_simulator//DNSDB.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        records =new HashMap<>();
        try {
            sc = new Scanner(file);

            while (sc.hasNextLine()) {

                String[]  args = sc.nextLine().split(" ");
                records.put(args[0],args[1]);
                //System.out.println(Arrays.toString(args));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            sc.close();
            e.printStackTrace();
        }

    }
    public DnsRecord lookup(String domain){
        if(records.containsKey(domain)){
            return new DnsRecord(domain,records.get(domain));
        }
        return null;
    }

    public void add(String domain, String ip, BufferedWriter writter) throws IOException {
        file =  new File(System.getProperty("user.dir")+"//src//dns_simulator//DNSDB.txt");
        // Construct the new file that will later be renamed to the original

        //BufferedReader br = new BufferedReader(new FileReader(file));
        PrintWriter pw = new PrintWriter(new FileWriter(file));
        if(!records.containsKey(domain)){

            pw.println(domain + " " + ip);
            pw.flush();

        }else{
            System.out.println("Already in the file");
            writter.write(domain + " Already Exists");
        }

        pw.close();
        refresh();
    }
    public void removeLineFromFile(String lineToRemove) {

        try {



            if (!file.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }
            File tempFile = new File(file.getAbsolutePath() + ".tmp");
            // Construct the new file that will later be renamed to the original

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            // Read from the original file and write to the new
            // unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.startsWith(lineToRemove)) {

                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            // Delete the original file
            if (!file.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            // Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(file))
                System.out.println("Could not rename file");
           file = tempFile;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void remove(String domain) throws IOException {
       removeLineFromFile(domain);

        refresh();
    }



}
