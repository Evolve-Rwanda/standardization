package com.example.springauth.utilities.decoders.json;

import com.example.springauth.models.json.LogMessageJSONModel;
import com.fasterxml.jackson.databind.ObjectMapper;



public class LogMessageModelDecoder {


    private String jsonEncodedLogMessageString;


    public LogMessageModelDecoder(String jsonEncodedLogMessageString) {
        this.jsonEncodedLogMessageString = jsonEncodedLogMessageString;
    }

    public LogMessageJSONModel decode() {
        ObjectMapper objectMapper = new ObjectMapper();
        LogMessageJSONModel logMessageJSONModel = new LogMessageJSONModel();

        try{
            logMessageJSONModel = objectMapper.readValue(
                    this.jsonEncodedLogMessageString,
                    LogMessageJSONModel.class
            );
        }catch (Exception e) {
            // log error
            System.out.println("Error decoding log message: " + e.getMessage());
        }
        return logMessageJSONModel;
    }
}
