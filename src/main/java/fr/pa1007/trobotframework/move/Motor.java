package fr.pa1007.trobotframework.move;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;

public abstract class Motor {

    /**
     * We need a controller for adding the Pin
     */
    protected GpioController gpio;

    /**
     * The pin for the pwm GPIO.
     *
     * @since 0.1
     */
    protected Pin pwmPin;

    /**
     * The output digitam.
     *
     * @since 0.1
     */
    protected GpioPinDigitalOutput forwardOutput;

    /**
     * The forward pin.
     *
     * @since 0.1
     */
    protected Pin pin;


    protected PCA9685GpioProvider provider;


    /**
     * For keeping the last speed input
     */
    protected int pwm;

    /**
     * If you need it
     */
    protected GpioPinPwmOutput output;

    public Motor(Pin pwmPin, Pin pin) {
        this.pwmPin = pwmPin;
        this.pin = pin;
    }


    /**
     * @return The output digitam.
     * @since 0.1
     */
    public GpioPinDigitalOutput getForwardOutput() {
        return this.forwardOutput;
    }

    /**
     * Sets the <code>forwardOutput</code> field.
     *
     * @param forwardOutput The output digitam.
     * @since 0.1
     */
    public void setForwardOutput(GpioPinDigitalOutput forwardOutput) {
        this.forwardOutput = forwardOutput;
    }


    /**
     * @return The forward pin.
     * @since 0.1
     */
    public Pin getPin() {
        return this.pin;
    }

    /**
     * Sets the <code>forwardPin</code> field.
     *
     * @param pin The forward pin.
     * @since 0.1
     */
    public void setPin(Pin pin) {
        this.pin = pin;
    }

    /**
     * @return The pin for the pwm GPIO.
     * @since 0.1
     */
    public Pin getPwmPin() {
        return this.pwmPin;
    }

    /**
     * Sets the <code>pwmPin</code> field.
     *
     * @param pwmPin The pin for the pwm GPIO.
     * @since 0.1
     */
    public void setPwmPin(Pin pwmPin) {
        this.pwmPin = pwmPin;
    }


    public void forward() {
        forward(pwm);
    }

    public void reverse() {
        reverse(pwm);
    }

    public abstract void forward(int speed);

    public abstract void reverse(int speed);

    public abstract void changeSpeed(int speed);

    public abstract void addSpeed(int dSpeed);

    public abstract void stop();

    public abstract void forwardWithoutChange(int speed);

    public abstract void reverseWithoutChange(int speed);

    public abstract void unBound();

    protected abstract void init();
}