import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by Rezenter on 11/16/15.
 */
public class Mountain {
    public Tree cut;
    public float pos;
    public float h;

    public Mountain(float x) {
        Random random = new Random();
        pos = x;
        float l = x - (float) (random.nextFloat() * 0.8);
        float r = x + (float) (random.nextFloat() * 0.8);
        cut = new Tree(new Tuple(
                new Vector2f(l, 0), new Vector2f(new Vector2f(r, 0))));
        generate(cut.getRoot());
    }

    public void generate(Node t) {
        if (t != null) {
            Random random = new Random();
            boolean flag = true;
            if (t.equals(cut.getRoot())) {
                flag = false;
                h = random.nextFloat();
                Vector2f tmp = new Vector2f(pos, h);
                cut.getRoot().addL(new Tuple(cut.getRoot().val.a, tmp));
                cut.getRoot().addR(new Tuple(tmp, cut.getRoot().val.b));
            }
            float length = ((t.val.b.get()[0] - t.val.a.get()[0]));
            if (length > 0.01 && length != Float.POSITIVE_INFINITY) {
                if (flag) {
                    float x = (random.nextFloat() * length) + t.val.a.get()[0];
                    float y = (float) Math.abs(((random.nextFloat() - 0.5) * length / 2) + t.val.a.get()[1]);
                    Vector2f point = new Vector2f(x, y);
                    t.addL(new Tuple(t.val.a, point));
                    t.addR(new Tuple(point, t.val.b));
                }
                generateL(t.l);
                generateR(t.r);
            }
        }
    }

    public void generateR(Node t) {
        Random random = new Random();
        float length = ((t.val.b.get()[0] - t.val.a.get()[0]));
        if (length > 0.001 && length != Float.POSITIVE_INFINITY) {
            float x = (random.nextFloat() * length) + t.val.a.get()[0];
            float y = (float) Math.abs(((random.nextFloat() - 0.5) * length / 2) + t.val.b.get()[1]);
            Vector2f point = new Vector2f(x, y);
            t.addL(new Tuple(t.val.a, point));
            t.addR(new Tuple(point, t.val.b));
            generateL(t.l);
            generateR(t.r);
        }
    }

    public void generateL(Node t) {
        Random random = new Random();
        float length = ((t.val.b.get()[0] - t.val.a.get()[0]));
        if (length > 0.001 && length != Float.POSITIVE_INFINITY) {
            float x = (random.nextFloat() * length) + t.val.a.get()[0];
            float y = (float) Math.abs(((random.nextFloat() - 0.5) * length / 2) + t.val.a.get()[1]);
            Vector2f point = new Vector2f(x, y);
            t.addL(new Tuple(t.val.a, point));
            t.addR(new Tuple(point, t.val.b));
            generateL(t.l);
            generateR(t.r);
        }
    }

    public ArrayList<Triangle> triangulate(Vector3f col, float far) {
        ArrayList<Triangle> res = new ArrayList<>();
        for (Node tmp : lists) {
            Tuple cur = tmp.val;
            Vector3f c = col;
            res.add(new Triangle(
                    new Vector3f(cur.a.get()[0], cur.a.get()[1], far),
                    new Vector3f(cur.b.get()[0], cur.b.get()[1], far),
                    new Vector3f(cur.a.get()[0], 0, far),
                    c));
            res.add(new Triangle(
                    new Vector3f(cur.b.get()[0], 0, far),
                    new Vector3f(cur.b.get()[0], cur.b.get()[1], far),
                    new Vector3f(cur.a.get()[0], 0, far),
                    c));
        }
        return res;
    }

    public class Tuple {
        public Vector2f a;
        public Vector2f b;

        public String toString() {
            return a + "_" + b;
        }

        public Tuple(Vector2f x, Vector2f y) {
            a = new Vector2f(x);
            b = new Vector2f(y);
        }
    }

    private class Node {
        public Tuple val;
        public Node l;
        public Node r;

        public Node(Tuple v) {
            val = v;
        }

        public String toString() {
            return val + "";
        }

        public void addL(Tuple v) {
            lists.remove(this);
            l = new Node(v);
            lists.add(l);
        }

        public void addR(Tuple v) {
            //lists.remove(this);
            r = new Node(v);
            lists.add(r);
        }

        public void setVal(Tuple v) {
            this.val = v;
        }
    }

    public HashSet<Node> lists = new HashSet<>();

    private class Tree {
        private Node root;

        public Tree(Tuple v) {
            root = new Node(v);
            lists.add(root);
        }

        public Node getRoot() {
            return root;
        }

        public void setRoot(Tuple v) {
            root.setVal(v);
        }
    }
}

