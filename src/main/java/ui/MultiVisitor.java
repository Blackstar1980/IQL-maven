package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ast.Ast.Containable;
import ast.Ast.Query;
import ast.Ast.Tabable;
import ast.Id;
import ast.components.*;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import fields.*;

public class MultiVisitor implements Visitor {
	private static final String DISPLAY_BLOCK = "display=block";
	private static final String DISPLAY_BLOCK_LIST = "display=blockList";
	private int currentPage = 1;
	private int totalPages = 1;
	private CompletableFuture<List<Map<String, String>>> data = new CompletableFuture<>();
	private final JTabbedPane tabbedPane = new JTabbedPane();
	
	@Override
	public CompletableFuture<List<Map<String, String>>> getData() {
		return data;
	}

	@Override
	public JDialog visitQuery(Query query) {
		JDialog jDialog = new JDialog();
		jDialog = visitMulti((DMulti)query.dialog());
		List<Containable> containers = query.containers();
		List<JPanelContainer> panels = new ArrayList<>();
		for(Containable container:containers)
			panels.add(getPanel(container));
		constructMultiDialog(jDialog, query.dialog().getDescription(), panels);
		jDialog.pack();
		jDialog.setVisible(true);
		System.out.println("in Multi");
		return jDialog;
	}
	
	private JPanelContainer getPanel(Containable container) {
		return container.accept(this);
	}
	
