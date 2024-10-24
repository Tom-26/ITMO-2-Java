package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 12345;
    private static final int THREAD_POOL_SIZE = 10;
    private final CollectionManager collectionManager;
    private final ExecutorService threadPool;

    public Server(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Новое подключение от " + clientSocket.getInetAddress());
                threadPool.submit(new ClientHandler(clientSocket, collectionManager));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public static void main(String[] args) {
        AuthenticationService authService = new AuthenticationService(new UserDAO());
        CollectionManager collectionManager = new CollectionManager("routes.json", authService);
        Server server = new Server(collectionManager);
        server.start();
    }
}
