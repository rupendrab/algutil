package com.alg.display;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.alg.BinaryTree;

public class BinaryTreeDisplay extends JPanel
{

    private static final long serialVersionUID = 1L;
    int WIDTH = 30;
    int HEIGHT = 30;
    int LEVELHEIGHT = HEIGHT * 3;
    int rootY = 50;
    int rootX = 500;
    int DISTANCE = 2 * WIDTH;
    
    BinaryTree tree;
    int maxLevels;
    int screenWidth;
    int screenHeight;
    
    public BinaryTreeDisplay(BinaryTree tree)
    {
        super();
        this.tree = tree;
        updateTree();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                System.out.println(String.format("x = %d, y = %d", e.getX(), e.getY()));
            }
        });
    }
    
    @Override
    public void revalidate()
    {
        updateTree();
        super.revalidate();
    }
    
    public JFrame getParentFrame(Container x)
    {
        Container parent = x.getParent();
        if (parent == null)
        {
            return null;
        }
        else if (parent instanceof JFrame)
        {
            return (JFrame) parent;
        }
        else
        {
            return getParentFrame(parent);
        }
    }
    
    public void updateTree()
    {
        if (tree != null)
        {
            this.maxLevels = tree.level();
            // System.out.println("maxLevels = " + maxLevels);
            screenWidth = (int) Math.pow(2, maxLevels) * DISTANCE + DISTANCE;
            screenHeight = maxLevels * LEVELHEIGHT + LEVELHEIGHT * 2;
            // System.out.println(screenWidth);
            // screenWidth = 960;
            rootX = screenWidth/2;
            setSize(screenWidth, screenHeight);
            JFrame parentFrame = getParentFrame(this);
            if (parentFrame != null)
            {
                parentFrame.setSize(screenWidth, screenHeight);
            }
        }
    }

    public void drawCircle(Graphics g, int x, int y, int value)
    {
        int startX = x - WIDTH/2;
        int startY = y - HEIGHT/2;
        g.drawOval(startX, startY, WIDTH, HEIGHT);
        g.drawString("" + value, startX + WIDTH/4, startY+ (int) (HEIGHT*0.6));
    }
    
    public void drawLine(Graphics g, int parentX, int parentY, int childX, int childY)
    {
        double d = Math.pow(Math.pow(childX - parentX, 2) + Math.pow(childY - parentY, 2), 0.5);
        int h = childY - parentY; // Always +ve
        int w = childX - parentX; // +ve for right, -ve for left
        double parentFromX = parentX + (WIDTH * w) / d;
        double parentFromY = parentY + (WIDTH * h) / d;
        double childToX = childX - (WIDTH * w) / d;
        double childToY = childY - (WIDTH * h) / d;
        g.drawLine((int) parentFromX, (int) parentFromY, (int) childToX, (int) childToY); 
        // g.drawLine(parentX, parentY, childX, childY);
    }
    
    public void paintNode(Graphics g, BinaryTree node, BinaryTree parent, boolean isLeft, int parentX, int parentY, int level)
    {
        if (parent == null) // Root Node
        {
            drawCircle(g, rootX, rootY, node.getValue());
            if (node.getLeft() != null)
            {
                paintNode(g, node.getLeft(), node, true, rootX, rootY, level + 1);
            }
            if (node.getRight() != null)
            {
                paintNode(g, node.getRight(), node, false, rootX, rootY, level + 1);
            }
        }
        else
        {
            int yPos = rootY + LEVELHEIGHT * (level);
            int distance = (int) Math.pow(2, maxLevels-level) * (DISTANCE);
            // System.out.println(String.format("Node = %d, Level = %d, Distance = %d, %d", node.getValue(), level, distance, maxLevels -level));
            int xPos = 0;
            if (isLeft)
            {
                xPos = parentX - distance/2;
            }
            else
            {
                xPos = parentX + distance/2;
            }
            drawCircle(g, xPos, yPos, node.getValue());
            drawLine(g, parentX, parentY, xPos, yPos);
            if (node.getLeft() != null)
            {
                paintNode(g, node.getLeft(), node, true, xPos, yPos, level + 1);
            }
            if (node.getRight() != null)
            {
                paintNode(g, node.getRight(), node, false, xPos, yPos, level + 1);
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        // Draw Tree Here
        // drawCircle(g, 100, 100, 1);
        // g.drawOval(5, 5, 25, 25);
        paintNode(g, tree, null, false, 0, 0, 0);
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

    public int getScreenHeight()
    {
        return screenHeight;
    }

    public static BinaryTree getTree01()
    {
        BinaryTree root = new BinaryTree(1, new BinaryTree(2), new BinaryTree(3));
        root.getLeft().setLeft(new BinaryTree(4));
        root.getLeft().setRight(new BinaryTree(5));
        root.getRight().setLeft(new BinaryTree(6));
        root.getRight().setRight(new BinaryTree(7));
        root.getRight().getRight().setLeft(new BinaryTree(13));
        root.getRight().getRight().setRight(new BinaryTree(15));
        root.getRight().getLeft().setLeft(new BinaryTree(16));
        root.getRight().getLeft().setRight(new BinaryTree(17));
        root.getLeft().getRight().setLeft(new BinaryTree(21));
        root.getLeft().getRight().setRight(new BinaryTree(22));
        root.getLeft().getLeft().setLeft(new BinaryTree(23));
        root.getLeft().getLeft().setRight(new BinaryTree(24));
        root.getLeft().getLeft().getLeft().setLeft(new BinaryTree(41));
        root.getLeft().getLeft().getLeft().setRight(new BinaryTree(42));
        return root;
    }
    
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        BinaryTree tree = getTree01();
        BinaryTreeDisplay btd = new BinaryTreeDisplay(tree);
        jFrame.add(btd);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(btd.screenWidth, btd.screenHeight);
        jFrame.setVisible(true);
    }

}
