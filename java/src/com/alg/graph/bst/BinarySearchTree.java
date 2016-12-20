package com.alg.graph.bst;

import java.util.ArrayList;
import java.util.Arrays;

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
        if (root != null)
        {
            this.size = 1;
        }
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
    
    public BinarySearchTree(T[] arr, int start, int end, boolean sort)
    {
        if (arr == null)
        {
            return;
        }
        if (start == end - 1)
        {
            root = arr[start];
            size = 1;
            return;
        }
        if (sort)
        {
            Arrays.sort(arr);
        }
        int midPosition = start + (end - start) / 2;
        // Forward midPosition until the same value persists, ensuring equal values go to left subtree
        int i = midPosition;
        for (i=midPosition; i<end-1 && compareTo(arr[i+1], arr[midPosition]) == 0; i++);
        midPosition = i;
        root = arr[midPosition];
        size = end - start;
        left = new BinarySearchTree<T>(arr, start, midPosition, false);
        if (midPosition < end -1)
        {
            right = new BinarySearchTree<T>(arr, midPosition+1, end, false);
        }
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
        if (root == null)
        {
            root = elem;
            size = 1;
            return;
        }
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
            if (bst == null)
            {
                return null;
            }
            else if (compareTo(bst.root, bstElem.root) > 0)
            {
                return null;
            }
            else
            {
                return bst;
            }
        }
    }
    
    public BinarySearchTree<T> predecessor(BinarySearchTree<T> reference)
    {
        if (reference == null)
        {
            return null;
        }
        if (reference.left != null)
        {
            return reference.left.max();
        }
        else
        {
            BinarySearchTree<T> bst = reference.parent;
            while (bst != null && compareTo(bst.root, reference.root) > 0)
            {
                bst = bst.parent;
            }
            if (bst == null)
            {
                return null;
            }
            else if (compareTo(bst.root, reference.root) > 0)
            {
                return null;
            }
            else
            {
                return bst;
            }
        }
    }

    public BinarySearchTree<T> successor(T elem)
    {
        // Not Implemented
        BinarySearchTree<T> bstElem = locate(elem);
        if (bstElem == null)
        {
            return null;
        }
        if (bstElem.right != null)
        {
            return bstElem.right.min();
        }
        else
        {
            BinarySearchTree<T> bst = bstElem.parent;
            while (bst != null && compareTo(bst.root, bstElem.root) < 0)
            {
                bst = bst.parent;
            }
            if (bst == null)
            {
                return null;
            }
            else if (compareTo(bst.root, bstElem.root) < 0)
            {
                return null;
            }
            else
            {
                return bst;
            }
        }
    }
    
    public BinarySearchTree<T> select(int pos)
    {
        // Returns the element at indicated position. Position is zero based
        if (pos < 0 || pos > size - 1)
        {
            return null;
        }
        int currentPosition = 0;
        if (left != null)
        {
            currentPosition = left.size;
        }
        if (currentPosition == pos)
        {
            return this;
        }
        else if (pos < currentPosition)
        {
            return left.select(pos);
        }
        else
        {
            if (right == null)
            {
                return null;
            }
            else
            {
                return right.select(pos - currentPosition - 1);
            }
        }
    }
    
    public BinarySearchTree<T> medianTree()
    {
        if (size == 0)
        {
            return null;
        }
        int pos = size / 2;
        if (size % 2 == 0)
        {
            pos -= 1;
        }
        return select(pos);
    }
    
    public T median()
    {
        BinarySearchTree<T> mTree = medianTree();
        if (mTree == null)
        {
            return null;
        }
        return mTree.root;
    }

    public int rank(T elem, int startRank)
    {
        // Returns the zero based rank of an element
        int currentPosition = startRank;
        if (left != null)
        {
            currentPosition += left.size;
        }
        int cmp = compareTo(elem, root);
        if (cmp == 0)
        {
            if (left != null)
            {
                int posInLeftTree = left.rank(elem, startRank);
                return posInLeftTree != -1 ? posInLeftTree : currentPosition;
            }
            else
            {
                return currentPosition;
            }
        }
        else if (cmp < 0)
        {
            if (left == null)
            {
                return -1;
            }
            else
            {
                return left.rank(elem, startRank);
            }
        }
        else
        {
            if (right == null)
            {
                return -1;
            }
            else
            {
                return right.rank(elem, currentPosition + 1);
            }
        }
    }
    
    public int rank(T elem)
    {
        return rank(elem, 0);
    }
    
    public ArrayList<T> orderedArray()
    {
        ArrayList<T> arr = new ArrayList<>();
        if (left != null)
        {
            arr.addAll(left.orderedArray());
        }
        if (root != null)
        {
            arr.add(root);
        }
        if (right != null)
        {
            arr.addAll(right.orderedArray());
        }
        return arr;
    }
    
    public int delete(T elem)
    {
        BinarySearchTree<T> bst = locate(elem);
        if (bst == null)
        {
            return 0;
        }
        boolean hasParent = bst.parent != null;
        boolean isLeftOfParent = hasParent && compareTo(bst.root, bst.parent.root) <= 0;
        if (bst.left == null && bst.right == null)
        {
            // No left or right child
            if (hasParent)
            {
                if (isLeftOfParent)
                {
                    bst.parent.setLeft(null);
                }
                else
                {
                    bst.parent.setRight(null);
                }
                bst.parent.computeSizeAllUpwards();
            }
            else
            {
                // Last Node deleted (Special case)
                root = null;
                left = null;
                right = null;
                size = 0;
            }
        }
        else if (bst.left != null && bst.right != null)
        {
            // Both left and right child
            BinarySearchTree<T> pred = predecessor(bst);
            BinarySearchTree<T> predParent = pred.parent;
            if (predParent.root.compareTo(pred.root) >= 0)
            {
                predParent.setLeft(pred.left);
            }
            else
            {
                predParent.setRight(pred.left);
            }
            bst.setRoot(pred.getRoot());
            predParent.computeSizeAllUpwards();
        }
        else
        {
            // Either a left or a right child
            BinarySearchTree<T> child = bst.left == null ? bst.right : bst.left;
            if (bst.parent != null)
            {
                if (isLeftOfParent)
                {
                    bst.parent.setLeft(child);
                }
                else
                {
                    bst.parent.setRight(child);
                }
                bst.parent.computeSizeAllUpwards();
            }
            else
            {
                child.parent = null;
                root = child.root;
                setLeft(child.left);
                setRight(child.right);
                this.computeSizeAllUpwards();
            }
        }
        return 1;
    }
    
    public int computeHeight()
    {
        int leftHeight = 0;
        int rightHeight = 0;
        if (left != null)
        {
            leftHeight = left.computeHeight();
        }
        if (right != null)
        {
            rightHeight = right.computeHeight();
        }
        return Math.max(leftHeight, rightHeight) + 1;
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
        sb.append((root == null ? "" : root.toString()) + "(" + size + ")");
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

    private void computeSizeAllUpwards()
    {
        computeSize();
        BinarySearchTree<T> p = this.parent;
        while (p != null)
        {
            p.computeSize();
            p = p.parent;
        }
    }
    
    public void destroy()
    {
    }
    
    public void unlink()
    {
        boolean hasParent = parent != null;
        boolean isLeftOfParent = hasParent && compareTo(root, parent.root) <= 0;
        if (hasParent)
        {
            if (isLeftOfParent)
            {
                parent.left = null;
            }
            else
            {
                parent.right = null;
            }
        }
    }
    
    public int getLeftSize()
    {
        return left == null ? 0 : left.size;
    }
    
    public int getRightSize()
    {
        return right == null ? 0 : right.size;
    }

    public boolean checkTree()
    {
        if (root == null && size == 0 && getLeftSize() == 0 && getRightSize() == 0)
        {
            return true;
        }
        if (size != getLeftSize() + 1 + getRightSize())
        {
            // System.out.println("Failed Size check at " + root);
            // System.out.println(String.format("Size = %d, left = %d, right = %d", getLeftSize(), getRightSize()));
            return false;
        }
        if (left != null)
        {
            if (compareTo(left.root, root) > 0)
            {
                // System.out.println(String.format("Value = %s, left = %s", root, left.root));
                return false;
            }
            if (this != left.parent)
            {
                System.out.println(String.format("Left Child mismatch: Value = %s, left = %s", root, left.root));
                return false;
            }
            if (! left.checkTree())
            {
                return false;
            }
        }
        if (right != null)
        {
            if (compareTo(right.root, root) <= 0)
            {
                // System.out.println(String.format("Value = %s, right = %s", root, right.root));
                return false;
            }
            if (this != right.parent)
            {
                System.out.println(String.format("Right Child mismatch: Value = %s, left = %s", root, right.root));
                return false;
            }
            if (! right.checkTree())
            {
                return false;
            }
        }
        return true;
    }

}
