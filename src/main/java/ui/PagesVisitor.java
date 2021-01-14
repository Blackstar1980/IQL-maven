package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import ast.Ast.Containable;
import ast.Ast.Query;
import ast.Ast.Tabable;
import ast.Id;
import ast.components.*;
import ast.constraints.*;
import ast.constraints.Constraint.HolderCon;
import ast.constraints.Constraint.RequiredCon;
import fields.*;

public class PagesVisitor implements Visitor {
	private int currentPage = 1;
	private int totalPages = 1;
	private CompletableFuture<List<Map<String, String>>> data = new CompletableFuture<>();
	JTabbedPane tabbedPane = new JTabbedPane();
	
	public CompletableFuture<List<Map<String, String>>> getData() {
		return data;
	}

	@Override
	public JDialog visitQuery(Query query) {
		Id dialogId = query.dialog().getType();
		JDialog jDialog = new JDialog();
		if(dialogId == Id.Single) {
			jDialog = visitSingle((DSingle)query.dialog());
			List<Containable> containers = query.containers();
			List<JPanelContainer> panels = new ArrayList<>();
			for(Containable container:containers) {
				panels.add(getPanel(container));
			}
			constructSingleDialog(jDialog, query.dialog().getDescription(), panels);
		}
		if(dialogId == Id.Multi) {
			jDialog = visitMulti((DMulti)query.dialog());
		}
		if(dialogId == Id.Pages) {
			jDialog = visitPages((DPages)query.dialog());
			List<Containable> containers = query.containers();
			List<JPanelContainer> panels = new ArrayList<>();
			for(Containable container:containers) {
				panels.add(getPanel(container));
			}
			constructPageDialog(jDialog, query.dialog().getDescription(), panels);
		}
		jDialog.pack();
		jDialog.setVisible(true);
		return jDialog;
	}
	
	private JPanelContainer getPanel(Containable container) {
		return container.accept(this);
	}
	
