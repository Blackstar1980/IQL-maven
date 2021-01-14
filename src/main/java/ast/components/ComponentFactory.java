package ast.components;

import java.util.function.Function;

import ast.Id;
import generated.GuiInputParser.ComponentContext;

// TODO ask if it right to do it this way or it is better to use enum methods!!!!
// Why not just doing "case String ->new CString(ctx) "
// instead of "case String -> String.operation.apply(ctx);"
// There is any benefits by using "String(){public Component make(Ctx ctx) {... }}"?
public enum ComponentFactory {
	String(ctx -> new CString(ctx)),
	Integer(ctx -> new CInteger(ctx)),
	Decimal(ctx -> new CDecimal(ctx)),
	Boolean(ctx -> new CBoolean(ctx)),
	SingleOpt(ctx -> new CSingleOpt(ctx)),
	MultiOpt(ctx -> new CMultiOpt(ctx)),
	Password(ctx -> new CPassword(ctx)),
	Slider(ctx -> new CSlider(ctx)),
	TextArea(ctx -> new CTextArea(ctx));

	ComponentFactory(Function<ComponentContext, Component> operation) {
		this.createComp = operation;
	}

	private final Function<ComponentContext, Component> createComp;

	public static Component from(ComponentContext ctx) {
		if(ctx == null || ctx.CompId() == null)
			throw new IllegalArgumentException("ComponentContext and Id cannot be null");
		Id id = Id.from(ctx.CompId().getText());
		return switch(id){
		case String -> String.createComp.apply(ctx);
		case Integer -> Integer.createComp.apply(ctx);
		case Decimal -> Decimal.createComp.apply(ctx);
		case Boolean -> Boolean.createComp.apply(ctx);
		case SingleOpt -> SingleOpt.createComp.apply(ctx);
		case MultiOpt -> MultiOpt.createComp.apply(ctx);
		case Password -> Password.createComp.apply(ctx);
		case Slider -> Slider.createComp.apply(ctx);
		case TextArea -> TextArea.createComp.apply(ctx);
		default -> throw new Error(id + " is not a valide id");
		};
	}
}