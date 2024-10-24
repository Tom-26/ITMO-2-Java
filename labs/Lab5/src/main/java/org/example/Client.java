package org.example;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Подключено к серверу " + SERVER_ADDRESS + " на порту " + SERVER_PORT);

            // Чтение приветственного сообщения от сервера
            System.out.println(in.readLine());

            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                // Отправка команды на сервер
                out.println(userInput);

                // Чтение и вывод ответа от сервера
                String serverResponse;
                while ((serverResponse = in.readLine()) != null) {
                    System.out.println(serverResponse);
                    if (serverResponse.equals("Соединение закрыто")) {
                        break;
                    }
                }

                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
