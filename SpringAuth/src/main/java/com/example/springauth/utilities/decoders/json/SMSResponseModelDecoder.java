package com.example.springauth.utilities.decoders.json;

import com.example.springauth.models.json.IResponseModel;
import com.example.springauth.models.json.pindo.PindoBulkMessageResponseModel;
import com.example.springauth.models.json.pindo.PindoSingleMessageResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SMSResponseModelDecoder {


    private final String jsonEncodedPindoResponseString;

    public SMSResponseModelDecoder(String jsonEncodedPindoResponseString) {
        this.jsonEncodedPindoResponseString = jsonEncodedPindoResponseString;
    }

    public IResponseModel decodeSingleMessagePindoResponse() {
        ObjectMapper objectMapper = new ObjectMapper();
        PindoSingleMessageResponseModel smsResponseJSONModel = null;
        try{
            smsResponseJSONModel = objectMapper.readValue(
                    this.jsonEncodedPindoResponseString,
                    PindoSingleMessageResponseModel.class
            );
        }catch (Exception e) {
            // log error
            System.out.println("Error decoding pindo single sms response: " + e.getMessage());
        }
        return smsResponseJSONModel;
    }

    public IResponseModel decodeBulkMessagePindoResponse() {
        ObjectMapper objectMapper = new ObjectMapper();
        PindoBulkMessageResponseModel smsResponseJSONModel = null;
        try{
            smsResponseJSONModel = objectMapper.readValue(
                    this.jsonEncodedPindoResponseString,
                    PindoBulkMessageResponseModel.class
            );
        }catch (Exception e) {
            // log error
            System.out.println("Error decoding pindo bulk sms response: " + e.getMessage());
        }
        return smsResponseJSONModel;
    }

}
