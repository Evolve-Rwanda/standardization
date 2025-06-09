package com.example.springauth.controllers;

import com.example.springauth.models.jpa.SentSMS;
import com.example.springauth.models.json.IResponseModel;
import com.example.springauth.models.json.PindoRecipientModel;
import com.example.springauth.models.json.pindo.PindoBulkMessageResponseModel;
import com.example.springauth.models.json.pindo.PindoSingleMessageResponseModel;
import com.example.springauth.services.SMSService;
import com.example.springauth.services.SentSMSService;
import com.example.springauth.utilities.Configuration;
import com.example.springauth.utilities.DateTime;
import com.example.springauth.utilities.SentSMSIdGenerator;
import com.example.springauth.utilities.decoders.json.PindoUserDecoder;
import com.example.springauth.utilities.decoders.json.SMSResponseModelDecoder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import okhttp3.RequestBody;
import okhttp3.*;
import pindo.OkHttpClientCreator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
public class SMSController {

    @Autowired
    SentSMSService sentSMSService;

    @Autowired
    private SMSService smsService;

    @Value("${pindo.auth_token}")
    private String pindoAuthToken;

    @Value("${pindo.sender_name}")
    private String pindoSender;

    // This section applies to twilio sms sending alone. It is left here as an option
    @GetMapping("/send_sms")
    public String sendTwilioSMS() {
        smsService.sendSMS("+250788963132", "X777. Niga, tell me you have received the sms. Maurice");
        return "SMS sent!";
    }

    // This an sms sender for pindo.io alone and nothing more.
    // There are four parts to this section.
    // 1. A test sms endpoint for single message sending.
    // 2. A test sms endpoint for bulk messaging.
    // 3. An endpoint for single message sending.
    // 4. An endpoint for bulk message sending.
    // Besides sending sms, the other endpoints that are still needed for this section include -
    // 1. Find the latest sms sent that matches a particular phone number.
    // 2. Find all the historical sms messages sent to a particular phone number
    // These endpoints may not be very import right now since the otp service already will have kept a record of the
    // OTP code that may necessitate looking up the latest sms sent to a particular phone number.

