package Authenticators;

import java.util.Random;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GaitAuthenticator implements Authenticator {

    public static String sendPOSTRequest(String urlStr, String urlParameters) throws Exception {
    URL url = new URL(urlStr);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

    conn.setDoOutput(true);
    try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
        wr.writeBytes(urlParameters);
        wr.flush();
    }

    StringBuilder response = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
    }

    return response.toString();
}

    @Override
    public double getScore() {
        double authScore = 0;
        try {
            String urlParameters = "username=john123"; // Replace with your parameters
            String response = sendPOSTRequest("https://superfidoauthapi-production.up.railway.app/get-gait-score", urlParameters);
            String startToken = "\"authScore\":[";
            String endToken = "]}";

            int startIndex = response.indexOf(startToken) + startToken.length();
            int endIndex = response.indexOf(endToken, startIndex);
            String scoreStr = response.substring(startIndex, endIndex);

            authScore = Double.parseDouble(scoreStr);

            

        } catch (Exception e) {
            e.printStackTrace();
        }

        return authScore*100;
    }


    @Override
    public void run() {

    }
    @Override
    public String toString() {
        return "Gait Authenticator";
    }
}
