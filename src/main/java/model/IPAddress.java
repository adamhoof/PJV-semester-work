package model;

public class IPAddress {
    int[] octets = new int[4];

    public IPAddress(int firstOctet, int secondOctet, int thirdOctet, int fourthOctet) {
        octets[0] = firstOctet;
        octets[1] = secondOctet;
        octets[2] = thirdOctet;
        octets[3] = fourthOctet;
    }

    public IPAddress(IPAddress ipAddress) {
        octets[0] = ipAddress.octets[0];
        octets[1] = ipAddress.octets[1];
        octets[2] = ipAddress.octets[2];
        octets[3] = ipAddress.octets[3];
    }

    public void incrementOctet(int octet, int increment) {
        switch (octet) {
            case 1:
                octets[0] += increment;
                break;
            case 2:
                octets[1] += increment;
                break;
            case 3:
                octets[2] += increment;
                break;
            case 4:
                octets[3] += increment;
                break;
        }
    }

    public long toLong() {
        long ipAsLong = 0;

        for (int i = 0; i < octets.length; i++) {
            ipAsLong = (ipAsLong << 8) + octets[i];
        }
        return ipAsLong;
    }

    public static IPAddress longToIPAddress(long ipAsLong) {
        int octet1 = (int) ((ipAsLong >> 24) & 0xFF);
        int octet2 = (int) ((ipAsLong >> 16) & 0xFF);
        int octet3 = (int) ((ipAsLong >> 8) & 0xFF);
        int octet4 = (int) (ipAsLong & 0xFF);
        return new IPAddress(octet1, octet2, octet3, octet4);
    }

    public int[] getOctets() {
        return octets;
    }
}