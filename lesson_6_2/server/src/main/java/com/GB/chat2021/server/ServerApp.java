package com.GB.chat2021.server;

import sun.util.locale.LocaleMatcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerApp {




    public static void main(String[] args) throws IOException, InterruptedException {

        new Server(8190);


    }

}