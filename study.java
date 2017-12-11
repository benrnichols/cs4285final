import java.net.*;
import java.lang.Math;
public Class study {
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
    void studyInput(int packetSize) {
        byte[] sendData = new byte[packetSize];
        byte[] relevantBytes = new byte[16];
        byte[] receive = new byte[1024];
        while(true) {
            for (int index =0; index< packetSize; index++) {
                sendData[index] = (byte) (Math.random()* 256) - 128; 
                if(index < 16) {
                    relevantBytes[index] = sendData[index];
                }
            }
            //send packet here
            InetAddress local = InetAddress.getByName("127.0.0.1");
            DatagramSocket sock = new DatagramSocket();
            DatagramPacket pack = new DatagramPacket(sendData, sendData.length, local, 1000);
            sock.send(pack);
            DatagramPacket inpack = new DatagramPacket(receive, receive.length);
            sock.receive(inpack);
            String 
            //listen for packet here
        }

    }

}