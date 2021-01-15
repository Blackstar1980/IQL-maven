package ast.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JToggleButton;

import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.StyleId;
import ast.constraints.Constraint.HolderCon;
import fields.JPanelWithValue;
import fields.PlaceholderPasswordField;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CPassword implements Component {	
	private final String name;
	private final String title;
	private final String defVal;
	private final List<Constraint> constraints;
	
	public CPassword(String name, String title, String defVal, List<Constraint> constraints) {
		this.name = name;
		this.title = title;
		this.defVal = defVal;
		this.constraints = constraints;
	}
	
	public CPassword(ComponentContext ctx) {
		name = extractCompName(ctx);
		title = extractCompTitle(ctx);
		defVal = extractCompDefVal(ctx);
		constraints = extractConstraints(ctx);
	}
	
	public JPanelWithValue make() {
		PlaceholderPasswordField passField = new PlaceholderPasswordField();
		Map<ConstraintId, Constraint> constraintMap = getMapConstraint(constraints);
		JPanelWithValue panel = new JPanelWithValue(Id.Password, name){
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.Password, String.valueOf(passField.getPassword()), constraintMap));
			}
			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					passField.setText(defVal);
					setValue(defVal);
				} else {
					passField.setText(value);
					setValue(value);
				}
			}
		};
		
		panel.setValueOrDefault("", true);
		JLabel jTitle = generateTitle(title, constraintMap);
		JLabel errorMsg = panel.getErrorLabel();
		StyleId style = (StyleId)constraintMap.get(ConstraintId.STYLE);
		setPasswordPlaceHolder(passField, constraintMap);
		passField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setPasswordPlaceHolder(passField, constraintMap);
				String passVal = String.valueOf(passField.getPassword());
				boolean hasError = panel.setErrorLabel(validateConstraints(Id.Password, passVal, constraintMap));
				if(!hasError)
					panel.setValue(passVal);
			}
			
			@Override
			public void focusGained(FocusEvent e) {}
		});
		passField.addKeyListener(new KeyAdapter() {
			 public void keyReleased(KeyEvent e) {
				 String passVal = String.valueOf(passField.getPassword());
				 String error = validateConstraints(Id.Password, passVal, constraintMap);
				 if(" ".equals(error)) {
					errorMsg.setText(" "); 
					panel.setValue(passVal);
				 }
			 }
		});
		return setPasswordLayout(style, jTitle, passField, errorMsg, panel);
	}
	
	private void setPasswordPlaceHolder(PlaceholderPasswordField textField, Map<ConstraintId, Constraint> constraints) {
		if(String.valueOf(textField.getPassword()).isEmpty() && constraints.containsKey(ConstraintId.HOLDER)) {
			textField.setPlaceholder(((HolderCon)constraints.get(ConstraintId.HOLDER)).value());
		}
	}
	
	private JPanelWithValue setPasswordLayout(StyleId style, JLabel title,
			JPasswordField textField, JLabel errorMsg, JPanelWithValue panel) {
		return switch (style) {
			case Block -> setPasswordBlockStyle(title, textField, errorMsg, panel);
			case Inline -> setPasswordInlineStyle(title, textField, errorMsg, panel);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + style);
		};
	}
	
	private JPanelWithValue setPasswordInlineStyle(JLabel title, JPasswordField passField,
			JLabel errorMsg, JPanelWithValue panel) {
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JToggleButton showButton = new JToggleButton();
		showButton.setPreferredSize(new Dimension(28, 28));
		passField.setEchoChar('\u25CF');
		ImageIcon hideImg = new ImageIcon(this.getClass().getResource("/images/hide.png"));
		Image hideDim = hideImg.getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
		ImageIcon hideImg2 = new ImageIcon(hideDim);
		ImageIcon showImg = new ImageIcon(this.getClass().getResource("/images/show.png"));
		Image showDim = showImg.getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
		ImageIcon showImg2 = new ImageIcon(showDim);
		showButton.setIcon(showImg2);
		showButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(showButton.isSelected()) {
					passField.setEchoChar((char)0);
					showButton.setIcon(hideImg2);
				}
				else {
					passField.setEchoChar('\u25CF');
					showButton.setIcon(showImg2);
				}
			}
		});
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		passField.setPreferredSize(new Dimension(252, 30));
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		panel.add(passField, gbc);
		gbc.gridx = 2;
		panel.add(showButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(errorMsg, gbc);
		return panel;
	}

	private JPanelWithValue setPasswordBlockStyle(JLabel title, JPasswordField passField, JLabel errorMsg, JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		JToggleButton showButton = new JToggleButton();
		showButton.setPreferredSize(new Dimension(28, 28));
		passField.setEchoChar('\u25CF');
		ImageIcon hideImg = new ImageIcon(this.getClass().getResource("/images/hide.png"));
		Image hideDim = hideImg.getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
		ImageIcon hideImg2 = new ImageIcon(hideDim);
		ImageIcon showImg = new ImageIcon(this.getClass().getResource("/images/show.png"));
		Image showDim = showImg.getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
		ImageIcon showImg2 = new ImageIcon(showDim);
		showButton.setIcon(showImg2);
		showButton.addActionListener(e -> {
				if(showButton.isSelected()) {
					passField.setEchoChar((char)0);
					showButton.setIcon(hideImg2);
				}
				else {
					passField.setEchoChar('\u25CF');
					showButton.setIcon(showImg2);
				}
		});

		gbc.anchor = GridBagConstraints.WEST;
		passField.setPreferredSize(new Dimension(252, 30));
		gbc.insets = new Insets(0, 20, 0, 20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 20, 0, 0);
		panel.add(passField, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		panel.add(showButton, gbc);
		gbc.insets = new Insets(0, 20, 0, 20);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(errorMsg, gbc);
		return panel;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public String getDefVal() {
		return defVal;
	}

	public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}
	
	@Override
	public Id getType() { return Id.Password; }

	@Override
	public String toString() {
		return "Password [name=" + name + ", title=" + title + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}
	
	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitPassword(this);
	}	
}
