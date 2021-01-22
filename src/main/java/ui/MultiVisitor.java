package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
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

public class MultiVisitor extends UiVisitor {
	private static final String BLOCK = "block";
	private static final String BLOCK_LIST = "blockList";
	private static final int HEIGHT = 640;
	private static int width;
	private List<Containable> containers;
//	private Map<Integer, JPanel> rowsMap = new HashMap<>();
	private int fieldsRowsCounter = 0;
//	private int currentPage = 1;
//	private int totalPages = 1;
//	private CompletableFuture<List<Map<String, String>>> data = new CompletableFuture<>();
//	private final JTabbedPane tabbedPane = new JTabbedPane();

//	@Override
//	public CompletableFuture<List<Map<String, String>>> getData() {
//		return data;
//	}

	@Override
	public JFrame visitQuery(Query query) {
		JFrame jFrame = new JFrame();
		String desc = query.dialog().getDescription();
		jFrame = visitMulti((DMulti)query.dialog());
		this.containers = query.containers();
		List<JPanelContainer> panels = new ArrayList<>();
		for(Containable container:containers)
			panels.add(getPanel(container));
		constructDialog(jFrame, panels, desc);
		jFrame.pack();
		jFrame.setVisible(true);
		System.out.println("in Multi");
		return jFrame;
	}

	@Override
	protected void constructDialog(JFrame frame, List<JPanelContainer> panels, String desc) {
		GridBagConstraints gbc = new GridBagConstraints();
//		setMultiLayout(panels);
		List<JLabel> titles = getTitles(panels);
//		gbc.anchor = GridBagConstraints.PAGE_START;
//		gbc.weighty = 0.2;
//		gbc.weightx = 1.0;
//		gbc.gridx = 1;
//		gbc.gridy = 1;
//		gbc.weighty = 0.2;
//		gbc.weightx = 1.0;
		JPanel titlePanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		for(JLabel title : titles) {
			title.setPreferredSize(new Dimension(140, 22));
			titlePanel.add(title, gbc);
			gbc.insets = new Insets(0, 5, 0, 0);
			gbc.gridx++;
		}
		titlePanel.setBorder(new EmptyBorder(0, 30,0,0));
		gbc.gridx = 0;
		frame.add(titlePanel, gbc);
//		gbc.gridx = 0;
//		gbc.gridy = 2;
//		JCheckBox selectButton = new JCheckBox();
//		frame.add(selectButton, gbc);
//		gbc.gridx++;
		JPanel fieldsContainer = new JPanel(new GridBagLayout());
		gbc.weighty = 1.0;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 3;
//		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
//		gbc.anchor = GridBagConstraints.CENTER;
//		gbc.weightx = 1.0;
//		gbc.weighty = 1.0;
		JScrollPane scroll = new JScrollPane(fieldsContainer);
		
		gbc.fill = GridBagConstraints.NONE;
		frame.add(scroll, gbc);
		width = titlePanel.getPreferredSize().width + 25;
//		scroll.setMaximumSize(new Dimension(width, HEIGHT-180));
//		scroll.setMinimumSize(new Dimension(width, HEIGHT-180));
//		fieldsContainer.setMaximumSize(new Dimension(500, HEIGHT-100));
//		fieldsContainer.setMinimumSize(new Dimension(500, HEIGHT-100));
		addNewFields(frame, fieldsContainer);
		disableButton(fieldsContainer);
		
		
		JPanel buttonsPanel = new JPanel(new GridBagLayout());
		buttonsPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		JButton cancelButton = new JButton("Cancel");
		JButton approveButton = new JButton("Approve");
		JButton addButton = new JButton("Add");
//		buttonsPanel.add(approveButton, gbc);
//		gbc.gridx = 1;
//		gbc.insets = new Insets(0, 5, 0, 0);
//		buttonsPanel.add(cancelButton, gbc);
//		gbc.insets = new Insets(0, 0, 0, 0);
//		gbc.gridx = 0;
		List<Map<String, String>> results = new ArrayList<>();
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosed(WindowEvent e) {
				results.clear();
				results.add(getEntries(panels));
				commitData(results);
				frame.dispose();
		    }
		});

		addButton.addActionListener(e -> {
			addNewFields(frame, fieldsContainer);
		});
		cancelButton.addActionListener(e-> {
			results.clear();
			results.add(getEntries(panels));
			commitData(results);
			frame.dispose();
		});
		
		approveButton.addActionListener(e->{
			List<Map<String, String>> res = new ArrayList<>();
			var haveError = updateData(fieldsContainer.getComponents(), res);
//			var saved = saveAsMap(panels);
			if(!haveError) {
				commitData(res);
				frame.dispose();
			}});
		
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		
		buttonsPanel.add(approveButton, gbc);
		gbc.insets = new Insets(5, 5, 5, 0);
		gbc.gridx = 1;
		buttonsPanel.add(cancelButton, gbc);
		gbc.gridx = 2;
		buttonsPanel.add(addButton, gbc);
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 10;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 0; 
//		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.PAGE_END;
		frame.add(buttonsPanel, gbc);
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		
		JTextArea jDesc = generateDesc(desc);
