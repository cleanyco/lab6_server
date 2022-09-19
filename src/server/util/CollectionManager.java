package server.util;

import common.Request;
import common.Response;
import common.ResponseCode;
import data.Flat;
import data.Transport;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class CollectionManager {
    private final FileManager fileManager;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;

    private ArrayList<Flat> flats;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.lastInitTime = null;
        this.lastSaveTime = null;
        flats = loadCollection();
        Collections.sort(flats);
    }

    //FIXME сделать компактнее
    public Flat getLast() {
        if (flats.isEmpty()) return null;
        return flats.get(flats.size() - 1);
    }

    public Long generateNextId() {
        if (getLast() != null) {
            return getLast().getId() + 1;
        }
        return 1L;
    }

    public Response info(Request<?> request) {
        if (request.entity != null) {
            return new Response(ResponseCode.ERROR, "Аргумент для данной команды не предусмотрен.");
        }
        String initTime = (lastInitTime == null) ? "В данной сессии инициализации не происходило." :
                lastInitTime.toLocalDate().toString();
        String saveTime = (lastSaveTime == null) ? "В данной сессии сохранения не происходило" :
                lastSaveTime.toLocalDate().toString();
        String type = flats.getClass().getName();
        int size = flats.size();
        return new Response(ResponseCode.OK,
                "Дата инициализации: " + initTime + "\n" +
                "Дата последнего сохранения: " + saveTime + "\n" +
                "Тип коллекции: " + type + " \n" +
                "Размер коллекции " + size);
    }


    public Response show(Request<?> request) {
        if (request.entity != null) {
            return new Response(ResponseCode.ERROR, "Аргумент для данной команды не предусмотрен.");
        }
        String result = flats.stream().map(Flat::toString)
                .collect(Collectors.joining("\n"));
        return new Response(ResponseCode.OK, result);
    }

    public void save() {
            try {
                fileManager.writeCollection(flats);
                lastSaveTime = LocalDateTime.now();
            } catch (IOException e) {
                System.out.println("Не удалось сохранить коллекцию в файл.");
            }
        }

    public Response clear(Request<?> request) {
        if (request.entity != null) {
            return new Response(ResponseCode.ERROR, "Аргумент для данной команды не предусмотрен.");
        }
        flats.clear();
        return new Response(ResponseCode.OK, "Коллекция успешно очищена.");
    }


    public Response add(Request<?> request) {
        try {
            Flat flat = (Flat) request.entity;
            flat.setId(generateNextId());
            flats.add(flat);
            return new Response(ResponseCode.OK, "Новая квартира успешно добавлена.");
        } catch (Exception e) {
            return new Response(ResponseCode.ERROR, "Не удалось выполнить операцию 'add'.");
        }
    }

    public Response addIfMax(Request<?> request) {
        Flat flat = (Flat) request.entity;
        flat.setId(generateNextId());
        if (flat.compareTo(flats.get(flats.size()-1)) > 0) {
            flats.add(flat);
            return new Response(ResponseCode.OK, "Новая квартира успешно добавлена.");
        } else {
            return new Response(ResponseCode.ERROR, "Элемент не максимальный");
        }
    }


    //FIXME сделать обработку ошибок
    public Response addIfMin(Request<?> request) {
        Flat flat = (Flat) request.entity;
        flat.setId(generateNextId());
        if (flat.compareTo(flats.get(0)) < 0) {
            flats.add(flat);
            return new Response(ResponseCode.OK, "Новая квартира успешно добавлена.");
        } else {
            return new Response(ResponseCode.ERROR, "Элемент не минимальный.");
        }
    }


    public Response update(Request<?> request) {
        try {
            Flat flat = (Flat) request.entity;
            if (flats.get((int) flat.getId()) == null) {
                return new Response(ResponseCode.ERROR, "Квартиры с таким ID не существует.");
            } else {
                flats.stream().filter(flatOrig -> flatOrig.getId() == flat.getId()).forEach(flat1 -> flat1.update(flat));
                return new Response(ResponseCode.OK,"Квартира была успешно обновлена.");
            }
        } catch (ClassCastException e) {
            return new Response(ResponseCode.ERROR, "Передан неправильный аргумент.");
        }
    }


    public Response remove(Request<?> request) {
        Long id = (Long) request.entity;
        if (flats.stream().noneMatch(flat -> flat.getId() == id)) {
            return new Response(ResponseCode.ERROR, "Квартиры с таким ID нет.");
        } else {
            Flat flatToRemove;
            flatToRemove = flats.stream().filter(flat -> flat.getId() == id).findFirst().get();
            flats.remove(flatToRemove);
            return new Response(ResponseCode.OK, "Квартира была успешно удалена.");
        }
    }


    //FIXME а зачем сюда передавать элемент?
    public Response removeAnyByTransport(Request<?> request) {
        try {
            Transport transport = Transport.valueOf((String) request.entity);
            if (flats.stream().noneMatch(Flat -> Flat.getTransport() == transport)) {
                return new Response(ResponseCode.ERROR, "Нет квартиры с таким параметром 'transport'");
            } else {
                Flat flatToRemove = flats.stream().filter(flat -> flat.getTransport() == transport).findAny().get();
                flats.remove(flatToRemove);
                return new Response(ResponseCode.OK, "Квартира была успешно удалена.");
            }
        } catch (ClassCastException e) {
            return new Response(ResponseCode.ERROR, "Непредвиденная ошибка!");
        }
    }


    //FIXME может ли здесь быть выброшено исключение?
    public Response shuffle(Request<?> request) {
        if (request.entity != null) {
            return new Response(ResponseCode.ERROR, "Аргумент для данной команды не предусмотрен.");
        }
        try {
            Collections.shuffle(flats);
        } catch (NullPointerException e) {}
        return new Response(ResponseCode.OK, "Перетасовка коллекции прошла успешно.");
    }


    //FIXME сделать отправку вывода на сервер
    public Response printDescending(Request<?> request) {
        if (request.entity != null) {
            return new Response(ResponseCode.ERROR, "Аргумент для данной команды не предусмотрен.");
        }
        String result = flats.stream().sorted(Collections.reverseOrder())
                .map(Flat::toString)
                .collect(Collectors.joining("\n"));
        return new Response(ResponseCode.OK, "Операция вывода в обратном порядке успешно завершилась. " +
                "Результат: \n" + result);
    }

    public Response exit(Request<?> request) {
        if (request.entity != null) {
            return new Response(ResponseCode.ERROR, "Аргумент для данной команды не предусмотрен.");
        }
            save();
            System.exit(0);
            return null;
    }


    //FIXME может ли здесь быть выброшено исключение?
    public Response sumOfNumberOfBathrooms(Request<?> request) {
        if (request.entity != null) {
            return new Response(ResponseCode.ERROR, "Аргумент для данной команды не предусмотрен.");
        }
        long sum = 0;
        sum = flats.stream().mapToLong(Flat::getNumberOfBathrooms).sum();
        return new Response(ResponseCode.OK, "Подсчёт суммы ванн выполнен успешно. Сумма: " + sum);
    }

    public ArrayList<Flat> loadCollection() {
        lastInitTime = LocalDateTime.now();
        return fileManager.readCollection();
    }


    public Response execute(Request<?> request) {
        return null;
    }
}
