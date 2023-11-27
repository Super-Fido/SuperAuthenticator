import Authenticators.Authenticator;

import java.util.ArrayList;

public class AuthenticatorData {
    private Authenticator authenticator;
    private ArrayList<Double> scores;
    private double weight;

    public AuthenticatorData(Authenticator authenticator){
        this.authenticator = authenticator;
        this.scores = new ArrayList<>();
        this.weight = 1;
    }
    public AuthenticatorData(Authenticator authenticator, double weight){
        this.authenticator = authenticator;
        this.scores = new ArrayList<>();
        this.weight = weight;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public ArrayList<Double> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Double> scores) {
        this.scores = scores;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
