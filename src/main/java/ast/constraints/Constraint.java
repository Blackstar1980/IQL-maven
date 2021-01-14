package ast.constraints;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Constraint {
	public ConstraintId getID();
	
	record MaxCon(double value) implements Constraint{
		@Override
		public ConstraintId getID() {
			return ConstraintId.MAX;
		}

		public String validateLength(String text) {
			return text.length() > value? "Input must contain less than " + (int)value + " characters. " : " ";
		}
		public String validateInt(String text) {
			return text.isEmpty() ||  Integer.valueOf(text) > value? "Input must smaller or equals to " + (int)value : " ";
		}
		public String validateDec(String text) {
			return text.isEmpty() ||  Double.valueOf(text) > value? "Input must smaller or equals to " + value : " ";
		}};
	record MinCon(double value) implements Constraint{
		@Override
		public ConstraintId getID() {
			return ConstraintId.MIN;
		}

		public String validateLength(String text) {
			return text.length() < value? "Input must contain at least " + (int)value + " characters. " : " ";
		}
		public String validateInt(String text) {
			return text.isEmpty() || "-".equals(text) || Integer.valueOf(text) < value? 
					"Input must greater or equals to " + (int)value : " ";
		}
		public String validateDec(String text) {
			return text.isEmpty() || "-".equals(text) || Double.valueOf(text) < value?
					"Input must greater or equals to " + value : " ";
		}};
		
	record MajorTicksCon(int value) implements Constraint{
		@Override
		public ConstraintId getID() {
			return ConstraintId.MAJORTICKS;
		}};
	record MinorTicksCon(int value) implements Constraint{
		@Override
		public ConstraintId getID() {
			return ConstraintId.MINORTICKS;
		}};
	record HolderCon(String value) implements Constraint{
		@Override
		public ConstraintId getID() {
			return ConstraintId.HOLDER;
		}};
	record SelectedCon(String value) implements Constraint{
		@Override
		public ConstraintId getID() {
			return ConstraintId.SELECTED;
		}};
	record RegexCon(String value) implements Constraint{
		@Override
		public ConstraintId getID() {
			return ConstraintId.REGEX;
		}

		public String validate(String text) {
			Pattern pattern = Pattern.compile(value);
			Matcher matcher = pattern.matcher(text);
			return matcher.find()? " ": "Input don't match to the pattern '" + value +"'";
		}};
	enum RequiredCon implements Constraint{
		Required,NotRequired;
		public static RequiredCon from(String value) {
			if("true".contentEquals(value)) return RequiredCon.Required;
			if("false".contentEquals(value)) return RequiredCon.NotRequired;
			throw new IllegalArgumentException("Illigal constraint value: " + value);	
		}
		@Override
		public ConstraintId getID() {
			return ConstraintId.REQUIRED;
		}
		public String validate(String text) {
			return text == null || text.isEmpty()? "This field is required": " ";
		}
	}
	
}