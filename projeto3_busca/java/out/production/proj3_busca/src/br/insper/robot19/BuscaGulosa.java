package br.insper.robot19;

import java.util.*;

/**
 * Classe que implementa o algoritmo de busca
 * @author antonio
 *
 */
public class BuscaGulosa {

    private Block start = null;
    private Block end = null;
    private GridMap map = null;
    Comparator<Node> comparator = new Comparator1();
    private HashSet<Block> visited = new HashSet();


    /**
     * Construtor
     * @param map
     * @param start - ponto inicial
     * @param end - ponto final
     */
    public BuscaGulosa(GridMap map, Block start, Block end) {
        this.map = map;
        this.start = start;
        this.end = end;
    }

    /**
     * Método que realiza a busca
     * @return
     */
    public Node buscar() {

        Node root = new Node(start, null, null, 0);

        //Limpa a fronteira e insere o nó raiz
        PriorityQueue<Node> border = new PriorityQueue<Node>(comparator);
        border.add(root);

        while(!border.isEmpty()) {

            Node node = border.remove();
            Block atual = node.getValue();
            addVisited(atual);

            if(atual.row == end.row && atual.col == end.col) {
                return node;

            } else for(RobotAction acao : RobotAction.values()) {

                Block proximo = map.nextBlock(atual, acao);

                if(proximo != null && proximo.type != BlockType.WALL) {

                    if (!getVisited().contains(proximo)){
                        Node novoNode = new Node(proximo, node, acao, proximo.type.cost);
                        novoNode.setH(end);
                        border.add(novoNode);
                    }
                }
            }
        }
        return null;
    }

    public void addVisited(Block block){ visited.add(block); }

    public HashSet<Block> getVisited(){ return visited; }


    /**
     * Resolve o problema com base em busca, realizando
     * o backtracking após chegar ao estado final
     *
     * @return A solução encontrada
     */
    public RobotAction[] resolver() {

        // Encontra a solução através da busca
        Node destino = buscar();
        if(destino == null) {
            return null;
        }

        //Faz o backtracking para recuperar o caminho percorrido
        Node atual = destino;
        Deque<RobotAction> caminho = new LinkedList<RobotAction>();
        while(atual.getAction() != null) {
            caminho.addFirst(atual.getAction());
            atual = atual.getParent();
        }
        return caminho.toArray(new RobotAction[caminho.size()]);
    }
}
