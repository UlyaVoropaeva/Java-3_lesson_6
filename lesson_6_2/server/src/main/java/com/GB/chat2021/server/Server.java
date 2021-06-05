package com.GB.chat2021.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class Server {

    private int port;
    private List<ClientHandler> clients;
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psInsert;
    private static final Logger logger = Logger.getLogger(Server.class.getName());



    public Server(int port) throws IOException {


        this.port = port;
        this.clients = new ArrayList<>();

        logger.setLevel(Level.ALL);

        Handler fileHandler = new FileHandler("log_%g.log", 10 * 1024, 20, true);
        fileHandler.setFormatter(new SimpleFormatter());
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port + "...");
            logger.log(Level.INFO,"Сервер запущен на порту " + port + "...");
            //  SQLServer.connect();
            while (true) {
                System.out.println("Ждем нового клиента...");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(socket, this);
            }
        } catch (Exception e ) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();

              } finally {
            logger.log(Level.INFO, "Сервер закончил работу");

                  //SQLServer.disconnect();
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) throws SQLException {

        clients.add(clientHandler);
        //  SQLServer.registration(clientHandler);
        logger.log(Level.FINE,"Клиент " + clientHandler.getUsername() + " подключился");
        broadcastClientList();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastMessage("Клиент " + clientHandler.getUsername() + " отключился \n");
        logger.log(Level.FINE,"Клиент " + clientHandler.getUsername() + " отключился");
        broadcastClientList();
    }
    public synchronized void broadcastMessage(String message) {

        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(message);
            logger.log(Level.FINEST,"клиент " + clients + " прислал сообщение/команду");
        }
    }
    public boolean isUserOnline(String nickname) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getUsername().equals(nickname)) {
                return true;
            }
        }
        return false;
    }
    public void usernameMessage(String nickname) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(nickname)) {
                client.sendMessage("Ваш никнейм: " + client.getUsername() + "\n");
                logger.log(Level.FINEST,"клиент " + client.getUsername() + " прислал команду, узнал никнейм");
                return;
            }
        }
    }
    public void privatUserMessage(ClientHandler clientHandler, String username, String massage) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                client.sendMessage("От " + clientHandler.getUsername() + ":  " + massage + " \n");
                clientHandler.sendMessage("Пользователю " + username + ":  " + massage + " \n");
                logger.log(Level.FINEST,"От " + clientHandler.getUsername() + " отправлено сообщение " + "Пользователю " + username);
                return;
            }
        }
        clientHandler.sendMessage("Невозможно отправить сообщение. В списке контактов отсуствует : " + username + "\n");
        logger.log(Level.FINEST, "Невозможно отправить сообщение. В списке контактов отсуствует : " + username);
        return;
    }
    private void broadcastClientList() {
        StringBuilder stringBuilder = new StringBuilder("/clients_list ");
        for (ClientHandler client : clients) {
            stringBuilder.append(client.getUsername()).append(" ");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        String clientsList = stringBuilder.toString();
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(clientsList);
        }
    }


}
