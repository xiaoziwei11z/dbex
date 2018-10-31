package com.dn.dbex.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.dn.dbex.service.EmployeeService;


import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectTable extends JDialog {
	private EmployeeService employeeService = new EmployeeService();
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Create the dialog.
	 */
	public SelectTable() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
//		初始化表格，显示所有数据表
		Vector<String> name = new Vector<String>();
		name.add("请选择要子查询插入的表");
		Vector<String> tableNames = new Vector<String>();
		try {
			tableNames = employeeService.listTables();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<Vector<String>> datas = new Vector<Vector<String>>();
		for(int i=0;i<tableNames.size();i++) {
			Vector<String> row = new Vector<String>();
			row.add(tableNames.get(i));
			datas.add(row);
		}
		table = new JTable(datas,name);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 10, 374, 208);
		contentPanel.add(scrollPane);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String tableName = (String)table.getValueAt(table.getSelectedRow(), 0);
						new SelectDatas(tableName).setVisible(true);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
