package Tiralabra;

import java.util.Comparator;

public class MinHeap
{

    private Node[] heap;
    private int size;
    private Node node;

    public MinHeap()
    {
	heap = new Node[10];
	size = 0;

    }

    public float getG(Node node)
    {
	for (int i = 0; i <= size; i++)
	{
	    if (heap[i].getX() == node.getX() && heap[i].getY() == node.getY())
	    {
		return heap[i].getG();
	    }
	}
	return 0;
    }

    public void setParent(Node node, Node parent)
    {
	for (int i = 0; i <= size; i++)
	{
	    if (heap[i].getX() == node.getX() && heap[i].getY() == node.getY())
	    {
		heap[i].setParent(parent);
	    }
	}
    }

    public void setG(Node node, float G)
    {
	for (int i = 0; i <= size; i++)
	{
	    if (heap[i].getX() == node.getX() && heap[i].getY() == node.getY())
	    {
		heap[i].setG(G);
	    }
	}
    }

    private int left(int x)
    {
	return 2 * x;
    }

    private int right(int x)
    {
	return 2 * x + 1;
    }

    private int parent(int x)
    {
	return x / 2;
    }

    private boolean isleaf(int x)
    {
	return ((x > size / 2) && (x <= size));
    }

    private void swap(int pos1, int pos2)
    {
	Node tmp;
	tmp = heap[pos1];
	heap[pos1] = heap[pos2];
	heap[pos2] = tmp;
    }

    public void add(Node node)
    {
	size++;
	if (size == heap.length)
	{
	    Node[] temp = heap;
	    heap = new Node[size * 2];
	    for (int i = 0; i < temp.length; i++)
	    {
		heap[i] = temp[i];
	    }
	}
	heap[size] = node;
	int current = size;
	while (heap[parent(current)] != null && heap[current].getF() < heap[parent(current)].getF())
	{
	    swap(current, parent(current));
	    current = parent(current);
	}
    }

    public boolean contains(Node node)
    {
	for (int i = 0; i <= this.size; i++)
	{
	    if (heap[i] != null && heap[i].getX() == node.getX() && heap[i].getY() == node.getY())
	    {
		return true;
	    }
	}
	return false;
    }

    public void print()
    {
	int i;
	for (i = 1; i <= size; i++)
	{
	    System.out.print(heap[i].getF() + " ");
	}
	System.out.println();
    }

    public Node removemin()
    {
	swap(1, size);
	size--;
	if (size != 0)
	{
	    pushdown(1);
	}
	return heap[size + 1];
    }

    public void remove(Node node)
    {
	for (int i = 0; i <= size; i++)
	{
	    if (heap[i].getX() == node.getX() && heap[i].getY() == node.getY())
	    {
		pushdown(i);
		return;
	    }
	}
    }

    public int size()
    {
	return this.size;
    }

    private void pushdown(int position)
    {
	int smallestchild;
	while (!isleaf(position))
	{
	    smallestchild = left(position);
	    if ((smallestchild < size) && (heap[smallestchild].getF() > heap[smallestchild + 1].getF()))
	    {
		smallestchild = smallestchild + 1;
	    }
	    if (heap[position].getF() <= heap[smallestchild].getF())
	    {
		return;
	    }
	    swap(position, smallestchild);
	    position = smallestchild;
	}
    }
}
