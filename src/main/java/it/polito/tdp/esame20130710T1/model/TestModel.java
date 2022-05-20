package it.polito.tdp.esame20130710T1.model;

public class TestModel {

	public static void main(String[] args) {
		Model model = new Model();
		
		model.createGraph(4);
		System.out.println(model.printGraph());
		
		String p1="cane";
		String p2 = "ossa";

		for(String s : model.winTheMatch(p1, p2)) {
    		System.out.print("\n\t"+s);
    	}
		
	}

	
}
