package ast.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.StyleId;
import fields.JPanelWithValue;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CSlider implements Component {
	private String name;
	private String title;
	private int minVal;
	private int maxVal;
	private int defVal;
	private List<Constraint> constraints;
	
	public CSlider(String name, String title, int minVal, int maxVal, int defVal,List<Constraint> constraints) {
		this.name=name;
		this.title=title;
		this.minVal=minVal;
		this.maxVal=maxVal;
		this.defVal=defVal;
		this.constraints=constraints;
	}
	public CSlider(ComponentContext ctx) {
		name = extractCompName(ctx);
		title = extractCompTitle(ctx);
		initDefVal(ctx);
		constraints = extractConstraints(ctx);
	}
	
	private void initDefVal(ComponentContext ctx) {
		String input = extractCompDefVal(ctx);
		if(input == null || input.isEmpty() || input.endsWith(",") || input.startsWith(","))
			throw new IllegalArgumentException("'"+input + "' is not a valid option for slider");
		String[] values = input.trim().split(",");
		if(values.length != 2 && values.length != 3)
			throw new IllegalArgumentException(input + " must contain 2 or 3 numbers seperated by comma");
		try {
			minVal = Integer.valueOf(values[0].trim());
			maxVal = Integer.valueOf(values[1].trim());
			defVal = values.length == 3 ? Integer.valueOf(values[2].trim()): minVal;
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Invalid Slider default values");
		}
		
		if(minVal >= maxVal) {
			throw new NumberFormatException("Slider max value must be bigger than the min value");
		}
		
		if(minVal > defVal || maxVal < defVal) {
			throw new NumberFormatException("Default value must be between or equal to the min and max values");
		}
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public int getMinVal() {
		return minVal;
	}

	public int getMaxVal() {
		return maxVal;
	}

	public int getDefVal() {
		return defVal;
	}

	public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}
	
	@Override
	public Id getType() { return Id.Slider;	}

	@Override
	public String toString() {
		return "Slider [name=" + name + ", title=" + title + ", minVal=" + minVal + ", maxVal=" + maxVal + ", defVal="
				+ defVal + ", constraints=" + constraints + "]";
	}
	
	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitSlider(this);
	}
	
	public JPanelWithValue make() {
		JLabel valueLabel = new JLabel();
		valueLabel.setFont(new Font(valueLabel.getFont().getName(), valueLabel.getFont().getStyle(), 20));
		valueLabel.setText(""+ defVal);
		JSlider jSlider = generateJSlider();
		JPanelWithValue panel = new JPanelWithValue(Id.Slider, getName()){
			@Override
			public boolean checkForError() {
				return false;
			}
			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value)) {
					valueLabel.setText(""+getDefVal());
					jSlider.setValue(getDefVal());
				} else {
					valueLabel.setText(value);
					jSlider.setValue(Integer.valueOf(value));
				}
			}
		};
		panel.setValueOrDefault("");
		panel.setValue(String.valueOf(getDefVal()));
		panel.setLayout(new GridBagLayout());
		jSlider.addChangeListener(new ChangeListener() {
	            @Override
	            public void stateChanged(ChangeEvent e) {
	            	valueLabel.setText(""+jSlider.getValue());
	                panel.setValue(String.valueOf(jSlider.getValue()));
	            }
	        });
		Map<ConstraintId, Constraint> constraints = getMapConstraint(getConstraints());
		JLabel title = generateTitle(getTitle(), constraints);
		StyleId style = (StyleId)constraints.get(ConstraintId.STYLE);
		return switch (style) {
			case Block -> setSliderBlockStyle(title, jSlider, valueLabel, panel);
			case Inline -> setSliderInlineStyle(title, jSlider, valueLabel, panel);
			default ->
				throw new IllegalArgumentException("Unexpected value: " + style);
		};
	}
	
	private JSlider generateJSlider() {
		JSlider jSlider = new JSlider(getMinVal(), getMaxVal(), getDefVal());
		jSlider.setLabelTable(null);
		Map<ConstraintId, Constraint> constraints = getMapConstraint(getConstraints());
		if(constraints.containsKey(ConstraintId.MAJORTICKS))
			jSlider.setMajorTickSpacing(((Constraint.MajorTicksCon)constraints.get(ConstraintId.MAJORTICKS)).value());
		else
			jSlider.setMajorTickSpacing(getMaxVal() - getMinVal());
		if(constraints.containsKey(ConstraintId.MINORTICKS))
			jSlider.setMinorTickSpacing(((Constraint.MinorTicksCon)constraints.get(ConstraintId.MINORTICKS)).value());
		jSlider.setPaintTicks(true);
		jSlider.setPaintLabels(true);
		return jSlider;
	}
	
	private JPanelWithValue setSliderInlineStyle(JLabel title, JSlider slider, JLabel value,
			JPanelWithValue panel) {
		GridBagConstraints gbc = new GridBagConstraints();
		slider.setPreferredSize(new Dimension(230, 50));
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		panel.add(slider, gbc);
		gbc.gridx = 2;
		gbc.insets = new Insets(0, 0, 0, 32);
		panel.add(value, gbc);
		return panel;
	}

	private JPanelWithValue setSliderBlockStyle(JLabel title, JSlider slider, JLabel value, JPanelWithValue panel) {
		GridBagConstraints gbc = new GridBagConstraints();
		slider.setPreferredSize(new Dimension(230, 50));
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(slider, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 20, 0, 32);
		panel.add(value, gbc);
		return panel;
	}
}

