package lexer;

public final class ConjunctiveRepresentation {
	
	
	private final String representation;
	private final boolean negation;
	
	public final String getRepresentation() {
		return representation;
	}
	public final boolean isNegation() {
		return negation;
	}
	
	public ConjunctiveRepresentation(String representation, boolean negation){
		this.representation = representation;
		this.negation = negation;
	}
}
