package smith.lib.net;

import com.google.gson.Gson;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.*;
import okhttp3.*;

@SuppressWarnings({"Unused"})
class SConnectController {
    
    private static final int SOCKET_TIMEOUT = 15000;
    private static final int READ_TIMEOUT = 25000;
    protected OkHttpClient client;
    private static SConnectController mInstance;

    public static synchronized SConnectController getInstance() {
        if (mInstance == null) mInstance = new SConnectController();
        return mInstance;
    }

    private OkHttpClient getClient() {
        if (client == null) {
            var builder = new OkHttpClient.Builder();
            try {
                final var trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

                        @Override public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

                        @Override public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[] {};
                        }
                    }
                };

                final var sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new SecureRandom());
                final var sslSocketFactory = sslContext.getSocketFactory();
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.connectTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);
                builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
                builder.writeTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            } catch (Exception e) {}
            
            client = builder.build();
        }

        return client;
    }

    public void connect(final SConnect sconnect, String method, String url, final String tag, final SConnectCallBack callback) {
        var reqBuilder = new Request.Builder();
        var headerBuilder = new Headers.Builder();

        if (sconnect.getHeaders().size() > 0) {
            sconnect.getHeaders().forEach((key, value) -> headerBuilder.add(key, value));
        }

        try {
            if (sconnect.getParamsType() == SConnect.PARAM) {
                if (method.equals("GET")) {
                    HttpUrl.Builder httpBuilder;
                    try {
                        httpBuilder = HttpUrl.parse(url).newBuilder();
                    } catch (NullPointerException ne) {
                        throw new NullPointerException("Unexpected url: " + url);
                    }

                    if (sconnect.getParams().size() > 0) {
                        sconnect.getParams().forEach((key, value) -> httpBuilder.addQueryParameter(key, value));
                    }

                    reqBuilder.url(httpBuilder.build()).headers(headerBuilder.build()).get();
                } else {
                    var formBuilder = new FormBody.Builder();
                    if (sconnect.getParams().size() > 0) {
                        sconnect.getParams().forEach((key, value) -> formBuilder.add(key, value));
                    }
                    var reqBody = formBuilder.build();
                    reqBuilder.url(url).headers(headerBuilder.build()).method(method, reqBody);
                }
            } else {
                var reqBody = RequestBody.create("application/json; charset=utf-8;", MediaType.parse(new Gson().toJson(sconnect.getParams())));
                if (method.equals("GET")) {
                    reqBuilder.url(url)
                        .headers(headerBuilder.build())
                        .get();
                } else {
                    reqBuilder.url(url)
                        .headers(headerBuilder.build())
                        .method(method, reqBody);
                }
            }

            var req = reqBuilder.build();

            getClient().newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    sconnect.getActivity().runOnUiThread(new Runnable() {
                        @Override public void run() {
                            callback.onFailure(new SResponse(e.getMessage()), tag);
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final var responseBody = response.body().string().trim();
                    sconnect.getActivity().runOnUiThread(new Runnable() {
                        @Override public void run() {
                            var b = response.headers();
                            var map = new HashMap<String, String>();
                            for (var k : b.names()) {
                                map.put(k, b.get(k) != null ? b.get(k) : "null");
                            }
                            callback.onSuccess(new SResponse(responseBody), tag, map);
                        }
                    });
                }
            });
        } catch (Exception e) {
            callback.onFailure(new SResponse(e.getMessage()), tag);
        }
    }
}