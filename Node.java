package implementation;

public class Node {

	/**
	 * @param args
	 */
	public String value;
	Node[] kids = new Node[2];
	private boolean firstEqual = false;
	int i;
	String operators = "-+=(*)?:>=".toString();
	private int countParanthezis = 0;
	int len,priority = 1;
	
	Node(String s) {
		value = s;

		countParanthezis = 0;
		len = value.length();
		
		
		for(i = 0 ; i < len - 1 ; i++) {
			if(value.toCharArray()[i] == '(') {
				countParanthezis++;
			}
			
			if(value.toCharArray()[i] == ')') {
				countParanthezis--;
			}
			if(countParanthezis == 0)
				break;
			
		}
		
		if(countParanthezis > 0 && value.charAt(0) == '(') {
			value = new String(value.substring(1,len - 1));
			len = value.length();
		}
		
		if(countParanthezis < 0 ) {
			value = new String(value.substring(0,len - 1));
			len = value.length();
		}
		
		for(i = 0; i < value.length() ; i++) {
			
			if(value.charAt(i) == '=' && !firstEqual) {
				firstEqual = true;
				kids[0] = new Node(value.substring(0,i));
				kids[1] = new Node(value.substring(i+1,value.length()));
				
				value = new String("=");
				return;
			}
		}
		
		for(i = 0; i < value.length() ; i++) {
			if(value.charAt(i) == '+' ) {
				if(isBetween(i,value) || 0 == i || (!Character.isDigit(value.charAt(i-1)) && (!Character.isAlphabetic(value.charAt(i-1))) && value.charAt(i-1) != ')')) {
					continue;
				}
				kids[0] = new Node(value.substring(0,i));
				kids[1] = new Node(value.substring(i+1,len));
				
				value = new String("+");
				return;
			}	
		}
		
		for(i = len - 1 ; i >= 0 ; i--) {
			if(value.charAt(i) == '-') {
				if(isBetween(i,value) || 0 == i || (!Character.isDigit(value.charAt(i-1)) && (!Character.isAlphabetic(value.charAt(i-1))) && value.charAt(i-1) != ')')) {
					continue;
				}
				kids[0] = new Node(value.substring(0,i));
				kids[1] = new Node(value.substring(i+1,len));
				
			
				value = new String("-");
				return;
			}
		}
		
		for(i = 0; i < value.length() ; i++) {
			if(value.charAt(i) == '*') {
				if(isBetween(i,value)) {
					continue;
				}
				kids[0] = new Node(value.substring(0,i));
				kids[1] = new Node(value.substring(i+1,len));
				value = new String("*");
				return;
			}
		}
		
		for(i = 0; i < value.length() ; i++) {
			if(value.charAt(i) == ':') {
				if(isBetween(i,value)) {
					continue;
				}
				kids[0] = new Node(value.substring(0,i));
				kids[1] = new Node(value.substring(i+1,len));
				value = new String(":");
				return;
			}
		}
		
		for(i = 0; i < value.length() ; i++) {
			if(value.charAt(i) == '?') {
				if(isBetween(i,value)) {
					continue;
				}
				kids[0] = new Node(value.substring(0,i));
				kids[1] = new Node(value.substring(i+1,len));
				value = new String("?");
				return;
			}
		}
		
		for(i = 0; i < value.length() ; i++) {
			if(value.charAt(i) == '>') {
				if(isBetween(i,value)) {
					continue;
				}
				kids[0] = new Node(value.substring(0,i));
				kids[1] = new Node(value.substring(i+1,len));
				value = new String(">");
				return;
			}
		}
		
		for(i = 0; i < value.length() ; i++) {
			if(value.charAt(i) == '+' && isUnary(value,i)) {
				if(isBetween(i,value)) {
					continue;
				}
				
				kids[0] = new Node("0");
				kids[1] = new Node(value.substring(i+1,len));
				value = new String("+");
				return;
			}
		}
		
		for(i = value.length() - 1 ; i >= 0 ; i--) {
			if(value.charAt(i) == '-' && isUnary(value,i)) {
				if(isBetween(i,value)) {
					continue;
				}
				kids[0] = new Node("0");
				kids[1] = new Node(value.substring(i+1,len));
				value = new String("-");
				return;
			}
			
		}
		
		
	}

	/**
	 * @return the firstEqual
	 */
	public boolean isFirstEqual() {
		return firstEqual;
	}

	/**
	 * @param firstEqual the firstEqual to set
	 */
	public void setFirstEqual(boolean firstEqual) {
		this.firstEqual = firstEqual;
	}
	
	boolean isOperator(char letter) {
		
		if(operators.indexOf(letter) == -1) {
			return false;
		}
		return true;
	}
	
	boolean isUnary(String s, int index) {
		if(s.charAt(index) == '+' || s.charAt(index) == '-') {
			if ((index > 0) && (s.toCharArray()[index - 1] == ')' || Character.isDigit(s.toCharArray()[index - 1]) || Character.isAlphabetic((s.toCharArray()[index - 1])))) {
				return false;
			}
		}
		
		return true;
	}
	
	boolean isBetween(int index, String s) {
		int i;
		int counter1 = 0, counter2 = 0;
		for( i = index + 1 ; i < s.length() ; i++) {
			if(s.charAt(i) == ')')
			{
				counter1++;
			}
			if(s.charAt(i) == '(')
			{
				counter1--;
			}
		}
		
		for(i = index - 1 ; i >= 0 ; i--) {
			if(s.charAt(i) == '(')
			{
				counter2++;
			}
			if(s.charAt(i) == ')')
			{
				counter2--;
			}
		if(counter1 == counter2 && counter1>0)
			return true;
		}
		return false;
	}
	

}
