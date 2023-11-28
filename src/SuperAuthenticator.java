import Authenticators.Authenticator;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SuperAuthenticator {
    private ArrayList<AuthenticatorData> auths;
    private double[] weightsForScores = {0.2, 0.15, 0.1, 0.09, 0.09, 0.08, 0.08, 0.08, 0.07, 0.06};

    public SuperAuthenticator(ArrayList<Authenticator> authenticators) {
        auths = new ArrayList<>();
        int numbOfAuthenticators = authenticators.size();
        double weights = 1.0/numbOfAuthenticators;
        for (Authenticator auth : authenticators) {
            auths.add(new AuthenticatorData(auth, weights));
            auth.run();
        }
    }

    public SuperAuthenticator(ArrayList<Authenticator> authenticators, double[] weightsForScores) {
        this.weightsForScores = weightsForScores;
        auths = new ArrayList<>();
        int numbOfAuthenticators = authenticators.size();
        double weights = 1.0/numbOfAuthenticators;
        for (Authenticator auth : authenticators) {
            auths.add(new AuthenticatorData(auth, weights));
            auth.run();
        }
    }

    boolean authenticate(){
        double totalScore = 0;
        for (AuthenticatorData auth : auths) {
            double score = auth.getAuthenticator().getScore();
            auth.getScores().add(score);
            double authScore = getScore(auth);
            System.out.println(auth.getAuthenticator().toString() + " score: " + authScore + ", weigh: "+ auth.getWeight());
            totalScore += authScore * auth.getWeight();
        }
        System.out.println("Total Score: " + totalScore);
        System.out.println();
        return  totalScore >= 80;
    }

    void update(double variation){
        AuthenticatorData faultyAuthenticator = auths.get(0);
        for (AuthenticatorData auth : auths) {
            if(getScore(auth) < getScore(faultyAuthenticator)){
                faultyAuthenticator = auth;
            }
        }

        auths.sort((obj1, obj2) -> {
            // Compare based on the size property
            return (int) (getScore(obj1) - getScore(obj2));
        });

        int size = auths.size();
        double spread = (variation*2)/(size-1);
        double[] weighDistribution = new double[size];
        for (int i=0; i<size; i++){
            weighDistribution[i] = -variation + spread*i;
        }


        for (int i=0; i<size; i++) {
            auths.get(i).setWeight(auths.get(i).getWeight() + weighDistribution[i]);
        }
    };


    private double getScore(AuthenticatorData data) {
        ArrayList<Double> scores = data.getScores();
        int size = scores.size();
        if(size < this.weightsForScores.length){
            return 100;
        }

        double score = 0;
        for(int i = 0; i < 10; i++){
            score += scores.get(size - i - 1) * weightsForScores[i];
        }
        return score;
    }



    // CTAP2

    public void authenticatorMakeCredential() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        Key pub = kp.getPublic();
        Key pvt = kp.getPrivate();
        System.out.println(pub);
        System.out.println(pvt);
    }
}
