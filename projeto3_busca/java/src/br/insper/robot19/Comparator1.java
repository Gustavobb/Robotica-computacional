package br.insper.robot19;

import java.util.Comparator;

public class Comparator1 implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        if (o1.getH() < o2.getH())
        {
            return -1;
        }
        if (o1.getH() > o2.getH())
        {
            return 1;
        }
        return 0;
    }
}