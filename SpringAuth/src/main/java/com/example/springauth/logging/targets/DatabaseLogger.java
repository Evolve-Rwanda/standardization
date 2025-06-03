package com.example.springauth.logging.targets;

import com.example.springauth.authentication.AuthenticationUtility;
import com.example.springauth.logging.loggers.Level;
import com.example.springauth.logging.message.Message;
import com.example.springauth.models.jpa.Log;
import com.example.springauth.models.json.LogMessageJSONModel;
import com.example.springauth.services.LogService;
import com.example.springauth.utilities.DateTime;
import com.example.springauth.utilities.UUIDGenerator;
import com.example.springauth.utilities.decoders.json.LogMessageModelDecoder;


public class DatabaseLogger implements ILogObserver {


    private int level;
    private LogService logService;


    @Override
    public void setLevel(int level){
        this.level = level;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void log(String message){

        boolean isJSONEncoded = Message.isJSONEncoded(message);
        Log log = new Log();

        if (isJSONEncoded) {
            var logMessageJSONModel = (new LogMessageModelDecoder(message)).decode();
            log.setId(logMessageJSONModel.getId());
            log.setTimestamp(logMessageJSONModel.getTimestamp());
            log.setType(Level.getName(this.level));
            log.setUserId(logMessageJSONModel.getUser());
            log.setAction(logMessageJSONModel.getAction());
            log.setContents(logMessageJSONModel.getContents());
        } else {
            String action = " ";
            log.setId(UUIDGenerator.generateUUID());
            log.setTimestamp(DateTime.getTimeStamp());
            log.setType(Level.getName(this.level));
            log.setUserId(AuthenticationUtility.getCurrentUsername());
            log.setAction(action);
            log.setContents(message);
        }
        this.logService.createLog(log);
    }
}
