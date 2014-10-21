package clueGUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class ControlGUI extends JFrame{
	
	public ControlGUI()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("GUI Example");
		setSize(750, 150);
		setLocation(500,250);
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
	 	panel.setLayout(new FlowLayout());
	 	JLabel nameLabel = new JLabel("Who's Turn?");
	 	JTextField name = new JTextField(10);
		panel.add(nameLabel);
		panel.add(name);
		panel.add(new JButton("Next Player"));
		panel.add(new JButton("Make an Accusation"));
		add(panel, BorderLayout.NORTH);
		
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		JPanel subpan = new JPanel();
		subpan.setLayout(new FlowLayout());
		subpan.add(new JLabel("Roll"));
		subpan.add(new JTextField(5));
		subpan.setBorder(new TitledBorder (new EtchedBorder(), "Dice"));
		panel.add(subpan);
		
		subpan = new JPanel();
		subpan.setLayout(new FlowLayout());
		subpan.add(new JLabel("Guess"));
		subpan.add(new JTextField(30));
		subpan.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		panel.add(subpan);
		
		subpan = new JPanel();
		subpan.setLayout(new FlowLayout());
		subpan.add(new JLabel("Response"));
		subpan.add(new JTextField(10));
		subpan.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		panel.add(subpan);
		add(panel, BorderLayout.CENTER);
	}
	 
	private JPanel createButtonPanel() {
		JButton agree = new JButton("I agree");
		JButton disagree = new JButton("I disagree");
		JPanel panel = new JPanel();
		panel.add(agree);
		panel.add(disagree);
		return panel;
	}

	public static void main(String[] args) {
		ControlGUI gui = new ControlGUI();	
		gui.setVisible(true);
	}
}
