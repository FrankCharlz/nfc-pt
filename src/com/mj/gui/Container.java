package com.mj.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.mj.gui.BRT.WindowEventHandler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Container extends JFrame {
	private static final long serialVersionUID = 18767L;
	private JTextField text_adress;
	private JTextField text_port;
	private JLabel lblUid;
	private ActionListener myBtnActionListener;
	private WindowListener myWindowListener;
	private JLabel lblInfo;
	private JButton btnConnect;

	
	public Container() {
		
		setTitle("BRT");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(myWindowListener);
		

		ContainerPanel panel = new ContainerPanel();
		this.add(panel);
		
		setVisible(true);
		/*
		SwingUtilities.updateComponentTreeUI(this);
		try { 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		*/

		
	}

	class ContainerPanel extends JPanel{
		private static final long serialVersionUID = 892080576008560480L;

		public ContainerPanel() {
			setLayout(null);


			JLabel lblAdress = new JLabel("Adress:");
			lblAdress.setBounds(70, 62, 46, 14);
			add(lblAdress);

			text_adress = new JTextField();
			text_adress.setBounds(126, 59, 145, 20);
			add(text_adress);
			text_adress.setColumns(10);
			text_adress.setText(Adress.IP);

			JLabel lblPort = new JLabel("Port: ");
			lblPort.setBounds(70, 100, 46, 14);
			add(lblPort);

			text_port = new JTextField();
			text_port.setBounds(126, 97, 145, 20);
			add(text_port);
			text_port.setColumns(10);
			text_port.setText(""+Adress.PORT);

			lblInfo = new JLabel();
			lblInfo.setBounds(70, 200, 358, 14);
			lblInfo.setBackground(Color.BLUE);
			add(lblInfo);

			btnConnect = new JButton("SAVE ADRESS");
			btnConnect.setBounds(126, 141, 89, 23);
			btnConnect.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Adress.IP = text_adress.getText();
						Adress.PORT = Integer.parseInt(text_port.getText());
						lblInfo.setText("Server at: "+Adress.getAdress());

					} catch (NumberFormatException err) {
						err.printStackTrace();
					}

				}
			});

			add(btnConnect);
			btnConnect.addActionListener(myBtnActionListener);

			lblUid = new JLabel();
			lblUid.setBounds(70, 227, 358, 14);
			add(lblUid);


		}

		public void dispInfo(String info) {
			lblUid.setText(info);
		}

	}


	public JTextField getText_adress() {
		return text_adress;
	}

	public JTextField getText_port() {
		return text_port;
	}

	public JLabel getLblUid() {
		return lblUid;
	}

	public JLabel getLblInfo() {
		return lblInfo;
	}

	public JButton getBtnConnect() {
		return btnConnect;
	}
	
	

}
