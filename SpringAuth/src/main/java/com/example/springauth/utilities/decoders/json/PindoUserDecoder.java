package com.example.springauth.utilities.decoders.json;

import com.example.springauth.models.json.PindoRecipientModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


public class PindoUserDecoder {

    private final String jsonEncodedPindoReceipientString;

    public PindoUserDecoder(String jsonEncodedPindoReceipientString) {
        this.jsonEncodedPindoReceipientString = jsonEncodedPindoReceipientString;
    }

    public List<PindoRecipientModel> decode() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PindoRecipientModel> pindoRecipientModelList = new ArrayList<>();

        try{
            pindoRecipientModelList = objectMapper.readValue(
                    this.jsonEncodedPindoReceipientString,
                    new TypeReference<>(){}
            );
        }catch (Exception e) {
            // log error
            System.out.println("Error decoding pindo recipient: " + e.getMessage());
        }
        return pindoRecipientModelList;
    }
}
