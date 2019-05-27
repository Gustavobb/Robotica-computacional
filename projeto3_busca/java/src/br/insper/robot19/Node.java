package br.insper.robot19;

import static java.lang.Math.abs;

public class Node {
	private Block value;
	private Node parent;
	private RobotAction action;
	private float pathCost;
	private float h;
	private float f;

	public Node (Block value, Node parent, RobotAction action, float cost) {
		this.value = value;
		this.parent = parent;
		this.action = action;
		this.pathCost = parent == null ? 0 : parent.getPathCost() + cost;
	}


	public Block getValue() {
		return value;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public RobotAction getAction() {
		return action;
	}
	
	public float getPathCost() {
		return pathCost;
	}

	public float getF(){ return f; }

	public void setF(){ this.f = getH() + getPathCost(); }

	public float getH(){ return h; }

	public void setH(Block end){ this.h = (abs(end.row - getValue().row) + abs(end.col - getValue().col)); }

}
