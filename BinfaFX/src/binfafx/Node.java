/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binfafx;

/**
 *
 * @author atka
 */
public class Node {
  private final char letter;
  private Node left;
  private Node right;
  public Node (char c) {
    letter = c;
  }
  public Node getLeft () {
    return left;
  }
  public Node getRight () {
    return right;
  }
  public void setLeft (Node node) {
    left = node;
  }
  public void setRight (Node node) {
    right = node;
  }
  public char getLetter () {
    return letter;
  }
}
