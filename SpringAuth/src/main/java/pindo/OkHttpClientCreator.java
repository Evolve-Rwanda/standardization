package pindo;

import okhttp3.OkHttpClient;


public class OkHttpClientCreator {

    private static OkHttpClient client;

    private OkHttpClientCreator() {
    }

    public static OkHttpClient getOkHttpClient(){
        if (client == null) {
            client = new OkHttpClient();
        }
        return client;
    }
}
