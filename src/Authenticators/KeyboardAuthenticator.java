package Authenticators;

import java.util.Random;

public class KeyboardAuthenticator implements Authenticator {
    @Override
    public double getScore() {
        Random random = new Random();
        return random.nextGaussian()*1+85;
    }

    @Override
    public void run() {

    }
    @Override
    public String toString() {
        return "Keyboard Authenticator";
    }
}