//		double frameWidth = frame.getPreferredSize().getWidth() - 30;
		int height = jDesc.getPreferredSize().height;
		int newheight =jDesc.getPreferredSize().getWidth() > width?
		 (int) ((jDesc.getPreferredSize().getWidth()/(width-30))*height) : height;
		jDesc.setPreferredSize(new Dimension(width-30, newheight));
		jDesc.setWrapStyleWord(true);
		jDesc.setLineWrap(true);
		gbc.gridx = 0;
		gbc.gridy = 0;
//		gbc.weighty = 1.0;
//		gbc.weightx = 1.0;
		gbc.insets = new Insets(0, 5, 0, 0);
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 0;
		scroll.setMaximumSize(new Dimension(width, HEIGHT-newheight - 136));
		scroll.setMinimumSize(new Dimension(width, HEIGHT-newheight - 136));
		frame.add(jDesc, gbc);
//		int width = frame.getPreferredSize().width+20;
//		int height = frame.getPreferredSize().height;
		frame.setPreferredSize(new Dimension(width+30, HEIGHT));
	}



	private boolean updateData(Component[] components, List<Map<String, String>> results) {
		if(components == null)
			return true;
		boolean haveErrors = false;
		for(int i=0; i<components.length; i++) {
			Map<String, String> map = new HashMap<>();
			if(components[i] instanceof JPanel jPanel) {
				for(int j=0; j<jPanel.getComponentCount(); j++) {
					haveErrors |= checkComponentErrors(map, haveErrors, jPanel.getComponent(j));
				}
				results.add(map);
			}			
		}
		return haveErrors;		
	}

	private void addNewFields(JFrame frame, JPanel fieldsContainer) {
//		boolean isFirst = true;
		enableButton(fieldsContainer);
		GridBagConstraints gbc = new GridBagConstraints();
		List<JPanelContainer> panels = new ArrayList<>();
		JPanel row = new JPanel(new GridBagLayout());
		for(Containable container:containers)
			panels.add(getPanel(container));
		setMultiLayout(panels);
		gbc.insets = new Insets(5, 0, 5, 0);
//		gbc.gridy = fieldsRowsCounter;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
		ImageIcon trashImg = new ImageIcon(this.getClass().getResource("/images/trash.png"));
		Image trashDim = trashImg.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon y = new ImageIcon(trashDim);
//		showButton.setIcon(showImg2);
		JButton deleteButton = new JButton();
		deleteButton.setIcon(y);
//		gbc.insets = new Insets(0, 0, 0, 0);
		deleteButton.setPreferredSize(new Dimension(22, 22));

		row.add(deleteButton, gbc);
//		gbc.weightx = 1.0;
		for(JPanel panel : panels) {
			gbc.gridx++;
			gbc.insets = new Insets(5, 5, 0, 0);
			row.add(panel, gbc);	
		}
//		gbc.gridx = 0;
//		gbc.weighty = 0.1;
		gbc.insets = new Insets(0, 0, 0, 0);
//		gbc.gridheight = GridBagConstraints.REMAINDER;
//		gbc.weighty = 0.1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.PAGE_START;
		fieldsContainer.add(row, gbc);
//		rowsMap.put(fieldsRowsCounter, fieldsContainer);
//		if(fieldsRowsCounter == 0)
//			deleteButton.setEnabled(false);
		deleteButton.addActionListener(e -> {
			fieldsContainer.remove(row);
			disableButton(fieldsContainer);
			fieldsRowsCounter--;
			frame.revalidate();
			frame.validate();
		});
		fieldsRowsCounter++;
		frame.revalidate();
		frame.validate();
	}
	
	private void disableButton(JPanel fieldsContainer) {
		if(fieldsContainer.getComponentCount() == 1) {
			JButton button = (JButton)(((JPanel)fieldsContainer.getComponent(0)).getComponent(0));
			button.setEnabled(false);
		}
	}
	
	private void enableButton(JPanel fieldsContainer) {
		if(fieldsRowsCounter > 0) {
			JButton button = (JButton)(((JPanel)fieldsContainer.getComponent(0)).getComponent(0));
			button.setEnabled(true);
		}
	}
	
