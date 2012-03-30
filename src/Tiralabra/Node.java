package Tiralabra;

import java.util.Comparator;

public class Node
{

    private float F;
    private float G;
    private float H;
    private int x;
    private int y;
    private int RGB;
    private Node parent;

    Node(int x, int y)
    {
	this.x = x;
	this.y = y;
	this.F = 0;
	this.G = 0;
	this.H = 0;
	this.parent = null;
    }

    public float getF()
    {
	return F;
    }

    public void setF(float F)
    {
	this.F = F;
    }

    public float getG()
    {
	return G;
    }

    public void setG(float G)
    {
	this.G = G;
    }

    public float getH()
    {
	return H;
    }

    public void setH(float H)
    {
	this.H = H;
    }

    public int getX()
    {
	return x;
    }

    public void setX(int x)
    {
	this.x = x;
    }

    public int getY()
    {
	return y;
    }

    public void setY(int y)
    {
	this.y = y;
    }

    public int getRGB()
    {
	return RGB;
    }

    public void setRGB(int RGB)
    {
	this.RGB = RGB;
    }

    public Node getParent()
    {
	return parent;
    }

    public void setParent(Node parent)
    {
	this.parent = parent;
    }
}
