package main;

public class TypeLink implements WithPosition {
	Position pos;
	
	public TypeLink(Scope pscope, String pname, Position p) {
		scope = pscope;
		name=pname;
		pos = p;
		Type.getLink(this);
		
	}

	Type type = null;
	String name;
	Scope scope;
	
	
	public Position getPosition() {
		return pos;
	}
	
	public void setPosition(Position p) {
		pos = p;
	}
	public String getName() {
		if (type == null) {
			return "<unknown type>";
		}
		return type.name;
	}
	public void print(StringBuilder sb) {
		if (type == null) {
			sb.append("<unknown type>");
		} else {
			sb.append(type.name);
		}
		
	}
	public boolean isSubTypeOf(Type p) {
		if (type == null) {
			return false;
		}
		return type.isSubTypeOf(p);
	}
	public Type getType() {
		if (type != null) {
			return type;
		}
		return Type.tUnknown;
	}
	public void setType(Type t) {
		type = t;
	}
	
}
