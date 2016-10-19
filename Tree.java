package implementation;

public class Tree {

	/**
	 * @param args
	 */
	public Node root;
	
	public Tree(Node root) {
		this.root = root;
	}
	
	public void print(Node n) {
		if(n == null)
			return;
		System.out.println(n.value);
		print(n.kids[0]);
		print(n.kids[1]);
		
	}
	
	private boolean ok = false;
	
	public int compute(Node root) {
		if(root.value.compareTo("=") == 0) {
			return compute(root.kids[1]);
		}
		if(root.value.compareTo("+") == 0) {
			return compute(root.kids[0]) + compute(root.kids[1]);
		}
		
		if(root.value.compareTo("-") == 0) {
			return compute(root.kids[0]) - compute(root.kids[1]);
		}
		
		if(root.value.compareTo("*") == 0) {
			return compute(root.kids[0]) * compute(root.kids[1]);
		}
		
		if(root.value.compareTo(":") == 0) {
			if(compute(root.kids[0]) == 0 && ok == false) {
				return compute(root.kids[1]);
			}
			else return compute(root.kids[0]);
		}
		
		if(root.value.compareTo("?") == 0) {
			if(compute(root.kids[0]) == 1) {
				ok = true;
				return compute(root.kids[1]);
			}
			ok = false;
			return 0;
		}
		
		if(root.value.compareTo(">") == 0) {
			if(compute(root.kids[0]) > compute(root.kids[1]))
				return 1;
			else
				return 0;
		}
		
		return Integer.parseInt(root.value);
	}
	
	
	
	String operatorAt(Node root) {
		return root.value;
	}
	
	public int maxLevel(Node node) {
	    if (node.kids.length == 0 || (node.kids[0] == null && node.kids[1] == null)) return 1;

	    int max = maxLevel(node.kids[0]);
	    for (int i = 0 ; i < node.kids.length ; i++) {
	        int n = maxLevel(node.kids[i]);
	        if (n > max) max = n;
	    }

	    return max + 1;
	}
	
	
	public Node getFatherOf(Node node,Node root) {
		
		Node left=null,right=null;
		if(root == null) {
			return null;
		}
		if(root.kids[0] == node || root.kids[1] == node)
			return root;
		
		left = getFatherOf(node,root.kids[0]);
		right = getFatherOf(node,root.kids[1]);
		
		return (left != null) ? left : right;
		
	}
	
	
	
	String line = new String();
	public void toStringLvL(Node root, int lvl, int curLvl) {
		if (root.kids[0] == null && root.kids[1] == null) {
			line += getNodeType(root);
			return;
		}
		
		//TODO
		if (curLvl < lvl) {
			if(root.value.compareTo("+") == 0 || root.value.compareTo("-") == 0 || root.value.compareTo(">") == 0 || root.value.compareTo(":") == 0)
				line+="(";
			toStringLvL(root.kids[0], lvl, curLvl + 1);
			line += root.value;
			toStringLvL(root.kids[1], lvl, curLvl + 1);
			if(root.value.compareTo("+") == 0 || root.value.compareTo("-") == 0 || root.value.compareTo(">") == 0 || root.value.compareTo(":") == 0)
				line+=")";
		}
		else {
			line += getNodeType(root);
		}
	}
	
	public String getNodeType(Node root) {
		if(root.value.compareTo("0") == 0 && (getFatherOf(root,this.root).value.compareTo("+") == 0 || getFatherOf(root,this.root).value.compareTo("-") == 0) && getFatherOf(root,this.root).kids[0].value.compareTo("0") == 0)
			return "";
		if(getFatherOf(root,this.root) == null || root.kids[0] != null ) {
			return "E";
		}
		if(getFatherOf(root, this.root).value.compareTo(":") == 0 || getFatherOf(root, this.root).value.compareTo("?") == 0 || getFatherOf(root, this.root).value.compareTo(">") == 0){
			return "N";
		}
		if(getFatherOf(root, this.root).value.compareTo("+") == 0 || getFatherOf(root, this.root).value.compareTo("-") == 0 || getFatherOf(root, this.root).value.compareTo("=") == 0) {
			return "T";
		}
		if(getFatherOf(root, this.root).value.compareTo("*") == 0) {
			return "F";
		}
		return "E";
	}
	
}

