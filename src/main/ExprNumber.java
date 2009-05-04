package main;

public interface ExprNumber extends Expression {
	ExprNumber add(ExprNumber x);
	ExprNumber sub(ExprNumber x);
	ExprNumber mul(ExprNumber x);
	ExprNumber div(ExprNumber x);
	double compare(ExprNumber x);
}
