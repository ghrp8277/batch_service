package com.example.batchservice.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
    public static String sendGetRequest(String url) throws Exception {
        return sendHttpRequest(url, "GET", null);
    }

    public static String sendPostRequest(String url, String body) throws Exception {
        return sendHttpRequest(url, "POST", body);
    }

    public static String sendPutRequest(String url, String body) throws Exception {
        return sendHttpRequest(url, "PUT", body);
    }

    public static String sendDeleteRequest(String url, String body) throws Exception {
        return sendHttpRequest(url, "DELETE", body);
    }

    private static String sendHttpRequest(String url, String method, String body) throws Exception {
        HttpURLConnection con = createConnection(url, method);
        if (body != null) {
            sendRequestBody(con, body);
        }
        return getResponse(con);
    }

    private static HttpURLConnection createConnection(String url, String method) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json");
        return con;
    }

    private static void sendRequestBody(HttpURLConnection con, String body) throws Exception {
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = body.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
    }

    private static String getResponse(HttpURLConnection con) throws Exception {
        int responseCode = con.getResponseCode();
        if (isSuccess(responseCode)) {
            return readResponse(con);
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }

    private static boolean isSuccess(int responseCode) {
        return responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED;
    }

    private static String readResponse(HttpURLConnection con) throws Exception {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        }
    }
}