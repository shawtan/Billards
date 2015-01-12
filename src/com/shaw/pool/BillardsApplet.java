package com.shaw.pool;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;

public class BillardsApplet extends JApplet {

	public void init() {
		final TablePanel tp = new TablePanel();
		getContentPane().add(tp,BorderLayout.CENTER);
		
		JButton btnStart;
		btnStart = new JButton("New Game");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tp.newGame();				
			}
			
		});
		
		getContentPane().add(btnStart,BorderLayout.NORTH);

	    Container cp = getContentPane();
	    Dimension d = cp.getLayout().preferredLayoutSize(cp);
	    setSize((int)d.getWidth(),(int)d.getHeight());
	}
	
}
