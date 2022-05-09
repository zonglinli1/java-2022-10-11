
import java.util.*;
import java.util.function.BiConsumer;

public class Assignment3 {
    public static List<Integer> getList(){
        List<Integer> listA = Arrays.asList(1,2,3,4,10);
        Map<String, String> mapB = new HashMap<>();
        mapB.put("a", "1");
        mapB.put("b", "2");
        mapB.put("c", "10");
        List<Integer> list = new LinkedList<>();
        for(Integer elem : listA){
            if(!mapB.containsValue(elem.toString())){
                list.add(elem);
            }
        }
        return list;
    }

    public static void main(String[] args) {

        Shape circle = new Circle(2);
        
        Shape rectangle = new Rectangle(3,5);
        Shape square = new Square(4);

        // calculate areas:
        System.out.format("area of circle: %f\n", circle.getArea());
        System.out.format("area of rectangle: %f\n", rectangle.getArea());
        System.out.format("area of  square: %f\n", square.getArea());

        BiConsumer<Shape, Shape> cmp = (a, b) -> System.out.format("%f compare to %f: %d\n", a.getArea(), b.getArea(), a.compareTo(b));

        //compare using area
        cmp.accept(circle, circle);
        cmp.accept(circle, rectangle);
        cmp.accept(circle, square);

        cmp.accept(rectangle, rectangle);
        cmp.accept(rectangle, circle);
        cmp.accept(rectangle, square);

        cmp.accept(square, square);
        cmp.accept(square, rectangle);
        cmp.accept(square, circle);
    }
}

interface Shape extends Comparable<Shape>{
    double getArea();
}

class Circle implements Shape{
    private final double radius;

    public double getRadius() {
        return radius;
    }

    public Circle(double radius){
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return radius * radius * Math.PI;
    }

    @Override
    public int compareTo(Shape o) {
        Double selfArea = this.getArea();
        Double oArea = o.getArea();
        return selfArea.compareTo(oArea);
    }
}

class Rectangle implements Shape{
    private final double width;
    private final double length;

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public Rectangle(double width, double length){
        this.width = width;
        this.length = length;
    }
    @Override
    public double getArea() {
        return this.width * this.length;
    }

    @Override
    public int compareTo(Shape o) {
        Double selfArea = this.getArea();
        Double oArea = o.getArea();
        return selfArea.compareTo(oArea);
    }
}

class Square implements Shape{
    private final double side;

    public Square(double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    @Override
    public double getArea() {
        return side * side;
    }

    @Override
    public int compareTo(Shape o) {
        Double selfArea = this.getArea();
        Double oArea = o.getArea();
        return selfArea.compareTo(oArea);
    }
}
