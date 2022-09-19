package server.util;

import data.Flat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

//TODO елси
public class FileManager {
    private final String fileName;
    private final String HEADERS = "ID, NAME, COORDINATES, CREATION_DATE, AREA, NUMBER_OF_ROOMS, FLOOR, " +
            "NUMBER_OF_BATHROOMS, TRANSPORT, HOUSE_NAME, HOUSE_YEAR, HOUSE_NUMBEROFFLOORS " +
            "HOUSE_NUMBEROFFLATONFLOOR, HOUSE_NUMBEROFLIFTS";
    private File file;

    public FileManager(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }

    //TODO: добавить запись строки с заголовками
    public void writeCollection(ArrayList<Flat> flats) throws IOException {
        if (file.canWrite() && file.exists() && file.isFile()) {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(HEADERS + "\n");
            fileWriter.flush();
            for (Flat flat : flats) {
                fileWriter.write(flat.toString() + System.lineSeparator());
                fileWriter.flush();
            }
            System.out.println("Запись коллекции в файл прошла успешно.");

        } else System.out.println("Не удалось записать коллекцию в файл.");
    }

    public ArrayList<Flat> readCollection() {
        if (!(file.canRead() && file.exists() && file.isFile())) {
            System.out.println("Файл с коллекцией невозможно прочитать.");
            System.exit(-1);
        }
        ArrayList<Flat> flats = new ArrayList<>();
        String line;

        int index = 0;
        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] lineArr = line.replace(" ", "").split(",");
                if (lineArr.length > 0 && (lineArr.length == 13 || lineArr.length == 15) && index != 0) {
                    flats.add(new Flat(lineArr));
                }
                index++;
            }  } catch (FileNotFoundException e) {
            System.out.println("Файл для загрузки коллекции не был найден.");
            System.exit(-1);
            //здесь было FileNotFoundException()
        } catch (NoSuchElementException | NullPointerException | ArrayIndexOutOfBoundsException| NumberFormatException |
                IllegalStateException | DateTimeParseException e) {
            System.out.println("Файл содержит недопустимые строки, размер коллекции: 0.");
            return new ArrayList<>();
        }
            System.out.println("Коллекция успешно загружена! Размер: " + flats.size());
            return flats;

    }
}
