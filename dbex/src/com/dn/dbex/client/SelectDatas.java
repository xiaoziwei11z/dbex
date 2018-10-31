package com.dn.dbex.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.dn.dbex.service.EmployeeService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectDatas extends JDialog {
	private EmployeeService employeeService = new EmployeeService();
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Create the dialog.
	 */
	public SelectDatas(String tableName) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
//		初始化表格，显示所有数据
		Vector<String> name = new Vector<String>();
		try {
			name = employeeService.getColumnName(tableName);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Vector<Vector<String>> datas = new Vector<Vector<String>>();
		try {
			datas = employeeService.listDatas(tableName);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					public void actionPerformed(ActionEvent arg0) {
//						得到选定数据
						int[] rows = table.getSelectedRows();
						int columnCount = table.getColumnCount();
						Vector<Vector<String>> datas = new Vector<Vector<String>>();
						for(int i=0;i<rows.length;i++) {
							Vector<String> row = new Vector<String>();
							for(int j=0;j<columnCount;j++) {
								row.add((String)table.getValueAt(rows[i], j));
							}
							datas.add(row);
						}
						GUI.selectOthers(datas);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
