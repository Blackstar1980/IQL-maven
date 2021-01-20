package ast.constraints;

import ast.Id;
import ast.constraints.Constraint.HolderCon;
import ast.constraints.Constraint.MajorTicksCon;
import ast.constraints.Constraint.MaxCon;
import ast.constraints.Constraint.MinCon;
import ast.constraints.Constraint.MinorTicksCon;
import ast.constraints.Constraint.RegexCon;
import ast.constraints.Constraint.OptionalCon;
import ast.constraints.Constraint.SelectedCon;

public enum ConstraintId {
	MIN, MAX, REGEX, DISPLAY, HOLDER, SELECTED, MAJORTICKS, MINORTICKS, OPTIONAL;

	public static Constraint from(Id id, String input) {
		if(isDisplay(input.trim()))
			return getDisplayCon(id, input.trim());
		if("optional".equals(input.trim()))
			return getOptionalCon(id, input.trim());
		String constrient = input.substring(0, input.indexOf('=')).trim();
		String value = input.substring(input.indexOf('=') + 1).trim();
		return switch (constrient) {
		case "max" -> getMaxCon(id, value);
		case "min" -> getMinCon(id, value);
//		case "required" -> getRequiredCon(id, value);
//		case "optional" -> getOptionalCon(id, value);
		case "placeholder" -> getHolderCon(id, value);
		case "selected" -> getSelectedCon(id, value);
//		case "display" -> getDisplayCon(id, value);
		case "majorTicks" -> getMajorTicksCon(id, value);
		case "minorTicks" -> getMinorTicksCon(id, value);
		case "regex" -> getRegexCon(id, value);
//		case "inline" -> getDisplayCon(id, value);
//		case "block" -> getDisplayCon(id, value);
//		case "inlineList" -> getDisplayCon(id, value);
//		case "blockList" -> getDisplayCon(id, value);
//		case "blockRadio" -> getDisplayCon(id, value);
//		case "blockCheckbox" -> getDisplayCon(id, value);
		default -> throw new IllegalArgumentException("Illigal constraint: " + constrient);
		};
	}

	private static boolean isDisplay(String input) {
		return switch (input.trim()) {
			case "inline" -> true;
			case "block" -> true;
			case "inlineList" -> true;
			case "blockList" -> true;
			case "blockRadio" -> true;
			case "blockCheckbox" -> true;
			default -> false;
		};
	}

	private static OptionalCon getOptionalCon(Id id, String value) {
		return switch (id) {
		case String, Integer, Decimal, Password, TextArea, Boolean, SingleOpt, MultiOpt, Slider -> OptionalCon.from(value);
		default -> throw new IllegalArgumentException(id + " is not support Optional constraint");
		};
	}

	private static SelectedCon getSelectedCon(Id id, String value) {
		return switch (id) {
		case SingleOpt, MultiOpt -> new SelectedCon(value.substring(1, value.length() - 1));
		default -> throw new IllegalArgumentException(id + " is not support Selected constraint");
		};
	}

	private static MinorTicksCon getMinorTicksCon(Id id, String value) {
		int tickValue = getTickConValue(id, value);
		return new MinorTicksCon(tickValue);
	}

	private static MajorTicksCon getMajorTicksCon(Id id, String value) {
		int tickValue = getTickConValue(id, value);
		return new MajorTicksCon(tickValue);
	}

	private static int getTickConValue(Id id, String value) {
		if (id == Id.Slider) {
			int tickValue;
			try {
				tickValue = Integer.valueOf(value.trim());
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Tick value: " + value + ", Must be of type Integer");
			}
			if (tickValue <= 0)
				throw new IllegalArgumentException(id + " tick constraint, must be bigger than 0");
			return tickValue;
		}
		throw new IllegalArgumentException(id + " is not support Ticks constraint");
	}

	private static DisplayId getDisplayCon(Id id, String value) {
		return DisplayId.from(id, value);
	}

	private static RegexCon getRegexCon(Id id, String value) {
		return switch (id) {
		case String, Password -> new RegexCon(value.substring(1, value.length() - 1));
		default -> throw new IllegalArgumentException(id + " is not support Regex constraint");
		};
	}

	private static HolderCon getHolderCon(Id id, String value) {
		return switch (id) {
		case String, Integer, Decimal, Password, TextArea -> new HolderCon(value.substring(1, value.length() - 1));
		default -> throw new IllegalArgumentException(id + " is not support Holder constraint");
		};
	}

//	private static RequiredCon getRequiredCon(Id id, String value) {
//		return switch (id) {
//		case String, Integer, Decimal, Password, TextArea, Boolean, SingleOpt, MultiOpt -> {
//			yield RequiredCon.from(value);
//		}
//		default ->
//			throw new IllegalArgumentException(id + " is not support Required constraint");
//		};
//	}

	private static MaxCon getMaxCon(Id id, String value) {
		return switch (id) {
		case String, Password, TextArea, Multi, Pages -> {
			int maxValue = Integer.valueOf(value);
			if (maxValue < 0)
				throw new IllegalArgumentException(id + " max constraint, must be bigger than 0");
			yield new MaxCon(maxValue);
		}
		case Integer -> new MaxCon(Integer.valueOf(value));
		case Decimal -> new MaxCon(Double.valueOf(value));
		default -> throw new IllegalArgumentException(id + " is not support Max constraint");
		};
	}

	private static MinCon getMinCon(Id id, String value) {
		return switch (id) {
		case String, Password, TextArea, Multi, Pages -> {
			int minValue = Integer.valueOf(value);
			if (minValue < 0)
				throw new IllegalArgumentException(id + " min constraint must be bigger than 0");
			yield new MinCon(minValue);
		}
		case Integer -> new MinCon(Integer.valueOf(value));
		case Decimal -> new MinCon(Double.valueOf(value));
		default -> throw new IllegalArgumentException(id + " is not support Min constraint");
		};
	}

}
