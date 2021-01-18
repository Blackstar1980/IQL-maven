package ui;

import java.awt.Color;
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
import fields.*;

public class SingleVisitor implements Visitor {
	private CompletableFuture<List<Map<String, String>>> data = new CompletableFuture<>();
	private final JTabbedPane tabbedPane = new JTabbedPane();
	
	private void commitData(List<Map<String, String>> res){
		res=res.stream().map(m->new HashMap<>(m)).collect(Collectors.toList());
	    data.complete(res);  
	}
	
	@Override
	public CompletableFuture<List<Map<String, String>>> getData() {
		return data;
	}

	@Override
	public JDialog visitQuery(Query query) {
		JDialog jDialog = new JDialog();
		jDialog = visitSingle((DSingle)query.dialog());
		List<Containable> containers = query.containers();
		List<JPanelContainer> panels = new ArrayList<>();
		for(Containable container:containers)
			panels.add(getPanel(container));
		constructSingleDialog(jDialog, panels);
		jDialog.pack();
		jDialog.setVisible(true);
		System.out.println("in Single");
		return jDialog;
	}
	
	private JPanelContainer getPanel(Containable container) {
		return container.accept(this);
	}
	
	private void constructSingleDialog(JDialog dialog, List<JPanelContainer> panels) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
//		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		for(JPanel panel : panels) {
			gbc.gridy++;
			dialog.add(panel, gbc);
		}
		gbc.fill = GridBagConstraints.NONE;
		JPanel buttonsPanel = new JPanel(new GridBagLayout());
		buttonsPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		JButton cancelButton = new JButton("Cancel");
		JButton approveButton = new JButton("Approve");
		buttonsPanel.add(approveButton, gbc);
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 5, 0, 0);
		buttonsPanel.add(cancelButton, gbc);
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.gridx = 0;
		List<Map<String, String>> results = new ArrayList<>();
		
		dialog.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosed(WindowEvent e) {
				results.clear();
				results.add(getEntries(panels));
//				data.complete(results);
				commitData(results);
				dialog.dispose();
		    }
		});
		cancelButton.addActionListener(e-> {
			results.clear();
			results.add(getEntries(panels));
//			this.data.complete(results);
			commitData(results);
			dialog.dispose();
		});
		approveButton.addActionListener(e->{
			var saved = saveAsMap(panels);
			if(saved!=null) {
				results.add(saved);
//				this.data.complete(results);
				commitData(results);
				dialog.dispose();
			}});
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.WEST;
		dialog.add(buttonsPanel, gbc);
	}
	
	private Map<String, String> setNullValues(Map<String, String> entries) {
		for (Map.Entry<String, String> entry : entries.entrySet()) {
		    entry.setValue(null);
		}
		return entries;
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
		JDialog jDialog = new JDialog();
		jDialog.setLayout(new GridBagLayout());
		JPanel panel = (JPanel)jDialog.getContentPane();
		panel.setBorder(new EmptyBorder(0, 15, 5, 15));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		jDialog.setTitle(dialog.getTitle());
		JTextArea desc = generateDesc(dialog.getDescription());
		jDialog.add(desc, gbc);
		jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jDialog.setResizable(false);
		return jDialog;
	}

	@Override
	public JDialog visitMulti(DMulti dialog) {
		return null;
	}

	@Override
	public JDialog visitPages(DPages dialog) {
		return null;
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
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 0, 5, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.gridwidth = 3;
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
		gbc.weightx = 1.0;
//		gbc.insets = new Insets(10, 10, 10, 10);
//		tabPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
//		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		tabPanel.add(tabbedPane, gbc);
//		tabPanel.setBackground(Color.green);
//		tabPanel.setOpaque(true);
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
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 10, 0, 10);
		gbc.weightx = 1.0;
		
		// Align all the component in the tab to the top right corner
		for(int i=0; i<panels.size(); i++) {
			if(i == panels.size()-1)
				gbc.weighty = 1.0;
			gbc.gridy++;
			tabPanel.add(panels.get(i), gbc);
		}
//		tabPanel.setBackground(Color.blue);
//		tabPanel.setOpaque(true);
		return tabPanel;
	}

	@Override
	public JPanelWithValue visitString(CString component) {
		return component.make();
	}
	
	@Override
	public JPanelWithValue visitTextArea(CTextArea component) {
		return component.make();
	}

	@Override
	public JPanelWithValue visitPassword(CPassword component) {
		return component.make();
	}

	@Override
	public JPanelWithValue visitInteger(CInteger component) {
		return component.make();
	}

	@Override
	public JPanelWithValue visitDecimal(CDecimal component) {
		return component.make();
	}

	@Override
	public JPanelWithValue visitBoolean(CBoolean component) {
		return component.make();
	}

	@Override
	public JPanelWithValue visitSlider(CSlider slider) {
		return slider.make();
	}

	@Override
	public JPanelWithValue visitSingleOpt(CSingleOpt component) {
		return component.make();
	}
	
	@Override
	public JPanelWithValue visitMultiOpt(CMultiOpt component) {
		return component.make();
	}
	
	private JLabel generateGroupTitle(String title) {
		JLabel label = new JLabel();
		label.setBorder(new EmptyBorder(0, 0, 0, 10));
		Font font = label.getFont().deriveFont(16.0f);
		label.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
		label.setText(title);
		return label;
	}
	
	private JTextArea generateDesc(String title) {
		JTextArea label = new JTextArea();
		label.setBorder(new EmptyBorder(5, 0, 10, 0));
		label.setText(title);
		label.setEditable(false);
		label.setBackground(new Color(238, 238, 238));
		return label;
	}
	
}
