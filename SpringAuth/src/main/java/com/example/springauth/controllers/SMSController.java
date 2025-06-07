package com.example.springauth.controllers;

import com.example.springauth.services.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import okhttp3.RequestBody;
import okhttp3.*;
import pindo.OkHttpClientCreator;

import java.io.IOException;


@RestController
public class SMSController {


    @Autowired
    private SMSService smsService;

    @Value("${pindo.auth_token}")
    private String pindoAuthToken; // use this same pattern to move environment variables to the properties file

    @GetMapping("/send_sms")
    public String sendTwilioSMS() {
        smsService.sendSMS("+250788963132", "X777. Niga, tell me you have received the sms. Maurice");
        return "SMS sent!";
    }

    // This an sms sender for pindo.io alone and nothing more.
    @GetMapping("/test_sms")
    public String sendTestSMS(){
        // Recommended to use a singleton to avoid performance overheads in a time
        // critical action attached to short expiry times.
        OkHttpClient client = OkHttpClientCreator.getOkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        // For single sms
        String smsJSONRequest = String.format(
                "{\"to\":\"%s\", \"text\": \"%s\", \"sender\":\"%s\"}",
                "+250793897619", "Evolve spring-boot sms test. Common core functionality will not be done by Monday. Today is a public holiday. Regards, Maurice", "PindoTest"
        );
        RequestBody body = RequestBody.create(mediaType, smsJSONRequest);
        String url = "https://api.pindo.io/v1/sms/";

        System.out.println(pindoAuthToken);
        // add the token the environment variables
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", pindoAuthToken))
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.toString();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return "null";
    }
}
