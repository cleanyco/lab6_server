package server.util;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleThread extends Thread{
    private final Scanner serverScanner = new Scanner(System.in);
    private volatile boolean isRunning = true;
    private final CollectionManager collectionManager;

    public ConsoleThread(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void start() {
        try {
            while (isRunning) {
                String line = serverScanner.nextLine();
                if ("save".equalsIgnoreCase(line)) {
                    collectionManager.save();
                }
                if ("exit".equalsIgnoreCase(line)) {
                    System.out.println("Сервер закрывается...");
                    System.exit(0);
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Непредвиденная ошибка!");
            System.exit(0);
        }
    }

    public void shutdown() {
        this.isRunning = false;
    }
}
