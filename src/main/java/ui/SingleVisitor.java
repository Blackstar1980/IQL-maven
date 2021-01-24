package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ast.Ast.Containable;
import ast.Ast.Query;
import ast.components.*;
import fields.*;

public class SingleVisitor extends UiVisitor {

	@Override
	public JFrame visitQuery(Query query) {
		JFrame jFrame = new JFrame();
		String desc = query.dialog().getDescription();
		jFrame = visitSingle(query.dialog());
		List<Containable> containers = query.containers();
		List<JPanelContainer> panels = new ArrayList<>();
		for(Containable container:containers)
			panels.add(getPanel(container));
		constructDialog(jFrame, panels, desc);
		jFrame.pack();
		jFrame.setVisible(true);
		System.out.println("in Single");
		return jFrame;
	}
	
	@Override
	protected void constructDialog(JFrame frame, List<JPanelContainer> panels, String desc) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
//		gbc.fill = GridBagConstraints.HORIZONTAL;
		
//		JTextArea JDesc = generateDesc(desc);
//		double frameWidth = frame.getPreferredSize().getWidth();
//		JDesc.setPreferredSize(new Dimension((int)frameWidth, (int)frame.getPreferredSize().getHeight()));
//		JDesc.setWrapStyleWord(true);
//		JDesc.setLineWrap(true);
//		JDesc.setEditable(false);
		
		
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		for(JPanel panel : panels) {
			gbc.gridy++;
			frame.add(panel, gbc);
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
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosed(WindowEvent e) {
				results.clear();
				results.add(getEntries(panels));
				commitData(results);
				frame.dispose();
		    }
		});
		cancelButton.addActionListener(e-> {
			results.clear();
			results.add(getEntries(panels));
			commitData(results);
			frame.dispose();
		});
		approveButton.addActionListener(e->{
			var saved = saveAsMap(panels);
			if(saved!=null) {
				results.add(saved);
				commitData(results);
				frame.dispose();
			}});
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.CENTER;
		frame.add(buttonsPanel, gbc);
		
		JTextArea jDesc = generateDesc(desc);
//		double frameWidth = frame.getPreferredSize().getWidth() - 30;
//		double height = jDesc.getPreferredSize().getHeight();
//		int newheight = (int) ((jDesc.getPreferredSize().getWidth()/frameWidth)*height);
//		jDesc.setPreferredSize(new Dimension((int)frameWidth, newheight));
//		jDesc.setWrapStyleWord(true);
//		jDesc.setLineWrap(true);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		frame.add(jDesc, gbc);
	}
	
}
