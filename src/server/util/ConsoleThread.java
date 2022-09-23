package server.util;

import java.util.Scanner;

public class ConsoleThread extends Thread {
    private volatile boolean running = true;
    private final Scanner threadScanner = new Scanner(System.in);
    private final CollectionManager collectionManager;

    public ConsoleThread(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public void run() {
        System.out.println("Консоль сервера запущена...");
        while (running) {
                String input = threadScanner.nextLine();
                if ("save".equals(input)) {
                    collectionManager.save();
                    System.out.println("Коллекция успешно сохранена...");

                }
            if ("exit".equals(input)) {
                System.out.println("Работа сервера останавливается...");
                System.exit(0);
            }
        }
    }

    public void shutdown() {
        this.running = false;
    }
}

