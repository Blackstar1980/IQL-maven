package ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ast.Ast.Containable;
import ast.Ast.Query;
import ast.Id;
import ast.components.*;
import fields.*;

public class PagesVisitor extends UiVisitor {
	private int currentPage = 1;
	private int totalPages = 1;

	@Override
	public JFrame visitQuery(Query query) {
		JFrame jFrame = new JFrame();
		jFrame = visitPages((DPages)query.dialog());
		List<Containable> containers = query.containers();
		List<JPanelContainer> panels = new ArrayList<>();
		for(Containable container:containers) {
			panels.add(getPanel(container));
		}
		constructDialog(jFrame, panels);
		jFrame.pack();
		jFrame.setVisible(true);
		System.out.println("in Pages");
		return jFrame;
	}

	@Override
	protected void constructDialog(JFrame frame, List<JPanelContainer> panels) {
		List<Map<String, String>> results = new ArrayList<>();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		for(JPanel panel : panels) {
			gbc.gridy++;
			frame.add(panel, gbc);
		}
		gbc.fill = GridBagConstraints.NONE;
		JPanel buttonsPanel = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		JButton approveButton = new JButton("Approve");
		JButton prevButton = new JButton("<");
		JButton nextButton = new JButton(">");
		JButton addButton = new JButton("Add");
		JButton deleteButton = new JButton("Delete");
		JLabel pagesIndex = new JLabel(getPageIndex());
		JPanel utilsPanel = new JPanel(new GridBagLayout());
		deleteButton.setBackground(Color.red);
		deleteButton.setForeground(Color.white);
		deleteButton.setEnabled(false);
		prevButton.setEnabled(false);
		nextButton.setEnabled(false);
		deleteButton.setEnabled(false);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosed(WindowEvent e) {
				results.clear();
				results.add(getEntries(panels));
				commitData(results);
				frame.dispose();
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
			if(totalPages+1 == results.size())
				results.remove(currentPage);
			updateUiFields(panels, results.get(currentPage -1), false);
		});
		
		utilsPanel.setBorder(new EmptyBorder(5, 5, 0, 5));
		utilsPanel.add(prevButton, gbc);
		gbc.gridx++;
		utilsPanel.add(pagesIndex, gbc);
		gbc.gridx++;
		utilsPanel.add(nextButton, gbc);
		gbc.gridx++;
		utilsPanel.add(deleteButton, gbc);
		gbc.gridx++;
		utilsPanel.add(addButton, gbc);
		gbc.gridx = 0;
		
		cancelButton.addActionListener(e-> {
			results.clear();
			results.add(getEntries(panels));
			commitData(results);
			frame.dispose();
		});
		buttonsPanel.add(approveButton);
		approveButton.addActionListener(e->{
			var saved = saveAsMap(panels);
			if(saved==null) 
				return;
			updateDataList(results, saved, currentPage -1, false);
			commitData(results);
			frame.dispose();
		});
		buttonsPanel.add(cancelButton);
		gbc.gridy++;
		frame.add(utilsPanel, gbc);
		gbc.gridy++;
		frame.add(buttonsPanel, gbc);
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
	
	
}
