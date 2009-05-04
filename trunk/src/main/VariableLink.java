package main;

public class VariableLink implements WithPosition {
	Variable var = null;
	String name;
	Variablespace variablespace;
	Position pos;
	
	public VariableLink(String pname, Variablespace pvariablespace, Position ppos) {
		name = pname;
		variablespace = pvariablespace;
		pos = ppos;
		Variable.getLink(this);
	}
	
	
	
	public Position getPosition() {
		return pos;
	}

	
	public void setPosition(Position p) {
		pos = p;
	}


	public Type getType() {
		if (var != null) {
			return var.getType();
		}
		else return Type.tUnknown;
	}


	public void print(StringBuilder sb) {
		if (var != null) {
			//var.print(sb);
			sb.append(var.name);
		} else {
			sb.append("<Unknown Variable>");
		}
		
	}


	public boolean isSubTypeOf(Type p) {
		if (var != null) {
			return var.isSubTypeOf(p);
		}
		return false;
	}

}