	private void constructSingleDialog(JDialog dialog, String description,
			List<JPanelContainer> panels) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		for(JPanel panel : panels) {
			gbc.gridy++;
			dialog.add(panel, gbc);
		}
		JPanel buttonsPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		JButton approveButton = new JButton("Approve");
		List<Map<String, String>> results = new ArrayList<>();
		cancelButton.addActionListener(l-> System.exit(0));
		buttonsPanel.add(approveButton);
		approveButton.addActionListener(e->{
			var saved = saveData(panels);
			if(saved!=null) {
				results.add(saved);
				this.data.complete(results);
				dialog.dispose();
			}});
		buttonsPanel.add(cancelButton);
		gbc.gridy++;
		dialog.add(buttonsPanel, gbc);
	}
	
	private void constructPageDialog(JDialog dialog, String description,
			List<JPanelContainer> panels) {
		List<Map<String, String>> results = new ArrayList<>();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		for(JPanel panel : panels) {
			gbc.gridy++;
			dialog.add(panel, gbc);
		}
		JPanel buttonsPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		JButton approveButton = new JButton("Approve");
		JButton prevButton = new JButton("<");
		JButton nextButton = new JButton(">");
		JButton addButton = new JButton("Add");
		JButton deleteButton = new JButton("Delete");
		JLabel pagesIndex = new JLabel(getPageIndex());
		JPanel utilsPanel = new JPanel();
		deleteButton.setEnabled(false);
		prevButton.setEnabled(false);
		nextButton.setEnabled(false);
		deleteButton.setEnabled(false);
		
		nextButton.addActionListener(e -> {
////			if(results.size() < currentPage) {
//				var saved = saveData(panels);
//				if(saved==null)
//					return;
//				results.set(currentPage -1, saved);
////			}
			currentPage++;
			if(currentPage == totalPages)
				nextButton.setEnabled(false);
			if(currentPage > 1)
				prevButton.setEnabled(true);
			pagesIndex.setText(getPageIndex());
			updateFields(panels, results.get(currentPage -1));
		});
		
		prevButton.addActionListener(e -> {
////			if(results.size() < currentPage) {
//				var saved = saveData(panels);
//				if(saved==null)
//					return;
//				addOrUpdate(results, saved, currentPage -1);
////				results.add(saved);
////			}
			currentPage--;
			if(currentPage == 1)
				prevButton.setEnabled(false);
			if(currentPage < totalPages)
				nextButton.setEnabled(true);
			pagesIndex.setText(getPageIndex());
			updateFields(panels, results.get(currentPage -1));
		});
		
		addButton.addActionListener(e -> {
			var saved = saveData(panels);
			if(saved==null)
				return;
			addOrUpdate(results, saved, currentPage -1);
//			results.add(saved);
			updateFields(panels, new HashMap<String, String>());
			totalPages++;
			deleteButton.setEnabled(true);
			if(totalPages > 1) 
				prevButton.setEnabled(true);
			currentPage = totalPages;
			pagesIndex.setText(getPageIndex());
		});
		
		deleteButton.addActionListener(e -> {
			totalPages--;
			if(totalPages == 1)
				deleteButton.setEnabled(false);
			currentPage = currentPage-1>0? currentPage-1: currentPage;
			if(currentPage == 1)
				prevButton.setEnabled(false);
			if(currentPage == totalPages)
				nextButton.setEnabled(false);
			pagesIndex.setText(getPageIndex());
		});
		
		utilsPanel.add(prevButton);
		utilsPanel.add(pagesIndex);
		utilsPanel.add(nextButton);
		utilsPanel.add(deleteButton);
		utilsPanel.add(addButton);

		cancelButton.addActionListener(l-> System.exit(0));
		buttonsPanel.add(approveButton);
		approveButton.addActionListener(e->{
			var saved = saveData(panels);
			if(saved==null) 
				return;
//			if(results.size() != totalPages)
			addOrUpdate(results, saved, currentPage -1);
			this.data.complete(results);
			dialog.dispose();
		});
		buttonsPanel.add(cancelButton);
		gbc.gridy++;
		dialog.add(utilsPanel, gbc);
		gbc.gridy++;
		dialog.add(buttonsPanel, gbc);
	}
	
	private void addOrUpdate(List<Map<String, String>> map, Map<String, String> value, int index) {
		if(map.size() > index)
			map.set(index, value);
		else
			map.add(value);
	}

	private String getPageIndex() {
		return "Page " + currentPage + " of " + totalPages;
	}
	
	private void updateFields(List<JPanelContainer> panels, Map<String, String> values) {
		for(JPanelContainer panel: panels) {
			if(panel.getId() == Id.TabsContainer)
				updateTabFields(panel, values);
			else if(panel.getId() == Id.Group) {
				updateGroupFields(panel, values);
				} else {
					if(panel instanceof JPanelWithValue panelWithValue) {
						updateComponentFields(panelWithValue, values);
					}
				}
		}
	}
	
	private void updateTabFields(JPanelContainer panel, Map<String, String> values) {
		if(panel.getComponents().length == 0)
			return;
		JTabbedPane tabPanel = (JTabbedPane)panel.getComponents()[0];
		for(int i=0; i<tabPanel.getTabCount(); i++) {
			JPanelContainer tab = (JPanelContainer)(tabPanel.getComponent(i));
			java.awt.Component[] comps = tab.getComponents();
		
			for(java.awt.Component comp: comps) 
			{
				if(comp instanceof JPanelWithValue panelWithValue) {
					updateComponentFields(panelWithValue, values);
				} else if (comp instanceof JPanelContainer container){
					updateGroupFields(container, values);
				}
			}
		}
	}
	
	private void updateGroupFields(JPanelContainer panel, Map<String, String> values) {
		java.awt.Component[] comps = panel.getComponents();
		for(java.awt.Component comp: comps) {
			if(comp instanceof JPanelWithValue panelWithValue) {
				updateComponentFields(panelWithValue, values);
			}
		}
	}
	
	private void updateComponentFields(JPanelWithValue panelWithValue, Map<String, String> values) {
			String fieldName = panelWithValue.getName();
			String value = values.get(fieldName) == null? "":values.get(fieldName);
			panelWithValue.setValueOrDefault(value);
	}
	
	private Map<String, String> saveData(List<JPanelContainer> panels) {
		Map<String, String> data = new HashMap<>();
		boolean haveErrors = false;
		for(JPanelContainer panel: panels) {
			if(panel.getId() == Id.TabsContainer)
				haveErrors |= checkTabErrors(data, haveErrors, panel);
			else if(panel.getId() == Id.Group) {
				haveErrors |= checkGroupErrors(data, haveErrors, panel);
				} else {
					haveErrors |= checkComponentErrors(data, haveErrors, panel);
				}
		}
		if(haveErrors)
			return null;
		return data;
	}

	private boolean checkComponentErrors(Map<String, String> data, boolean haveErrors,
			java.awt.Component comp) {
		if(comp instanceof JPanelWithValue) {
			JPanelWithValue panelWithValue = (JPanelWithValue)comp;
			haveErrors |=panelWithValue.checkForError();
			String value = panelWithValue.getValue() == null || panelWithValue.getValue().isEmpty()? null: panelWithValue.getValue();
			data.put(panelWithValue.getName(), value);
		}
		return haveErrors;
	}

	private boolean checkGroupErrors(Map<String, String> data, boolean haveErrors,
			JPanelContainer panel) {
		java.awt.Component[] comps = panel.getComponents();
		for(java.awt.Component comp: comps) {
			haveErrors |= checkComponentErrors(data, haveErrors, comp);
		}
		return haveErrors;
	}
	
	private boolean checkTabErrors(Map<String, String> data, boolean haveErrors,
			JPanelContainer panel) {
		if(panel.getComponents().length == 0)
			return false;
		JTabbedPane tabPanel = (JTabbedPane)panel.getComponents()[0];
		for(int i=0; i<tabPanel.getTabCount(); i++) {
			JPanelContainer tab = (JPanelContainer)(tabPanel.getComponent(i));
			java.awt.Component[] comps = tab.getComponents();
		
			for(java.awt.Component comp: comps) 
			{
				if(comp instanceof JPanelWithValue panelWithValue) {
					haveErrors |= checkComponentErrors(data, haveErrors, panelWithValue);
				} else if (comp instanceof JPanelContainer container){
					haveErrors |= checkGroupErrors(data, haveErrors, container);
				}
			}
		}
		return haveErrors;
	}
	

	@Override
	public JDialog visitSingle(DSingle dialog) {
		JDialog jDialog = new JDialog();
		jDialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		jDialog.setTitle(dialog.getTitle());
		JLabel desc = generateDesc(dialog.getDescription());
		jDialog.add(desc, gbc);
		jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jDialog.setResizable(false);
		return jDialog;
	}

	@Override
	public JDialog visitMulti(DMulti dialog) {
		JDialog jDialog = new JDialog();
		jDialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		jDialog.setTitle(dialog.getTitle());
		JLabel desc = generateDesc(dialog.getDescription());
		jDialog.add(desc, gbc);
		jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jDialog.setResizable(false);
		return jDialog;
	}

	@Override
	public JDialog visitPages(DPages dialog) {
		JDialog jDialog = new JDialog();
		jDialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		jDialog.setTitle(dialog.getTitle());
		JLabel desc = generateDesc(dialog.getDescription());
		jDialog.add(desc, gbc);
		jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jDialog.setResizable(false);
		return jDialog;
	}

	@Override
	public JPanelContainer visitGroup(Group group) {
		String title = group.getTitle();
		List<Component> components = group.getComponents();
		List<JPanelWithValue> panels = new ArrayList<>();
		for(Component c:components) {
			JPanelWithValue panel = (JPanelWithValue)getPanel((Containable)c);
			panels.add(panel);
		}
		return addGroupPanels(title, panels);
	}
	
	private JPanelContainer addGroupPanels(String title, List<JPanelWithValue> panels) {
		JPanelContainer groupPanel = new JPanelContainer(Id.Group){
			@Override
			public boolean checkForError() {
				boolean haveErrors = false;
				for (JPanelWithValue panel: panels) {
					haveErrors |= panel.checkForError();
				}
				setHasError(haveErrors);
				return haveErrors;
			}
		};
		groupPanel.setLayout(new GridBagLayout());
		groupPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 20, 5, 20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel jTitle = generateGroupTitle(title);
		groupPanel.add(jTitle, gbc);
		gbc.insets.set(0, 0, 0, 0);
		for(JPanelWithValue panel : panels) {
			gbc.gridy++;
			groupPanel.add(panel, gbc);
		}
		return groupPanel;
	}

	@Override
	public JPanelContainer visitTab(Tab tab) {
		List<JPanelContainer> panels = new ArrayList<>();
		String title = tab.getTitle();
		List<Tabable> containers = tab.getContainers();
		for(Tabable container: containers) {
			JPanelContainer panel = getPanel((Containable)container);
			panels.add(panel);
		}
		tabbedPane.add(title, addTabPanels(title, panels));
		JPanelContainer tabPanel = new JPanelContainer(Id.TabsContainer){
			@Override
			public boolean checkForError() {
				boolean haveErrors = false;
				for (JPanelContainer panel: panels) {
					haveErrors |= haveErrors || panel.checkForError();
				}
				setHasError(haveErrors);
				return haveErrors;
				}
		};
		tabPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		tabPanel.add(tabbedPane, gbc);
		return tabPanel;
	}

	private JPanelContainer addTabPanels(String title, List<JPanelContainer> panels) {
		JPanelContainer tabPanel = new JPanelContainer(Id.Tab){
			@Override
			public boolean checkForError() {
				boolean haveErrors = false;
				for (JPanelContainer panel: panels) {
					haveErrors |= haveErrors || panel.checkForError();
				}
				setHasError(haveErrors);
				return haveErrors;
			}
		};
		tabPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		
		// Align all the component in the tab to the top right corner
		for(int i=0; i<panels.size(); i++) {
			if(i == panels.size()-1)
				gbc.weighty = 1;
			gbc.gridy++;
			tabPanel.add(panels.get(i), gbc);
		}
		return tabPanel;
	}

	@Override
	public JPanelWithValue visitString(CString component) {
		PlaceholderTextField textField = new PlaceholderTextField();
		Map<ConstraintId, Constraint> constraints = getMapConstraint(component.getConstraints());
		JPanelWithValue panel = new JPanelWithValue(Id.String, component.getName()) {
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.String, textField.getText(), constraints));
			}

			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value))
					textField.setText(component.getDefVal() == null? "": component.getDefVal());
				else
					textField.setText(value);
			}
		};
		JLabel title = generateTitle(component.getTitle(), constraints);
		JLabel errorMsg = panel.getErrorLabel();
		panel.setValueOrDefault("");

		StyleId style = (StyleId)constraints.get(ConstraintId.STYLE);
		if(textField.getText().isEmpty() && constraints.containsKey(ConstraintId.HOLDER)) {
			textField.setPlaceholder(((HolderCon)constraints.get(ConstraintId.HOLDER)).value());
		}
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setPlaceHolder(textField, constraints);
				boolean hasError = panel.setErrorLabel(validateConstraints(Id.String, textField.getText(), constraints));
				if(!hasError)
					panel.setValue(textField.getText());
			}	
			@Override
			public void focusGained(FocusEvent e) {}
		});
		textField.addKeyListener(new KeyAdapter() {
			 public void keyReleased(KeyEvent e) {
				 String error = validateConstraints(Id.String, textField.getText(), constraints);
				 if(" ".equals(error)) {
					errorMsg.setText(" "); 
					panel.setValue(textField.getText());
				 }
			 }
		});
		return setLayout(style, title, textField, errorMsg, panel);
	}
	
	private JPanelWithValue setLayout(StyleId style, JLabel title, JComponent textField,
			JLabel errorMsg, JPanelWithValue panel) {
		return switch (style) {
			case Block -> setBlockStyle(title, textField, errorMsg, panel);
			case Inline -> setInlineStyle(title, textField, errorMsg, panel);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + style);
		};
	}
	
	private JPanelWithValue setInlineStyle(JLabel title, JComponent textField,
			JLabel errorMsg, JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		if(!(textField instanceof JScrollPane))
			textField.setPreferredSize(new Dimension(280, 30));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		panel.add(textField, gbc);
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(errorMsg, gbc);
		return panel;
	}

	private JPanelWithValue setBlockStyle(JLabel title, JComponent textField, JLabel errorMsg, JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		if(!(textField instanceof JScrollPane))
			textField.setPreferredSize(new Dimension(280, 30));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(textField, gbc);
		gbc.gridy = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(errorMsg, gbc);
		return panel;
	}
	
	private void setTextAreaPlaceHolder(PlaceholderTextAreaField textField, Map<ConstraintId, Constraint> constraints) {
		if(textField.getText().isEmpty() && constraints.containsKey(ConstraintId.HOLDER)) {
			textField.setPlaceholder(((HolderCon)constraints.get(ConstraintId.HOLDER)).value());
		}
	}
	
	private void setPlaceHolder(PlaceholderTextField textField, Map<ConstraintId, Constraint> constraints) {
		if(textField.getText().isEmpty() && constraints.containsKey(ConstraintId.HOLDER)) {
			textField.setPlaceholder(((HolderCon)constraints.get(ConstraintId.HOLDER)).value());
		}
	}

	private void setPasswordPlaceHolder(PlaceholderPasswordField textField, Map<ConstraintId, Constraint> constraints) {
		if(String.valueOf(textField.getPassword()).isEmpty() && constraints.containsKey(ConstraintId.HOLDER)) {
			textField.setPlaceholder(((HolderCon)constraints.get(ConstraintId.HOLDER)).value());
		}
	} 
	
	@Override
	public JPanelWithValue visitTextArea(CTextArea component) {
		PlaceholderTextAreaField textArea = new PlaceholderTextAreaField(5, 25);
		textArea.setPreferredSize(new Dimension(250, 30));
		Map<ConstraintId, Constraint> constraints = getMapConstraint(component.getConstraints());
		JPanelWithValue panel = new JPanelWithValue(Id.TextArea, component.getName()){
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.TextArea, String.valueOf(textArea.getText()), constraints));
			}
			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value))
					textArea.setText(component.getDefVal());
				else
					textArea.setText(value);
			}
		};
		
		panel.setValueOrDefault("");
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane (textArea);
		JLabel title = generateTitle(component.getTitle(), constraints);
		JLabel errorMsg = panel.getErrorLabel();
		StyleId style = (StyleId)constraints.get(ConstraintId.STYLE);
		setTextAreaPlaceHolder(textArea, constraints);
		textArea.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setTextAreaPlaceHolder(textArea, constraints);
				String text = String.valueOf(textArea.getText());
				boolean hasError = panel.setErrorLabel(validateConstraints(Id.TextArea, text, constraints));
				if(!hasError)
					panel.setValue(text);
			}
			
			@Override
			public void focusGained(FocusEvent e) {}
		});
		textArea.addKeyListener(new KeyAdapter() {
			 public void keyReleased(KeyEvent e) {
				 String text = String.valueOf(textArea.getText());
				 String error = validateConstraints(Id.TextArea, text, constraints);
				 if(" ".equals(error)) {
					errorMsg.setText(" "); 
					panel.setValue(text);
				 }
			 }
		});
		return setLayout(style, title, scroll, errorMsg, panel);
	}

	@Override
	public JPanelWithValue visitPassword(CPassword component) {
		PlaceholderPasswordField passField = new PlaceholderPasswordField();
		Map<ConstraintId, Constraint> constraints = getMapConstraint(component.getConstraints());
		JPanelWithValue panel = new JPanelWithValue(Id.Password, component.getName()){
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.Password, String.valueOf(passField.getPassword()), constraints));
			}
			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value))
					passField.setText(component.getDefVal());
				else
					passField.setText(value);
			}
		};
		
		panel.setValueOrDefault("");
		JLabel title = generateTitle(component.getTitle(), constraints);
		JLabel errorMsg = panel.getErrorLabel();
		StyleId style = (StyleId)constraints.get(ConstraintId.STYLE);
		setPasswordPlaceHolder(passField, constraints);
		passField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setPasswordPlaceHolder(passField, constraints);
				String passVal = String.valueOf(passField.getPassword());
				boolean hasError = panel.setErrorLabel(validateConstraints(Id.Password, passVal, constraints));
				if(!hasError)
					panel.setValue(passVal);
			}
			
			@Override
			public void focusGained(FocusEvent e) {}
		});
		passField.addKeyListener(new KeyAdapter() {
			 public void keyReleased(KeyEvent e) {
				 String passVal = String.valueOf(passField.getPassword());
				 String error = validateConstraints(Id.Password, passVal, constraints);
				 if(" ".equals(error)) {
					errorMsg.setText(" "); 
					panel.setValue(passVal);
				 }
			 }
		});
		return setPasswordLayout(style, title, passField, errorMsg, panel);
	}

	@Override
	public JPanelWithValue visitInteger(CInteger component) {
		PlaceholderIntegerField textField = new PlaceholderIntegerField();
		Map<ConstraintId, Constraint> constraints = getMapConstraint(component.getConstraints());
		JPanelWithValue panel = new JPanelWithValue(Id.Integer, component.getName()){
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.Integer,textField.getText(), constraints));
			}
			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value))
					textField.setText(String.valueOf(component.getDefVal()));
				else
					textField.setText(value);
			}
		};
		panel.setValueOrDefault("");
		JLabel title = generateTitle(component.getTitle(), constraints);
		JLabel errorMsg = panel.getErrorLabel();
		StyleId style = (StyleId)constraints.get(ConstraintId.STYLE);
		setPlaceHolder(textField, constraints);
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setPlaceHolder(textField, constraints);
				boolean hasError = panel.setErrorLabel(validateConstraints(Id.Integer,textField.getText(), constraints));
				if(!hasError)
					panel.setValue(textField.getText());
			}
			@Override
			public void focusGained(FocusEvent e) {}
		});
		textField.addKeyListener(new KeyAdapter() {
			 public void keyReleased(KeyEvent e) {
				 String error = validateConstraints(Id.Integer, textField.getText(), constraints);
				 if(" ".equals(error)) {
					errorMsg.setText(" "); 
					panel.setValue(textField.getText());
				 }
			 }
		});
		return setLayout(style, title, textField, errorMsg, panel);
	}

	@Override
	public JPanelWithValue visitDecimal(CDecimal component) {
		PlaceholderDecimalField textField = new PlaceholderDecimalField();
		Map<ConstraintId, Constraint> constraints = getMapConstraint(component.getConstraints());
		JPanelWithValue panel = new JPanelWithValue(Id.Decimal, component.getName()){
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.Decimal, textField.getText(), constraints));
			}
			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value))
					textField.setText(String.valueOf(component.getDefVal()));
				else
					textField.setText(value);
			}
		};
		panel.setValueOrDefault("");
		JLabel title = generateTitle(component.getTitle(), constraints);
		JLabel errorMsg = panel.getErrorLabel();
		StyleId style = (StyleId)constraints.get(ConstraintId.STYLE);
		setHolder(textField, constraints);
		setPlaceHolder(textField, constraints);
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setPlaceHolder(textField, constraints);
				boolean hasError = panel.setErrorLabel(validateConstraints(Id.Decimal, textField.getText(), constraints));
				if(!hasError)
					panel.setValue(textField.getText());
			}
			@Override
			public void focusGained(FocusEvent e) {}
		});
		textField.addKeyListener(new KeyAdapter() {
			 public void keyReleased(KeyEvent e) {
				 String error = validateConstraints(Id.Decimal, textField.getText(), constraints);
				 if(" ".equals(error)) {
					errorMsg.setText(" "); 
					panel.setValue(textField.getText());
				 }
			 }
		});
		return setLayout(style, title, textField, errorMsg, panel);
	}

	@Override
	public JPanelWithValue visitBoolean(CBoolean component) {
		JRadioButton yesButton = new JRadioButton("Yes");
		JRadioButton noButton = new JRadioButton("No");
		Map<ConstraintId, Constraint> constraints = getMapConstraint(component.getConstraints());
		JPanelWithValue panel = new JPanelWithValue(Id.Boolean, component.getName()){
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.Boolean, getValue(), constraints));
			}
			@Override
			public void setValueOrDefault(String value) {
				String defValue = "".equals(value)? component.getDefVal(): value;
				if("".contentEquals(defValue)) {
					yesButton.setSelected(false);
					noButton.setSelected(false);
				}					
				if("true".equals(defValue)) {
					yesButton.setSelected(true);
					noButton.setSelected(false);
				}
				if("false".equals(defValue)) {
					noButton.setSelected(true);
					yesButton.setSelected(false);
				}
			}
		};
		panel.setValueOrDefault(component.getDefVal());
		JLabel title = generateTitle(component.getTitle(), constraints);

		StyleId style = (StyleId)constraints.get(ConstraintId.STYLE);
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noButton.setSelected(false);
				yesButton.setSelected(yesButton.isSelected());
				if (yesButton.isSelected())
					panel.setValue("true");
				else 
					panel.setValue("");
			}
		});
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				yesButton.setSelected(false);
				noButton.setSelected(noButton.isSelected());
				if (noButton.isSelected())
					panel.setValue("false");
				else 
					panel.setValue("");
			}
		});
		return setBooleanLayout(style, title, yesButton, noButton, panel);
	}
	
	private JPanelWithValue setBooleanLayout(StyleId style, JLabel title, JRadioButton yesButton,
			JRadioButton noButton, JPanelWithValue panel) {
		return switch (style) {
			case Block -> setBooleanBlockStyle(title, yesButton, noButton, panel);
			case Inline -> setBooleanInlineStyle(title, yesButton, noButton, panel);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + style);
		};
	}
	
	private JPanelWithValue setBooleanInlineStyle(JLabel title, JRadioButton yesButton,
			JRadioButton noButton, JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		panel.add(yesButton, gbc);
		gbc.gridx = 2;
		panel.add(noButton, gbc);
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setBooleanBlockStyle(JLabel title, JRadioButton yesButton,
			JRadioButton noButton, JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(yesButton, gbc);
		gbc.gridy = 2;
		panel.add(noButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	@Override
	public JPanelWithValue visitSlider(CSlider slider) {
		return slider.make();
//		JLabel valueLabel = new JLabel();
//		valueLabel.setFont(new Font(valueLabel.getFont().getName(), valueLabel.getFont().getStyle(), 20));
//		valueLabel.setText(""+slider.getDefVal());
//		JSlider jSlider = initJSlider(slider);
//		JPanelWithValue panel = new JPanelWithValue(Id.Slider, slider.getName()){
//			@Override
//			public boolean checkForError() {
//				return false;
//			}
//			@Override
//			public void setValueOrDefault(String value) {
//				if("".equals(value)) {
//					valueLabel.setText(""+slider.getDefVal());
//					jSlider.setValue(slider.getDefVal());
//				} else {
//					valueLabel.setText(value);
//					jSlider.setValue(Integer.valueOf(value));
//				}
//			}
//		};
//		panel.setValueOrDefault("");
//		panel.setValue(String.valueOf(slider.getDefVal()));
//		panel.setLayout(new GridBagLayout());
//		jSlider.addChangeListener(new ChangeListener() {
//	            @Override
//	            public void stateChanged(ChangeEvent e) {
//	            	valueLabel.setText(""+jSlider.getValue());
//	                panel.setValue(String.valueOf(jSlider.getValue()));
//	            }
//	        });
//		Map<ConstraintId, Constraint> constraints = getMapConstraint(slider.getConstraints());
//		JLabel title = generateTitle(slider.getTitle(), constraints);
//		StyleId style = (StyleId)constraints.get(ConstraintId.STYLE);
//		return switch (style) {
//			case Block -> setSliderBlockStyle(title, jSlider, valueLabel, panel);
//			case Inline -> setSliderInlineStyle(title, jSlider, valueLabel, panel);
//			default ->
//			throw new IllegalArgumentException("Unexpected value: " + style);
//		};
	}
	
	private JPanelWithValue setSliderInlineStyle(JLabel title, JSlider slider, JLabel value,
			JPanelWithValue panel) {
		GridBagConstraints gbc = new GridBagConstraints();
		slider.setPreferredSize(new Dimension(230, 50));
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
//		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		panel.add(slider, gbc);
//		gbc.gridx = 2;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 0, 32);
		panel.add(value, gbc);
		return panel;
	}

	private JPanelWithValue setSliderBlockStyle(JLabel title, JSlider slider, JLabel value, JPanelWithValue panel) {
		GridBagConstraints gbc = new GridBagConstraints();
		slider.setPreferredSize(new Dimension(230, 50));
		gbc.anchor = GridBagConstraints.WEST;
//		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(slider, gbc);
//		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.insets = new Insets(0, 0, 0, 32);
		panel.add(value, gbc);
		return panel;
	}
	
	private JSlider initJSlider(CSlider slider) {
		JSlider jSlider = new JSlider(slider.getMinVal(), slider.getMaxVal(), slider.getDefVal());
		jSlider.setLabelTable(null);
		Map<ConstraintId, Constraint> constraints = getMapConstraint(slider.getConstraints());
		if(constraints.containsKey(ConstraintId.MAJORTICKS))
			jSlider.setMajorTickSpacing(((Constraint.MajorTicksCon)constraints.get(ConstraintId.MAJORTICKS)).value());
		else
			jSlider.setMajorTickSpacing(slider.getMaxVal() - slider.getMinVal());
		if(constraints.containsKey(ConstraintId.MINORTICKS))
			jSlider.setMinorTickSpacing(((Constraint.MinorTicksCon)constraints.get(ConstraintId.MINORTICKS)).value());
		jSlider.setPaintTicks(true);
		jSlider.setPaintLabels(true);
		return jSlider;
	}

	@Override
	public JPanelWithValue visitSingleOpt(CSingleOpt component) {
		Map<ConstraintId, Constraint> constraints = getMapConstraint(component.getConstraints());
		StyleId style = (StyleId)constraints.get(ConstraintId.STYLE);
		JLabel title = new JLabel();
		String name = component.getName();
		title.setText(component.getTitle());
		List<String> options = component.getOptions();
		String defaultValue = getSelectedOpt(constraints);
		return switch (style) {
			case InlineRadio -> setSingleInlineRadioStyle(name, title, options, defaultValue, constraints);
			case InlineList -> setSingleInlineListStyle(name, title, options, defaultValue, constraints);
			case BlockRadio -> setSingleBlockRadioStyle(name, title, options, defaultValue, constraints);
			case BlockList -> setSingleBlockListStyle(name, title, options, defaultValue, constraints);
			default ->
				setSingleBlockRadioStyle(name, title, options, defaultValue, constraints);
		};
	}
	
	private String getSelectedOpt(Map<ConstraintId, Constraint> constraints) {
		Constraint.SelectedCon selected = (Constraint.SelectedCon)constraints.get(ConstraintId.SELECTED);
		return selected == null? "" : selected.value();
	}

	private JPanelWithValue setSingleBlockListStyle(String name, JLabel title,
			List<String> options, String defaultValue, Map<ConstraintId, Constraint> constraints) {
		String[] optionsArray = options.toArray(String[]::new);
		JComboBox<String> combo = new JComboBox<String>(optionsArray);
//		if("".equals(defaultValue))
//			combo.setSelectedIndex(-1);
//		else
//			combo.setSelectedItem(defaultValue);
		JPanelWithValue panel = new JPanelWithValue(Id.SingleOpt, name){
			@Override
			public boolean checkForError() {
				if(combo.getSelectedItem() != null)
					setValue(combo.getSelectedItem().toString());
				return setErrorLabel(validateConstraints(Id.SingleOpt, getValue(), constraints));
			}

			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value))
					combo.setSelectedIndex(-1);
				else
					combo.setSelectedItem(value);
			}
		};
		panel.setValueOrDefault(defaultValue);
		panel.setLayout(new GridBagLayout());
		panel.setValue(defaultValue);
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.setValue(combo.getSelectedItem().toString());
				panel.setErrorLabel(validateConstraints(Id.SingleOpt, panel.getValue(), constraints));
			}
		});
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(combo, gbc);
		gbc.gridy = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setSingleInlineListStyle(String name, JLabel title,
			List<String> options, String defaultValue, Map<ConstraintId, Constraint> constraints) {
		String[] optionsArray = options.toArray(String[]::new);
		JComboBox<String> combo = new JComboBox<String>(optionsArray);
//		if("".equals(defaultValue))
//			combo.setSelectedIndex(-1);
//		else
//			combo.setSelectedItem(defaultValue);
		JPanelWithValue panel = new JPanelWithValue(Id.SingleOpt, name){
			@Override
			public boolean checkForError() {
				if(combo.getSelectedItem() != null)
				setValue(combo.getSelectedItem().toString());
			return setErrorLabel(validateConstraints(Id.SingleOpt, getValue(), constraints));
			}
			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value))
					combo.setSelectedIndex(-1);
				else
					combo.setSelectedItem(value);
			}
		};
		panel.setValueOrDefault(defaultValue);
		panel.setLayout(new GridBagLayout());
		panel.setValue(defaultValue);
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.setValue(combo.getSelectedItem().toString());
				panel.setErrorLabel(validateConstraints(Id.SingleOpt, panel.getValue(), constraints));
			}
		});
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		panel.add(combo, gbc);
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setSingleInlineRadioStyle(String name, JLabel title,
			List<String> options, String defaultValue, Map<ConstraintId, Constraint> constraints) {
		ButtonGroup buttonGroup = new ButtonGroup();
		JPanelWithValue panel = new JPanelWithValue(Id.SingleOpt, name){
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.SingleOpt, getValue(), constraints));
			}

			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value))
					buttonGroup.clearSelection();
			}
		};
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
//		ButtonGroup buttonGroup = new ButtonGroup();
		options.forEach(option -> {
			JRadioButton button = new JRadioButton(option);
			if(option.equals(defaultValue)) {
				button.setSelected(true);
				panel.setValue(defaultValue);
			}
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					panel.setValue(button.getText());	
					panel.setErrorLabel(validateConstraints(Id.SingleOpt, panel.getValue(), constraints));
				}
			});
			buttonGroup.add(button);
			gbc.gridx++;
			panel.add(button, gbc);
		});
		return panel;
	}
	
	private JPanelWithValue setSingleBlockRadioStyle(String name, JLabel title,
			List<String> options, String defaultValue, Map<ConstraintId, Constraint> constraints) {
		ButtonGroup buttonGroup = new ButtonGroup();
		JPanelWithValue panel = new JPanelWithValue(Id.SingleOpt, name){
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.SingleOpt, getValue(), constraints));
			}

			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value))
					buttonGroup.clearSelection();
			}
		};
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		options.forEach(option -> {
			JRadioButton button = new JRadioButton(option);
			if(option.equals(defaultValue))
				button.setSelected(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					panel.setValue(button.getText());	
					panel.setErrorLabel(validateConstraints(Id.SingleOpt, panel.getValue(), constraints));
				}
			});
			panel.setValue(defaultValue);
			buttonGroup.add(button);
			gbc.gridy++;
			panel.add(button, gbc);
		});
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	@Override
	public JPanelWithValue visitMultiOpt(CMultiOpt component) {
		Map<ConstraintId, Constraint> constraints = getMapConstraint(component.getConstraints());
		StyleId style = (StyleId)constraints.get(ConstraintId.STYLE);
		JLabel title = new JLabel();
		String name = component.getName();
		title.setText(component.getTitle());
		List<String> options = component.getOptions();
		List<String> defaultValues= Stream.of(getSelectedOpt(constraints).split("\\|"))
			     .map(String::trim)
			     .collect(Collectors.toList());
		return switch (style) {
			case InlineCheckbox -> setMultiInlineCheckboxStyle(name, title, options, defaultValues, constraints);
			case InlineList -> setMultiInlineListStyle(name, title, options, defaultValues, constraints);
			case BlockCheckbox -> setMultiBlockCheckboxStyle(name, title, options, defaultValues, constraints);
			case BlockList -> setMultiBlockListStyle(name, title, options, defaultValues, constraints);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + style);
		};
	}
	
	private JPanelWithValue setMultiBlockListStyle(String name, JLabel title,
			List<String> options, List<String> defaultValues,
			Map<ConstraintId, Constraint> constraints) {
		MultiOptItem[] items = new MultiOptItem[options.size()];
		for(int i=0; i<options.size(); i++) {
			items[i] = new MultiOptItem(options.get(i), defaultValues.contains(options.get(i)));
		}
		DefaultComboBoxModel<MultiOptItem> model = new DefaultComboBoxModel<>(items);
		CheckedComboBox<MultiOptItem> ccb = new CheckedComboBox<>(model);
		JPanelWithValue panel = new JPanelWithValue(Id.MultiOpt, name){
			@Override
			public boolean checkForError() {
				String selectedItems = ccb.getSelectedItems();
				setValue(selectedItems);
				return setErrorLabel(validateConstraints(Id.MultiOpt, selectedItems, constraints));
			}

			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value)) {
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(defaultValues.contains(options.get(i)));
					}
					ccb.updateUI();
				} else {
					List<String> values = new ArrayList<String>(Arrays.asList(value.split("|")));
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(values.contains(options.get(i)));
					}
					ccb.updateUI();
				}
			}
		};
		panel.setValueOrDefault("");
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		MultiOptComboBox comboBox = new MultiOptComboBox();
		comboBox.setPreferredSize(new Dimension(120, 25));
		comboBox.setEditable(true);
		comboBox.addItems(options);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(ccb, gbc);
		gbc.gridy = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setMultiInlineListStyle(String name, JLabel title, List<String> options,
			List<String> defaultValues, Map<ConstraintId, Constraint> constraints) {
		MultiOptItem[] items = new MultiOptItem[options.size()];
		for(int i=0; i<options.size(); i++) {
			items[i] = new MultiOptItem(options.get(i), defaultValues.contains(options.get(i)));
		}
		DefaultComboBoxModel<MultiOptItem> model = new DefaultComboBoxModel<>(items);
		CheckedComboBox<MultiOptItem> ccb = new CheckedComboBox<>(model);
		JPanelWithValue panel = new JPanelWithValue(Id.MultiOpt, name){
			@Override
			public boolean checkForError() {
				String selectedItems = ccb.getSelectedItems();
				setValue(selectedItems);
				return setErrorLabel(validateConstraints(Id.MultiOpt, selectedItems, constraints));
			}

			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value)) {
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(defaultValues.contains(options.get(i)));
					}
					ccb.updateUI();
				} else {
					List<String> values = new ArrayList<String>(Arrays.asList(value.split("|")));
					for(int i=0; i<options.size(); i++) {
						items[i].setSelected(values.contains(options.get(i)));
					}
					ccb.updateUI();
				}
			}
		};
		panel.setValueOrDefault("");
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		MultiOptComboBox comboBox = new MultiOptComboBox();
		comboBox.setPreferredSize(new Dimension(120, 25));
		comboBox.setEditable(true);
		comboBox.addItems(options);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		panel.add(ccb, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setMultiBlockCheckboxStyle(String name, JLabel title,
			List<String> options, List<String> defaultValues, Map<ConstraintId, Constraint> constraints) {
		List<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
		JPanelWithValue panel = new JPanelWithValue(Id.MultiOpt, name){
			@Override
			public boolean checkForError() {
				String selectedItems = checkBoxes.stream()
						.filter(c -> c.isSelected())
						.map(c -> c.getText())
						.collect(Collectors.toList()).toString();
				// Remove the '[' and ']' characters of the list
				selectedItems = selectedItems.substring(1, selectedItems.length()-1);
				setValue(selectedItems);
				return setErrorLabel(validateConstraints(Id.MultiOpt, selectedItems, constraints));
			}

			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value)) {
					setCheckBoxValues(checkBoxes, defaultValues);
				}else {
					List<String> values = new ArrayList<String>(Arrays.asList(value.split(",")));
					setCheckBoxValues(checkBoxes, values);
				}
			}
		};
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		
		options.forEach(option -> {
			JCheckBox checkBox = new JCheckBox(option);
			checkBoxes.add(checkBox);
			if(defaultValues.contains(option))
				checkBox.setSelected(true);
			gbc.gridy++;
			panel.add(checkBox, gbc);
		});
		gbc.gridy++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setMultiInlineCheckboxStyle(String name, JLabel title,
			List<String> options, List<String> defaultValues, Map<ConstraintId, Constraint> constraints) {
		List<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
		JPanelWithValue panel = new JPanelWithValue(Id.MultiOpt, name){
			@Override
			public boolean checkForError() {
				String selectedItems = checkBoxes.stream()
				.filter(c -> c.isSelected())
				.map(c -> c.getText())
				.collect(Collectors.toList()).toString();
				// Remove the '[' and ']' characters of the list
				selectedItems = selectedItems.substring(1, selectedItems.length()-1);
				setValue(selectedItems);
				return setErrorLabel(validateConstraints(Id.MultiOpt, selectedItems, constraints));
			}
			@Override
			public void setValueOrDefault(String value) {
				if("".equals(value)) {
					setCheckBoxValues(checkBoxes, defaultValues);
//					for(JCheckBox checkBox: checkBoxes ) {
//						if(defaultValues.contains(checkBox.getText()))
//							checkBox.setSelected(true);
//						else
//							checkBox.setSelected(false);
//					}
				}else {
					List<String> values = new ArrayList<String>(Arrays.asList(value.split(",")));
					setCheckBoxValues(checkBoxes, values);
//					for(JCheckBox checkBox: checkBoxes ) {
//						if(values.contains(checkBox.getText()))
//							checkBox.setSelected(true);
//						else
//							checkBox.setSelected(false);
//					}	
				}
			}
		};
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 20, 0, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		options.forEach(option -> {
			JCheckBox checkBox = new JCheckBox(option);
			checkBoxes.add(checkBox);
			if(defaultValues.contains(option))
				checkBox.setSelected(true);
			gbc.gridx++;
			panel.add(checkBox, gbc);
		});
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}
	
	private void setCheckBoxValues(List<JCheckBox> checkBoxes , List<String> values) {
		for(JCheckBox checkBox: checkBoxes ) {
			if(values.contains(checkBox.getText()))
				checkBox.setSelected(true);
			else
				checkBox.setSelected(false);
		}
	}

	private JLabel generateTitle(String title, Map<ConstraintId, Constraint> constraints) {
		JLabel label = new JLabel();
		label.setBorder(new EmptyBorder(0, 0, 0, 10));
		Font font = label.getFont();
		label.setFont(font.deriveFont(font.getStyle() | Font.ITALIC));
		label.setText(title);
		addRequiredToLabel(label, constraints);
		return label;
	}
	
	private JLabel generateGroupTitle(String title) {
		JLabel label = new JLabel();
		label.setBorder(new EmptyBorder(0, 0, 0, 10));
		Font font = label.getFont().deriveFont(16.0f);
		label.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
		label.setText(title);
		return label;
	}
	
	private JLabel generateDesc(String title) {
		JLabel label = new JLabel();
		label.setBorder(new EmptyBorder(10, 10, 20, 10));
		label.setText(title);
		return label;
	}
	
	private void setHolder(JTextField textField, Map<ConstraintId, Constraint> constraints) {
		if(textField.getText().isEmpty() && constraints.containsKey(ConstraintId.HOLDER)) {
			textField.setForeground(Color.gray);
			textField.setText(((HolderCon)constraints.get(ConstraintId.HOLDER)).value());
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

	private Map<ConstraintId, Constraint> getMapConstraint(List<Constraint> constraints) {
		Map<ConstraintId, Constraint> constraintMap = new HashMap<>();
		constraints.forEach(con -> {
			ConstraintId id = con.getID();
			switch (id) {
				case MIN -> constraintMap.put(id,(Constraint.MinCon)con);
				case MAX -> constraintMap.put(id,(Constraint.MaxCon)con);
				case REGEX -> constraintMap.put(id,(Constraint.RegexCon)con);
				case HOLDER -> constraintMap.put(id,(Constraint.HolderCon)con);
				case REQUIRED -> constraintMap.put(id,(Constraint.RequiredCon)con);
				case STYLE -> constraintMap.put(id,(StyleId)con);
				case MINORTICKS -> constraintMap.put(id,(Constraint.MinorTicksCon)con);
				case MAJORTICKS -> constraintMap.put(id,(Constraint.MajorTicksCon)con);
				case SELECTED -> constraintMap.put(id,(Constraint.SelectedCon)con);
				default ->
					throw new IllegalArgumentException("Unexpected String constraint: " + id);
			}
		});
		// If not exist then apply those default setting
		if (!constraintMap.containsKey(ConstraintId.STYLE))
			constraintMap.put(ConstraintId.STYLE, StyleId.Block);
		if (!constraintMap.containsKey(ConstraintId.REQUIRED))
			constraintMap.put(ConstraintId.REQUIRED, RequiredCon.Required);
		return constraintMap;	
	}
	
	private JLabel addRequiredToLabel(JLabel label, Map<ConstraintId, Constraint> constraints) {
		if(constraints.get(ConstraintId.REQUIRED) == RequiredCon.Required) {
			label.setText(label.getText() + " (Required)" );
		}
		return label;
	}
	
	private String validateConstraints(Id id, String text, Map<ConstraintId, Constraint> constraints) {
		if(constraints.get(ConstraintId.REQUIRED) == RequiredCon.Required) {
			String requiredError = ((Constraint.RequiredCon)constraints.get(ConstraintId.REQUIRED)).validate(text);
			if(!" ".equals(requiredError))
				return requiredError;
		}
		if(!text.isEmpty() && constraints.containsKey(ConstraintId.MIN)) {
			String minError = switch (id) {
				case Integer ->
					((Constraint.MinCon)constraints.get(ConstraintId.MIN)).validateInt(text);
				case Decimal ->
					((Constraint.MinCon)constraints.get(ConstraintId.MIN)).validateDec(text);
				default ->
					((Constraint.MinCon)constraints.get(ConstraintId.MIN)).validateLength(text);
			};
			if(!" ".equals(minError))
				return minError;
		}
		if(!text.isEmpty() && constraints.containsKey(ConstraintId.MAX)) {
			String maxError = switch (id) {
				case Integer ->
					((Constraint.MaxCon)constraints.get(ConstraintId.MAX)).validateInt(text);
				case Decimal ->
					((Constraint.MaxCon)constraints.get(ConstraintId.MAX)).validateDec(text);
				default ->
					((Constraint.MaxCon)constraints.get(ConstraintId.MAX)).validateLength(text);
			};
			if(!" ".equals(maxError))
				return maxError;
		}
		if(!text.isEmpty() && constraints.containsKey(ConstraintId.REGEX)) {
			String regexError = ((Constraint.RegexCon)constraints.get(ConstraintId.REGEX)).validate(text);
			if(!" ".equals(regexError))
				return regexError;
			}
		return " ";
	}

}
