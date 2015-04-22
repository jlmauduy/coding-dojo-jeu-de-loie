package jeuDeLoie;

public class Case {

	private static final String OUTPUT_DEPART = "[D]";
	private static final String OUTPUT_ARRIVEE = "[A]";
	private static final String OUTPUT_AVANCE_TROIS_CASES = "[+3]";
	private static final String OUTPUT_RETOUR_AU_DEPART = "[<-]";
	private static final String OUTPUT_DEFAULT = "[]";
	
	private CaseType type;

	public Case(CaseType type) {
		this.type = type;
	}

	public CaseType getCaseType() {
		return type;
	}

	public String getOutput() {
		//FIXME retirer le switch case, et definir l'output via une autre methode (factory, ...)
		switch (type) {
			case DEPART:
				return OUTPUT_DEPART;
	
			case ARRIVEE:
				return OUTPUT_ARRIVEE;
				
			case AVANCE_TROIS_CASES:
				return OUTPUT_AVANCE_TROIS_CASES;
				
			case RETOUR_AU_DEPART:
				return OUTPUT_RETOUR_AU_DEPART;
				
			case DEFAULT:
				return OUTPUT_DEFAULT;
		}
		
		return "";
	}
	
	

}
