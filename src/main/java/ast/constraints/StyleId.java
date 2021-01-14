package ast.constraints;

import ast.Id;

public enum StyleId implements Constraint{
	Inline, Block, InlineRadio,	InlineList, 
	BlockRadio, BlockList, InlineCheckbox, BlockCheckbox;
	
	public static StyleId from(Id id, String value) {
		return switch (id) {
		case String, Integer, Decimal, Password, TextArea, Boolean, Slider -> getStyle(value);
		case MultiOpt -> getMultiStyle(value);
		case SingleOpt -> getSingleStyle(value);
		default ->
			throw new IllegalArgumentException(id + " is not support Style constraint");
		};
	}
	
	private static StyleId getStyle(String value) {
		return switch (value) {
		case "inline" -> Inline;
		case "block" -> Block;
		default -> throw new IllegalArgumentException(value + " is not a valide component style");
		};
	}
	
	private static StyleId getSingleStyle(String value) {
		return switch (value) {
		case "inlineRadio" -> InlineRadio;
		case "inlineList" -> InlineList;
		case "blockRadio" -> BlockRadio;
		case "blockList" -> BlockList;
		default -> throw new IllegalArgumentException(value + " is not a valide component style");
		};
	}
	
	private static StyleId getMultiStyle(String value) {
		return switch (value) {
		case "blockList" -> BlockList;
		case "inlineList" -> InlineList;
		case "inlineCheckbox" -> InlineCheckbox;
		case "blockCheckbox" -> BlockCheckbox;
		default -> throw new IllegalArgumentException(value + " is not a valide component style");
		};
	}

	@Override
	public ConstraintId getID() {
		return ConstraintId.STYLE;
	}
}
