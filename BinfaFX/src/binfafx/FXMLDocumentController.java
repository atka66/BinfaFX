/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binfafx;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author atka
 */
public class FXMLDocumentController implements Initializable {

    GraphicsContext gc;
    FileChooser fc = new FileChooser();

    @FXML
    Canvas canvas;
    @FXML
    Label fileLabel;
    @FXML
    Label depthLabel;
    @FXML
    Label meanLabel;
    @FXML
    Label varLabel;
    @FXML
    CheckBox drawCBox;
    @FXML
    TextField outFileField;

    @FXML
    private void openBtnClick(ActionEvent event) throws IOException {
        File file = fc.showOpenDialog(new Stage());
        if (file != null) {
            System.out.println("\nLoading " + file.getPath());
            System.out.println("Building tree...");
            long startTime = System.nanoTime();
            BinaryTree binTree = new BinaryTree();
            BinfaFX.makeTree(binTree, file.getPath());
            System.out.println("Building completed!\n");
            if (!outFileField.getText().contentEquals("")) {
                System.out.println("Writing tree...");
                try (PrintWriter writer = new PrintWriter(outFileField.getText(), "UTF-8")) {
                    binTree.writeFile(writer);
                }
                System.out.println("Writing completed!\n");
            }
            long endTime = System.nanoTime();
            long millis = (endTime - startTime) / 1000000;
            System.out.println((new SimpleDateFormat("mm:ss:SSS")).format(new Date(millis)));
            binTree.writeOut();
            fileLabel.setText("File Name: " + file);
            depthLabel.setText("Depth: " + binTree.getDepth());
            meanLabel.setText("Mean: " + binTree.getMean());
            varLabel.setText("Dev: \n" + binTree.getDev());
            if (drawCBox.isSelected()) {
                System.out.println("Drawing may take a while...\n");
                binTree.drawTree(canvas, gc);
            }
        } else {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            System.out.println("Canceled.\n");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gc = canvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setStroke(Color.BLACK);

        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.setTitle("Open Resource File");
    }

}