//	private void deleteRows(JFrame frame, JPanel fieldsContainer) {
//		for(int i=0; i<rowsMap.size(); i++) {
//			JPanel panel = (JPanel) rowsMap.get(i).getComponent(0);
//			JCheckBox checkBox = (JCheckBox) panel.getComponent(0);
//			if(checkBox.isSelected()) {
//				JPanel row = rowsMap.get(i);
//				rowsMap.remove(i);
//				fieldsContainer.remove(row);
////				panelName.revalidate();
//				fieldsContainer.revalidate();
//				fieldsContainer.validate();
//				fieldsContainer.repaint();
//			}
//		}
//		
//	}
	
	private List<JLabel> getTitles(List<JPanelContainer> panels) {
		Map<String, String> titleMap = getEntries(panels);
		List<JLabel> titles = new ArrayList<>();
		titleMap.forEach((key, value) -> {
			JLabel label = new JLabel(key);
//			label.setBorder(new EmptyBorder(0, 0, 0 , 0));
			titles.add(label);
		});
		return titles;
	}
	
//	private Map<String, String> setNullValues(Map<String, String> entries) {
//		for (Map.Entry<String, String> entry : entries.entrySet()) {
//		    entry.setValue(null);
//		}
//		return entries;
//	}
//	
//	private void updateDataList(List<Map<String, String>> dataList, Map<String, String> value, int index, boolean setDefault) {
//		if(dataList.size() > index)
//			dataList.set(index, value);
//		else
//			dataList.add(value);
//	}

//	private String getPageIndex() {
//		return "Page " + currentPage + " of " + totalPages;
//	}
	
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
		panelWithValue.getComponent(1).setPreferredSize(new Dimension(140, 22));
	}

//	private void updateUiFields(List<JPanelContainer> panels, Map<String, String> values, boolean setDefault) {
//		for(JPanelContainer panel: panels) {
//			if(panel.getId() == Id.TabsContainer)
//				updateTabFields(panel, values, setDefault);
//			else if(panel.getId() == Id.Group) {
//				updateGroupFields(panel, values, setDefault);
//				} else {
//					if(panel instanceof JPanelWithValue panelWithValue) {
//						updateComponentFields(panelWithValue, values, setDefault);
//					}
//				}
//		}
//	}
	
//	private void updateTabFields(JPanelContainer panel, Map<String, String> values, boolean setDefault) {
//		if(panel.getComponents().length == 0)
//			return;
//		JTabbedPane tabPanel = (JTabbedPane)panel.getComponents()[0];
//		for(int i=0; i<tabPanel.getTabCount(); i++) {
//			JPanelContainer tab = (JPanelContainer)(tabPanel.getComponent(i));
//			java.awt.Component[] comps = tab.getComponents();
//		
//			for(java.awt.Component comp: comps) 
//			{
//				if(comp instanceof JPanelWithValue panelWithValue) {
//					updateComponentFields(panelWithValue, values, setDefault);
//				} else if (comp instanceof JPanelContainer container){
//					updateGroupFields(container, values, setDefault);
//				}
//			}
//		}
//	}
	
//	private void updateGroupFields(JPanelContainer panel, Map<String, String> values, boolean setDefault) {
//		java.awt.Component[] comps = panel.getComponents();
//		for(java.awt.Component comp: comps) {
//			if(comp instanceof JPanelWithValue panelWithValue) {
//				updateComponentFields(panelWithValue, values, setDefault);
//			}
//		}
//	}
	
//	private void updateComponentFields(JPanelWithValue panelWithValue, Map<String, String> values, boolean setDefault) {
//			String fieldName = panelWithValue.getName();
//			String value = values.get(fieldName) == null? "":values.get(fieldName);
//			panelWithValue.setValueOrDefault(value, setDefault);
//	}
	
