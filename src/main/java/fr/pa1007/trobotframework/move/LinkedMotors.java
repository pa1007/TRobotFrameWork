package fr.pa1007.trobotframework.move;

public class LinkedMotors {

    private Motor[] motors;

    public LinkedMotors(Motor... motors) {
        if (motors == null || motors.length == 0) {
            throw new NullPointerException("No motors linked between");
        }
        this.motors = motors;
    }

    public void forward() {
        for (Motor m : motors) {
            m.forward();
        }
    }

    public void changeSpeed(int speed) {
        for (Motor m : motors) {
            m.changeSpeed(speed);
        }
    }

    public void addSpeed(int dSpeed) {
        for (Motor m : motors) {
            m.addSpeed(dSpeed);
        }
    }

    public void reverse() {
        for (Motor m : motors) {
            m.reverse();
        }
    }

    public void forward(int speed) {
        for (Motor m : motors) {
            m.forward(speed);
        }
    }

    public void reverse(int speed) {
        for (Motor m : motors) {
            m.reverse(speed);
        }
    }

    public void stop() {
        for (Motor m : motors) {
            m.stop();
        }
    }
}
