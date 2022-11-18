package Java.Component;

public class Point {

    public double X;
    public double Y;

    public Point(double x, double y) {
        this.X = x;
        this.Y = y;
    }

    public Point(Point toClone) {
        this.X = toClone.X;
        this.Y = toClone.Y;
    }

    @Override
    public String toString() {
        String str = "";

        str += "Position [x: " + X + " ,Y: " + Y + "]";

        return str;
    }
}
