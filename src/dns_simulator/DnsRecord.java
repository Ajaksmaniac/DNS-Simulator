package dns_simulator;

import javax.jws.Oneway;

public class DnsRecord {
    public String domain, ip;

    public DnsRecord(String domain, String ip) {
        this.domain = domain;
        this.ip = ip;
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIpAddress() {
        return ip;
    }

    public void setIpAddress(String ip) {
        this.ip = ip;
    }



    @Override
    public String toString(){
        return this.domain + "\t" + this.ip;
    }

}
