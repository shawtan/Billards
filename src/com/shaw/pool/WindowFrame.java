package com.shaw.pool;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/*
 * This is the main window...
 * 
 * Shaw Tan
 * 31/05/2013
 */

public class WindowFrame extends JFrame {

	WindowFrame(){

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Billards");
//		this.setResizable(false);
		
		final TablePanel tp = new TablePanel();
		this.add(tp,BorderLayout.CENTER);
		
		JButton btnStart;
		btnStart = new JButton("New Game");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tp.newGame();				
			}
			
		});
		
		this.add(btnStart,BorderLayout.NORTH);

		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new WindowFrame();
		
	}

}
