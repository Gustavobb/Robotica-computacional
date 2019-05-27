package br.insper.robot19;

import br.insper.robot19.vrep.VrepRobot;
import br.insper.robot19.vrep.VrepSimulator;
import br.insper.robot19.vrep.VrepWorld;

import java.util.Scanner;

public class ExecutaBusca {

	private static float cellSize = 0.5f; //m

	public static void main(String[] args) {

		VrepSimulator sim = VrepSimulator.getInstance();
		VrepWorld world = sim.createWorld();
		VrepRobot robot = sim.createRobot();
		RobotAction[] solucao;

		GridMap map;
		map = world.buildMap(cellSize);
		
		if(map == null) 
			throw new RuntimeException("O mapa discretizado não pode ser obtido a partir do modelo");
		
		//Imprimie o mapa em ASCII
		System.out.println("Mapa inicial:");
		System.out.println(map);
		
		//Realiza a busca
		int[] rowColIni = map.getStart();
		int[] rowColFim = map.getGoal();
		Block inicial = new Block(rowColIni[0], rowColIni[1], BlockType.FREE) ;
		Block alvo = new Block(rowColFim[0], rowColFim[1], BlockType.FREE) ;

		String s;
		Scanner in = new Scanner(System.in);

		while (true){
			System.out.println("Qual busca você quer escolher? (Gulosa, A*, Largura)");
			s = in.nextLine();
			if(s.toLowerCase().equals("gulosa")){
				System.out.println("Inicializando Busca Gulosa\n");
				BuscaGulosa busca = new BuscaGulosa(map, inicial, alvo);
				solucao = busca.resolver();
				break;
			}
			if(s.toLowerCase().equals("a*")){
				System.out.println("Inicializando Busca A*\n");
				BuscaAEstrela busca = new BuscaAEstrela(map, inicial, alvo);
				solucao = busca.resolver();
				break;
			}
			if(s.toLowerCase().equals("largura")){
				System.out.println("Inicializando Busca Largura\n");
				BuscaLargura busca = new BuscaLargura(map, inicial, alvo);
				solucao = busca.resolver();
				break;
			}
		}

		//Mostra a solução
		if(solucao == null) {
			System.out.println("Nao foi encontrada solucao para o problema");
		} else {
			
			Block atual = inicial;
			System.out.print("Solução: ");
			for(RobotAction a : solucao) {
				System.out.print(", " + a);
				Block next = map.nextBlock(atual, a);
				map.setRoute(next.row, next.col);
				atual = next;
			}
			
			//Mostra o mapa com a rota encontrada
			System.out.println();
			System.out.println("Rota encontrada:");
			System.out.println(map + "\n");

			System.out.println("Clique play para simular o robo");

			for(RobotAction a : solucao) {
				System.out.println(a);
				robot.execute(a, cellSize);
			}
			System.out.println("FIM");
		}
	}
}