//	private Map<String, String> getEntries(List<JPanelContainer> panels) {
//		Map<String, String> data = new HashMap<>();
//		for(JPanelContainer panel: panels) {
//			if(panel.getId() == Id.TabsContainer)
//				checkTabErrors(data, false, panel);
//			else if(panel.getId() == Id.Group) {
//				checkGroupErrors(data, false, panel);
//				} else {
//					checkComponentErrors(data, false, panel);
//				}
//		}
//		return setNullValues(data);
//	}
//	
//	private Map<String, String> saveAsMap(List<JPanelContainer> panels) {
//		Map<String, String> data = new HashMap<>();
//		boolean haveErrors = false;
//		for(JPanelContainer panel: panels) {
//			if(panel.getId() == Id.TabsContainer)
//				haveErrors |= checkTabErrors(data, haveErrors, panel);
//			else if(panel.getId() == Id.Group) {
//				haveErrors |= checkGroupErrors(data, haveErrors, panel);
//				} else {
//					haveErrors |= checkComponentErrors(data, haveErrors, panel);
//				}
//		}
//		if(haveErrors)
//			return null;
//		return data;
//	}

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
//
//	private boolean checkGroupErrors(Map<String, String> data, boolean haveErrors,
//			JPanelContainer panel) {
//		java.awt.Component[] comps = panel.getComponents();
//		for(java.awt.Component comp: comps) {
//			haveErrors |= checkComponentErrors(data, haveErrors, comp);
//		}
//		return haveErrors;
//	}
	
//	private boolean checkTabErrors(Map<String, String> data, boolean haveErrors,
//			JPanelContainer panel) {
//		if(panel.getComponents().length == 0)
//			return false;
//		JTabbedPane tabPanel = (JTabbedPane)panel.getComponents()[0];
//		for(int i=0; i<tabPanel.getTabCount(); i++) {
//			JPanelContainer tab = (JPanelContainer)(tabPanel.getComponent(i));
//			java.awt.Component[] comps = tab.getComponents();
//		
//			for(java.awt.Component comp: comps) 
//			{
//				if(comp instanceof JPanelWithValue panelWithValue) {
//					haveErrors |= checkComponentErrors(data, haveErrors, panelWithValue);
//				} else if (comp instanceof JPanelContainer container){
//					haveErrors |= checkGroupErrors(data, haveErrors, container);
//				}
//			}
//		}
//		return haveErrors;
//	}
	

//	@Override
//	public JFrame visitSingle(DSingle dialog) {
//		return null;
//	}
//
//	@Override
//	public JFrame visitMulti(DMulti dialog) {
//		JFrame jDialog = new JFrame();
//		jDialog.setLayout(new GridBagLayout());
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.anchor = GridBagConstraints.WEST;
//		gbc.gridx = 0;
//		gbc.gridy = 0;
//		gbc.gridwidth = 3;
//		jDialog.setTitle(dialog.getTitle());
//		JLabel desc = generateDesc(dialog.getDescription());
//		jDialog.add(desc, gbc);
//		jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		jDialog.setResizable(false);
//		return jDialog;
//	}
//
//	@Override
//	public JFrame visitPages(DPages dialog) {
//		return null;
//	}

	@Override
	public JPanelContainer visitGroup(Group group) {
		throw new IllegalArgumentException("Multi dialog do not support groups");
	}
//	
//	private JPanelContainer addGroupPanels(String title, List<JPanelWithValue> panels) {
//		JPanelContainer groupPanel = new JPanelContainer(Id.Group){
//			@Override
//			public boolean checkForError() {
//				boolean haveErrors = false;
//				for (JPanelWithValue panel: panels) {
//					haveErrors |= panel.checkForError();
//				}
//				setHasError(haveErrors);
//				return haveErrors;
//			}
//		};
//		groupPanel.setLayout(new GridBagLayout());
//		groupPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.anchor = GridBagConstraints.WEST;
//		gbc.insets = new Insets(10, 20, 5, 20);
//		gbc.gridx = 0;
//		gbc.gridy = 0;
//		JLabel jTitle = generateGroupTitle(title);
//		groupPanel.add(jTitle, gbc);
//		gbc.insets.set(0, 0, 0, 0);
//		for(JPanelWithValue panel : panels) {
//			gbc.gridy++;
//			groupPanel.add(panel, gbc);
//		}
//		return groupPanel;
//	}
	
	@Override
	public JPanelContainer visitTab(Tab tab) {
		throw new IllegalArgumentException("Multi dialog do not support tabs");
	}

