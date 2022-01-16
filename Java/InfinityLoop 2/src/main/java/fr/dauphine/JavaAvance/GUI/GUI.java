package fr.dauphine.JavaAvance.GUI;

import java.awt.GridLayout;
import java.awt.Color;
//import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Solve.Checker;
import fr.dauphine.JavaAvance.Solve.Solver;

/**
 * This class handles the GUI
 * 
 *
 */
public class GUI {
	 private JButton[][] jb;
	 private JPanel jp = new JPanel();
	 private JPanel jp2 = new JPanel();
	 public JFrame frame = new JFrame();
	 private static ArrayList<ImageIcon> allIcons;
	 
	 
	 /**
		 * 
		 * @param inputFile
		 *            String from IO
		 * @throws IOException
		 *             if there is a problem with the gui
		 */
	public static void startGUI(final String inputFile) throws NullPointerException {
		// We have to check that the grid is generated before to launch the GUI
		// construction
		Runnable task = new Runnable() {
			public void run() {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						final Grid grid = Checker.buildGrid(inputFile);
						GUI window;
						window = new GUI(grid);
						//window.frame.setSize(grid.getHeight()*130, grid.getWidth()*37);
						window.frame.setLayout(new FlowLayout(FlowLayout.CENTER));
						window.jp.setLayout(new GridLayout(grid.getHeight(),grid.getWidth()));
						window.frame.add(window.jp2);
						JScrollPane scrollPane = new JScrollPane(window.jp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						scrollPane.setPreferredSize(new Dimension(300 + grid.getHeight()*10,300 + grid.getWidth()*10));
						window.frame.add(scrollPane, BorderLayout.CENTER);
						window.frame.pack();
						
						// create and assign a GridLayout for my frame
				        //window.frame.setLayout(new GridLayout(grid.getHeight(),grid.getWidth()));
						
						window.frame.setVisible(true);
						
					}
				});

			}
		};
		new Thread(task).start();
	}

	/**
	 * Create the application.
	 * @param grid
	 */
	public GUI(Grid grid)  {
		allIcons = initAllIcons();
		this.jb = new JButton[grid.getHeight()][grid.getWidth()];
		this.initialize(grid);
		showPartialSolved(grid);
		helpSolve(grid);
		
		
	}
	
	/**
	 * Create a blue Jbutton to give a player a hint by fixing one piece as many times he clicks on the button
	 * A fixed button by our alogrithm fixPiece is colored in green
	 * @see fixPiece 
	 * @param g
	 */
	public void helpSolve(Grid g) {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(50, 50));
		button.setIcon(allIcons.get(15));
		button.setBackground(Color.BLUE);
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					outerloop:
					for (int i = 0; i < g.getHeight(); i++) {
						for (int j = 0; j < g.getWidth(); j++) {
							Piece p = g.getPiece(i, j);
							if (!p.isFixed()) {
								g.fixPiece(p);
								if (p.isFixed()) {
									jb[i][j].setIcon(getImageIcon(p));
									jb[i][j].setBackground(Color.GREEN);
									jb[i][j].setOpaque(true);
									jb[i][j].setBorderPainted(false);
									break outerloop;
								}
							}

						}
					}
				}
				
				
			}
		});
		this.jp2.add(button);
	}
	
	/**
	 * Create a red Jbutton to give a player a hint by applying fixAllPieces2 as many times he makes left click on the button 
	 * if he make a right click all the grid is solved by our alogrihtm
	 * A fixed button by our alogrithm fixPiece is colored in green
	 * @see fixAllPieces2 
	 * @see solveGrid
	 * @param g
	 */
	public void showPartialSolved(Grid g) {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(50, 50));
		button.setIcon(allIcons.get(15));
		button.setBackground(Color.RED);
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					g.fixAllPieces2();
					System.out.println(g);
					for (int i = 0; i < g.getHeight(); i++) {
						for (int j = 0; j < g.getWidth(); j++) {
							jb[i][j].setIcon(getImageIcon(g.getPiece(i, j)));
							if (g.getPiece(i, j).isFixed() && jb[i][j].getBackground().equals(new Color(238,238,238))) {
								jb[i][j].setBackground(Color.GREEN);
								jb[i][j].setOpaque(true);
								jb[i][j].setBorderPainted(false);
							}
						}
					}
				}
				if (SwingUtilities.isRightMouseButton(e)) {
					g.fixAllPieces2();
					Grid s = Solver.solveGrid(g);
					for (int i = 0; i < g.getHeight(); i++) {
						for (int j = 0; j < g.getWidth(); j++) {
							g.setPiece(i, j, s.getPiece(i,j));
							jb[i][j].setIcon(getImageIcon(g.getPiece(i, j)));
							if (g.getPiece(i, j).isFixed()) {
								jb[i][j].setBackground(Color.GREEN);
								jb[i][j].setOpaque(true);
								jb[i][j].setBorderPainted(false);
							}
						}
					}
				}
				
			}
		});
		this.jp2.add(button);	
	}
	
	/**
	 * Initialise a tab 2D of Jbutton of size of the grid and put an ImageIcon on them according to the pieces in the grid
	 * If a button is clicked(left) the piece make a rotation of 90° and a right click fixe the piece and color it in cyan 
	 * but we still can rotate it 
	 * @param grid
	 */
	private void initialize(Grid grid) {
		final JButton[][] jb = this.jb;
		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				final JButton button = new JButton();
				button.setPreferredSize(new Dimension(37, 37));
				//button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                button.setFocusPainted(false);
                button.addMouseListener(new MouseInputAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						int[] index = findIndexButton(button);
						if (SwingUtilities.isLeftMouseButton(e)) {
							if (button.getIcon() == allIcons.get(15));
							else if (button.getIcon() == allIcons.get(3))
								button.setIcon(allIcons.get(0));
							else if (button.getIcon() == allIcons.get(5))
								button.setIcon(allIcons.get(4));
							else if (button.getIcon() == allIcons.get(9))
								button.setIcon(allIcons.get(6));
							else if (button.getIcon() == allIcons.get(10))
								return;
							else if (button.getIcon() == allIcons.get(14))
								button.setIcon(allIcons.get(11));
							else
								button.setIcon(allIcons.get(1 + allIcons.indexOf(button.getIcon())));
							if (button.getBackground().equals(Color.GREEN)) {
								button.setBackground(new Color(238,238,238));
								button.setOpaque(false);
								button.setBorderPainted(true);
								grid.getPiece(index[0], index[1]).setFixed(false);
							}
								
							grid.getPiece(index[0], index[1]).turn();
							
							buildFile("checkFile.txt", jb);
							if (Checker.isSolved("checkFile.txt"))
								System.out.println("VOUS GAGNEZ");
						}
						else if (SwingUtilities.isRightMouseButton(e)) {
							if (button.getBackground().equals(new Color(238,238,238))) {
								button.setBackground(Color.CYAN);
								button.setOpaque(true);
								button.setBorderPainted(false);
								grid.getPiece(index[0], index[1]).setFixed(true);
							}
								
							else {
								button.setBackground(new Color(238,238,238));
								button.setOpaque(false);
								button.setBorderPainted(true);
								grid.getPiece(index[0], index[1]).setFixed(false);
							}
						}
						
					}
                });
                ImageIcon zbi = this.getImageIcon(grid.getPiece(i, j));
                if (zbi != null)
                	button.setIcon(zbi);
				this.jb[i][j] = button;
				this.jp.add(jb[i][j]);
				
			}
		}
		buildFile("checkFile.txt", jb);
	}
	
	
	/**
	 * find the coordinates of the button in the grid
	 * @param button
	 */
	public int[] findIndexButton(JButton button) {
		for (int i = 0; i < this.jb.length; i++) {
			for (int j = 0; j < this.jb[i].length; j++) {
				if (this.jb[i][j] == button) {
					return new int[] {i,j};
				}
			}
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Display the correct image from the piece's type and orientation
	 * 
	 * @param p the piece     
	 * @return an image icon
	 */
	private ImageIcon getImageIcon(Piece p) {
		//To be implemented
		int ori = p.getOrientation().getValuefromOri();
		switch(p.getType()) {
		case VOID :
			return allIcons.get(15);
		case BAR:
			return allIcons.get(4 + ori);
		case FOURCONN:
			return allIcons.get(10 + ori);
		case LTYPE:
			return allIcons.get(11 + ori);
		case ONECONN:
			return allIcons.get(0 + ori);
		case TTYPE:
			return allIcons.get(6 + ori);
		default:
			throw new IllegalArgumentException();
		}
		
	}
	
	
	/**
	 * to represent a void 
	 * @param width 
	 * @param height     
	 * @return a BufferedImage
	 */
	
	public static BufferedImage createTransparentImage (final int width, final int height)
	  {
	    return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	  }
	
	/**
	 * to represent a void by ImageIcon
	 * @param width 
	 * @param height     
	 * @return a ImageIcon
	 */
	
	public static ImageIcon createTransparentIcon (final int width, final int height)
	  {
	    return new ImageIcon(createTransparentImage(width, height));
	  }
	
	/**
	 * we store in a list all possible ImageIcon of the pieces
	 * @param height     
	 * @return a List of possbile ImageIcon 
	 */
	private static ArrayList<ImageIcon> initAllIcons() {
		allIcons = new ArrayList<ImageIcon>();
		String path = "src/main/resources/fr/dauphine/JavaAvance/icons/io/";
		for (int i = 1; i < 16; i++) {
			ImageIcon zbi = new ImageIcon(path + i + ".png");
			//allIcons.add(new ImageIcon(path + i + ".png"));
			allIcons.add(new ImageIcon(zbi.getImage().getScaledInstance(38, 38,  java.awt.Image.SCALE_SMOOTH)));
		}
		allIcons.add(createTransparentIcon (35, 35));
		//allIcons.add(new ImageIcon());
		
		return allIcons;
	}
	
	/**
	 * we creat a file according to the current grid of the user to check instantatly if he solved it 
	 * @see initialize
	 * @param height     
	 */
	public static void buildFile(String outputFile, final JButton[][] jb) {
		Charset charset = Charset.forName("US-ASCII");
		Path p = FileSystems.getDefault().getPath(outputFile);
		try (BufferedWriter output = Files.newBufferedWriter(p, charset)){
			String text = "" + jb[0].length + "\n" + jb.length + "\n";

			for (JButton lb[] : jb) {
				for (JButton b : lb) {
					String tmp = null;
					if (b.getIcon() == allIcons.get(15))tmp = "0 0"  ; 
					else if (b.getIcon() == allIcons.get(0))tmp = "1 0"; 
					else if (b.getIcon() == allIcons.get(1))tmp = "1 1"; 
					else if (b.getIcon() == allIcons.get(2))tmp = "1 2"; 
					else if (b.getIcon() == allIcons.get(3))tmp = "1 3"; 
					else if (b.getIcon() == allIcons.get(4))tmp = "2 0"; 
					else if (b.getIcon() == allIcons.get(5))tmp = "2 1"; 
					else if (b.getIcon() == allIcons.get(6))tmp = "3 0"; 
					else if (b.getIcon() == allIcons.get(7))tmp = "3 1"; 
					else if (b.getIcon() == allIcons.get(8))tmp = "3 2"; 
					else if (b.getIcon() == allIcons.get(9))tmp = "3 3"; 
					else if (b.getIcon() == allIcons.get(10))tmp = "4 0"; 
					else if (b.getIcon() == allIcons.get(11))tmp = "5 0"; 
					else if (b.getIcon() == allIcons.get(12))tmp = "5 1"; 
					else if (b.getIcon() == allIcons.get(13))tmp = "5 2"; 
					else if (b.getIcon() == allIcons.get(14))tmp = "5 3";
					text += tmp + "\n";
				}
			}
			text = text.substring(0, text.length() - 1);
			output.write(text, 0, text.length());
		}
		catch (IOException e) {
			System.out.println("Erreur fichier");
		}
	}
	
}