	private void constructMultiDialog(JDialog dialog, String description,
			List<JPanelContainer> panels) {
		GridBagConstraints gbc = new GridBagConstraints();
		setMultiLayout(panels);
		List<JLabel> titles = getTitles(panels);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		for(JLabel title : titles) {
			gbc.gridx++;
			dialog.add(title, gbc);
		}
		gbc.gridx = 0;
		gbc.gridy = 2;
		for(JPanel panel : panels) {
			gbc.gridx++;
			dialog.add(panel, gbc);
		}
		JPanel buttonsPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		JButton approveButton = new JButton("Approve");
		List<Map<String, String>> results = new ArrayList<>();
		
		dialog.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosed(WindowEvent e) {
				results.clear();
				results.add(getEntries(panels));
				data.complete(results);
				dialog.dispose();
		    }
		});
		cancelButton.addActionListener(e-> {
			results.clear();
			results.add(getEntries(panels));
			this.data.complete(results);
			dialog.dispose();
		});
		buttonsPanel.add(approveButton);
		approveButton.addActionListener(e->{
			var saved = saveAsMap(panels);
			if(saved!=null) {
				results.add(saved);
				this.data.complete(results);
				dialog.dispose();
			}});
		buttonsPanel.add(cancelButton);
		gbc.gridy++;
		dialog.add(buttonsPanel, gbc);
	}
	
	private List<JLabel> getTitles(List<JPanelContainer> panels) {
		Map<String, String> titleMap = getEntries(panels);
		List<JLabel> titles = new ArrayList<>();
		titleMap.forEach((key, value) -> {
			JLabel label = new JLabel(key);
			label.setBorder(new EmptyBorder(0, 20, 0 , 0));
			titles.add(label);
		});
		return titles;
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
		
		dialog.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosed(WindowEvent e) {
				results.clear();
				results.add(getEntries(panels));
				data.complete(results);
				dialog.dispose();
		    }
		});
		cancelButton.addActionListener(e-> {
			results.clear();
			results.add(getEntries(panels));
			this.data.complete(results);
			dialog.dispose();
		});
		buttonsPanel.add(approveButton);
		approveButton.addActionListener(e->{
			var saved = saveAsMap(panels);
			if(saved!=null) {
				results.add(saved);
				this.data.complete(results);
				dialog.dispose();
			}});
		buttonsPanel.add(cancelButton);
		gbc.gridy++;
		dialog.add(buttonsPanel, gbc);
	}
	
	private Map<String, String> setNullValues(Map<String, String> entries) {
		for (Map.Entry<String, String> entry : entries.entrySet()) {
		    entry.setValue(null);
		}
		return entries;
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
		
		dialog.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosed(WindowEvent e) {
				results.clear();
				results.add(getEntries(panels));
				data.complete(results);
				dialog.dispose();
		    }
		});
		
		nextButton.addActionListener(e -> {
			var saved = saveAsMap(panels);
			if(saved==null)
				return;
			updateDataList(results, saved, currentPage -1, false);
			currentPage++;
			if(currentPage == totalPages)
				nextButton.setEnabled(false);
			if(currentPage > 1)
				prevButton.setEnabled(true);
			pagesIndex.setText(getPageIndex());
			updateUiFields(panels, results.get(currentPage-1), false);
		});
		
		prevButton.addActionListener(e -> {
			var saved = saveAsMap(panels);
			if(saved==null)
				return;
			if(results.size() < currentPage) {
				results.add(saved);
			}
			updateDataList(results, saved, currentPage -1, false);
			currentPage--;
			
			if(currentPage == 1)
				prevButton.setEnabled(false);
			if(currentPage < totalPages)
				nextButton.setEnabled(true);
			pagesIndex.setText(getPageIndex());
			updateUiFields(panels, results.get(currentPage -1), false);
		});
		
		addButton.addActionListener(e -> {
			var saved = saveAsMap(panels);
			if(saved==null)
				return;
			updateDataList(results, saved, currentPage -1, true);
			updateUiFields(panels, new HashMap<String, String>(), true);
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
			results.remove(currentPage);
			updateUiFields(panels, results.get(currentPage -1), false);
		});
		
		utilsPanel.add(prevButton);
		utilsPanel.add(pagesIndex);
		utilsPanel.add(nextButton);
		utilsPanel.add(deleteButton);
		utilsPanel.add(addButton);

		cancelButton.addActionListener(e-> {
			results.clear();
			results.add(getEntries(panels));
			this.data.complete(results);
			dialog.dispose();
		});
		buttonsPanel.add(approveButton);
		approveButton.addActionListener(e->{
			var saved = saveAsMap(panels);
			if(saved==null) 
				return;
			updateDataList(results, saved, currentPage -1, false);
			this.data.complete(results);
			dialog.dispose();
		});
		buttonsPanel.add(cancelButton);
		gbc.gridy++;
		dialog.add(utilsPanel, gbc);
		gbc.gridy++;
		dialog.add(buttonsPanel, gbc);
	}
	
	private void updateDataList(List<Map<String, String>> dataList, Map<String, String> value, int index, boolean setDefault) {
		if(dataList.size() > index)
			dataList.set(index, value);
		else
			dataList.add(value);
	}

	private String getPageIndex() {
		return "Page " + currentPage + " of " + totalPages;
	}
	
	private void setMultiLayout(List<JPanelContainer> panels) {
		for(JPanelContainer panel: panels) {
			if(panel.getId() == Id.TabsContainer)
				setMultiTabLayout(panel);
			else if(panel instanceof JPanelWithValue panelWithValue) {
				setMultiComponentLayout(panelWithValue);
			}
		}
	}
	
	private void setMultiTabLayout(JPanelContainer panel) {
		if(panel.getComponents().length == 0)
			return;
		JTabbedPane tabPanel = (JTabbedPane)panel.getComponents()[0];
		for(int i=0; i<tabPanel.getTabCount(); i++) {
			JPanelContainer tab = (JPanelContainer)(tabPanel.getComponent(i));
			java.awt.Component[] comps = tab.getComponents();
			for(java.awt.Component comp: comps) {
				if(comp instanceof JPanelWithValue panelWithValue) {
					setMultiComponentLayout(panelWithValue);
				}
			}
		}
	}
	
	private void setMultiComponentLayout(JPanelWithValue panelWithValue) {
		panelWithValue.getErrorLabel().setVisible(false);
		panelWithValue.getComponent(0).setVisible(false);
		panelWithValue.getComponent(1).setPreferredSize(new Dimension(100, 22));
	}

	private void updateUiFields(List<JPanelContainer> panels, Map<String, String> values, boolean setDefault) {
		for(JPanelContainer panel: panels) {
			if(panel.getId() == Id.TabsContainer)
				updateTabFields(panel, values, setDefault);
			else if(panel.getId() == Id.Group) {
				updateGroupFields(panel, values, setDefault);
				} else {
					if(panel instanceof JPanelWithValue panelWithValue) {
						updateComponentFields(panelWithValue, values, setDefault);
					}
				}
		}
	}
	
	private void updateTabFields(JPanelContainer panel, Map<String, String> values, boolean setDefault) {
		if(panel.getComponents().length == 0)
			return;
		JTabbedPane tabPanel = (JTabbedPane)panel.getComponents()[0];
		for(int i=0; i<tabPanel.getTabCount(); i++) {
			JPanelContainer tab = (JPanelContainer)(tabPanel.getComponent(i));
			java.awt.Component[] comps = tab.getComponents();
		
			for(java.awt.Component comp: comps) 
			{
				if(comp instanceof JPanelWithValue panelWithValue) {
					updateComponentFields(panelWithValue, values, setDefault);
				} else if (comp instanceof JPanelContainer container){
					updateGroupFields(container, values, setDefault);
				}
			}
		}
	}
	
	private void updateGroupFields(JPanelContainer panel, Map<String, String> values, boolean setDefault) {
		java.awt.Component[] comps = panel.getComponents();
		for(java.awt.Component comp: comps) {
			if(comp instanceof JPanelWithValue panelWithValue) {
				updateComponentFields(panelWithValue, values, setDefault);
			}
		}
	}
	
	private void updateComponentFields(JPanelWithValue panelWithValue, Map<String, String> values, boolean setDefault) {
			String fieldName = panelWithValue.getName();
			String value = values.get(fieldName) == null? "":values.get(fieldName);
			panelWithValue.setValueOrDefault(value, setDefault);
	}
	
	private Map<String, String> getEntries(List<JPanelContainer> panels) {
		Map<String, String> data = new HashMap<>();
		for(JPanelContainer panel: panels) {
			if(panel.getId() == Id.TabsContainer)
				checkTabErrors(data, false, panel);
			else if(panel.getId() == Id.Group) {
				checkGroupErrors(data, false, panel);
				} else {
					checkComponentErrors(data, false, panel);
				}
		}
		return setNullValues(data);
	}
	
	private Map<String, String> saveAsMap(List<JPanelContainer> panels) {
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
		return null;
	}

	@Override
	public JDialog visitMulti(DMulti dialog) {
		JDialog jDialog = new JDialog();
		jDialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		jDialog.setTitle(dialog.getTitle());
		JLabel desc = generateDesc(dialog.getDescription());
		jDialog.add(desc, gbc);
		jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jDialog.setResizable(false);
		return jDialog;
	}

	@Override
	public JDialog visitPages(DPages dialog) {
		return null;
	}

	@Override
	public JPanelContainer visitGroup(Group group) {
		throw new IllegalArgumentException("Multi dialog do not support groups");
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
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
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
	
	private List<Constraint> setMultiDisplay(Id id, List<Constraint> constraints, String display) {
		constraints = constraints.stream()
			.filter(con -> con.getID() != ConstraintId.DISPLAY)
			.collect(Collectors.toList());
		Constraint constraintId = ConstraintId.from(id, display);
		constraints.add(constraintId);
		return constraints;
	}

	@Override
	public JPanelWithValue visitString(CString component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, DISPLAY_BLOCK);
		CString cString = new CString(component.getName(), component.getTitle(), component.getDefVal() , newConstraints);
		return cString.make();
	}
	
	@Override
	public JPanelWithValue visitTextArea(CTextArea component) {
		throw new IllegalArgumentException("TextArea is not supported in Multi dialog");
	}

	@Override
	public JPanelWithValue visitPassword(CPassword component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, DISPLAY_BLOCK);
		CPassword cPassword = new CPassword(component.getName(), component.getTitle(), component.getDefVal() , newConstraints);
		return cPassword.make();
	}

	@Override
	public JPanelWithValue visitInteger(CInteger component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, DISPLAY_BLOCK);
		CInteger cInteger = new CInteger(component.getName(), component.getTitle(), component.getDefVal() , newConstraints);
		return cInteger.make();
	}

	@Override
	public JPanelWithValue visitDecimal(CDecimal component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, DISPLAY_BLOCK);
		CDecimal cDecimal = new CDecimal(component.getName(), component.getTitle(), component.getDefVal() , newConstraints);
		return cDecimal.make();
	}

	@Override
	public JPanelWithValue visitBoolean(CBoolean component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, DISPLAY_BLOCK_LIST);
		CBoolean cBoolean = new CBoolean(component.getName(), component.getTitle(), component.getDefVal() , newConstraints);
		return cBoolean.make();
	}

	@Override
	public JPanelWithValue visitSlider(CSlider slider) {
		throw new IllegalArgumentException("Slider is not supported in Multi dialog");
	}

	@Override
	public JPanelWithValue visitSingleOpt(CSingleOpt component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, DISPLAY_BLOCK_LIST);
		CSingleOpt cSingle = new CSingleOpt(component.getName(), component.getTitle(), component.getOptions(), component.getDefValue() , newConstraints);
		return cSingle.make();
	}
	
	@Override
	public JPanelWithValue visitMultiOpt(CMultiOpt component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, DISPLAY_BLOCK_LIST);
		CMultiOpt cMulti = new CMultiOpt(component.getName(), component.getTitle(), component.getOptions(), component.getDefValues() , newConstraints);
		return cMulti.make();
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
	
}
