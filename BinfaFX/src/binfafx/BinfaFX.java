/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binfafx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author atka
 */
public class BinfaFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Binary Tree Builder");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static BinaryTree makeTree(BinaryTree binTree, String path) throws FileNotFoundException, IOException {
        
        BufferedReader file = new BufferedReader(new FileReader(path));

        String line;
        Boolean isFirstLine = true;
        boolean comment = false;

        while ((line = file.readLine()) != null) {
            line += System.lineSeparator();
            if (isFirstLine) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 0x0a) {
                        isFirstLine = false;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 0x3e) {
                        comment = true;
                        continue;
                    }
                    if (line.charAt(i) == 0x0a) {
                        comment = false;
                        continue;
                    }
                    if (comment) {
                        continue;
                    }
                    if (line.charAt(i) == 0x4e) {
                        continue;
                    }
                    char currentChar = line.charAt(i);
                    for (int j = 0; j < 8; j++) {
                        if ((currentChar & 0x80) != 0) {
                            binTree.insert('1');
                        } else {
                            binTree.insert('0');
                        }
                        currentChar <<= 1;
                    }
                }
            }
        }
        return binTree;
    }

}
