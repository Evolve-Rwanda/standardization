package com.example.springauth.logging.targets;

import com.example.springauth.authentication.AuthenticationUtility;
import com.example.springauth.logging.loggers.Level;
import com.example.springauth.logging.message.Message;
import com.example.springauth.logging.writers.LogFileWriter;
import com.example.springauth.services.LogService;
import com.example.springauth.utilities.DateTime;
import com.example.springauth.utilities.UUIDGenerator;

import java.nio.file.FileSystems;


public class FileLogger implements ILogObserver {


    private int level;
    private LogService logService;
    private static final String SEPARATOR = FileSystems.getDefault().getSeparator();
    private static final String LOG_DIRECTORY;

    static {
        LOG_DIRECTORY = String.format("%s%s%s", System.getProperty("user.home"), SEPARATOR, "logs");
    }

    @Override
    public void setLevel(int level){
        this.level = level;
    }

    @Override
    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void log(String message){
        String id = UUIDGenerator.generateUUID();
        String timestamp = DateTime.getTimeStamp();
        String type = Level.getName(this.level);
        String user = AuthenticationUtility.getCurrentUsername();
        String action = " ";
        Message logMessage = new Message(id, timestamp, type, user, action, message);
        LogFileWriter logFileWriter = new LogFileWriter();
        String specificDirectory = DateTime.getDate().replace("-", SEPARATOR);
        logFileWriter.setDirectory(LOG_DIRECTORY + SEPARATOR + specificDirectory);
        logFileWriter.write(this.getLogFileName(), logMessage.toJSON());
    }

    private String getLogFileName() {
        String dateString = DateTime.getDate();
        return String.format("%s.log", dateString);
    }
}
