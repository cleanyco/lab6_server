package server.util;

import common.Request;
import common.Response;
import common.ResponseCode;

import java.io.IOException;
import java.util.ArrayList;

//TODO сделать чтение файла из коллекции, сделать валидации прочтенных файлов (а надо?)
//TODO сделать сохранение коллекции и выход из сервера
//TODO сделать обработку когда клиент отсоединяется
public class ResponseSender{
    ArrayList<String> commands = new ArrayList<>();
    CollectionManager collectionManager;

    public ResponseSender(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    {
        commands.add("add");
        commands.add("add_if_max");
        commands.add("add_if_min");
        commands.add("clear");
//        commands.add("exit");
        commands.add("info");
        commands.add("print_descending");
        commands.add("remove_any_by_transport");
        commands.add("remove");
        commands.add("show");
        commands.add("shuffle");
        commands.add("sum_of_number_of_bathrooms");
        commands.add("update");
    }

    public Response executeCommand(Request<?> request) throws IOException {
        if (!commands.contains(request.command)) {
            return new Response(ResponseCode.ERROR, "Команда не определена на сервере.");
        } else {
            switch (request.command) {
                case "add":
                    return collectionManager.add(request);
                case "add_if_max":
                    return collectionManager.addIfMax(request);
                case "add_if_min":
                    return collectionManager.addIfMin(request);
                case "clear":
                    return collectionManager.clear(request);
                case "info":
                    return collectionManager.info(request);
//                case "exit":
//                    return collectionManager.exit(request);
                case "print_descending":
                    return collectionManager.printDescending(request);
                case "remove_any_by_transport":
                    return collectionManager.removeAnyByTransport(request);
                case "remove":
                    return collectionManager.remove(request);
                case "show":
                    return collectionManager.show(request);
                case "shuffle":
                    return collectionManager.shuffle(request);
                case "sum_of_number_of_bathrooms":
                    return collectionManager.sumOfNumberOfBathrooms(request);
                case "update":
                    return collectionManager.update(request);
                default:
                    return new Response(ResponseCode.ERROR, "Команда не определена на сервере.");
            }
        }
    }
}
