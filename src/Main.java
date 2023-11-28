import Authenticators.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException {
        final String PASSWORD = "admin";
        Scanner myObj = new Scanner(System.in);

        double weightUpdate = 0.05;

        double[] slidingWindowWeights = {0.2, 0.15, 0.1, 0.09, 0.09, 0.08, 0.08, 0.08, 0.07, 0.06};
//        double[] slidingWindowWeights = {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
//        double[] slidingWindowWeights = {0.2, 0.2, 0.2, 0.2, 0.2, 0, 0, 0, 0, 0};
//        double[] slidingWindowWeights = {0.5, 0.5, 0, 0, 0, 0, 0, 0, 0, 0};
//        double[] slidingWindowWeights = {1.0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        KeyboardAuthenticator keyAuth = new KeyboardAuthenticator();
        MouseAuthenticator mouseAuth = new MouseAuthenticator();
        FaceAuthenticator faceAuth = new FaceAuthenticator();
        GaitAuthenticator gaitAuth = new GaitAuthenticator();

        ArrayList<Authenticator> authenticators = new ArrayList<>();
        authenticators.add(keyAuth);
        authenticators.add(mouseAuth);
        authenticators.add(faceAuth);
        authenticators.add(gaitAuth);

        SuperAuthenticator authenticator = new SuperAuthenticator(authenticators, slidingWindowWeights);
//        authenticator.authenticatorMakeCredential();

        while (true) {
            if(!authenticator.authenticate()) {
                System.out.println("Please enter the password");
                String password = myObj.nextLine();
                if (password.equals(PASSWORD)) {
                    authenticator.update(weightUpdate);
                }
                else{
                    break;
                }
            }
            Thread.sleep(2000);
        }
    }
}