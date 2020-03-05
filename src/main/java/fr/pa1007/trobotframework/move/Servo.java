package fr.pa1007.trobotframework.move;

import com.pi4j.io.i2c.I2CFactory;
import fr.pa1007.trobotframework.utils.Module;
import java.io.IOException;

public abstract class Servo {

    public static final int                  MIN_ANGLE      = 22;
    public static final int                  MAX_ANGLE      = 33;
    public static final int                  STRAIGHT_ANGLE = 25;
    protected           PWMDevice            pwmDevice;
    protected           PWMDevice.PWMChannel channel;
    protected           int                  angle;
    protected           Module               instance;


    public Servo(Module main) throws IOException, I2CFactory.UnsupportedBusNumberException {
        pwmDevice = new PWMDevice();
        channel = pwmDevice.getChannel(0);
        instance = main;
    }

    public Servo() {

    }

    public void move(int angle) {
        if (angle > MAX_ANGLE) {
            angle = MAX_ANGLE;
        }
        if (angle < MIN_ANGLE) {
            angle = MIN_ANGLE;
        }
        this.angle = angle;
        int r = angle * 10;
        try {
            channel.setPWM(0, r);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getAngle() {
        return angle;
    }

    public PWMDevice getPwm() {
        return pwmDevice;
    }
}
