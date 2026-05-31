class BSTNode {
    int key;
    BSTNode left, right;

    BSTNode(int key) {
        this.key = key;
    }
}

class AVLNode {
    int key, height;
    AVLNode left, right;

    AVLNode(int key) {
        this.key = key;
        this.height = 1;
    }
}

public class CO1_Casestudy_MediFLowAVL {

    // ---------------- BST ----------------

    static BSTNode bstInsert(BSTNode root, int key) {
        if (root == null)
            return new BSTNode(key);

        if (key < root.key)
            root.left = bstInsert(root.left, key);
        else
            root.right = bstInsert(root.right, key);

        return root;
    }

    static int bstHeight(BSTNode root) {
        if (root == null)
            return -1;

        return 1 + Math.max(
                bstHeight(root.left),
                bstHeight(root.right));
    }

    static void printBST(BSTNode root, int space) {

        if (root == null)
            return;

        space += 8;

        printBST(root.right, space);

        System.out.println();

        for (int i = 8; i < space; i++)
            System.out.print(" ");

        System.out.println(root.key);

        printBST(root.left, space);
    }

    // ---------------- AVL ----------------

    static int height(AVLNode n) {
        return n == null ? 0 : n.height;
    }

    static void updateHeight(AVLNode n) {
        if (n != null)
            n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    static int getBalance(AVLNode n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    static AVLNode rotateRight(AVLNode y) {

        AVLNode x = y.left;
        AVLNode t2 = x.right;

        x.right = y;
        y.left = t2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    static AVLNode rotateLeft(AVLNode x) {

        AVLNode y = x.right;
        AVLNode t2 = y.left;

        y.left = x;
        x.right = t2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    static AVLNode insert(AVLNode node, int key) {

        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);

        else if (key > node.key)
            node.right = insert(node.right, key);

        else
            return node;

        updateHeight(node);

        int balance = getBalance(node);

        // LL
        if (balance > 1 && key < node.left.key)
            return rotateRight(node);

        // RR
        if (balance < -1 && key > node.right.key)
            return rotateLeft(node);

        // LR
        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // RL
        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    static AVLNode minValueNode(AVLNode node) {

        AVLNode current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    static AVLNode deleteNode(AVLNode root, int key) {

        if (root == null)
            return root;

        if (key < root.key)
            root.left = deleteNode(root.left, key);

        else if (key > root.key)
            root.right = deleteNode(root.right, key);

        else {

            if (root.left == null || root.right == null) {

                AVLNode temp;

                if (root.left != null)
                    temp = root.left;
                else
                    temp = root.right;

                if (temp == null)
                    root = null;
                else
                    root = temp;

            } else {

                AVLNode temp = minValueNode(root.right);

                root.key = temp.key;

                root.right = deleteNode(root.right, temp.key);
            }
        }

        if (root == null)
            return root;

        updateHeight(root);

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return rotateRight(root);

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0)
            return rotateLeft(root);

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    static int avlHeight(AVLNode root) {
        return root == null ? -1 : root.height - 1;
    }

    static void printAVL(AVLNode root, int space) {

        if (root == null)
            return;

        space += 8;

        printAVL(root.right, space);

        System.out.println();

        for (int i = 8; i < space; i++)
            System.out.print(" ");

        System.out.println(root.key);

        printAVL(root.left, space);
    }

    public static void main(String[] args) {

        int[] ids = {
                20, 30, 35, 40, 45, 50,
                60, 65, 70, 75, 80, 85, 90
        };

        BSTNode bstRoot = null;
        AVLNode avlRoot = null;

        for (int id : ids) {
            bstRoot = bstInsert(bstRoot, id);
            avlRoot = insert(avlRoot, id);
        }

        System.out.println("=================================");
        System.out.println("PLAIN BST");
        System.out.println("=================================");

        printBST(bstRoot, 0);

        System.out.println("\nBST Height = " +
                bstHeight(bstRoot));

        System.out.println("\n=================================");
        System.out.println("AVL TREE");
        System.out.println("=================================");

        printAVL(avlRoot, 0);

        System.out.println("\nAVL Height = " +
                avlHeight(avlRoot));

        avlRoot = deleteNode(avlRoot, 30);
        avlRoot = deleteNode(avlRoot, 70);
        avlRoot = deleteNode(avlRoot, 50);

        System.out.println("\n=================================");
        System.out.println("AVL AFTER DELETIONS");
        System.out.println("=================================");

        printAVL(avlRoot, 0);

        System.out.println("\nProgram Executed Successfully");
    }
}