//	@Override
//	public JPanelContainer visitTab(Tab tab) {
//		List<JPanelContainer> panels = new ArrayList<>();
//		String title = tab.getTitle();
//		List<Tabable> containers = tab.getContainers();
//		for(Tabable container: containers) {
//			JPanelContainer panel = getPanel((Containable)container);
//			panels.add(panel);
//		}
//		tabbedPane.add(title, addTabPanels(title, panels));
//		JPanelContainer tabPanel = new JPanelContainer(Id.TabsContainer){
//			@Override
//			public boolean checkForError() {
//				boolean haveErrors = false;
//				for (JPanelContainer panel: panels) {
//					haveErrors |= haveErrors || panel.checkForError();
//				}
//				setHasError(haveErrors);
//				return haveErrors;
//				}
//		};
//		tabPanel.setLayout(new GridBagLayout());
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.anchor = GridBagConstraints.CENTER;
//		gbc.fill = GridBagConstraints.HORIZONTAL;
//		tabPanel.add(tabbedPane, gbc);
//		return tabPanel;
//	}
//
//	private JPanelContainer addTabPanels(String title, List<JPanelContainer> panels) {
//		JPanelContainer tabPanel = new JPanelContainer(Id.Tab){
//			@Override
//			public boolean checkForError() {
//				boolean haveErrors = false;
//				for (JPanelContainer panel: panels) {
//					haveErrors |= haveErrors || panel.checkForError();
//				}
//				setHasError(haveErrors);
//				return haveErrors;
//			}
//		};
//		tabPanel.setLayout(new GridBagLayout());
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.anchor = GridBagConstraints.NORTHWEST;
//		gbc.gridx = 0;
//		gbc.gridy = 0;
//		gbc.weightx = 1;
//		
//		// Align all the component in the tab to the top right corner
//		for(int i=0; i<panels.size(); i++) {
//			if(i == panels.size()-1)
//				gbc.weighty = 1;
//			gbc.gridy++;
//			tabPanel.add(panels.get(i), gbc);
//		}
//		return tabPanel;
//	}
	
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
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, BLOCK);
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
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, BLOCK);
		CPassword cPassword = new CPassword(component.getName(), component.getTitle(), component.getDefVal() , newConstraints);
		return cPassword.make();
	}

	@Override
	public JPanelWithValue visitInteger(CInteger component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, BLOCK);
		CInteger cInteger = new CInteger(component.getName(), component.getTitle(), component.getDefVal() , newConstraints);
		return cInteger.make();
	}

	@Override
	public JPanelWithValue visitDecimal(CDecimal component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, BLOCK);
		CDecimal cDecimal = new CDecimal(component.getName(), component.getTitle(), component.getDefVal() , newConstraints);
		return cDecimal.make();
	}

	@Override
	public JPanelWithValue visitBoolean(CBoolean component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, BLOCK_LIST);
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
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, BLOCK_LIST);
		CSingleOpt cSingle = new CSingleOpt(component.getName(), component.getTitle(), component.getOptions(), component.getDefValue() , newConstraints);
		return cSingle.make();
	}
	
	@Override
	public JPanelWithValue visitMultiOpt(CMultiOpt component) {
		Id id = component.getType();
		List<Constraint> constraints = component.getConstraints();
		List<Constraint> newConstraints = setMultiDisplay(id, constraints, BLOCK_LIST);
		CMultiOpt cMulti = new CMultiOpt(component.getName(), component.getTitle(), component.getOptions(), component.getDefValues() , newConstraints);
		return cMulti.make();
	}
	
//	private JLabel generateGroupTitle(String title) {
//		JLabel label = new JLabel();
//		label.setBorder(new EmptyBorder(0, 0, 0, 10));
//		Font font = label.getFont().deriveFont(16.0f);
//		label.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
//		label.setText(title);
//		return label;
//	}
	
//	private JLabel generateDesc(String title) {
//		JLabel label = new JLabel();
//		label.setBorder(new EmptyBorder(10, 10, 20, 10));
//		label.setText(title);
//		return label;
//	}
//	
}
