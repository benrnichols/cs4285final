import java.net.*;
import java.lang.Math;
public class study {
    static long totalPackets = 0;
    static double totalTime= 0;
    static double[][] timeByByte = new double[16][256];
    static double[][] timeSquaredByByte = new double[16][256];
    static long[][] packetNumByByte = new long[16][256];
    
    //@params timing, the amount of time the server took to process the packet
    //@params packet, the value of the first 16 bytes sent in the packet
    //adds the the data available using the given timing and packet
    public static void tally (double timing, byte[] packet) {
        int index;
        for(index =0; index< 16; ++index) {
            ++totalPackets;
            totalTime+=timing;
            int val = (0xFF &packet[index]);
            timeByByte[index][val] += timing;
            timeSquaredByByte[index][val] += timing *timing;
            packetNumByByte[index][val]++;
        }
    }
    //this is the part that sends out the packets, which means I need to relearn java networking YAYYYYYY
    public static void studyInput(int packetSize) {
        byte[] sendData = new byte[packetSize];
        byte[] relevantBytes = new byte[16];
        byte[] receive = new byte[1024];
        while(true) {
            for (int index =0; index< packetSize; index++) {
                sendData[index] = (byte) ((Math.random()* 256) - 128); 
                if(index < 16) {
                    relevantBytes[index] = sendData[index];
                }
            }
		try{
            //send packet here
            InetAddress local = InetAddress.getByName("127.0.0.1");
            DatagramSocket sock = new DatagramSocket();
            DatagramPacket pack = new DatagramPacket(sendData, sendData.length, local, 10000);
            sock.send(pack);
            DatagramPacket inpack = new DatagramPacket(receive, receive.length);
            
            //listen for packet here
            sock.receive(inpack);
            String fromServer = new String(inpack.getData());
            System.out.println(fromServer);
		} catch (Exception e) {}
            return;
        }

    }

    public static void main(String[] args) {
        studyInput(400);
    }

    public static void printPatterns () {
        double timeaverage = totalTime/totalPackets;
        double[][] avgtimebybyte  = new double[16][256];
        double[][] deviationbybyte = new double[16][256];

        for(int i =0; i<16; i++) {
            for (int j =0; j<256; j++) {
                avgtimebybyte [i][j] = timeByByte[i][j]/packetNumByByte[i][j];
                deviationbybyte [i][j] = timeSquaredByByte[i][j] / packetNumByByte[i][j];
                deviationbybyte[i][j] -=  avgtimebybyte [i][j] * avgtimebybyte [i][j];
                deviationbybyte[i][j] = Math.sqrt(deviationbybyte[i][j]);         
            }
        }

        for(int i =0; i<16; i++) {
            for (int j =0; j<256; j++) {
    }
}
}}
