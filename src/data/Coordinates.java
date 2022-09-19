package data;

import java.io.Serializable;

/**
 * A class containing the data required for an object of type 'Flat'.
 */
public class Coordinates implements Comparable<Coordinates>, Serializable {
    private Double x; //Максимальное значение поля: 927, Поле не может быть null
    private float y; //Максимальное значение поля: 24

    public Double getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Double sumXY() {
        return (Double) x+y;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Coordinates(Double x, float y){
        this.x = x;
        this.y = y;
    }

    public Coordinates() {}

    @Override
    public String toString() {
        return "Data-Class, содержащий данные, необходимые для создания класса 'Flat'.";
    }

    @Override
    public int compareTo(Coordinates o) {
        return sumXY().compareTo(o.sumXY());
    }
}

