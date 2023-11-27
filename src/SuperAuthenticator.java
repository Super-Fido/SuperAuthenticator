import Authenticators.Authenticator;

import java.util.ArrayList;

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
            totalScore += getScore(auth) * auth.getWeight();
        }
        System.out.println("Total Score: " + totalScore);
        return  totalScore >= 80;
    }

    void update(){
        AuthenticatorData faultyAuthenticator = auths.get(0);
        for (AuthenticatorData auth : auths) {
            if(getScore(auth) < getScore(faultyAuthenticator)){
                faultyAuthenticator = auth;
            }
        }

        double weightToRemove = 0.1;
        double weightToAdd = weightToRemove/(auths.size()-1);


        for (AuthenticatorData auth : auths) {
            if(auth.equals(faultyAuthenticator)){
                auth.setWeight(auth.getWeight() -  weightToRemove);
            }
            else{
                auth.setWeight(auth.getWeight() + weightToAdd);
            }

        }
    };


    private double getScore(AuthenticatorData data) {
        ArrayList<Double> scores = data.getScores();
        int size = scores.size();
        if(size < 10){
            return 100;
        }

        double score = 0;
        for(int i = 0; i < 10; i++){
            score += scores.get(size - i - 1) * weightsForScores[i];
        }
        return score;
    }
}
