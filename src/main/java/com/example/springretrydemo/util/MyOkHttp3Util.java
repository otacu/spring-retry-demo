package com.example.springretrydemo.util;

import okhttp3.*;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public final class MyOkHttp3Util {

    private MyOkHttp3Util() {
    }

    /**
     * OkHttpClient
     */
    private static OkHttpClient client;

    /**
     * 超时时间
     */
    public static final int TIMEOUT = 90;

    /**
     * application/x-www-form-urlencoded
     */
    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    static {
        TrustAllManager trustAllManager = new TrustAllManager();
        client = new OkHttpClient().newBuilder()
                .sslSocketFactory(createTrustAllSSLFactory(trustAllManager), trustAllManager)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("101.89.78.26", 8080)))
                .build();
    }

    /**
     * 同步发送post请求 map为body
     *
     * @param url  请求url
     * @param json 请求参数
     * @return 返回回来的JSONObject
     * @throws Exception 异常
     */
    public static String postJson(String url, String json) throws Exception {
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String data = response.body().string();
            return data;
        }

        return "请求失败，请检查参数";
    }

    /**
     * 创建信任所有sslFactory
     *
     * @param trustAllManager TrustAllManager
     * @return SSLSocketFactory
     */
    protected static SSLSocketFactory createTrustAllSSLFactory(TrustAllManager trustAllManager) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, new TrustManager[]{trustAllManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return ssfFactory;
    }
}

/**
 * 信任ssl的类
 */
class TrustAllManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}

