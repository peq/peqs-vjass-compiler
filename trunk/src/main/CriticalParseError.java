package main;

@SuppressWarnings("serial")
public class CriticalParseError extends Error implements WithPosition {
	String msg;
	Position pos;
	
	public CriticalParseError(String string) {
		super(string);
		pos =  new Position(Qjass.token);
	}
	@Override
	public Position getPosition() {
		return pos;
	}
	@Override
	public void setPosition(Position p) {
		pos = p;		
	}
	
}
