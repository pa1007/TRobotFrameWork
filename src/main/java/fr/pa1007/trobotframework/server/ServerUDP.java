package fr.pa1007.trobotframework.server;

import fr.pa1007.trobotframework.utils.Module;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerUDP {

    private static final AtomicInteger  lastPort = new AtomicInteger(8000);
    private              DatagramSocket socket;
    private              int            port;
    private              InetAddress    address;

    public ServerUDP(int port) throws SocketException {
        socket = new DatagramSocket(port);
        this.port = port;
    }

    public ServerUDP(Module m) throws SocketException {
        this(m.getPort());
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

    public static synchronized int getPort() {
        return lastPort.getAndIncrement();
    }

    public static synchronized ServerUDP getInstance(Module m) throws SocketException {
        return new ServerUDP(m);
    }
}
