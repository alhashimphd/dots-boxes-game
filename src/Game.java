import edu.macalester.graphics.CanvasWindow;

public class Game {
    public static void main(String[] args) {
        CanvasWindow canvas = new CanvasWindow(null, 500, 500);
        
        Board b = new Board(3, 3, 100, 10);
        canvas.add(b.getBoard(), 0, 0);
    }
}
