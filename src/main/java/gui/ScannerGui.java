package gui;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ScanResult;
import scanner.IPScan;
import scanner.PortScan;

import java.util.List;

public class ScannerGui extends Application {

    @Override
    public void start(Stage stage) {

        Label operationLabel = new Label("Operation:");

        ComboBox<String> operationBox = new ComboBox<>();

        operationBox.getItems().addAll(
                "Scan Single IP",
                "Scan Subnet",
                "Scan Single Port",
                "Scan Port Range"
        );

        operationBox.setValue("Scan Single IP");

        Label ipLabel = new Label("IP Address:");
        TextField ipField = new TextField();
        ipField.setPromptText("192.168.1.1");

        Label startPortLabel = new Label("Start Port:");
        TextField startPortField = new TextField();
        startPortField.setPromptText("1");

        Label endPortLabel = new Label("End Port:");
        TextField endPortField = new TextField();
        endPortField.setPromptText("1000");

        Button scanButton = new Button("Start Scan");

        TextArea results = new TextArea();
        results.setEditable(false);

        startPortLabel.setVisible(false);
        startPortField.setVisible(false);
        endPortLabel.setVisible(false);
        endPortField.setVisible(false);

        operationBox.setOnAction(e -> {

            String operation = operationBox.getValue();

            boolean showPorts =
                    operation.equals("Scan Single Port") ||
                            operation.equals("Scan Port Range");

            boolean showEndPort =
                    operation.equals("Scan Port Range");

            startPortLabel.setVisible(showPorts);
            startPortField.setVisible(showPorts);

            endPortLabel.setVisible(showEndPort);
            endPortField.setVisible(showEndPort);

            if (operation.equals("Scan Subnet")) {
                ipField.setPromptText("192.168.1.0/24");
            } else {
                ipField.setPromptText("192.168.1.1");
            }
        });

        scanButton.setOnAction(e -> {

            String operation = operationBox.getValue();
            String ip = ipField.getText();

            scanButton.setDisable(true);
            results.setText("Scanning...");

            Task<String> task = new Task<>() {

                @Override
                protected String call() {

                    IPScan ipScan = new IPScan();
                    PortScan portScan = new PortScan();

                    StringBuilder output = new StringBuilder();

                    switch (operation) {

                        case "Scan Single IP":

                            output.append("Scanning ")
                                    .append(ip)
                                    .append("\n\n");

                            if (ipScan.isHostAliveForGui(ip)) {

                                output.append("Host is up.\n");

                                output.append("Hostname: ")
                                        .append(ipScan.getHostName(ip))
                                        .append("\n");

                                output.append("OS: ")
                                        .append(ipScan.detectOS(ip))
                                        .append("\n");

                            } else {
                                output.append("Host is down.");
                            }

                            break;

                        case "Scan Subnet":

                            output.append(
                                    "Subnet scanning started...\n\n"
                            );
                            break;

                        case "Scan Single Port":

                            int port = Integer.parseInt(
                                    startPortField.getText()
                            );

                            List<ScanResult> singlePort =
                                    portScan.scanPorts(
                                            ip,
                                            port,
                                            port
                                    );

                            if (singlePort.isEmpty()) {

                                output.append(
                                        "Port " + port + " is closed."
                                );

                            } else {

                                for (ScanResult result :
                                        singlePort) {

                                    output.append(
                                            "Port "
                                                    + result.getPort()
                                                    + " OPEN - "
                                                    + result.getService()
                                                    + "\n"
                                    );
                                }
                            }

                            break;

                        case "Scan Port Range":

                            int startPort = Integer.parseInt(
                                    startPortField.getText()
                            );

                            int endPort = Integer.parseInt(
                                    endPortField.getText()
                            );

                            List<ScanResult> rangeResults =
                                    portScan.scanPorts(
                                            ip,
                                            startPort,
                                            endPort
                                    );

                            if (rangeResults.isEmpty()) {

                                output.append(
                                        "No open ports found."
                                );

                            } else {

                                for (ScanResult result :
                                        rangeResults) {

                                    output.append(
                                            "Port "
                                                    + result.getPort()
                                                    + " OPEN - "
                                                    + result.getService()
                                                    + "\n"
                                    );
                                }
                            }

                            break;
                    }

                    return output.toString();
                }
            };

            task.setOnSucceeded(event -> {

                results.setText(task.getValue());

                scanButton.setDisable(false);
            });

            task.setOnFailed(event -> {

                results.setText(
                        "Error: " +
                                task.getException().getMessage()
                );

                scanButton.setDisable(false);
            });

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        });

        VBox layout = new VBox(10);

        layout.getChildren().addAll(
                operationLabel,
                operationBox,
                ipLabel,
                ipField,
                startPortLabel,
                startPortField,
                endPortLabel,
                endPortField,
                scanButton,
                new Label("Results:"),
                results
        );

        Scene scene = new Scene(layout, 700, 500);

        stage.setTitle("Net Scan");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}