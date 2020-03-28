package dns_simulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdminDatabase {
    File file =  new File(System.getProperty("user.dir")+"//src//dns_simulator//AdminDB.txt");
    Scanner sc = null;
    private Map<String, String> db = new HashMap<>();

    public AdminDatabase(){

        //Loads stored Admins from AdminDB.txt file
      refresh();

    }
    public void refresh(){

        try {
            sc = new Scanner(file);

            while (sc.hasNextLine()) {

                String[]  args = sc.nextLine().split(" ");
                db.put(args[0],args[1]);
                //  System.out.println(Arrays.toString(args));
            }
            sc.close();

        } catch (FileNotFoundException e) {
            sc.close();
            e.printStackTrace();
        }

    }


    public String getSecretKey(String adminId){
        return db.get(adminId);
    }
}
