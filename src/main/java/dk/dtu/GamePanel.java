package dk.dtu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

	GamePanel(GameState gs){
	
		this.setPreferredSize(new Dimension(gs.SCREEN_WIDTH,gs.SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		
		startGame();
	}
	private void startGame() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
