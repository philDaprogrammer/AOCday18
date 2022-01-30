import java.util.ArrayList;

public class Solution {
    private ArrayList<Pair> pairs ;

    public Solution(Parser parser) { this.pairs = parser.parse(); }

    private void dumpPair(Pair root) {
        if (root.leftValue != null) { System.out.print("[" + root.leftValue + ", "); }
        if (root.left != null) {
            System.out.print("[");
            dumpPair(root.left);
        }

        if (root.rightValue != null) { System.out.print(root.rightValue + "]"); }
        if (root.right != null) {
            dumpPair(root.right);
            System.out.print("]");
        }
    }

    private boolean getRightMost(Pair pair, int rightVal) {
        while (pair != null) {
            if (pair.rightValue != null) {
                pair.rightValue += rightVal;
                return true;
            }

            pair = pair.right;
        }

        return false;
    }

    private void getClosetsLeft(Pair pair, int rightVal, ArrayList<Pair> explored) {
        while (pair != null) {
            explored.add(pair);

            if (pair.leftValue != null) {
                pair.leftValue += rightVal;
                return;
            }

            if ((pair.left != null) && (!explored.contains(pair.left))) {
                if (getRightMost(pair.left, rightVal)) { return; }
            }

            pair = pair.parent;
        }
    }

    private boolean getLeftMost(Pair pair, int rightVal) {
        while (pair != null) {
            if (pair.leftValue != null) {
                pair.leftValue += rightVal;
                return true;
            }

            pair = pair.left;
        }

        return false;
    }

    private void getClosetsRight(Pair pair, int rightVal, ArrayList<Pair> explored) {
        while (pair != null) {
            explored.add(pair);

            if (pair.rightValue != null) {
                pair.rightValue += rightVal;
                return;
            }

            if ((pair.right != null) && (!explored.contains(pair.right))) {
                if (getLeftMost(pair.right, rightVal)) { return; }
            }

            pair = pair.parent;
        }
    }

    private void reducePair(Pair pair, int depth) {
        if (depth >= 4) {
            ArrayList<Pair> leftExplored  = new ArrayList<>();
            ArrayList<Pair> rightExplored = new ArrayList<>();
            leftExplored.add(pair);
            rightExplored.add(pair);

            getClosetsLeft(pair.parent, pair.leftValue, leftExplored);
            getClosetsRight(pair.parent, pair.rightValue, rightExplored);

            if (pair.parent.left != null) {
                pair.parent.leftValue = 0;
                pair.parent.left      = null;
            } else {
                pair.parent.right   = null;
                pair.parent.rightValue = 0;
            }
        } else {
            if (pair.left != null) { reducePair(pair.left,depth + 1); }
            if (pair.right != null) { reducePair(pair.right, depth + 1); }
        }
    }

    private Pair addPair(Pair p1, Pair p2) {
        Pair newPair  = new Pair();
        p1.parent     = newPair;
        p2.parent     = newPair;
        newPair.left  = p1;
        newPair.right = p2;
        return newPair;
    }

    public void solveP1() {
        Pair result = this.pairs.remove( 0);
        for (Pair p : this.pairs) {
            result = addPair(result, p);
            reducePair(result, 0);
        }

        dumpPair(result);
    }
}