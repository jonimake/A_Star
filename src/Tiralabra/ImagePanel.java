package Tiralabra;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class ImagePanel extends JPanel implements MouseListener
{

    BufferedImage image;
    BufferedImage bakup, bakup2;
    final static int MUSTA = -16777216;
    final static int VALKEA = -1;
    final static int LAHTO = -256;
    final static int MAALI = -65536;
    long starttime, endtime;
    int drawX, drawY, width, height;
    int startx = 10;
    int starty = 18;
    int endx = 300;
    int endy = 350;
    float D = 1 + (1 / 100000);
    Boolean debug = false;
    Node end, start;
    Node[][] nodes;
    Node[][] closed;
    int pathlength = 0;
    public boolean startSet = false;
    public boolean endSet = false;

    public ImagePanel(String url)//, int startx, int starty, int endx, int endy) 
    {
	/*
	 * this.startx = startx; this.starty = starty; this.endx = endx;
	 * this.endy = endy;
	 */
	setIgnoreRepaint(true);
	setBackground(Color.BLACK);
	this.image = loadImage(url);
	width = image.getWidth();
	height = image.getHeight();
	addMouseListener(this);
	setVisible(true);
	this.bakup = this.deepCopy(this.image);
	//this.bakup2 = this.image;
    }

    @Override
    public void paint(Graphics g)
    {
	Graphics2D g2d = (Graphics2D) g;
	synchronized (g2d)
	{
	    g2d.drawImage(image, 0, 0, null);
	    g2d.setColor(Color.GRAY);
	    g2d.fillRect(0, height, width, 70);
	    g2d.setColor(Color.WHITE);
	    g2d.drawString("Path length: " + Integer.toString(pathlength), 10, height + 15);
	    g2d.drawString("Run time: " + Long.toString(endtime - starttime) + " ms", 10, height + 35);
	}
    }

    public float manhattan(Node current)
    {
	float dx = Math.abs(endx - current.getX());
	float dy = Math.abs(endy - current.getY());
	return dx + dy;
    }

    public float moveTo(int x, int y)
    {
	if ((x == 0 && (y == -1 || y == 1)) || (y == 0 && (x == -1 || x == 1)))
	{
	    return (float) 1;
	}
	return (float) 1.4142;
    }

    public boolean isValid(int x, int y)
    {
	if (x < 0 || x > image.getWidth() - 1)
	{
	    return false;
	}
	if (y < 0 || y > image.getHeight() - 1)
	{
	    return false;
	}
	if (closed[x][y] != null || image.getRGB(x, y) != VALKEA)
	{
	    return false;
	}
	return true;
    }

    public float euclidean(Node node)
    {
	return (float) Math.sqrt(Math.pow((node.getX() - end.getX()), 2) + Math.pow((node.getY() - end.getY()), 2));
    }

    private void reset()
    {
	startSet = false;
	endSet = false;
	startx = 0;
	starty = 0;
	endx = 0;
	endy = 0;
	//this.image.flush();
	this.image = deepCopy(bakup);
	//this.bakup = this.bakup2;

	//repaint();
    }

    public float crossHeuristic(Node current)
    {
	float dx1 = current.getX() - endx;
	float dy1 = current.getY() - endy;
	float dx2 = start.getX() - endx;
	float dy2 = start.getY() - endy;
	float cross = Math.abs(dx1 * dy2 - dx2 * dy1);

	return (float) (euclidean(current) + cross * 0.001);
    }

    private boolean closedContains(Node neighbour)
    {
	if (	closed[neighbour.getX()][neighbour.getY()] != null && 
		closed[neighbour.getX()][neighbour.getY()].getX() == neighbour.getX() && 
		closed[neighbour.getX()][neighbour.getY()].getY() == neighbour.getY())
	{
	    return true;
	}
	return false;
    }

    public void formatTiles()
    {
	nodes = new Node[width][height];
	for (int x = 0; x < width; x++)
	{
	    for (int y = 0; y < height; y++)
	    {
		nodes[x][y] = new Node(x, y);
		if (x == startx && y == starty)
		{
		    start = nodes[x][y];
		}
		else if (x == endx && y == endy)
		{
		    end = nodes[x][y];
		}
	    }
	}
    }

    public void reconstructPath(Node end)
    {
	Node node = end;
	while (node != start)
	{
	    image.setRGB(node.getX(), node.getY(), MAALI);
	    node = node.getParent();
	    pathlength++;
	}
	endtime = System.currentTimeMillis();
	repaint();
	//reset();
    }

    public void astar()
    {
	pathlength = 0;
	formatTiles();
	MinHeap open = new MinHeap();
	closed = new Node[width][height];
	open.add(start);

	System.out.println("Starting search");

	starttime = System.currentTimeMillis();
	while (open.size() != 0)
	{
	    Node current = open.removemin();	//Poistetaan heapista pienin
	    closed[current.getX()][current.getY()] = current;	//Lisätään se suljettuun kaksiulotteiseen taulukkoon

	    if (current == end) //jos nykyinen piste on päätepiste
	    {
		System.out.println("Voitto!");
		reconstructPath(end);	//konstruoidaan polku
		return;
	    }

	    for (int x = -1; x < 2; x++) 		//Liikutaan koordinaatistossa 
	    {								//jokaiseen ilmansuuntaan
		for (int y = -1; y < 2; y++) 	//
		{
		    if (debug)
		    {
			System.out.println("Naapurikoordinaatit X: " + x + " Y: " + y);
		    }

		    if (x == 0 && y == 0) //jos koordinaattien muutos on (0,0) ei tehdä mitään
		    {
			if (debug)
			{
			    System.out.println("Continue");
			}
			continue;
		    }

		    int xp = x + current.getX(); //naapurin x-koordinaatti
		    int yp = y + current.getY(); //naapurin y-koordinaatti

		    if (isValid(xp, yp))
		    {
			float nextStepCost = current.getG() + moveTo(x, y) * 10;//*MUSTA/bakup.getRGB(xp,yp);
			//System.out.println(nextStepCost);
			Node neighbour = nodes[xp][yp];
			neighbour.setG(nextStepCost);
			neighbour.setH(euclidean(neighbour));
			neighbour.setF(neighbour.getG() + neighbour.getH());

			if (closedContains(neighbour))
			{
			    continue;
			}

			neighbour.setParent(current);
			open.add(neighbour);

			//this.bakup.setRGB(neighbour.x,neighbour.y,-5000);
			closed[neighbour.getX()][neighbour.getY()] = neighbour;
			//reconstructPath(current);


		    }
		}
	    }
	}
	System.out.println("Häviö!");
    }

    public BufferedImage loadImage(String ref)
    {
	BufferedImage bimg = null;
	try
	{
	    bimg = ImageIO.read(new File(ref));
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	return bimg;
    }

    @Override
    public void mouseClicked(MouseEvent ev)
    {
	if (!startSet)
	{
	    reset();
	    startx = ev.getX();
	    starty = ev.getY();
	    if (isValidCoord(startx, starty))
	    {
		startSet = true;
		System.out.println("Start X: " + startx + " Start Y: " + starty);
	    }
	    return;
	}
	if (!endSet)
	{
	    endx = ev.getX();
	    endy = ev.getY();
	    if (isValidCoord(endx, endy))
	    {
		endSet = true;
		System.out.println("End X: " + endx + " End Y: " + endy);
	    }
	}
	if (startSet && endSet)
	{
	    startSet = false;
	    endSet = false;
	    
	    // this.image = this.deepCopy(bakup);
	    astar();
	}
    }

    private BufferedImage deepCopy(BufferedImage bi)
    {
	ColorModel cm = bi.getColorModel();
	boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	WritableRaster raster = bi.copyData(null);
	return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    private boolean isValidCoord(int x, int y)
    {
	if (x < 0 || x > image.getWidth() - 1)
	{
	    return false;
	}
	if (y < 0 || y > image.getHeight() - 1)
	{
	    return false;
	}
	return true;
    }

    @Override
    public void mouseEntered(MouseEvent ev)
    {
	// TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent ev)
    {
	// TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent ev)
    {
	// TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {
	// TODO Auto-generated method stub
    }
}
