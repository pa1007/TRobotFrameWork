package fr.pa1007.trobotframework.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP{

    private int port;
    private ServerSocket serverSocket;
    private Socket client;
    private BufferedReader enter;
    private PrintWriter exit;

    public ServerTCP(int s) throws IOException {
        this.port = s;
        this.serverSocket = new ServerSocket(port);
    }

    public void startConnection() throws IOException {
        this.client = serverSocket.accept();
        this.enter = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.exit = new PrintWriter(client.getOutputStream());
    }

    public String waitMessage() throws IOException, InterruptedException {
        return this.enter.readLine();
    }

    public void sendMessage(String s){
        this.exit.println(s);
        this.exit.flush();
    }

    public void endConnection() throws IOException {
        this.serverSocket.close();
    }
}
