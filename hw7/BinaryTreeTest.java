import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BinaryTreeTest {

    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(BinaryTreeTest.class));
    }

    @Test
    public void testPrintTree() {
        BinaryTree.TreeNode<String> root = new BinaryTree.TreeNode<String>("A");
        root.left = new BinaryTree.TreeNode<String>("B");

        BinaryTree.TreeNode<String> right =
            new BinaryTree.TreeNode<String>("C");
        root.right = right;

        right.left = new BinaryTree.TreeNode<String>("D");
        right.right = new BinaryTree.TreeNode<String>("E");

        BinaryTree<String> tree = new BinaryTree<String>(root);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        System.setOut(ps);

        tree.print();

        assertEquals("        E\n"
                     + "    C\n"
                     + "        D\n"
                     + "A\n"
                     + "    B\n",
                     baos.toString());
    }

    @Test
    public void testExprTree() {
        String expr = "((a+(5*(a+b)))+(6*5))";

        BinaryTree<String> bt = BinaryTree.exprTree(expr);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        System.setOut(ps);

        bt.print();

        assertEquals("        5\n"
                     + "    *\n"
                     + "        6\n"
                     + "+\n"
                     + "                b\n"
                     + "            +\n"
                     + "                a\n"
                     + "        *\n"
                     + "            5\n"
                     + "    +\n"
                     + "        a\n",
                     baos.toString());
    }

    @Test
    public void testOptimize() {
        String expr = "((a+(5*(9+1)))+(6*5))";

        BinaryTree<String> bt = BinaryTree.exprTree(expr);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        System.setOut(ps);

        BinaryTree.optimize(bt);

        bt.print();

        assertEquals("    30\n"
                     + "+\n"
                     + "        50\n"
                     + "    +\n"
                     + "        a\n", baos.toString());
    }
}
