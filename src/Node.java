import com.sun.javafx.geom.Point2D;

public class Node {

    public Node parent;
    public Point2D point;
    public double distance;

    public double helper = 0;

    public Node(Node parent, Point2D point){
        this.parent = parent;
        this.point = point;

        this.distance = parent.distance + Math.sqrt((parent.point.x-point.x)*(parent.point.x-point.x) +
                (parent.point.y-point.y)*(parent.point.y-point.y));
    }

    public Node(Point2D point){
        this.point = point;
        this.parent = null;
        this.distance = 0;
    }

}

