package fr.kernioz.util.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;

public class Log4JFilter implements Filter {
    public Filter.Result filter(LogEvent record) {
        try {
            if (record == null || record.getMessage() == null)
                return Filter.Result.NEUTRAL;
            String logM = record.getMessage().getFormattedMessage().toLowerCase();
            if (!logM.contains("issued server command: /login ") && !logM.contains("issued server command: /l ") && !logM.contains("issued server command: /register ") && !logM.contains("issued server command: /changepassword ") && !logM.contains("issued server command: /changepass ") && !logM.contains("issued server command: /reg "))
                return Filter.Result.NEUTRAL;
            return Filter.Result.DENY;
        } catch (NullPointerException npe) {
            return Filter.Result.NEUTRAL;
        }
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object... arg4) {
        try {
            if (message == null)
                return Filter.Result.NEUTRAL;
            String logM = message.toLowerCase();
            if (!logM.contains("issued server command: /login ") && !logM.contains("issued server command: /l ") && !logM.contains("issued server command: /register ") && !logM.contains("issued server command: /changepassword ") && !logM.contains("issued server command: /changepass ") && !logM.contains("issued server command: /reg "))
                return Filter.Result.NEUTRAL;
            return Filter.Result.DENY;
        } catch (NullPointerException npe) {
            return Filter.Result.NEUTRAL;
        }
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Object message, Throwable arg4) {
        try {
            if (message == null)
                return Filter.Result.NEUTRAL;
            String logM = message.toString().toLowerCase();
            if (!logM.contains("issued server command: /login ") && !logM.contains("issued server command: /l ") && !logM.contains("issued server command: /register ") && !logM.contains("issued server command: /changepassword ") && !logM.contains("issued server command: /changepass ") && !logM.contains("issued server command: /reg "))
                return Filter.Result.NEUTRAL;
            return Filter.Result.DENY;
        } catch (NullPointerException npe) {
            return Filter.Result.NEUTRAL;
        }
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Message message, Throwable arg4) {
        try {
            if (message == null)
                return Filter.Result.NEUTRAL;
            String logM = message.getFormattedMessage().toLowerCase();
            if (!logM.contains("issued server command: /login ") && !logM.contains("issued server command: /l ") && !logM.contains("issued server command: /register ") && !logM.contains("issued server command: /changepassword ") && !logM.contains("issued server command: /changepass ") && !logM.contains("issued server command: /reg "))
                return Filter.Result.NEUTRAL;
            return Filter.Result.DENY;
        } catch (NullPointerException npe) {
            return Filter.Result.NEUTRAL;
        }
    }

    public Filter.Result getOnMatch() {
        return Filter.Result.NEUTRAL;
    }

    public Filter.Result getOnMismatch() {
        return Filter.Result.NEUTRAL;
    }
}
