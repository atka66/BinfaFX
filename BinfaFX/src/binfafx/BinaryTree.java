/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binfafx;

import java.io.PrintWriter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author atka
 */
public class BinaryTree {

    int verticalDistance;

    private final Node root = new Node('/');
    private Node currentNode = root;
    private int depth, maxDepth, meanSum, meanQ;
    private double mean, disp, dispSum;

    public void insert(char b) {
        if (b == '0') {
            if (currentNode.getLeft() == null) {
                currentNode.setLeft(new Node('0'));
                currentNode = root;
            } else {
                currentNode = currentNode.getLeft();
            }
        } else {
            if (currentNode.getRight() == null) {
                currentNode.setRight(new Node('1'));
                currentNode = root;
            } else {
                currentNode = currentNode.getRight();
            }
        }
    }

    int getDepth() {
        depth = maxDepth = 0;
        depthRec(root);
        return maxDepth - 1;
    }

    double getMean() {
        depth = meanSum = meanQ = 0;
        meanRec(root);
        mean = ((double) meanSum) / meanQ;
        return mean;
    }

    double getDev() {
        mean = getMean();
        dispSum = 0.0;
        depth = meanQ = 0;
        devRec(root);
        if (meanQ - 1 > 0) {
            disp = Math.sqrt(dispSum / (meanQ - 1));
        } else {
            disp = Math.sqrt(dispSum);
        }
        return disp;
    }

    void depthRec(Node node) {
        if (node != null) {
            depth++;
            if (depth > maxDepth) {
                maxDepth = depth;
            }
            depthRec(node.getRight());
            depthRec(node.getLeft());
            depth--;
        }
    }

    void meanRec(Node node) {
        if (node != null) {
            depth++;
            meanRec(node.getRight());
            meanRec(node.getLeft());
            depth--;
            if (node.getRight() == null && node.getLeft() == null) {
                meanQ++;
                meanSum += depth;
            }
        }
    }

    void devRec(Node node) {
        if (node != null) {
            depth++;
            devRec(node.getRight());
            devRec(node.getLeft());
            depth--;
            if (node.getRight() == null && node.getLeft() == null) {
                meanQ++;
                dispSum += ((depth - mean) * (depth - mean));
            }
        }
    }

    public void writeOut() {
        System.out.println("Depth = " + getDepth());
        System.out.println("Mean = " + getMean());
        System.out.println("Dev = " + getDev());
    }

    public void writeFile(PrintWriter writer) {
        depth = 0;
        writeFileRec(writer, root);
        writer.println("Depth = " + getDepth());
        writer.println("Mean = " + getMean());
        writer.println("Dev = " + getDev());
    }

    private void writeFileRec(PrintWriter writer, Node node) {
        if (node != null) {
            depth++;
            writeFileRec(writer, node.getRight());

            for (int i = 0; i < depth; i++) {
                writer.print("-");
            }
            writer.println(node.getLetter() + "(" + (depth - 1) + ")");

            writeFileRec(writer, node.getLeft());
            depth--;
        }
    }

    public void drawTree(Canvas canvas, GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        getDepth();
        verticalDistance = (int) (canvas.getHeight() / (maxDepth + 1));
        depth = 0;
        drawTreeRec(canvas, gc, root,
                (int) canvas.getWidth() / 2, (int) canvas.getWidth() / 2, 0
        );
    }

    private void drawTreeRec(Canvas canvas, GraphicsContext gc, Node node,
            int where, int prevX, int prevY
    ) {
        if (node != null) {
            depth++;
            drawTreeRec(
                    canvas, gc, node.getRight(),
                    where + (int) ((double) canvas.getWidth() / (double) Math.pow(2, depth + 1)),
                    where, depth * verticalDistance
            );
            drawTreeRec(
                    canvas, gc, node.getLeft(),
                    where - (int) ((double) canvas.getWidth() / (double) Math.pow(2, depth + 1)),
                    where, depth * verticalDistance
            );
            gc.strokeLine(prevX, prevY, where, (depth * (int) verticalDistance) + 1);
            gc.setFill(Color.GREEN);
            gc.fillOval(where - 7, ((depth * (int) verticalDistance) + 1) - 7, 14, 14);
            gc.strokeOval(where - 7, ((depth * verticalDistance) + 1) - 7, 14, 14);
            gc.setFill(Color.WHITE);
            gc.fillText("" + node.getLetter(), where, (depth * (int) verticalDistance) + 1 + 4);
            depth--;
        }
    }
}
