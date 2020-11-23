package fr.kernioz.util.logger;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class LoginFilter implements Filter {
    public boolean isLoggable(LogRecord record) {
        return (!record.getMessage().contains("issued server command: /login ") && !record.getMessage().contains("issued server command: /l ") && !record.getMessage().contains("issued server command: /register ") && !record.getMessage().contains("issued server command: /changepassword ") && !record.getMessage().contains("issued server command: /changepass ") && !record.getMessage().contains("issued server command: /reg "));
    }
}
