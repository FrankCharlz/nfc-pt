package com.mj.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class View extends JPanel {
	private static final long serialVersionUID = -7086325879776683942L;
	private JTextField txtIP;
	private JTextField txtPort;
	private JTextField txtPath;
	private JTextPane txtDisplay;
	private JButton btnSaveAddress;

	public View() {
		setLayout(null);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setFont(new Font("Calibri", lblIp.getFont().getStyle(), lblIp.getFont().getSize()));
		lblIp.setBounds(48, 42, 20, 14);
		add(lblIp);
		
		txtIP = new JTextField();
		txtIP.setFont(new Font("Consolas", Font.PLAIN, 13));
		txtIP.setBounds(88, 39, 198, 20);
		add(txtIP);
		txtIP.setColumns(10);
		
		JLabel lblPort = new JLabel("PORT:");
		lblPort.setFont(new Font("Calibri", lblPort.getFont().getStyle(), lblPort.getFont().getSize()));
		lblPort.setBounds(48, 79, 46, 14);
		add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setFont(new Font("Consolas", Font.PLAIN, 13));
		txtPort.setBounds(88, 75, 198, 20);
		add(txtPort);
		txtPort.setColumns(10);
		
		JTextPane txtIns = new JTextPane();
		txtIns.setFont(new Font("Calibri", txtIns.getFont().getStyle(), 12));
		txtIns.setBackground(new Color(216, 191, 216));
		txtIns.setText("Copy the path below and integrate it with GoToTags App:");
		txtIns.setBounds(48, 153, 238, 47);
		add(txtIns);
		
		txtPath = new JTextField();
		txtPath.setBounds(48, 211, 238, 20);
		add(txtPath);
		txtPath.setColumns(10);
		
		btnSaveAddress = new JButton("SAVE NEW ADRESS");
		btnSaveAddress.setBounds(88, 104, 198, 23);
		add(btnSaveAddress);
		
		txtDisplay = new JTextPane();
		txtDisplay.setFont(new Font("Consolas", txtDisplay.getFont().getStyle(), txtDisplay.getFont().getSize() + 3));
		txtDisplay.setBackground(new Color(230, 230, 250));
		txtDisplay.setBounds(336, 42, 187, 85);
		add(txtDisplay);

	}

	public JTextField getTxtIP() {
		return txtIP;
	}

	public JTextField getTxtPort() {
		return txtPort;
	}

	public JTextField getTxtPath() {
		return txtPath;
	}

	public JTextPane getTxtDisplay() {
		return txtDisplay;
	}

	public JButton getBtnSaveAddress() {
		return btnSaveAddress;
	}
	
	
	
}
