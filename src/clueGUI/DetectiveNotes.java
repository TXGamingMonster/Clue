package clueGUI;

import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import clueGame.Player;

public class DetectiveNotes extends JDialog {

	public DetectiveNotes(ArrayList<Player> players, ArrayList<String> weapons, ArrayList<String> roomlist) {
		setTitle("Detective Notes");
		setSize(400,500);
		setLayout(new GridLayout(3,2));
		Box people = Box.createVerticalBox();
		JComboBox bestPerson = new JComboBox();
		for (Player i : players)
		{
			bestPerson.addItem(i.getName());
			JCheckBox cb = new JCheckBox();
			cb.setText(i.getName());
			people.add(cb);
		}
		add(people);
		add(bestPerson);
		Box w = Box.createVerticalBox();
		JComboBox bestWeapon = new JComboBox();
		for (String i : weapons)
		{
			bestWeapon.addItem(i);
			JCheckBox cb = new JCheckBox();
			cb.setText(i);
			w.add(cb);
		}
		add(w);
		add(bestWeapon);
		
		Box r = Box.createVerticalBox();
		JComboBox bestRoom = new JComboBox();
		for (String i : roomlist)
		{
			bestRoom.addItem(i);
			JCheckBox cb = new JCheckBox();
			cb.setText(i);
			r.add(cb);
		}
		add(r);
		add(bestRoom);
	}


}
