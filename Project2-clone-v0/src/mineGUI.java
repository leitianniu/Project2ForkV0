// my version
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class mineGUI extends JFrame implements ActionListener {

	private button mineButtons[][] = new button[10][10];
	private GridLayout mineBoard, infoBoard;
	private Container boardContainer;
	private JMenuBar topMenu;
	private JMenu gameMenu, hMenu;
	private JMenuItem gReset, gTopTen, gExit, hHelp, hAbout;
	private mine mines = new mine();
	private int[] mineLocs = new int[10];

	public mineGUI() {

		super("Minesweeper");
		setSize(480, 860);

		topMenu = new JMenuBar();
		setJMenuBar(topMenu);

		gameMenu = new JMenu("Game");
		topMenu.add(gameMenu);

		gReset = new JMenuItem("Reset");
		gameMenu.add(gReset);
		gReset.setMnemonic(KeyEvent.VK_R);
		gReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.ALT_MASK));
		gReset.addActionListener(this);
		gameMenu.addSeparator();

		gTopTen = new JMenuItem("Top Ten");
		gameMenu.add(gTopTen);
		gTopTen.setMnemonic(KeyEvent.VK_T);
		gTopTen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				ActionEvent.ALT_MASK));
		gTopTen.addActionListener(this);
		gameMenu.addSeparator();

		gExit = new JMenuItem("Exit");
		gExit.setMnemonic(KeyEvent.VK_X);
		gExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.ALT_MASK));
		gExit.addActionListener(this);
		gameMenu.add(gExit);

		hMenu = new JMenu("Help");
		topMenu.add(hMenu);

		hHelp = new JMenuItem("Help");
		hHelp.setMnemonic(KeyEvent.VK_H);
		hHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				ActionEvent.ALT_MASK));
		hHelp.addActionListener(this);
		hMenu.add(hHelp);
		hMenu.addSeparator();

		hAbout = new JMenuItem("About");
		hAbout.setMnemonic(KeyEvent.VK_A);
		hAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.ALT_MASK));
		hAbout.addActionListener(this);
		hMenu.add(hAbout);

		JPanel mboard = new JPanel(new GridLayout(10, 10));

		JPanel top = new JPanel();

		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		// boardContainer=getContentPane();

		// boardContainer.add(top);
		JLabel time = new JLabel("Time");

		top.add(time);

		// boardContainer.add(mboard);

		mines.initMineLocs(10);
		mineLocs = mines.getMines();

		// initialize buttons
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				mineButtons[i][j] = new button(" ");
				mboard.add(mineButtons[i][j]);
				mineButtons[i][j].setXcoord(i);
				mineButtons[i][j].setYcoord(j);
				int position = (i * 10) + j;
				mineButtons[i][j].setPos(position);
				mineButtons[i][j].addMouseListener(new buttonListener());
				mineButtons[i][j].addActionListener(new buttonListener());
			}

		}

		for (int a = 0; a < 10; a++) {
			for (int b = 0; b < 10; b++) {
				for (int c = 0; c < 10; c++) {
					if (mineLocs[a] == mineButtons[b][c].getPos()) {
						mineButtons[b][c].setBomb(1);
					}
				}
			}
		}

		container.add(top);

		container.add(mboard);

		add(container);

		// get adjacent mine count
		countAdjacentMines(); // function to get adjacent mines

		setVisible(true);

	}

	private void doClear(button[][] mineButtons, int x, int y) {
		System.out.println("x:" + x + " y:" + y);
		if (x < 0 || x >= 10 || y < 0 || y >= 10) {
			return;
		}
		button currButton = mineButtons[x][y];
		if (!currButton.isEnabled()) {
			return;
		}

		currButton.setBackground(Color.lightGray);
		currButton.setEnabled(false);

		if (mineButtons[x][y].isBomb) {
			currButton.setText("X");
			System.out.println("clicked on button with bomb, exiting...");
			// end game
		} else {
			if (mineButtons[x][y].getAdjacentBombs() != 0)
				mineButtons[x][y].setText(mineButtons[x][y]
						.getAdjacentBombsString());
			else
				mineButtons[x][y].setText(" ");

			if (currButton.getAdjacentBombs() == 0) {
				doClear(mineButtons, x - 1, y - 1); // top left
				doClear(mineButtons, x, y - 1); // top middle
				doClear(mineButtons, x + 1, y - 1); // top right
				doClear(mineButtons, x - 1, y); // mid left
				doClear(mineButtons, x + 1, y); // mid right
				doClear(mineButtons, x - 1, y + 1); // bot left
				doClear(mineButtons, x, y + 1); // bot middle
				doClear(mineButtons, x + 1, y + 1); // bot right
			}
		}
	}

	private void countAdjacentMines() {
		// TO DO: STUDENT CODE HERE
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (!(mineButtons[i][j].getIsBomb())) {
					int count = 0;
					for (int p = i - 1; p <= i + 1; p++) {
						for (int q = j - 1; q <= j + 1; q++) {
							if (0 <= p && p < mineButtons.length && 0 <= q
									&& q < mineButtons.length) {
								if (mineButtons[p][q].getIsBomb())
									count++;
							} // end if
						} // end for
					} // end for

					mineButtons[i][j].setAdjacentBombs(count);
				} // end if

			} // end for loop rows
		} // end for loop columns
	} // end setAdjacentBombs

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == gReset) {
			System.out.println("Reset Pressed");
			// main.gui = new mineGUI();
		}

		else if (e.getSource() == gTopTen) {
			System.out.println("Top Ten");
		} else if (e.getSource() == gExit) {
			System.out.println("Exit");
			super.setVisible(false);
			super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else if (e.getSource() == hHelp) {
			JPopupMenu pop = new JPopupMenu();
			String text = "Help is here!";
			pop.add(text);
			pop.isVisible();

		} else if (e.getSource() == hAbout) {
			System.out.println("About");
		} else {
			System.out.println(e);
		}

	}

	class buttonListener implements ActionListener, MouseListener {

		public void actionPerformed(ActionEvent e) {

			button currButton = (button) e.getSource();
			// System.out.println(currButton.getPos());

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {

			if (SwingUtilities.isRightMouseButton(e) || e.isControlDown()) {

				button currButton = (button) e.getSource();
				// if the button hasn't been grayed out, do these actions
				if (currButton.getBackground() != Color.lightGray) {

					if (currButton.getState() == 0) {
						currButton.setText("M");
						currButton.setState(1);
					} else if (currButton.getState() == 1) {
						currButton.setText("?");
						currButton.setState(2);
					} else if (currButton.getState() == 2) {
						currButton.setText(" ");
						currButton.setState(0);
					} else {
						System.out.println("Problem here in RightMouseClick");
					}

				}
				// else, do nothing
				else {
					System.out
							.println("user clicked a grayed out button, does nothing");
				}

			}

			else if (SwingUtilities.isLeftMouseButton(e)) {

				button currButton = (button) e.getSource();
				// if the button hasn't been grayed out, do these actions
				if (currButton.getBackground() != Color.lightGray) {
					if (currButton.getState() == 1
							|| currButton.getState() == 2) {
						// already "M", do nothing
						System.out
								.println("pressed on button that's already marked as bomb, no action done.");
					} else if (currButton.getState() == 0) {
						doClear(mineButtons, currButton.getXcoord(),
								currButton.getYcoord());
					} else {
						System.out.println("Problem here");
					}
				}

				else {
					System.out.println("Problem Here in Left Mouse click");
				}
			}
			// else, do nothing
			else {
				System.out
						.println("user clicked a grayed out button, does nothing");
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}

}