package racingcar;

import java.util.Objects;

public class Car {
    public static final int MIN_CAR_NAME_LENGTH = 1;
    public static final int MAX_CAR_NAME_LENGTH = 5;
    
    private static final int POWER_THRESHOLD = 4;

    private final String name;
    private int position;
    private final Engine engine;

    public static Car from(String name, Engine engine) {
        return Car.from(CarInfo.from(name, 0), engine);
    }

    public static Car from(CarInfo info, Engine engine) {
        return new Car(info, engine);
    }

    private Car(CarInfo carInfo, Engine engine) {
        this.name = getValidCarName(carInfo.getName());
        this.position = carInfo.getPosition();
        this.engine = engine;
    }

    private static String getValidCarName(String name) {
        String nameWithTrim = name.trim();
        checkNameLength(nameWithTrim);
        return nameWithTrim;
    }

    private static void checkNameLength(String name) {
        if (name.length() < MIN_CAR_NAME_LENGTH) {
            throw new RuntimeException("Car name too short.");
        }
        if (name.length() > MAX_CAR_NAME_LENGTH) {
            throw new RuntimeException("car name too long.");
        }
    }

    public void moveOrStop() {
        if (Objects.isNull(engine)) {
            throw new RuntimeException("Engine is null.");
        }
        if (engine.getPower() >= POWER_THRESHOLD) {
            position++;
        }
    }

    public CarInfo getCarInfo() {
        return CarInfo.from(name, position);
    }
}
