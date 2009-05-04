package main;

public class Local extends Variable  {

	
	public Local(Token typename, boolean array, Token varname, Expression pinitial) {
		super(currentVariablespace, varname.image, typename.image, array, false, Variable.defaultArraySize(), pinitial, new Position(varname));
		 
	}

	@Override
	public Local compile() {
		
		
		if (initial != null) {
			initial = initial.compile();
			if (array) {
				new CompilerError(this, "An array cannot have an initial value.");
			}
			if (!initial.isSubTypeOf(typelink.getType())) {
				StringBuilder sb = new StringBuilder();
				print(sb);
				new CompilerError(this, "Type mismatch in local-Declaration. Cannot convert from " +
						initial.getType().name + " to " + typelink.getType().name + " in\n " + sb);
			}
		}
		
		
		return this;
	}

	@Override
	public void print(StringBuilder sb) {
		sb.append("local ");
		typelink.getType().print(sb);
		sb.append(" ");
		if (array) {
			sb.append("array ");
		}
		sb.append(name);
		if (initial != null) {
			sb.append(" = ");
			initial.print(sb);
		}
		sb.append("\n");
	}
}
