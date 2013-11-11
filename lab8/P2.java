import java.util.Scanner;
import java.util.regex.MatchResult;

import java.util.List;
import java.util.ArrayList;


/** Solution to P2.
 *  @author Kiet Lam
 */
public class P2 {

    /** Solve P2.*/
    public static void main(String... ignored) {
        String regex = "\\s*([A-Za-z0-9]+)\\s+([A-Za-z0-9]+)";

        Scanner scanner = new Scanner(System.in);

        int n = 1;

        while (scanner.findWithinHorizon(regex, 0) != null) {
            MatchResult match = scanner.match();
            String preorder = match.group(1);
            String inorder = match.group(2);

            Tree tree = constructTree(preorder, inorder);
            String str = traverseInOrder(tree, "");

            System.out.printf("Case %d: %s\n\n", n, str);
            n += 1;
        }
    }


    /** Return a tree constructed from PREORDER and INORDER.*/
    private static Tree constructTree(String preorder, String inorder) {
        Tree tree = new Tree(null, null, preorder.charAt(0));

        List<Character> traversed = new ArrayList<Character>();

        traversed.add(preorder.charAt(0));

        for (int i = 1; i < preorder.length(); i += 1) {
            char ch = preorder.charAt(i);
            char closestTraversedChar = findClosestChar(traversed, ch, inorder);

            int d = inorder.indexOf(ch) - inorder.indexOf(closestTraversedChar);
            Tree tr = findInTree(tree, closestTraversedChar);

            if (d <= 0) {
                tr._left = new Tree(null, tr, ch);
            } else {
                tr._right = new Tree(null, tr, ch);
            }

            traversed.add(ch);
        }

        return tree;
    }


    /** Returns the tree containing CH from TREE.*/
    private static Tree findInTree(Tree tree, char ch) {
        if (tree == null) {
            return null;
        }

        if (tree._entry == ch) {
            return tree;
        } else {
            Tree trl = findInTree(tree._left, ch);
            Tree trr = findInTree(tree._right, ch);

            if (trl != null) {
                return trl;
            } else if (trr != null) {
                return trr;
            } else {
                return null;
            }
        }
    }


    /** Returns the closest char from TRAVERSED, CH and INORDER.*/
    private static char findClosestChar(List<Character> traversed, char ch,
                                        String inorder) {
        int distance = Integer.MAX_VALUE;
        int prevTraversedPos = -1;
        char current = traversed.get(0);

        for (int j = 0; j < traversed.size(); j += 1) {
            char chi   = traversed.get(j);
            int chiPos = inorder.indexOf(chi);
            int chPos  = inorder.indexOf(ch);
            int dist   = chPos - chiPos;

            if (Math.abs(dist) <= Math.abs(distance)) {
                current = chi;
                distance = chPos - chiPos;
                prevTraversedPos = j;
            }
        }

        return current;
    }


    /** Returns the string built by traversing TREE in ORDER,
     *  accumulating STR.*/
    private static String traverseInOrder(Tree tree, String str) {
        if (tree == null) {
            return "";
        } else {
            return str
                + traverseInOrder(tree._left, str)
                + traverseInOrder(tree._right, str)
                + tree._entry;
        }
    }


    /** Tree class.*/
    private static class Tree {

        /** Left branch.*/
        private Tree _left;

        /** Right branch.*/
        private Tree _right;

        /** Entry of tree.*/
        private Character _entry;

        /** Construct a tree from LEFT, RIGHT and ENTRY.*/
        Tree(Tree left, Tree right, Character entry) {
            _left = left;
            _right = right;
            _entry = entry;
        }
    }
}
