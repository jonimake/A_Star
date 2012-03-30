package Tiralabra;

import java.awt.*;
import java.applet.Applet;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class Main extends JFrame
{

    private ImagePanel paneeli;

    public Main()
    {
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setResizable(true);
	setTitle("A-star pathfinding");
	init();
	setSize(paneeli.image.getWidth() + 15, paneeli.image.getHeight() + 80);
	setVisible(true);
	//pack();
	//paneeli.go();
    }

    public void init()
    {
	setPaneeli(new ImagePanel("maze.bmp"));
	//paneeli = new ImagePanel("maze2.bmp");
	//paneeli = new ImagePanel("maze3.bmp");
	//paneeli = new ImagePanel("heightmap2.bmp");
	add(getPaneeli());
	System.out.println(getPaneeli().getColorModel());

    }

    public static void main(String[] args)
    {
	Main main = new Main();
    }

    public ImagePanel getPaneeli()
    {
	return paneeli;
    }

    public void setPaneeli(ImagePanel paneeli)
    {
	this.paneeli = paneeli;
    }
}