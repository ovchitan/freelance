/**
 * Created by Rezenter on 11/11/15.
 */
public class Triangle {

    private Vector3f a;
    private Vector3f b;
    private Vector3f c;
    private Vector3f col;
    private String colour;

    public Triangle(Vector3f a, Vector3f b, Vector3f c, Vector3f col){
        set(a,b,c, col);
    }

    public final void set(Vector3f a, Vector3f b, Vector3f c, Vector3f co){
        this.a = a;
        this.b = b;
        this.c = c;
        col = co;
    }

    public final Vector3f[] get(){
        Vector3f[] res = new Vector3f[4];
        res[0] = a;
        res[1] = b;
        res[2] = c;
        res[3] = col;
        return res;
    }

    public String toString(){
        String res = "";
        for(Vector3f tmp: get()){
            res += tmp;
        }
        res += "colour = " + colour + col;
        return res;
    }
}
