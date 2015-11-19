import java.util.ArrayList;

/**
 * Created by Rezenter on 11/12/15.
 */
public class Solid{
    private ArrayList<Obj> parts;

    public Solid(ArrayList<Obj> p){
        parts = p;
    }


    public ArrayList<Triangle> triangulate(){
        ArrayList<Triangle> res = new ArrayList<>();
        for(Obj tmp:parts){
            res.addAll(tmp.triangulate());
        }
        return res;
    }

    public void resize(){
        float max = 1;
        for(Obj tmp: parts) {
            for (Vector3f vec : tmp.getPoints()) {
                for (float coord : vec.get()) {
                    if (Math.abs(coord) > max) {
                        max = Math.abs(coord);
                    }
                }
            }
        }
        scale(max);
    }

    public void scale(float max){
        for(Obj tmp: parts) {
            tmp.scale(max);
        }
    }
}
