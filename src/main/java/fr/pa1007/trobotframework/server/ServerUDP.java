package fr.pa1007.trobotframework.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerUDP {

    private static ServerUDP      instance;
    private        DatagramSocket socket;
    private        int            port;
    private        InetAddress    address;

    public ServerUDP(int port) throws SocketException {
        socket = new DatagramSocket(port);
        this.port = port;
    }

    public ServerUDP() throws SocketException {
        this(8000);
    }

    public String waitServer() throws IOException {
        byte[]         buffer         = new byte[500];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(datagramPacket);
        return new String(buffer, 0, buffer.length);
    }

    public void send(String msg) throws IOException {
        byte[]         buffer         = msg.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(datagramPacket);
    }

    public static synchronized ServerUDP getInstance() throws SocketException {
        if (instance == null) {
            instance = new ServerUDP();
        }
        return instance;
    }
}
