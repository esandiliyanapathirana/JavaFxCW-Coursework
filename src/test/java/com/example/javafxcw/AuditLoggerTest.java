package com.example.javafxcw;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.*;

public class AuditLoggerTest {

    @Test
    void logAppendsToAuditFile() throws Exception {
        File logFile = new File("audit_log.txt");
        long sizeBefore = logFile.exists() ? logFile.length() : 0;

        AuditLogger.log("TestAction", "P001", 2);

        assertTrue(logFile.exists());
        assertTrue(logFile.length() >= sizeBefore);

        String content = Files.readString(logFile.toPath());
        assertTrue(content.contains("TestAction"));
        assertTrue(content.contains("P001"));
    }
}
