package data;

import java.io.Serializable;

/**
 * A class containing the data required for an object of type 'Flat'.
 */
public class House implements Serializable {
    private String name; //Поле не может быть null
    private Long year; //Значение поля должно быть больше 0
    private Long numberOfFloors; //Максимальное значение поля: 76, Значение поля должно быть больше 0
    private long numberOfFlatsOnFloor; //Значение поля должно быть больше 0
    private long numberOfLifts; //Значение поля должно быть больше 0

    public String getName() {
        return name;
    }

    public Long getYear() {
        return year;
    }

    public Long getNumberOfFloors() {
        return numberOfFloors;
    }

    public long getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    public long getNumberOfLifts() {
        return numberOfLifts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public void setNumberOfFloors(Long numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public void setNumberOfFlatsOnFloor(long numberOfFlatsOnFloor) {
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    public void setNumberOfLifts(long numberOfLifts) {
        this.numberOfLifts = numberOfLifts;
    }

    public House(String name, Long year, Long numberOfFloors, long numberOfFlatsOnFloor, long numberOfLifts) {
        this.name = name;
        this.year = year;
        this.numberOfFloors = numberOfFloors;
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
        this.numberOfLifts = numberOfLifts;
    }

    public House() {}

    @Override
    public String toString() {
        return "Data-Class, содержащий данные, необходимые для создания класса 'Flat'.";
    }
}

