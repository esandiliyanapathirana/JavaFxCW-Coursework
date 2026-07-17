package com.example.javafxcw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {
    private static final String log_file = "audit_log.txt";

    public static void log(String action, String itemCode, int quantity) {
        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String line = time + " | " + action + " | " + itemCode + " | Quantity =" + quantity;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(log_file, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Could not write audit log: " + e.getMessage());
        }
    }

    public static void log(String action, String itemCode) {
        log(action, itemCode, 0);
    }
}