    @GetMapping("/test_pindo_sms")
    public String testSingleSMS(){
        OkHttpClient client = OkHttpClientCreator.getOkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String smsJSONRequest = String.format(
                "{\"to\":\"%s\", \"text\": \"%s\", \"sender\":\"%s\"}",
                "+250793897619", "Evolve spring-boot sms test.", pindoSender
        );
        RequestBody body = RequestBody.create(mediaType, smsJSONRequest);
        String url = "https://api.pindo.io/v1/sms/";
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

    @GetMapping("/test_pindo_bulk_sms")
    public String testBulkSMS(){

        OkHttpClient client = OkHttpClientCreator.getOkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        String recipientJSONObject1 = String.format(
                "{\"phonenumber\": \"%s\", \"name\": \"%s\"}",
                "+250781234567", "Remy Muhire"
        );
        String recipientJSONObject2 = String.format(
                "{\"phonenumber\": \"%s\", \"name\": \"%s\"}",
                "+250793897619", "Maurice Mugisha"
        );

        String recipientsJSONArray = String.format("[%s, %s]", recipientJSONObject1, recipientJSONObject2);
        RequestBody body = RequestBody.create(
                mediaType,
                String.format(
                        "{\"recipients\": %s, \"text\": \"%s\", \"sender\": \"%s\"}",
                        recipientsJSONArray, "Hello @contact.name, Welcome to Pindo", pindoSender
                )
        );
        String url = "https://api.pindo.io/v1/sms/bulk";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", pindoAuthToken))
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseMessage = response.body() != null ? response.body().string() : null;
            int responseCode = response.code();
            System.out.println("The message response from pindo is: " + responseMessage + " code: " + responseCode);
            return response.toString();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return "null";
    }

    @GetMapping("/send_pindo_sms")
    public String sendSingleSMS(
            @RequestParam("sms_type") String type,
            @RequestParam("from") String from,
            @RequestParam("phone_number") String phoneNumber,
            @RequestParam("message") String message
    ){

        OkHttpClient client = OkHttpClientCreator.getOkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String smsJSONRequest = String.format(
                "{\"to\":\"%s\", \"text\": \"%s\", \"sender\":\"%s\"}",
                phoneNumber, message, pindoSender
        );
        RequestBody body = RequestBody.create(mediaType, smsJSONRequest);
        String url = "https://api.pindo.io/v1/sms/";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", pindoAuthToken))
                .build();

        try (Response response = client.newCall(request).execute()) {

            String responseMessage = response.body() != null ? response.body().string() : null;
            IResponseModel responseModel = new SMSResponseModelDecoder(responseMessage).decodeSingleMessagePindoResponse();
            PindoSingleMessageResponseModel singleSMSResponseModel = (PindoSingleMessageResponseModel)responseModel;

            String status = (responseModel != null) ? singleSMSResponseModel.getStatus() : Configuration.UNKNOWN_SMS_RESPONSE_STATUS;
            int totalMessages = (responseModel != null) ? singleSMSResponseModel.getItemCount() : 1;
            int responseCode = response.code();
            int successCount = responseCode == 201 ? 1 : 0;
            String toPhoneNumber = (responseModel != null) ? singleSMSResponseModel.getToPhoneNumber() : null;

            SentSMS sentSMS = new SentSMS(
                    SentSMSIdGenerator.generateSMSId(),
                    type,
                    "pindo.io",
                    status,
                    totalMessages,
                    successCount,
                    responseCode + "",
                    from,
                    toPhoneNumber,
                    message,
                    LocalDateTime.parse(DateTime.getTimeStampWithoutTimeZone()),
                    responseMessage
            );
            sentSMSService.saveSentSMS(sentSMS);
            System.out.println("The message response from pindo is: " + responseMessage + " code: " + responseCode);
            return response.toString();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return "null";
    }

    @GetMapping("/send_pindo_bulk_sms")
    public String sendBulkSMS(
            @RequestParam("sms_type") String type,
            @RequestParam("from") String from,
            @RequestParam("pindo_users") String pindoUserJSONArray,
            @RequestParam("message") String message
    ){

        List<PindoRecipientModel> recipientList = new PindoUserDecoder(pindoUserJSONArray).decode();
        OkHttpClient client = OkHttpClientCreator.getOkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        String recipientJSONArray = getRecipientJSONArray(recipientList);

        String courteousMessage = String.format("Hello @contact.name, %s", message);
        RequestBody body = RequestBody.create(
                mediaType,
                String.format(
                        "{\"recipients\": %s, \"text\": \"%s\", \"sender\": \"%s\"}",
                        recipientJSONArray, courteousMessage, pindoSender
                )
        );
        String url = "https://api.pindo.io/v1/sms/bulk";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", String.format("Bearer %s", pindoAuthToken))
                .build();

        try (Response response = client.newCall(request).execute()) {

            String responseMessage = response.body() != null ? response.body().string() : null;
            IResponseModel responseModel = new SMSResponseModelDecoder(responseMessage).decodeBulkMessagePindoResponse();
            PindoBulkMessageResponseModel bulkSMSResponseModel = (PindoBulkMessageResponseModel)responseModel;

            String status = (responseModel != null) ? bulkSMSResponseModel.getStatus() : Configuration.UNKNOWN_SMS_RESPONSE_STATUS;
            int totalMessages = (responseModel != null) ? bulkSMSResponseModel.getCount() : 1;
            int responseCode = response.code();
            int successCount = (responseCode == 201) ? 1 : 0;
            List<SentSMS> sentSMSList = new ArrayList<>();

            for (PindoRecipientModel recipient: recipientList) {

                sentSMSList.add(
                        new SentSMS(
                                SentSMSIdGenerator.generateSMSId(),
                                type,
                                "pindo.io",
                                status,
                                totalMessages,
                                successCount,
                                responseCode + "",
                                from,
                                recipient.getPhoneNumber(),
                                message,
                                LocalDateTime.parse(DateTime.getTimeStampWithoutTimeZone()),
                                responseMessage
                        )
                );

            }
            sentSMSService.saveManySentSMS(sentSMSList);
            return response.toString();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
        return "null";
    }

    @NotNull
    private static String getRecipientJSONArray(List<PindoRecipientModel> recipientList) {
        StringBuilder recipientJSONArrayBuilder = new StringBuilder();
        recipientJSONArrayBuilder.append("[");
        int i=1;
        int size = recipientList.size();
        for (PindoRecipientModel pindoRecipientModel: recipientList) {
            recipientJSONArrayBuilder.append(
                    String.format(
                            "{\"phonenumber\": \"%s\", \"name\": \"%s\"}",
                            pindoRecipientModel.getPhoneNumber(), pindoRecipientModel.getName()
                    )
            );
            if (i<size) {
                recipientJSONArrayBuilder.append(",");
            }
            i++;
        }
        recipientJSONArrayBuilder.append("]");
        return recipientJSONArrayBuilder.toString();
    }

}
