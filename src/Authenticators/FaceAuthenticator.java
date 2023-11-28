package Authenticators;

import java.util.Random;

public class FaceAuthenticator implements Authenticator {
    @Override
    public double getScore() {
        Random random = new Random();
        return Math.min(random.nextGaussian()*15+80, 100);
    }

    @Override
    public void run() {

    }
    @Override
    public String toString() {
        return "Face Authenticator";
    }
}
