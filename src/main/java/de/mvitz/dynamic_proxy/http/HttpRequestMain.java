package de.mvitz.dynamic_proxy.http;

import de.mvitz.dynamic_proxy.http.HttpRequestProxy.Request;

public class HttpRequestMain {

    public static void main(String[] args) {
        GoogleClient googleClient = HttpRequestProxy.create(GoogleClient.class);
        System.out.println(googleClient.google());
    }

    interface GoogleClient {

        @Request(method = "GET", uri = "https://google.de")
        String google();
    }
}
