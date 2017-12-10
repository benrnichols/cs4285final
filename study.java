public Class study {
    static long totalPackets = 0;
    static double totalTime= 0;
    static double[][] timeByByte = new double[16][256];
    static double[][] timeSquaredByByte = new double[16][256];
    static long[][] packetNumByByte = new long[16][256];
    
    //@params timing, the amount of time the server took to process the packet
    //@params packet, the value of the first 16 bytes sent in the packet
    //adds the the data available using the given timing and packet
    public static void tally (double timing, int[]] packet) {
        int index;
        for(index =0; index< 16; ++index) {
            ++totalPackets;
            totalTime+=timing;
            int val = packet[index];
            timeByByte[index][val] += timing;
            timeSquaredByByte[index][val] += timing *timing;
            packetNumByByte[index][val]++;
        }
    }
    //this is the part that sends out the packets, which means I need to relearn java networking YAYYYYYY
    void studyInput(int packetSize) {
        
    }

}