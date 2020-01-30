package de.mvitz.dynamic_proxy.http;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static java.net.http.HttpResponse.BodyHandlers.ofString;

public class HttpRequestProxy implements InvocationHandler {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request requestDefinition = method.getAnnotation(Request.class);
        HttpRequest request = HttpRequest.newBuilder()
                .method(requestDefinition.method(), noBody())
                .uri(URI.create(requestDefinition.uri()))
                .build();
        HttpResponse<String> response = httpClient.send(request, ofString());
        return response.body();
    }

    @Target(METHOD)
    @Retention(RUNTIME)
    public @interface Request {

        String method() default "GET";
        String uri();
    }

    public static <T> T create(Class<T> definition) {
        return (T) Proxy.newProxyInstance(
                definition.getClassLoader(),
                new Class<?>[] { definition },
                new HttpRequestProxy()
        );
    }
}
