package data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * The class whose collection we will work with.
 */
public class Flat implements Comparable<Flat>, Serializable {
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long area; //Максимальное значение поля: 605, Значение поля должно быть больше 0
    private Integer numberOfRooms; //Значение поля должно быть больше 0
    private Long floor; //Значение поля должно быть больше 0
    private long numberOfBathrooms; //Значение поля должно быть больше 0
    private Transport transport; //Поле не может быть null
    private House house; //Поле не может быть null
    //used to generate a unique Id
    public static long lastId;

    public Flat() {

    }

    public Flat(long id,
                String name,
                Coordinates coordinates,
                LocalDateTime creationDate,
                Long area,
                Integer numberOfRooms,
                Long floor,
                long numberOfBathrooms,
                Transport transport,
                House house) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.floor = floor;
        this.numberOfBathrooms = numberOfBathrooms;
        this.transport = transport;
        this.house = house;
    }

    public Flat(String[] variableArray) throws ArrayIndexOutOfBoundsException, NumberFormatException,
            IllegalStateException, NoSuchElementException, NullPointerException, DateTimeParseException {
            if (variableArray.length == 13) {
                lastId++;
                this.id = lastId;
                this.name = variableArray[0];
                if (Double.parseDouble(variableArray[1]) < 927) {
                    this.coordinates.setX(Double.parseDouble(variableArray[1]));
                }
                if (Float.parseFloat(variableArray[2]) < 24) {
                    this.coordinates.setY(Float.parseFloat(variableArray[2]));
                }
                creationDate = LocalDateTime.now();
                if (Long.parseLong(variableArray[3]) > 0 && Long.parseLong(variableArray[3]) < 605) {
                    this.area = Long.parseLong(variableArray[3]);
                }
                if (Integer.parseInt(variableArray[4]) > 0) {
                    this.numberOfRooms = Integer.parseInt(variableArray[4]);
                }
                if (Long.parseLong(variableArray[5]) > 0) {
                    this.floor = Long.parseLong(variableArray[5]);
                }
                if (Long.parseLong(variableArray[6]) > 0) {
                    this.numberOfBathrooms = Long.parseLong(variableArray[6]);
                }
                try {
                    this.transport = Transport.valueOf(variableArray[7]);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

                if (!variableArray[8].equals(null)) {
                    this.house.setName(variableArray[8]);
                }
                if (Long.parseLong(variableArray[9]) > 0) {
                    this.house.setYear(Long.parseLong(variableArray[9]));
                }
                if (Long.parseLong(variableArray[10]) > 0 && Long.parseLong(variableArray[10]) < 76) {
                    this.house.setNumberOfFloors(Long.parseLong(variableArray[10]));
                }
                if (Long.parseLong(variableArray[11]) > 0) {
                    this.house.setNumberOfFlatsOnFloor(Long.parseLong(variableArray[11]));
                }
                if (Long.parseLong(variableArray[12]) > 0) {
                    this.house.setNumberOfLifts(Long.parseLong(variableArray[12]));
                }
            } else if (variableArray.length == 15) {
                    if (Long.parseLong(variableArray[0]) > 0) {
                        id = Long.parseLong(variableArray[0]);
                    }
                    this.name = variableArray[1];
                    if (Double.parseDouble(variableArray[2]) < 927) {
                        coordinates.setX(Double.parseDouble(variableArray[2]));
                    }
                    if (Float.parseFloat(variableArray[3]) < 24) {
                        coordinates.setY(Float.parseFloat(variableArray[3]));
                    }
                    this.creationDate = LocalDateTime.parse(variableArray[4]);

                    if (Long.parseLong(variableArray[5]) > 0 && Long.parseLong(variableArray[5]) < 605) {
                        area = Long.parseLong(variableArray[5]);
                    }
                    if (Integer.parseInt(variableArray[6]) > 0) {
                        numberOfRooms = Integer.parseInt(variableArray[6]);
                    }
                    if (Long.parseLong(variableArray[7]) > 0) {
                        floor = Long.parseLong(variableArray[7]);
                    }
                    if (Long.parseLong(variableArray[8]) > 0) {
                        numberOfBathrooms = Long.parseLong(variableArray[8]);
                    }
                    try {
                        transport = Transport.valueOf(variableArray[9]);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    }

                    if (!variableArray[10].equals(null)) {
                        house.setName(variableArray[10]);
                    }
                    if (Long.parseLong(variableArray[11]) > 0) {
                        house.setYear(Long.parseLong(variableArray[11]));
                    }
                    if (Long.parseLong(variableArray[12]) > 0 && Long.parseLong(variableArray[12]) < 76) {
                        house.setNumberOfFloors(Long.parseLong(variableArray[12]));
                    }
                    if (Long.parseLong(variableArray[13]) > 0) {
                        house.setNumberOfFlatsOnFloor(Long.parseLong(variableArray[13]));
                    }
                    if (Long.parseLong(variableArray[14]) > 0) {
                        house.setNumberOfLifts(Long.parseLong(variableArray[14]));
                    }
                }
        }

    public Flat(String name,
                Coordinates coordinates,
                LocalDateTime creationDate,
                Long area,
                Integer numberOfRooms,
                Long floor,
                long numberOfBathrooms,
                Transport transport,
                House house) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.floor = floor;
        this.numberOfBathrooms = numberOfBathrooms;
        this.transport = transport;
        this.house = house;
    }

    public void update(Flat flat) {
        this.id = flat.id;
        this.name = flat.name;
        this.coordinates = flat.coordinates;
        this.creationDate = LocalDateTime.now();
        this.area = flat.area;
        this.numberOfRooms = flat.numberOfRooms;
        this.floor = flat.floor;
        this.numberOfBathrooms = flat.numberOfBathrooms;
        this.transport = flat.transport;
        this.house = flat.house;
    }

    {
        coordinates = new Coordinates();
        house = new House();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getArea() {
        return area;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public Long getFloor() {
        return floor;
    }

    public long getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public Transport getTransport() {
        return transport;
    }

    public House getHouse() {
        return house;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Double x, float y) {
        this.coordinates.setX(x);
        this.coordinates.setY(y);
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setFloor(Long floor) {
        this.floor = floor;
    }

    public void setNumberOfBathrooms(long numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public void setHouse(String houseName,
                         Long year,
                         Long numberOfFloors,
                         long numberOfFlatsOnFloor,
                         long numberOfLifts) {
        this.house.setName(houseName);
        this.house.setYear(year);
        this.house.setNumberOfFloors(numberOfFloors);
        this.house.setNumberOfFlatsOnFloor(numberOfFlatsOnFloor);
        this.house.setNumberOfLifts(numberOfLifts);
    }

    @Override
    public int compareTo(Flat flat) {
        return (getCoordinates()).compareTo(flat.getCoordinates());
    }

    public boolean equals(Flat flat) {
        if (Objects.equals(id, flat.id)
        && Objects.equals(name, flat.name)
        && Objects.equals(coordinates, flat.coordinates)
        && Objects.equals(creationDate, flat.creationDate)
        && Objects.equals(area, flat.area)
        && Objects.equals(numberOfRooms, flat.numberOfRooms)
        && Objects.equals(floor, flat.floor)
        && Objects.equals(numberOfBathrooms, flat.numberOfBathrooms)
        && Objects.equals(transport, flat.transport)
        && Objects.equals(house, flat.house)) {
            return true;
        } else return false;
    }

    @Override
    public String toString() {
        return  id + "," + name + "," + coordinates.getX() + "," + coordinates.getY() + ","
                + creationDate + "," + area + "," + numberOfRooms + "," + floor + ","
                + numberOfBathrooms + "," + transport.name() + "," + house.getName() + ","
                + house.getYear() + "," + house.getNumberOfFloors() + ","
                + house.getNumberOfFlatsOnFloor() + "," + house.getNumberOfLifts();
    }
}
