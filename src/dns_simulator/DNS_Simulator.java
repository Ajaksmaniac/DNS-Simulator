package dns_simulator;

public class DNS_Simulator {
    public static void main(String[] args) {
        try {
            new DnsServer().run();
        } catch (Exception e) {
            System.err.println(e.getMessage() + "AA");
            e.printStackTrace();
        }
    }
}