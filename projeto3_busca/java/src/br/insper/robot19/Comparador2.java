package br.insper.robot19;

import java.util.Comparator;

public class Comparador2 implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        if (o1.getF() < o2.getF())
        {
            return -1;
        }
        if (o1.getF() > o2.getF())
        {
            return 1;
        }
        return 0;
    }

}
