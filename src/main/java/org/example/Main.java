package org.example;

import org.example.service.DiagnosticService;

public class Main {

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("POS Network & Database Diagnostic Tool");
        System.out.println("====================================");

        try {

            DiagnosticService diagnosticService =
                    new DiagnosticService();

            diagnosticService.startDiagnostic();

        } catch (Exception e) {

            System.err.println(
                    "Application failed: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        System.out.println("Diagnostic process completed.");
    }
}