package view;

import common.GlobalEventBus;
import io.ConnectionLineDTO;
import io.JsonExporter;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Menu extends javafx.scene.control.Menu {
    ArrayList<NetworkDeviceView> deviceViews;
    ArrayList<ConnectionLine> connectionLines;

    public Menu(String name, ArrayList<NetworkDeviceView> deviceViews, ArrayList<ConnectionLine> connectionLines) {
        super(name);
        this.deviceViews = deviceViews;
        this.connectionLines = connectionLines;
        GlobalEventBus.register(this);

        MenuItem save = new MenuItem("Save");
        save.setOnAction(clickEvent -> saveEventHandler(save));

        this.getItems().addAll(save);
    }

    private void saveEventHandler(MenuItem save) {
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save simulation setup");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JSON Files", "*.json")
            );
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            Window window = save.getParentPopup().getOwnerWindow();
            File file = fileChooser.showSaveDialog(window);
            if (file != null) {
                try {
                    exportNetworkData(file);
                } catch (IOException e) {
                    System.err.println("Failed to save network data: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void exportNetworkData(File file) throws IOException {
        JsonExporter jsonExporter = new JsonExporter();

        List<ConnectionLineDTO> connectionDTOs = new ArrayList<>();
        for (ConnectionLine connectionLine : connectionLines) {

            UUID startDeviceUuid = connectionLine.getStartDevice().getUuid();
            UUID endDeviceUuid = connectionLine.getEndDevice().getUuid();

            ConnectionLineDTO dto = new ConnectionLineDTO(startDeviceUuid, endDeviceUuid);
            connectionDTOs.add(dto);
        }

        jsonExporter.exportNetworkData(deviceViews, connectionDTOs, file);
    }
}