/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package multiplayersnail;

/**
 *
 * @author Yadhu Ravinath
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;








import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main extends Core implements KeyListener {
	// main method

        int count = 0;
        //String msg = "3";
	//private ObjectOutputStream output;
	//private ObjectInputStream input;
	//private ServerSocket server;
	//private Socket connection;
        static int whitecount = 0;
        static int yellowcount = 0;


	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){}


		Object[] opts = { "Start", "Rules", "Exit" };
		int dif = JOptionPane.showOptionDialog(null, "Enter your choice : ",
				"Snake", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, opts, opts);

		if (dif == 0) {
			sleep = 159;
			grid = true;
			new Main().run();
		}else if(dif == 1){
                    JOptionPane.showMessageDialog(null, "Yellow uses w, s, a and d for moving up, down, left and right respectively\n"
                            + "White uses arrow keys for moving up, down, left and right\n"
                            + "Both players have to collect grey food to increase points and avoid red obstacles\n"
                            + "In case of collision with each other,the player with more points wins\n"
                            + "The player colliding with red obstacles or itself loses\n"
                            + "Count is maintained for no of wins by each player\n"
                            + "No of wins for each player is displayed after each game\n"
                            + "On exit the winner of the tournament is displayed",
							"Snake", JOptionPane.PLAIN_MESSAGE);
                    main(null);

                }
			else {
                        if(yellowcount>whitecount)
                        JOptionPane.showMessageDialog(null, "Yellow Wins the tournament...",
							"Snake", JOptionPane.PLAIN_MESSAGE);
                        else if (whitecount>yellowcount)
                            JOptionPane.showMessageDialog(null, "White wins the tournament... ",
							"Snake", JOptionPane.PLAIN_MESSAGE);

			System.exit(0);
		}

		if(replay){
                    JOptionPane.showMessageDialog(null, "Score:\nYellow : " + yellowcount + "\nWhite : " + whitecount,
							"Snake", JOptionPane.PLAIN_MESSAGE);

			main(null);
		}
	}

	ArrayList<Rectangle> snake;
        ArrayList<Rectangle> snake1;
	ArrayList<Rectangle> obst;
        Rectangle food;
	boolean up, down, left, right,up1,down1,left1,right1, eaten, dirChanged,dirChanged1;
	static boolean grid;
	int score,score1;

	// init method
	public void init() {

                super.init();
		w.addKeyListener(this);
		removeCursor();

                obst = new ArrayList<Rectangle>();
		snake = new ArrayList<Rectangle>();
                snake1 = new ArrayList<Rectangle>();
		snake1.add(new Rectangle(60, 240, 20, 20));
		snake1.add(new Rectangle(40, 240, 20, 20));
		//snake.add(new Rectangle(-20, -20, 20, 20));
                snake.add(new Rectangle(520,240,20,20));
                snake.add(new Rectangle(540,240,20,20));
                //snake1.add(new Rectangle(-40,-40,20,20));
		eaten = true;
		score = 0;
                score1 = 0;
	}

	// updates the game
	public void update() {
		// food
		food();

		dirChanged = false;
                dirChanged1 = false;

		// Collision between snake and itself
		for (int i = 0; i < snake.size(); i++) {
			for (int j = i + 1; j < snake.size(); j++) {
				if (snake.get(i).getLocation().x == snake.get(j).getLocation().x
						&& snake.get(i).getLocation().y == snake.get(j)
								.getLocation().y) {
					stop();
					w.setVisible(false);
					JOptionPane.showMessageDialog(null, "Yellow Wins ",
							"Snake", JOptionPane.PLAIN_MESSAGE);
					replay = true;
                                        yellowcount++;
				}
			}
		}
                for (int i = 0; i < snake1.size(); i++) {
			for (int j = i + 1; j < snake1.size(); j++) {
				if (snake1.get(i).getLocation().x == snake1.get(j).getLocation().x
						&& snake1.get(i).getLocation().y == snake1.get(j)
								.getLocation().y) {
					stop();
					w.setVisible(false);
					JOptionPane.showMessageDialog(null, "White Wins ",
							"Snake", JOptionPane.PLAIN_MESSAGE);
					replay = true;
                                        whitecount++;
				}
			}
		}

                for(int i = 0 ; i < snake.size(); i++)
                {
                    for (int j = 0; j < snake1.size(); j++)
                    {
                        if(snake.get(i).getLocation().x == snake1.get(j).getLocation().x
                                && snake.get(i).getLocation().y == snake1.get(j).getLocation().y)
                        {
                            stop();
                            w.setVisible(false);
                            if(score > score1)
                            {
                                 replay = true;

                                JOptionPane.showMessageDialog(null, "White wins.. ",
							"Snake", JOptionPane.PLAIN_MESSAGE);
                                whitecount++;
                            }
                            else if(score1>score)
                            {
                                 replay = true;
                                 yellowcount++;
                                JOptionPane.showMessageDialog(null, "Yellow wins ",
							"Snake", JOptionPane.PLAIN_MESSAGE);
                            } else
                            {
                                replay = true;
                                JOptionPane.showMessageDialog(null, "Match Draw...",
							"Snake", JOptionPane.PLAIN_MESSAGE);
                        }}
                    }

                }
                for(int i=0;i<obst.size();i++)
                    {
                        if(snake.get(0).x == obst.get(i).x && snake.get(0).y == obst.get(i).y)
                        {
                            stop();
                            w.setVisible(false);
                            JOptionPane.showMessageDialog(null, "Yellow Wins ",
							"Snake", JOptionPane.PLAIN_MESSAGE);
                            replay = true;
                            yellowcount++;
                        }
                        if(snake1.get(0).x == obst.get(i).x && snake1.get(0).y == obst.get(i).y)
                        {
                            stop();
                            w.setVisible(false);
                            JOptionPane.showMessageDialog(null, "White Wins ",
							"Snake", JOptionPane.PLAIN_MESSAGE);
                             replay = true;
                             whitecount++;
                        }
                    }

		// movement for snake
		for (int i = snake.size() - 1; i >= 0; i--) {
			if (up) {
				try {
					snake.get(i).setLocation(snake.get(i - 1).x,
							snake.get(i - 1).y);
				} catch (Exception e) {
					if (snake.get(i).getLocation().y > 0) {
						snake.get(i).setLocation(snake.get(i).x,
								snake.get(i).y - 20);
					} else {
						snake.get(i).setLocation(snake.get(i).x,
								w.getHeight() - 20);
					}
				}
			} else if (down) {
				try {
					snake.get(i).setLocation(snake.get(i - 1).x,
							snake.get(i - 1).y);
				} catch (Exception e) {
					if (snake.get(i).getLocation().y < w.getHeight() - 20) {
						snake.get(i).setLocation(snake.get(i).x,
								snake.get(i).y + 20);
					} else {
						snake.get(i).setLocation(snake.get(i).x, 0);
					}
				}
			} else if (left) {
				try {
					snake.get(i).setLocation(snake.get(i - 1).x,
							snake.get(i - 1).y);
				} catch (Exception e) {
					if (snake.get(i).getLocation().x > 0) {
						snake.get(i).setLocation(snake.get(i).x - 20,
								snake.get(i).y);
					} else {
						snake.get(i).setLocation(w.getWidth() - 20,
								snake.get(i).y);
					}
				}
			} else if (right) {
				try {
					snake.get(i).setLocation(snake.get(i - 1).x,
							snake.get(i - 1).y);
				} catch (Exception e) {
					if (snake.get(i).getLocation().x < w.getWidth() - 20) {
						snake.get(i).setLocation(snake.get(i).x + 20,
								snake.get(i).y);
					} else {
						snake.get(i).setLocation(0, snake.get(i).y);
					}
				}
			}
		}
                for (int i = snake1.size() - 1; i >= 0; i--) {
			if (up1) {
				try {
					snake1.get(i).setLocation(snake1.get(i - 1).x,
							snake1.get(i - 1).y);
				} catch (Exception e) {
					if (snake1.get(i).getLocation().y > 0) {
						snake1.get(i).setLocation(snake1.get(i).x,
								snake1.get(i).y - 20);
					} else {
						snake1.get(i).setLocation(snake1.get(i).x,
								w.getHeight() - 20);
					}
				}
			} else if (down1) {
				try {
					snake1.get(i).setLocation(snake1.get(i - 1).x,
							snake1.get(i - 1).y);
				} catch (Exception e) {
					if (snake1.get(i).getLocation().y < w.getHeight() - 20) {
						snake1.get(i).setLocation(snake1.get(i).x,
								snake1.get(i).y + 20);
					} else {
						snake1.get(i).setLocation(snake1.get(i).x, 0);
					}
				}
			} else if (left1) {
				try {
					snake1.get(i).setLocation(snake1.get(i - 1).x,
							snake1.get(i - 1).y);
				} catch (Exception e) {
					if (snake1.get(i).getLocation().x > 0) {
						snake1.get(i).setLocation(snake1.get(i).x - 20,
								snake1.get(i).y);
					} else {
						snake1.get(i).setLocation(w.getWidth() - 20,
								snake1.get(i).y);
					}
				}
			} else if (right1) {
				try {
					snake1.get(i).setLocation(snake1.get(i - 1).x,
							snake1.get(i - 1).y);
				} catch (Exception e) {
					if (snake1.get(i).getLocation().x < w.getWidth() - 20) {
						snake1.get(i).setLocation(snake1.get(i).x + 20,
								snake1.get(i).y);
					} else {
						snake1.get(i).setLocation(0, snake1.get(i).y);
					}
				}
			}
		}
	}





	// food method
	public void food() {
		if (eaten && up || eaten && down || eaten && left || eaten && right || eaten && up1 || eaten && down1 || eaten && left1 || eaten && right1) {
			int x = new Random().nextInt(30) * 20;
			int y = new Random().nextInt(25) * 20;
			boolean spawn = true;

			for (int i = 0; i < snake.size(); i++) {
				if (snake.get(i).x == x && snake.get(i).y == y) {
					spawn = false;
				}
			}
                        for (int i = 0; i < snake1.size(); i++) {
				if (snake1.get(i).x == x && snake1.get(i).y == y) {
					spawn = false;
				}
			}

                        for (int i = 0; i < obst.size(); i++) {
				if (obst.get(i).x == x && obst.get(i).y == y) {
					spawn = false;
				}
			}

			if (spawn) {
				food = new Rectangle(x, y, 20, 20);
				eaten = false;

			}
                        int m = new Random().nextInt(30) * 20;
			int n = new Random().nextInt(25) * 20;
			spawn = true;

			for (int i = 0; i < snake.size(); i++) {
				if (snake.get(i).x == m && snake.get(i).y == n) {
					spawn = false;
				}
			}
                        for (int i = 0; i < snake1.size(); i++) {
				if (snake1.get(i).x == m && snake1.get(i).y == n) {
					spawn = false;
				}
			}
                        if(eaten == false)
                        {
                            if(food.x == m && food.y == n){
                                spawn = false;
                            }
                        }
                        for(int i=0;i<obst.size();i++)
                        {
                            if (obst.get(i).x == m && obst.get(i).y == n) {
					spawn = false;
				}
                        }
			if (spawn) {
				obst.add(new Rectangle(m, n, 20, 20));

			}
		} else if (food != null && !eaten) {
			if (snake.get(0).x == food.x && snake.get(0).y == food.y) {
				snake.add(new Rectangle(-20, -20, 20, 20));
				score++;
				eaten = true;
			}
                        if (snake1.get(0).x == food.x && snake1.get(0).y == food.y) {
				snake1.add(new Rectangle(-40, -40, 20, 20));
				score1++;
				eaten = true;
			}
		} 
                    
                
	}

	// draw method
	public void draw(Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, w.getWidth(), w.getHeight());
		g.setColor(Color.GRAY);
		if(grid){
			for (int x = 0; x < w.getWidth() / 20; x++) {
				for (int y = 0; y < w.getHeight() / 20; y++) {
					g.drawRect(x * 20, y * 20, 20, 20);
				}
			}
		}
		if (!eaten) {
			g.fill(food);
		}

		g.setColor(Color.WHITE);
		for (int i = snake.size() - 1; i >= 0; i--) {
			g.fill(snake.get(i));
		}

                g.setColor(Color.RED);
		for (int i = obst.size() - 1; i >= 0; i--) {
			g.fill(obst.get(i));
		}


                g.setColor(Color.YELLOW);
		for (int i = snake1.size() - 1; i >= 0; i--) {
			g.fill(snake1.get(i));
		}
                g.setColor(Color.GREEN);
		g.setFont(new Font("Dialog", Font.PLAIN, 16));
		g.drawString("Points:  White :" + score, 2, 17);
		g.drawString("Yellow :" + score1, 242, 17);
                g.drawString("Escape to quit", 482, 17);
		if(!up && !down && !left && !right && !up1 && !down1 && !left1 && !right1){
			g.drawString("Up, down, left, right, w, a, s or d to start game", 132, 57);
                        g.drawString("Yellow uses w, s, a and d for moving up, down, left and right respectively\n", 112, 97);
                        g.drawString("White uses arrow keys for moving up, down, left and right\n",112,117);
                        g.drawString("Both players have to collect grey food to increase points and avoid red obstacles\n",112,137);
                        g.drawString("In case of collision with each other,the player with more points wins\n",112,157);
                        g.drawString("The player colliding with red obstacles or itself loses\n",112,177);
                        g.drawString("Count is maintained for no of wins by each player\n",112,197);
                        g.drawString("No of wins for each player is displayed after each game\n",112,217);
                        g.drawString("On exit the winner of the tournament is displayed",112,237);
                     }

	}

	// Key listeners
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			stop();
		} else if (e.getKeyCode() == KeyEvent.VK_UP && !down && !dirChanged) {
			dirChanged = true;
			up = true;
			down = false;
			left = false;
			right = false;
                        //sendMessage("1");
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && !up && !dirChanged) {
			dirChanged = true;
			up = false;
			down = true;
			left = false;
			right = false;
                        //sendMessage("2");
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && !right && !dirChanged) {
			dirChanged = true;
			up = false;
			down = false;
			left = true;
			right = false;
                        //sendMessage("3");
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !left && !dirChanged) {
			dirChanged = true;
			up = false;
			down = false;
			left = false;
			right = true;
                        //sendMessage("4");
		}
                if (e.getKeyCode() == KeyEvent.VK_W && !down1 && !dirChanged1) {
			dirChanged1 = true;
			up1 = true;
			down1 = false;
			left1 = false;
			right1 = false;
                }//sendMessage("4");
		else if (e.getKeyCode() == KeyEvent.VK_S && !up1 && !dirChanged1) {
			dirChanged1 = true;
			up1 = false;
			down1 = true;
			left1 = false;
			right1 = false;
                } //sendMessage("4");
		else if (e.getKeyCode() == KeyEvent.VK_A && !right1 && !dirChanged1) {
			dirChanged1 = true;
			up1 = false;
			down1 = false;
			left1 = true;
			right1 = false;
                } //sendMessage("4");
		else if (e.getKeyCode() == KeyEvent.VK_D && !left1 && !dirChanged1) {
			dirChanged1 = true;
			up1 = false;
			down1 = false;
			left1 = false;
			right1 = true;
                }      //sendMessage("4");

	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
