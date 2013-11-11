/** A binary tree, principally for representing expressions.
 *  @author Kiet Lam
 */
public class BinaryTree<Item> {

    /** Root of this subtree. */
    private TreeNode<Item> myRoot;

    /** An empty tree. */
    public BinaryTree() {
        myRoot = null;
    }

    /** A singleton tree with T as its node. */
    public BinaryTree(TreeNode<Item> t) {
        myRoot = t;
    }

    /** Print the values in the tree in preorder: root value first,
     *  then values in the left subtree (in preorder), then values
     *  in the right subtree (in preorder). */
    public void printPreorder() {
        printPreorder(myRoot);
        System.out.println();
    }

    /** Print the values in the tree T in preorder: root value first,
     *  then ITEM values in the left subtree (in preorder), then values
     *  in the right subtree (in preorder). */
    private static <Item> void printPreorder(TreeNode<Item> t) {
        if (t != null) {
            System.out.print(t.myItem + " ");
            printPreorder(t.left);
            printPreorder(t.right);
        }
    }

    /** Print the values in the tree in inorder: values in the left
     *  subtree first (in inorder), then the root value, then values
     *  in the right subtree (in inorder). */
    public void printInorder() {
        printInorder(myRoot);
        System.out.println();
    }

    /** Print the values (type ITEM) in the tree T in inorder: values in
     *  the left subtree first (in inorder), then the root value, then values
     *  in the right subtree (in inorder). */
    private static <Item> void printInorder(TreeNode<Item> t) {
        if (t != null) {
            printInorder(t.left);
            System.out.print(t.myItem + " ");
            printInorder(t.right);
        }
    }

    /** Dump THIS, with indentation showing structure. */
    public void print() {
        if (myRoot != null) {
            print(myRoot, -1);
        }
    }

    /** Dump ROOT indented by INDENT indentation units. */
    void print(TreeNode<?> root, int indent) {
        if (root == null) {
            return;
        }

        print(root.right, indent + 1);
        println(root.myItem, indent + 1);
        print(root.left, indent + 1);
    }

    /** Number of spaces in one indentation unit. */
    static final int INDENTATION = 4;

    /** Print OBJ, indented by INDENT indentation units, followed by a
     *  newline. */
    private static void println(Object obj, int indent) {
        for (int k = 0; k < indent * INDENTATION; k += 1) {
            System.out.print(" ");
        }
        System.out.println(obj);
    }

    /** Print ITEM values in T, using DESCRIPTION for titles. */
    private static <Item> void print(BinaryTree<Item> t, String description) {
        System.out.println(description + " in preorder");
        t.printPreorder();
        System.out.println(description + " in inorder");
        t.printInorder();
        System.out.println();
    }

    /** One node in a BinaryTree<Item>. */
    static class TreeNode<Item> {

        /** Label on THIS. */
        public Item myItem;
        /** My left child. */
        public TreeNode<Item> left;
        /** My right child. */
        public TreeNode<Item> right;

        /** A node with no children and label OBJ. */
        public TreeNode(Item obj) {
            myItem = obj;
            left = right = null;
        }

        /** A node with children LEFT0 and RIGHT0, and label OBJ. */
        public TreeNode(Item obj, TreeNode<Item> left0, TreeNode<Item> right0) {
            myItem = obj;
            left = left0;
            right = right0;
        }
    }

    /** Return the expression tree corresponding to S.  S is a legal, fully
     *  parenthesized expressions, contains no blanks, and involves
     *  only the operations + and *, and leaf labels (which can be
     *  any string of characters other than *, + and parentheses). */
    public static BinaryTree<String> exprTree(String s) {
        BinaryTree<String> result = new BinaryTree<String>();
        result.myRoot = result.exprTreeHelper(s);
        return result;
    }

    /** Return the root tree node of an expression tree corresponding to
     *  EXPR.  EXPR is a legal, fully parenthesized expressions,
     *  contains no blanks, and involves only the operations + and *,
     *  and leaf labels (which can be any string of characters other
     *  than *, + and parentheses). */
    private TreeNode<String> exprTreeHelper(String expr) {
        /* If expr is a parenthesized expression,
         *   strip off the beginning and ending parentheses,
         *   find the main operator (an occurrence of + or * not nested
         *   in parentheses),
         *   and construct the two subtrees.
         * Otherwise, the expression is represented as a leaf. */
        if (expr.charAt(0) != '(') {
            return new TreeNode<String>(expr);
        } else {
            int nesting = 0;
            int opPos = 0;
            for (int k = 1; k < expr.length() - 1; k += 1) {

                char ch = expr.charAt(k);

                if (ch == '(') {
                    nesting += 1;
                }

                if (ch == ')') {
                    nesting -= 1;
                }

                if ((ch == '*' || ch == '+')
                    && nesting == 0) {

                    opPos = k;
                    break;
                }
            }
            String opnd1 = expr.substring(1, opPos);
            String opnd2 = expr.substring(opPos + 1, expr.length() - 1);
            String op = expr.substring(opPos, opPos + 1);
            return new TreeNode<String>(op, exprTreeHelper(opnd1),
                                        exprTreeHelper(opnd2));
        }
    }

    /** Destructively modify EXPR into a mathematically equivalent expression
     *  in which all subexpressions containing only numerals are replaced by
     *  equivalent numerals. */
    public static void optimize(BinaryTree<String> expr) {
        optimizeHelper(expr.myRoot);
    }

    /** Optimize EXPR.*/
    private static void optimizeHelper(TreeNode<String> expr) {
        if (expr == null) {
            return;
        }

        if (expr.left == null && expr.right == null) {
            return;
        }

        if (isOperator(expr.left.myItem)) {
            optimizeHelper(expr.left);
        }

        if (isOperator(expr.right.myItem)) {
            optimizeHelper(expr.right);
        }

        if (isInteger(expr.left.myItem) && isInteger(expr.right.myItem)) {
            int re = performOperand(expr.myItem,
                                    Integer.parseInt(expr.left.myItem),
                                    Integer.parseInt(expr.right.myItem));

            expr.myItem = "" + re;
            expr.left = null;
            expr.right = null;
        }
    }

    /** Returns the integer from performing OP in VAL1 and VAL2.*/
    private static int performOperand(String op, int val1, int val2) {
        if (op.equals("+")) {
            return val1 + val2;
        }

        if (op.equals("*")) {
            return val1 * val2;
        }

        return -1;
    }

    /** Returns iff STR is an integer.*/
    private static boolean isInteger(String str) {
        try {
            int k = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Returns iff STR is an operator..*/
    private static boolean isOperator(String str) {
        return str.equals("*") || str.equals("+");
    }
}
