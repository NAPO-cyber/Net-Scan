package gui;

import scanner.PortScan;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ScannerGui extends Application {
    @Override
    public void start(Stage stage) {

        Label ipLabel = new Label("ip address:");
        TextField ipField = new TextField();
        ipField.setPromptText("192.168.1.1");

        Label startLabel = new Label("start port:");
        TextField startPortField = new TextField();
        startPortField.setPromptText("1");

        Label endLabel = new Label("end port:");
        TextField endPortField = new TextField();
        endPortField.setPromptText("1000");

        Button scanBtn = new Button("start scan");

        TextArea results = new TextArea();
        results.setEditable(false);

        scanBtn.setOnAction(e -> {
            String ip = ipField.getText();
            int startPort = Integer.parseInt(startPortField.getText());
            int endPort = Integer.parseInt(endPortField.getText());

            PortScan portscan = new PortScan();

            String result = portscan.scanPorts(ip, startPort, endPort);
            results.setText(result);
        });

        VBox layout = new VBox(10);

        layout.getChildren().addAll(
                ipLabel,
                ipField,
                startLabel,
                startPortField,
                endLabel,
                endPortField,
                scanBtn,
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
