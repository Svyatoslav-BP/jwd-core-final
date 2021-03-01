package com.epam.jwd.core_final.domain;

/**
 * Expected fields:
 * <p>
 * location {@link java.util.Map} - planet coordinate in the universe
 */
public class Planet extends AbstractBaseEntity{
    private Point point;

    @Override
    public String toString() {
        return this.point.toString();
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public final class Point{
        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        private int x;
        private int y;

        public Point() {
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    public Planet(String name) {
        super(name);
        point = new Point();
    }
}
