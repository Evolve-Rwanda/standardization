package com.example.springauth.logging.targets;

import com.example.springauth.logging.loggers.Level;
import com.example.springauth.logging.message.Message;
import com.example.springauth.models.json.LogMessageJSONModel;
import com.example.springauth.services.LogService;
import com.example.springauth.utilities.DateTime;
import com.example.springauth.utilities.decoders.json.LogMessageModelDecoder;


public class ConsoleLogger implements ILogObserver {



    private int level;
    private LogService logService;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    private static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";

    private static final String ANSI_WHITE_TEXT = "\u001B[37m";
    private static final String ANSI_BLUE_TEXT = "\u001B[34m";
    private static final String ANSI_RED_TEXT = "\u001B[31m";
    private static final String ANSI_GREEN_TEXT = "\u001B[32m";
    private static final String ANSI_YELLOW_TEXT = "\u001B[33m";


    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void log(String message){

        boolean isJSONEncoded = Message.isJSONEncoded(message);
        String timestamp = DateTime.getTimeStamp();
        String name = String.format(" %s ", Level.getName(this.level).charAt(0));
        String formattedLogMessage = null;

        if (isJSONEncoded) {
            LogMessageJSONModel logMessageJSONModel = (new LogMessageModelDecoder(message)).decode();
            // paraphrasing the message
            message = String.format(
                    "User: %-25s - Action: %-30s - Message: %-100s",
                    logMessageJSONModel.getUser(), logMessageJSONModel.getAction(), logMessageJSONModel.getContents()
            );
        }
        formattedLogMessage = this.getFormattedLogMessage(timestamp, name, message);
        System.out.println(formattedLogMessage);
    }

    private String getFormattedLogMessage(String timestamp, String name, String message) {

        String consoleLogMessage = null;
        String formattedTimestamp = null;
        String formattedLevel = null;
        String formattedMessage = null;

        if (this.level == Level.INFO) {
            formattedTimestamp = String.format("%s%s%s", ANSI_BLUE_TEXT, timestamp, ANSI_RESET);
            formattedLevel = String.format("%s%s%s", ANSI_BLUE_BACKGROUND, name, ANSI_RESET);
            formattedMessage = String.format("%s%s%s", ANSI_BLUE_TEXT, message, ANSI_RESET);
        }else if (this.level == Level.ERROR) {
            formattedTimestamp = String.format("%s%s%s", ANSI_RED_TEXT, timestamp, ANSI_RESET);
            formattedLevel = String.format("%s%s%s", ANSI_RED_BACKGROUND, name, ANSI_RESET);
            formattedMessage = String.format("%s%s%s", ANSI_RED_TEXT, message, ANSI_RESET);
        }else if (this.level == Level.DEBUG) {
            formattedTimestamp = String.format("%s%s%s", ANSI_GREEN_TEXT, timestamp, ANSI_RESET);
            formattedLevel = String.format("%s%s%s", ANSI_GREEN_BACKGROUND, name, ANSI_RESET);
            formattedMessage = String.format("%s%s%s", ANSI_GREEN_TEXT, message, ANSI_RESET);
        }else if (this.level == Level.WARNING) {
            formattedTimestamp = String.format("%s%s%s", ANSI_YELLOW_TEXT, timestamp, ANSI_RESET);
            formattedLevel = String.format("%s%s%s", ANSI_YELLOW_BACKGROUND, name, ANSI_RESET);
            formattedMessage = String.format("%s%s%s", ANSI_YELLOW_TEXT, message, ANSI_RESET);
        }else if (this.level == Level.VERBOSE) {
            formattedTimestamp = String.format("%s%s%s", ANSI_RESET, timestamp, ANSI_RESET);
            formattedLevel = String.format("%s%s%s", ANSI_BLACK_BACKGROUND, name, ANSI_RESET);
            formattedMessage = String.format("%s%s%s", ANSI_RESET, message, ANSI_RESET);
        }
        consoleLogMessage = String.format(
                "%s %s %s",
                formattedTimestamp, formattedLevel, formattedMessage
        );
        return consoleLogMessage;
    }

}
