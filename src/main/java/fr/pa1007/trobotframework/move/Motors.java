package fr.pa1007.trobotframework.move;

import com.pi4j.io.gpio.Pin;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Motors {

    private static Map<Pin, Motor> registeredMap = Collections.synchronizedMap(new HashMap<>());

    private Motors() {

    }

    public static synchronized Motor getMotor(Class<? extends Motor> clazz, Pin pin, Pin motorPin)
    throws ReflectiveOperationException {
        if (registeredMap.containsKey(motorPin)) {
            return registeredMap.get(motorPin);
        }
        else {
            Motor m = clazz.getConstructor(pin.getClass(), motorPin.getClass()).newInstance(pin, motorPin);
            registeredMap.put(motorPin, m);
            return m;
        }
    }

}
