/*
 * Copyright (C) 2023, Hussein Shakir (Dev. Smith)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package smith.lib.net;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressWarnings({"unused", "all"})
class SConnectController {

    private static final int SOCKET_TIMEOUT = 15000;
    private static final int READ_TIMEOUT = 25000;

    protected OkHttpClient client;
    private static SConnectController mInstance;

    public static synchronized SConnectController getInstance() {
        if (mInstance == null) mInstance = new SConnectController();
        return mInstance;
    }

    @SuppressLint({"CustomX509TrustManager", "TrustAllX509TrustManager", "BadHostnameVerifier"})
    private OkHttpClient getClient() {
        if (client == null) {
            var builder = new OkHttpClient.Builder();
            try {
                final var trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override public void checkClientTrusted(X509Certificate[] chain, String authType) {}

                            @Override public void checkServerTrusted(X509Certificate[] chain, String authType) {}

                            @Override public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[]{};}
                        }
                };
                final var sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new SecureRandom());
                final var sslSocketFactory = sslContext.getSocketFactory();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.connectTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);
                builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
                builder.writeTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
                builder.hostnameVerifier((hostname, session) -> true);
            } catch (Exception ignored) {}
            client = builder.build();
        }
        return client;
    }

    public void connect(@NonNull final SConnect sconnect, String method, String url, final String tag, final SConnectCallBack callback) {
        var reqBuilder = new Request.Builder();
        var headerBuilder = new Headers.Builder();

        if (sconnect.getHeaders().size() > 0)
            sconnect.getHeaders().forEach((key, value) -> headerBuilder.add(key, String.valueOf(value)));

        try {
            if (sconnect.getParamsType() == SConnect.PARAM) {
                if (method.equals("GET")) {
                    HttpUrl.Builder httpBuilder;
                    try {
                        httpBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();
                    } catch (NullPointerException ne) {
                        callback.onFailure(new SResponse("Unexpected url: " + url), tag);
                        return;
                    }

                    if (sconnect.getParams().size() > 0)
                        sconnect.getParams().forEach((key, value) -> httpBuilder.addQueryParameter(key, String.valueOf(value)));

                    reqBuilder.url(httpBuilder.build()).headers(headerBuilder.build()).get();
                } else {
                    var formBuilder = new FormBody.Builder();
                    if (sconnect.getParams().size() > 0)
                        sconnect.getParams().forEach((key, value) -> formBuilder.add(key, String.valueOf(value)));
                    var reqBody = formBuilder.build();
                    reqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);
                }
            } else {
                if(sconnect.getMediaType() == "") {
                	var reqBody = RequestBody.create("application/json; charset=utf-8;",MediaType.parse(new Gson().toJson(sconnect.getParams())));
                        if (method.equals("GET")) reqBuilder.url(url).headers(headerBuilder.build()).get();
                        else reqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);
                } else {
                    var reqBody = RequestBody.create(MediaType.parse(sconnect.getMediaType()), sconnect.getParams().toString().replace("{", "").replace("}", ""));
                        if (method.equals("GET")) reqBuilder.url(url).headers(headerBuilder.build()).get();
                        else reqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);
                }
            }

            var req = reqBuilder.build();

            getClient().newCall(req).enqueue(new Callback() {
                @Override public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                    sconnect.getActivity().runOnUiThread(() -> callback.onFailure(new SResponse(e.getMessage()), tag));
                }

                @Override public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                    final var responseBody = response.body().string().trim();
                    sconnect.getActivity().runOnUiThread(() -> {
                        var headers = response.headers();
                        var map = new HashMap<String, Object>();
                        for (var key : headers.names()) {
                            Object value = headers.get(key) != null ? headers.get(key) : "null";
                            map.put(key, value);
                        }
                        callback.onSuccess(new SResponse(responseBody), tag, map);
                    });
                }
            });
        } catch (Exception e) {callback.onFailure(new SResponse(e.getMessage()), tag);}
    }
}
