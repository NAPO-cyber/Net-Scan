package gui;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import model.HostResult;
import model.ScanResult;
import scanner.IPScan;
import scanner.PortScan;

import java.util.List;

public class ScannerGui extends Application {

    @Override
    public void start(Stage stage) {

        // Title
        Label title = new Label("NET SCAN");
        title.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;"
        );

        Label subtitle = new Label(
                "Network discovery and port scanning tool"
        );
        subtitle.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #888888;"
        );

        // Operation
        Label operationLabel = new Label("Scan Type");

        ComboBox<String> operationBox = new ComboBox<>();

        operationBox.getItems().addAll(
                "Scan Single IP",
                "Scan Subnet",
                "Scan Single Port",
                "Scan Port Range"
        );

        operationBox.setValue("Scan Single IP");
        operationBox.setMaxWidth(Double.MAX_VALUE);

        // IP
        Label ipLabel = new Label("IP Address");

        TextField ipField = new TextField();
        ipField.setPromptText("192.168.1.1");

        // Ports
        Label startPortLabel = new Label("Start Port");

        TextField startPortField = new TextField();
        startPortField.setPromptText("1");

        Label endPortLabel = new Label("End Port");

        TextField endPortField = new TextField();
        endPortField.setPromptText("1000");

        HBox portBox = new HBox(10);

        VBox startPortBox = new VBox(5);
        startPortBox.getChildren().addAll(
                startPortLabel,
                startPortField
        );

        VBox endPortBox = new VBox(5);
        endPortBox.getChildren().addAll(
                endPortLabel,
                endPortField
        );

        HBox.setHgrow(startPortBox, Priority.ALWAYS);
        HBox.setHgrow(endPortBox, Priority.ALWAYS);

        portBox.getChildren().addAll(
                startPortBox,
                endPortBox
        );

        // Scan button
        Button scanButton = new Button("START SCAN");

        scanButton.setMaxWidth(Double.MAX_VALUE);

        scanButton.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-padding: 10px;"
        );

        // Results
        Label resultsLabel = new Label("Results");

        TextArea results = new TextArea();

        results.setEditable(false);
        results.setWrapText(false);
        results.setPrefHeight(300);

        VBox.setVgrow(results, Priority.ALWAYS);

        // Hide port fields initially
        portBox.setVisible(false);
        portBox.setManaged(false);

        // Operation selection
        operationBox.setOnAction(e -> {

            String operation = operationBox.getValue();

            boolean showPorts =
                    operation.equals("Scan Single Port") ||
                            operation.equals("Scan Port Range");

            boolean showEndPort =
                    operation.equals("Scan Port Range");

            portBox.setVisible(showPorts);
            portBox.setManaged(showPorts);

            endPortBox.setVisible(showEndPort);
            endPortBox.setManaged(showEndPort);

            if (operation.equals("Scan Subnet")) {
                ipField.setPromptText("192.168.1.0/24");
            } else {
                ipField.setPromptText("192.168.1.1");
            }
        });

        // Scan
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

                    StringBuilder output =
                            new StringBuilder();

                    switch (operation) {

                        case "Scan Single IP":

                            output.append(
                                    "Scanning "
                            ).append(ip).append("\n\n");

                            if (ipScan.isHostAliveForGui(ip)) {

                                output.append(
                                        "Host is UP\n\n"
                                );

                                output.append(
                                        "Hostname: "
                                ).append(
                                        ipScan.getHostName(ip)
                                ).append("\n");

                                output.append(
                                        "OS: "
                                ).append(
                                        ipScan.detectOS(ip)
                                );

                            } else {

                                output.append(
                                        "Host is DOWN"
                                );
                            }

                            break;

                        case "Scan Subnet":

                            List<HostResult> hosts =
                                    ipScan.scanSubnet(ip);

                            if (hosts.isEmpty()) {

                                output.append(
                                        "No hosts found."
                                );

                            } else {

                                output.append(
                                        "Hosts found: "
                                ).append(
                                        hosts.size()
                                ).append("\n\n");

                                for (HostResult host : hosts) {

                                    output.append(
                                            "Host: "
                                    ).append(
                                            host.getIp()
                                    ).append("\n");

                                    output.append(
                                            "Hostname: "
                                    ).append(
                                            host.getHostname()
                                    ).append("\n");

                                    output.append(
                                            "OS: "
                                    ).append(
                                            host.getOs()
                                    ).append("\n\n");
                                }
                            }

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
                                        "Port " + port +
                                                " is CLOSED."
                                );

                            } else {

                                for (ScanResult result :
                                        singlePort) {

                                    output.append(
                                            "Port "
                                    ).append(
                                            result.getPort()
                                    ).append(
                                            " OPEN - "
                                    ).append(
                                            result.getService()
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
                                    ).append(
                                            result.getPort()
                                    ).append(
                                            " OPEN - "
                                    ).append(
                                            result.getService()
                                    ).append("\n");
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

        // Main layout
        VBox layout = new VBox(12);

        layout.setPadding(new Insets(20));

        layout.getChildren().addAll(
                title,
                subtitle,
                new Separator(),
                operationLabel,
                operationBox,
                ipLabel,
                ipField,
                portBox,
                scanButton,
                resultsLabel,
                results
        );

        VBox.setVgrow(results, Priority.ALWAYS);

        // Scene
        Scene scene = new Scene(
                layout,
                750,
                600
        );

        stage.setTitle("Net Scan");
        stage.setScene(scene);
        stage.setMinWidth(650);
        stage.setMinHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
