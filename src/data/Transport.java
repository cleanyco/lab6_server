package data;

import java.io.Serializable;

/**
 * An enum containing the data required for an object of type 'Flat'.
 */
public enum Transport implements Serializable {
    FEW,
    NONE,
    NORMAL,
    ENOUGH;

    @Override
    public String toString() {
        return "Data-Class, содержащий данные, необходимые для создания класса 'Flat'.";
    }
}
