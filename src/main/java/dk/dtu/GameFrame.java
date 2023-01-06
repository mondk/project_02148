package dk.dtu;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

	public GameFrame(GameState gs) {
		this.add(new GamePanel(gs));
		this.setTitle("Snake ulitmate");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
