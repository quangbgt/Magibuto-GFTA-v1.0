package io.traderx.darknesss.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.SwingConstants;

import io.traderx.darkness.main.Agas;
import io.traderx.darkness.main.Followine;
import io.traderx.darkness.config.ConfigLoader;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class MainPanel extends JPanel {

	private JComboBox cbDealer, cbTable, cbServer, cbMode, cbEntry, cbTrigger, cbBaseBet, cbEngine, cbBOFP, cbBOAP, cbSEFP, cbSEAP, cbOffset, cbSL, cbTP, cbLimit ;
	
	public MainPanel() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dealer");
		lblNewLabel.setBounds(57, 51, 72, 14);
		add(lblNewLabel);
		
		cbDealer = new JComboBox();
		cbDealer.setModel(new DefaultComboBoxModel(new String[] {"W88", "M88", "188Bet"}));
		cbDealer.setBounds(153, 48, 94, 20);
		add(cbDealer);
		
		JLabel lblTable = new JLabel("Table");
		lblTable.setBounds(57, 92, 72, 14);
		add(lblTable);
		
		cbTable = new JComboBox();
		cbTable.setModel(new DefaultComboBoxModel(new String[] {"Gold S1", "Gold S2", "Grand S1", "Grand S2", "Premier S1", "Premier S2"}));
		cbTable.setBounds(153, 89, 94, 20);
		add(cbTable);
		
		JLabel lblBofp = new JLabel("Bofp");
		lblBofp.setBounds(344, 132, 72, 14);
		add(lblBofp);
		
		cbBOFP = new JComboBox();
		cbBOFP.setModel(new DefaultComboBoxModel(new String[] {"1", "11", "01", "011", "0111", "01111"}));
		cbBOFP.setSelectedIndex(3);
		cbBOFP.setBounds(440, 129, 94, 20);
		add(cbBOFP);
		
		cbBOAP = new JComboBox();
		cbBOAP.setModel(new DefaultComboBoxModel(new String[] {"0", "00", "10", "010", "0110", "01110"}));
		cbBOAP.setSelectedIndex(3);
		cbBOAP.setBounds(440, 170, 94, 20);
		add(cbBOAP);
		
		JLabel lblBoap = new JLabel("Boap");
		lblBoap.setBounds(344, 173, 72, 14);
		add(lblBoap);
		
		JLabel lblSefp = new JLabel("Sefp");
		lblSefp.setBounds(344, 214, 72, 14);
		add(lblSefp);
		
		cbSEFP = new JComboBox();
		cbSEFP.setModel(new DefaultComboBoxModel(new String[] {"0", "00", "10", "100", "1000", "10000"}));
		cbSEFP.setSelectedIndex(3);
		cbSEFP.setBounds(440, 211, 94, 20);
		add(cbSEFP);
		
		cbSEAP = new JComboBox();
		cbSEAP.setModel(new DefaultComboBoxModel(new String[] {"1", "01", "10", "101", "1001", "10001"}));
		cbSEAP.setSelectedIndex(3);
		cbSEAP.setBounds(440, 252, 94, 20);
		add(cbSEAP);
		
		JLabel lblSeap = new JLabel("Seap");
		lblSeap.setBounds(344, 255, 72, 14);
		add(lblSeap);
		
		JLabel lblBaseBet = new JLabel("Mode");
		lblBaseBet.setBounds(57, 173, 72, 14);
		add(lblBaseBet);
		
		cbMode = new JComboBox();
		cbMode.setModel(new DefaultComboBoxModel(new String[] {"Real", "Test"}));
		cbMode.setBounds(153, 170, 94, 20);
		add(cbMode);
		
		cbTrigger = new JComboBox();
		cbTrigger.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		cbTrigger.setSelectedIndex(0);
		cbTrigger.setBounds(440, 333, 94, 20);
		add(cbTrigger);
		
		JLabel lblSl = new JLabel("Trigger");
		lblSl.setBounds(344, 336, 72, 14);
		add(lblSl);
		
		JLabel lblTp = new JLabel("Base bet");
		lblTp.setBounds(57, 214, 72, 14);
		add(lblTp);
		
		cbBaseBet = new JComboBox();
		cbBaseBet.setModel(new DefaultComboBoxModel(new String[] {"10", "20", "25", "50", "100", "200", "250", "500", "1000", "2000"}));
		cbBaseBet.setSelectedIndex(1);
		cbBaseBet.setBounds(153, 211, 94, 20);
		add(cbBaseBet);
		
		cbSL = new JComboBox();
		cbSL.setModel(new DefaultComboBoxModel(new String[] {"5", "10", "15", "20", "30", "50", "60", "70", "80", "90", "100", "110", "120", "150", "160", "170", "180", "190", "200", "250", "300"}));
		cbSL.setSelectedIndex(19);
		cbSL.setBounds(153, 252, 94, 20);
		add(cbSL);
		
		JLabel lblTriger = new JLabel("Stop loss");
		lblTriger.setBounds(57, 255, 72, 14);
		add(lblTriger);
		
		JLabel lblMode = new JLabel("Take profit");
		lblMode.setBounds(57, 294, 72, 14);
		add(lblMode);
		
		cbTP = new JComboBox();
		cbTP.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "35", "40", "45", "50"}));
		cbTP.setSelectedIndex(1);
		cbTP.setBounds(153, 291, 94, 20);
		add(cbTP);
		
		cbEntry = new JComboBox();
		cbEntry.setModel(new DefaultComboBoxModel(new String[] {"BS", "EO", "DUAL"}));
		cbEntry.setSelectedIndex(2);
		cbEntry.setBounds(440, 89, 94, 20);
		add(cbEntry);
		
		JLabel lblEntry = new JLabel("Entry");
		lblEntry.setBounds(344, 92, 72, 14);
		add(lblEntry);
		
		JButton btnNewButton = new JButton("FIRE NOW");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String dealer = cbDealer.getSelectedItem().toString();
				String table = cbTable.getSelectedItem().toString();
				String server = cbServer.getSelectedItem().toString();
				String mode = cbMode.getSelectedItem().toString();
				String entryOption  = cbEntry.getSelectedItem().toString();
				String engine  = cbEngine.getSelectedItem().toString();
