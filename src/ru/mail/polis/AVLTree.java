package ru.mail.polis;

import java.util.*;

//TODO: write code here
public class AVLTree<E extends Comparable<E>> implements ISortedSet<E> {

    class Node {
        Node(E value) {
            this.value = value;
        }

        E value;
        Node left;
        Node right;
        int height;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("N{");
            sb.append("d=").append(value);
            if (left != null) {
                sb.append(", l=").append(left);
            }
            if (right != null) {
                sb.append(", r=").append(right);
            }
            sb.append('}');
            return sb.toString();
        }
    }

    private Node root;
    private int size = 0;
    private final Comparator<E> comparator;

    public AVLTree() {
        this.comparator = null;
    }

    public AVLTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public E first() {
        if (isEmpty()) {
            throw new NoSuchElementException("set is empty, no first element");
        }
        Node curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr.value;
    }

    @Override
    public E last() {
        if (isEmpty()) {
            throw new NoSuchElementException("set is empty, no last element");
        }
        Node curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr.value;
    }

    @Override
    public List<E> inorderTraverse() {
        List<E> list = new LinkedList<E>();
        inorder(root, list);
        return list;
    }

    private void inorder(Node r, List<E> list) {
        if (r != null) {
            inorder(r.left, list);
            list.add(r.value);
            inorder(r.right, list);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if(root == null)
            return false;
        return true;

    }

    @Override
    public boolean contains(E value) {
        return contains(root, value);
    }

    private boolean contains(Node t, E val) {
        boolean found = false;
        while ((t != null) && !found) {
            E tValue = t.value;
            if (val.compareTo(tValue) < 0)
                t = t.left;
            else if (val.compareTo(tValue) > 0)
                t = t.right;
            else {
                found = true;
                break;
            }
            found = contains(t, val);
        }
        return found;
    }

    private int height(Node t)
    {
        return t == null ? -1 : t.height;
    }

    @Override
    public boolean add(E value) {
        int tempSize = size;
        size++;
        root = insert(value, root);
        if(tempSize == size)
            return false;
        return true;
    }

    private Node insert(E x, Node t) {
        if(t == null)
            return new Node(x);

        int compareResult = x.compareTo(t.value);

        if(compareResult < 0)
            t.left = insert(x, t.left);
        else if(compareResult > 0)
            t.right = insert(x, t.right);
        else
            size--; // дублирующиеся элементы исключаются
        return balance(t);
    }

    private Node rotateWithLeftChild(Node k2) {
        Node k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private Node rotateWithRightChild(Node k1) {
        Node k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right), k1.height) + 1;
        return k2;
    }

    private Node doubleWithLeftChild(Node k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private Node doubleWithRightChild(Node k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    @Override
    public boolean remove(E value) {
        int tempSize = size;
        root = remove(value, root);
        if (tempSize == size)
            return false;
        return true;
    }

    private Node remove(E x, Node t) {
        if(t == null)
            return t;
        int compareResult = x.compareTo(t.value);
        if(compareResult < 0)
            t.left = remove(x, t.left);
        else if(compareResult > 0)
            t.right = remove(x, t.right);
        else if(t.left != null && t.right != null) {
            t.value = findMin(t.right).value;
            t.right = remove(t.value, t.right);
        }
        else {
            size--;
            t = (t.left != null) ? t.left : t.right;
        }
        return balance(t);
    }

    private Node findMin(Node t) {
        if(t == null)
            return t;
        while(t.left != null)
            t = t.left;
        return t;
    }

    private Node balance(Node t) {
        if(t == null)
            return t;
        if(height(t.left) - height(t.right) > 1)
            if(height(t.left.left) >= height(t.left.right))
                t = rotateWithLeftChild(t);
            else
                t = doubleWithLeftChild(t);
        else
        if(height(t.right) - height(t.left) > 1)
            if(height(t.right.right) >= height(t.right.left))
                t = rotateWithRightChild(t);
            else
                t = doubleWithRightChild(t);
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    private int compare(E v1, E v2) {
        return comparator == null ? v1.compareTo(v2) : comparator.compare(v1, v2);
    }

    @Override
    public String toString() {
        return "AVLT{" + root + "}";
    }

    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.add(10);
        tree.add(5);
        tree.add(15);
        System.out.println(tree.inorderTraverse());
        System.out.println(tree.size);
        System.out.println(tree);
        tree.remove(10);
        tree.remove(15);
        System.out.println(tree.size);
        System.out.println(tree);
        tree.remove(5);
        System.out.println(tree.size);
        System.out.println(tree);
        tree.add(15);
        System.out.println(tree.size);
        System.out.println(tree);

        System.out.println("------------");
        Random rnd = new Random();
        tree = new AVLTree<>();
        for (int i = 0; i < 15; i++) {
            tree.add(rnd.nextInt(50));
        }
        System.out.println(tree.inorderTraverse());
        tree = new AVLTree<>((v1, v2) -> {
            // Even first
            final int c = Integer.compare(v1 % 2, v2 % 2);
            return c != 0 ? c : Integer.compare(v1, v2);
        });
        for (int i = 0; i < 15; i++) {
            tree.add(rnd.nextInt(50));
        }
        System.out.println(tree.inorderTraverse());
    }
}
