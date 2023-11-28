package Authenticators;

import java.util.Random;

public class MouseAuthenticator implements Authenticator {
    @Override
    public double getScore() {
        Random random = new Random();
        return random.nextGaussian()*10+60;
    }

    @Override
    public void run() {

    }
    @Override
    public String toString() {
        return "Mouse Authenticator";
    }
}
