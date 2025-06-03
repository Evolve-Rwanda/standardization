package com.example.springauth.logging.targets;

import com.example.springauth.services.LogService;

public interface ILogObserver {
    void log(String message);
    void setLevel(int level);
    void setLogService(LogService logService);
}
