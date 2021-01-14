package ui;

import javax.swing.JDialog;
import ast.Ast.Query;
import ast.components.CBoolean;
import ast.components.CDecimal;
import ast.components.CInteger;
import ast.components.CMultiOpt;
import ast.components.CPassword;
import ast.components.CSingleOpt;
import ast.components.CSlider;
import ast.components.CString;
import ast.components.CTextArea;
import ast.components.DMulti;
import ast.components.DPages;
import ast.components.DSingle;
import ast.components.Group;
import ast.components.Tab;
import fields.JPanelContainer;
import fields.JPanelWithValue;

public interface Visitor {
	JDialog visitSingle(DSingle dialog);
	JDialog visitMulti(DMulti dialog);
	JDialog visitPages(DPages dialog);
	JPanelContainer visitGroup(Group group);
	JPanelContainer visitTab(Tab tab);
	JPanelWithValue visitString(CString component);
	JPanelWithValue visitTextArea(CTextArea component);
	JPanelWithValue visitPassword(CPassword component);
	JPanelWithValue visitInteger(CInteger component);
	JPanelWithValue visitDecimal(CDecimal component);
	JPanelWithValue visitBoolean(CBoolean component);
	JPanelWithValue visitSlider(CSlider component);
	JPanelWithValue visitSingleOpt(CSingleOpt component);
	JPanelWithValue visitMultiOpt(CMultiOpt component);
	JDialog visitQuery(Query query);
}