//				String inputRes = tfInputRes.getText();
				String bofp = cbBOFP.getSelectedItem().toString();
				String boap = cbBOAP.getSelectedItem().toString();
				String sefp = cbSEFP.getSelectedItem().toString();
				String seap = cbSEAP.getSelectedItem().toString();
				int offset = Integer.parseInt(cbOffset.getSelectedItem().toString());
				int triggerLevel = Integer.parseInt(cbTrigger.getSelectedItem().toString());
				int baseBet = Integer.parseInt(cbBaseBet.getSelectedItem().toString());
				int sl = Integer.parseInt(cbSL.getSelectedItem().toString());
				int tp = Integer.parseInt(cbTP.getSelectedItem().toString());
				int limit = Integer.parseInt(cbLimit.getSelectedItem().toString());
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				ConfigLoader.CONFIG_PATH = "config/" + (int)screenSize.getWidth() + "x" + (int)screenSize.getHeight() + ".conf";
				
				if(engine.equalsIgnoreCase("Followine")) {
					Followine bot = new Followine(dealer, table, server, mode, entryOption, "", bofp, boap, sefp, seap, offset, triggerLevel, baseBet, sl, tp);
					System.out.println(bot.toString());
					bot.run();
				}
				
				if(engine.equalsIgnoreCase("Agas")) {
					Agas bot = new Agas(dealer, table, server, mode, entryOption, "", baseBet, sl, tp, limit);
					System.out.println(bot.toString());
					bot.run();
				}
			}
		});
		btnNewButton.setBackground(Color.ORANGE);
		btnNewButton.setBounds(239, 395, 115, 23);
		add(btnNewButton);
		
		JLabel lblOffset = new JLabel("Offset");
		lblOffset.setBounds(344, 294, 72, 14);
		add(lblOffset);
		
		cbOffset = new JComboBox();
		cbOffset.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		cbOffset.setSelectedIndex(0);
		cbOffset.setBounds(440, 291, 94, 20);
		add(cbOffset);
		
		JLabel lblServer = new JLabel("Server");
		lblServer.setBounds(57, 132, 72, 14);
		add(lblServer);
		
		cbServer = new JComboBox();
		cbServer.setModel(new DefaultComboBoxModel(new String[] {"TraderX", "ScalperX"}));
		cbServer.setBounds(153, 129, 94, 20);
		add(cbServer);
		
		JLabel lblNewLabel_1 = new JLabel("Copyright \u00A9 2008 TraderX.io");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(199, 440, 190, 14);
		add(lblNewLabel_1);
		
		JLabel lblEngine = new JLabel("Engine");
		lblEngine.setBounds(344, 51, 72, 14);
		add(lblEngine);
		
		cbEngine = new JComboBox();
		cbEngine.setModel(new DefaultComboBoxModel(new String[] {"Agas", "Followine"}));
		cbEngine.setSelectedIndex(1);
		cbEngine.setBounds(440, 48, 94, 20);
		add(cbEngine);
		
		JLabel lblTripleBet = new JLabel("Limit");
		lblTripleBet.setBounds(57, 336, 72, 14);
		add(lblTripleBet);
		
		cbLimit = new JComboBox();
		cbLimit.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "100", "200", "300", "400", "500", "600", "700", "800", "900", "1000"}));
		cbLimit.setSelectedIndex(2);
		cbLimit.setBounds(153, 333, 94, 20);
		add(cbLimit);
		
		cbEngine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(cbEngine.getSelectedItem().toString().equalsIgnoreCase("Followine")) {
					cbBOAP.setEnabled(true);
					cbBOFP.setEnabled(true);
					cbSEAP.setEnabled(true);
					cbSEFP.setEnabled(true);
					cbOffset.setEnabled(true);
					cbTrigger.setEnabled(true);
					cbSL.setSelectedIndex(19);
					cbLimit.setSelectedItem("1000");
					cbEntry.setModel(new DefaultComboBoxModel(new String[] {"BS", "EO", "DUAL"}));
					cbEntry.setSelectedIndex(2);
				} else {
					cbBOAP.setEnabled(false);
					cbBOFP.setEnabled(false);
					cbSEAP.setEnabled(false);
					cbSEFP.setEnabled(false);
					cbOffset.setEnabled(false);
					cbTrigger.setEnabled(false);
					cbSL.setSelectedIndex(3);
					cbLimit.setSelectedIndex(3);
					cbEntry.setModel(new DefaultComboBoxModel(new String[] {"BS", "EO"}));
				}
			}
		});
		
		
		if(cbEngine.getSelectedItem().toString().equalsIgnoreCase("Followine")) {
			cbLimit.setSelectedItem("1000");
		}else {
			cbLimit.setSelectedIndex(3);
		}
		cbEntry.setModel(new DefaultComboBoxModel(new String[] {"BS", "EO", "DUAL"}));
		cbEntry.setSelectedIndex(2);
	}
}
