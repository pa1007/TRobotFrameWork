package fr.pa1007.trobotframework.move;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;

/**
 * This class represents an Adafruit 16 channel I2C PWM driver board.
 *
 * @author Marcus Hirt
 */
@SuppressWarnings("unused")
// Not using all commands - yet.
public class PWMDevice {

    private static final int       MODE1         = 0x00;
    private static final int       MODE2         = 0x01;
    private static final int       SUBADR1       = 0x02;
    private static final int       SUBADR2       = 0x03;
    private static final int       SUBADR13      = 0x04;
    private static final int       PRESCALE      = 0xFE;
    private static final int       LED0_ON_L     = 0x06;
    private static final int       LED0_ON_H     = 0x07;
    private static final int       LED0_OFF_L    = 0x08;
    private static final int       LED0_OFF_H    = 0x09;
    private static final int       ALL_LED_ON_L  = 0xFA;
    private static final int       ALL_LED_ON_H  = 0xFB;
    private static final int       ALL_LED_OFF_L = 0xFC;
    private static final int       ALL_LED_OFF_H = 0xFD;
    // Bits
    private static final int       RESTART       = 0x80;
    private static final int       SLEEP         = 0x10;
    private static final int       ALLCALL       = 0x01;
    private static final int       INVRT         = 0x10;
    private static final int       OUTDRV        = 0x04;
    private final        int       address;
    private final        I2CDevice i2cDevice;
    private final        int       bus;

    /**
     * Constructs a PWM device using the default settings. (I2CBUS.BUS_1, 0x40)
     *
     * @throws IOException if there was communication problem
     */
    public PWMDevice() throws IOException, I2CFactory.UnsupportedBusNumberException {
        // 0x40 is the default address used by the AdaFruit PWM board.
        this(I2CBus.BUS_1, 0x40);
    }

    /**
     * Creates a software interface to an Adafruit 16 channel I2C PWM driver
     * board (PCA9685).
     *
     * @param bus     the I2C bus to use.
     * @param address the address to use.
     * @throws IOException if there was communication problem
     * @see I2CBus
     */
    public PWMDevice(int bus, int address) throws IOException, I2CFactory.UnsupportedBusNumberException {
        this.bus = bus;
        this.address = address;
        i2cDevice = I2CFactory.getInstance(bus).getDevice(address);
        initialize();
    }

    /**
     * Sets all PWM channels to the provided settings.
     *
     * @param on  when to turn on the signal [0, 4095]
     * @param off when to turn off the signal [0, 4095]
     * @throws IOException if there was a problem communicating with the device.
     */
    public void setAllPWM(int on, int off) throws IOException {
        write(ALL_LED_ON_L, (byte) (on & 0xFF));
        write(ALL_LED_ON_H, (byte) (on >> 8));
        write(ALL_LED_OFF_L, (byte) (off & 0xFF));
        write(ALL_LED_OFF_H, (byte) (off >> 8));
    }

    /**
     * Sets the PWM frequency to use. This is common across all channels. For
     * controlling RC servos, 50Hz is a good starting point.
     *
     * @param frequency the PWM frequency to use, in Hz.
     * @throws IOException if a problem occured accessing the device.
     */
    public void setPWMFreqency(double frequency) throws IOException {
        double prescaleval = 25000000.0;
        prescaleval /= 4096.0;
        prescaleval /= frequency;
        prescaleval -= 1.0;
        double prescale = Math.floor(prescaleval + 0.5);
        int    oldmode  = i2cDevice.read(MODE1);
        int    newmode  = (oldmode & 0x7F) | 0x10;
        write(MODE1, (byte) newmode);
        write(PRESCALE, (byte) (Math.floor(prescale)));
        write(MODE1, (byte) oldmode);
        sleep(50);
        write(MODE1, (byte) (oldmode | 0x80));
    }

    /**
     * Returns one of the PWM channels on the device. Allowed range is [0, 15].
     *
     * @param channel the channel to retrieve.
     * @return the specified PWM channel.
     */
    public PWMChannel getChannel(int channel) {
        return new PWMChannel(channel);
    }

    /**
     * Returns the address used when communicating with this PWM device.
     *
     * @return the address used when communicating with this PWM device.
     */
    public int getAddress() {
        return address;
    }

    /**
     * Returns the bus used when communicating with this PWM device.
     *
     * @return the bus used when communicating with this PWM device.
     */
    public int getBus() {
        return bus;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            // Don't care
        }
    }

    private void write(int address, byte b) throws IOException {
        i2cDevice.write(address, b);
    }

    private int read(int address) throws IOException {
        return i2cDevice.read(address);
    }

    private void initialize() throws IOException {
        setAllPWM(0, 0);
        write(MODE2, (byte) OUTDRV);
        write(MODE1, (byte) ALLCALL);
        sleep(50);
        int mode1 = read(MODE1);
        mode1 = mode1 & ~SLEEP;
        write(MODE1, (byte) mode1);
        sleep(50);
    }

    /**
     * Use to control a PWM channel on the PWM device.
     *
     * @see fr.pa1007.trobotframework.move.PWMDevice#getChannel(int)
     */
    public class PWMChannel {

        private final int channel;

        private PWMChannel(int channel) {
            if (channel < 0 || channel > 15) {
                throw new IllegalArgumentException("There is no channel "
                                                   + channel + " on the board.");
            }
            this.channel = channel;
        }

        /**
         * Configures the PWM pulse for the PWMChannel.
         *
         * @param on  when to go from low to high [0, 4095]. 0 means at the very
         *            start of the pulse, 4095 at the very end.
         * @param off when to go from high to low [0, 4095]. 0 means at the very
         *            start of the pulse, 4095 at the very end.
         * @throws IOException
         */
        public void setPWM(int on, int off) throws IOException {
            i2cDevice.write(LED0_ON_L + 4 * channel, (byte) (on & 0xFF));
            i2cDevice.write(LED0_ON_H + 4 * channel, (byte) (on >> 8));
            i2cDevice.write(LED0_OFF_L + 4 * channel, (byte) (off & 0xFF));
            i2cDevice.write(LED0_OFF_H + 4 * channel, (byte) (off >> 8));
        }
    }
}
