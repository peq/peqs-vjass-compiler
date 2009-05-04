package main;

public class Parameter extends Variable implements Compilable, WithPosition {
	
	Position pos;
	
	// TODO treat parameter like a variable
	
	public Parameter(Token typename, Token pname) {
		super(Variable.currentVariablespace, pname.image, typename.image
				, false, true, Variable.defaultArraySize(), null
				, new Position(typename));
		pos = new Position(new Position(typename), new Position(pname));
	}

	@Override
	public Parameter compile() {
		return this;
	}

	@Override
	public void print(StringBuilder sb) {
		typelink.getType().print(sb);
		sb.append(" ");
		sb.append(name);
	}

	@Override
	public Position getPosition() {
		return pos;
	}

	@Override
	public void setPosition(Position p) {
		pos=p;
		
	}

	public Type getType() {
		return typelink.getType();
	}

}
