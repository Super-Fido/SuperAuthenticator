package Authenticators;

import java.util.Random;

public class KeyboardAuthenticator implements Authenticator {
    @Override
    public double getScore() {
        Random random = new Random();
        return random.nextGaussian()*1+90;
    }

    @Override
    public void run() {

    }
}
