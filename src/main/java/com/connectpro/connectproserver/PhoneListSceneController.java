package com.connectpro.connectproserver;

import com.connectpro.connectproserver.utils.NodePaneController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class PhoneListSceneController implements NodePaneController {

    @FXML
    private AnchorPane node;

    @Override
    public Node getNode() {
        return node;
    }
}
