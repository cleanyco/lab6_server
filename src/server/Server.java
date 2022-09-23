package server;

import common.Request;
import common.Response;
import server.util.CollectionManager;
import server.util.ConsoleThread;
import server.util.FileManager;
import server.util.ResponseSender;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private static final int PORT = 8888;
    private static Selector selector = null;
    private static FileManager fileManager;
    private static CollectionManager collectionManager;
    private static ResponseSender responseSender;
    private static ConsoleThread consoleThread;

    public static void main(String[] args) {

//        if (args.length == 0) {
//            System.out.println("Имя файла не поступило, остановка...");
//            System.exit(0);
//        }
        //FIXME вставить args[0]
        fileManager = new FileManager("src/collection.csv");
        collectionManager = new CollectionManager(fileManager);
        responseSender = new ResponseSender(collectionManager);
        consoleThread = new ConsoleThread(collectionManager);

        try{
            consoleThread.start();
            selector = Selector.open();
            ServerSocketChannel socket = ServerSocketChannel.open();
            ServerSocket serverSocket = socket.socket();
            serverSocket.bind(new InetSocketAddress("localhost", PORT));
            socket.configureBlocking(false);
            int ops = socket.validOps();
            socket.register(selector, ops, null);

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    if (key.isAcceptable()) {
                        //new client has been accepted
                        handleAccept(socket, key);
                    } else if (key.isReadable()) {
                        readQuery(key);
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            System.out.println("Возникла ошибка при открытии портов или каналов.");
        } catch (ClassNotFoundException e) {
            System.out.println("На сервере отсутствуют данные об отправленном классе.");
            e.printStackTrace();
        }
    }

    private static void handleAccept(ServerSocketChannel socket, SelectionKey key) throws IOException {
        System.out.println("Подключение установлено...");
        SocketChannel client = socket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    private static void readQuery(SelectionKey key) throws IOException, ClassNotFoundException {
        System.out.println("Идёт чтение запроса...");

        SocketChannel client = (SocketChannel) key.channel();
        client.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        client.read(buffer);

        ByteArrayInputStream in = new ByteArrayInputStream(buffer.array());
        ObjectInputStream ois = new ObjectInputStream(in);
        Request<?> request = (Request<?>) ois.readObject();
        System.out.println("Исполняемая команда: " + request.command);
        Response response = responseSender.executeCommand(request);
        key.interestOps(SelectionKey.OP_WRITE);
        if (key.isWritable()) {
            sendResponse(response, key);
        }
        }

        public static void sendResponse(Response response, SelectionKey key) throws IOException{
            SocketChannel channel = (SocketChannel) key.channel();
            channel.configureBlocking(false);
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(response);
                ByteBuffer buffer = ByteBuffer.allocate(1048576);
                buffer.put(bos.toByteArray());
                buffer.flip();
                channel.write(buffer);
                buffer.clear();
            }
        }
    }