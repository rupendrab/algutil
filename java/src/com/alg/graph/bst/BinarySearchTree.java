package com.alg.graph.bst;

public class BinarySearchTree<T extends Comparable<? super T>>
{
    T root;
    BinarySearchTree<T> left;
    BinarySearchTree<T> right;
    BinarySearchTree<T> parent;
    int size;

    public BinarySearchTree(T root)
    {
        super();
        this.root = root;
        this.size = 1;
    }

    public BinarySearchTree(T root, BinarySearchTree<T> left, BinarySearchTree<T> right)
    {
        super();
        this.root = root;
        this.left = left;
        this.right = right;
        if (left != null)
        {
            left.setParent(this);
        }
        if (right != null)
        {
            right.setParent(this);
        }
        computeSize();
    }
    
    public int compareTo(T a, T b)
    {
        if (a == null)
        {
            if (b == null)
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
        else if (b == null)
        {
            return -1;
        }
        else
        {
            return a.compareTo(b);
        }
    }
    
    public BinarySearchTree<T> locate(T elem)
    {
        int cmp = compareTo(elem, root);
        if (cmp == 0)
        {
            return this;
        }
        else if (cmp < 0) // Value is less than root
        {
            if (left == null)
            {
                return null;
            }
            else
            {
                return left.locate(elem);
            }
        }
        else // value is higher than root
        {
            if (right == null)
            {
                return null;
            }
            else
            {
                return right.locate(elem);
            }
        }
    }
    
    /*
     * This version of insert allows duplicate elements and it chooses the left branch to get 
     * to the duplicate elements.
     */
    public void insert(T elem)
    {
        int cmp = compareTo(elem, root);
        if (cmp <= 0) // If less or equal, choose the left subtree
        {
            if (left == null)
            {
                BinarySearchTree<T> left = new BinarySearchTree<T>(elem);
                this.setLeft(left);
            }
            else
            {
                size += 1;
                left.insert(elem);
            }
        }
        else // Choose the right subtree
        {
            if (right == null)
            {
                BinarySearchTree<T> right = new BinarySearchTree<T>(elem);
                this.setRight(right);
            }
            else
            {
                size += 1;
                right.insert(elem);
            }
        }
    }
    
    public BinarySearchTree<T> max()
    {
        BinarySearchTree<T> bst = this;
        while (bst.right != null)
        {
            bst = bst.right;
        }
        return bst;
    }

    public BinarySearchTree<T> min()
    {
        BinarySearchTree<T> bst = this;
        while (bst.left != null)
        {
            bst = bst.left;
        }
        return bst;
    }
    
    public BinarySearchTree<T> predecessor(T elem)
    {
        // Not Implemented
        BinarySearchTree<T> bstElem = locate(elem);
        if (bstElem == null)
        {
            return null;
        }
        if (bstElem.left != null)
        {
            return bstElem.left.max();
        }
        else
        {
            BinarySearchTree<T> bst = bstElem.parent;
            while (bst != null && compareTo(bst.root, bstElem.root) > 0)
            {
                bst = bst.parent;
            }
            if (compareTo(bst.root, bstElem.root) > 0)
            {
                return null;
            }
            else
            {
                return bst;
            }
        }
    }
    
    public int delete(T elem)
    {
        BinarySearchTree<T> bst = locate(elem);
        if (bst == null)
        {
            return 0;
        }
        if (bst.left == null && bst.right == null)
        {
            // No left or right child
            boolean isLeftOfParent = compareTo(bst.root, bst.parent.root) <= 0;
            if (isLeftOfParent)
            {
                bst.parent.setLeft(null);
            }
            else
            {
                bst.parent.setRight(null);
            }
            bst = null;
        }
        else if (bst.left != null && bst.right != null)
        {
            // Both left and right child
        }
        else
        {
            // Either a left or a right child
        }
        return 1;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (left != null)
        {
            sb.append(left.toString());
        }
        sb.append(":");
        sb.append(root.toString() + "(" + size + ")");
        sb.append(":");
        if (right != null)
        {
            sb.append(right.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    public T getRoot()
    {
        return root;
    }

    public void setRoot(T root)
    {
        this.root = root;
    }

    public BinarySearchTree<T> getLeft()
    {
        return left;
    }

    public void setLeft(BinarySearchTree<T> left)
    {
        this.left = left;
        if (left != null)
        {
            left.setParent(this);
        }
        computeSize();
    }

    public BinarySearchTree<T> getRight()
    {
        return right;
    }

    public void setRight(BinarySearchTree<T> right)
    {
        this.right = right;
        if (right != null)
        {
            right.setParent(this);
        }
        computeSize();
    }

    public BinarySearchTree<T> getParent()
    {
        return parent;
    }

    public void setParent(BinarySearchTree<T> parent)
    {
        this.parent = parent;
    }

    private void computeSize()
    {
        this.size = 1 + (this.left == null ? 0 : this.left.size) + (this.right == null ? 0 : this.right.size);
    }

}
