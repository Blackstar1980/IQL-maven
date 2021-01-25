package ast;

public enum Id{String,Integer,Decimal,Boolean,
	SingleOpt,MultiOpt,Password,Slider,TextArea, Single, Tabular, Pages, Group, Tab, Query, TabsContainer;
	
	public static Id from(String input) {
		return switch(input){
		case "String" -> String;
		case "Integer" -> Integer;
		case "Decimal" -> Decimal;
		case "Boolean" -> Boolean;
		case "SingleOpt" -> SingleOpt;
		case "MultiOpt" -> MultiOpt;
		case "Password" -> Password;
		case "Slider" -> Slider;
		case "TextArea" -> TextArea;
		case "Single" -> Single;
		case "Tabular" -> Tabular;
		case "Pages" -> Pages;
		case "Group" -> Group;
		case "Tab" -> Tab;
		case "Query" -> Query;
		default -> throw new IllegalArgumentException(input + " is not a valide id");
		};
	}
	
//	private List<String> supportedConstraints() {
//		return switch (this) {
//		case Multi, Pages -> Arrays.asList(MIN, MAX);
//		case String -> Arrays.asList(MIN, MAX, REGEX, STYLE, HOLDER, REQUIRED);
//		case Integer -> Arrays.asList(MIN, MAX, REGEX, STYLE, HOLDER, REQUIRED);
//		case Decimal -> Arrays.asList(MIN, MAX, REGEX, STYLE, HOLDER, REQUIRED);
//		case Boolean -> throw new IllegalArgumentException("No constraints are supported for Boolean Id");
//		case Group -> throw new IllegalArgumentException("No constraints are supported for Group Id");
//		case Tab -> throw new IllegalArgumentException("No constraints are supported for Tab Id");
//		case Single -> throw new IllegalArgumentException("No constraints are supported for Single Id");
//		case SingleOpt -> Arrays.asList(STYLE, SELECTED, REQUIRED);
//		case MultiOpt -> Arrays.asList(STYLE, SELECTED);
//		case Password -> Arrays.asList(MIN, MAX, REGEX, STYLE, HOLDER, REQUIRED);
//		case Slider -> Arrays.asList(MAJORTICKS, MINORTICKS, STYLE);
//		case TextArea -> Arrays.asList(MIN, MAX, REGEX, STYLE, HOLDER, REQUIRED);
//		default -> throw new IllegalArgumentException(this.toString() + " is not a valide id");
//		};
//	}

}