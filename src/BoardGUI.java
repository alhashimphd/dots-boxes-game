import java.awt.Color;
import java.awt.Paint;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle; 

public class BoardGUI extends GraphicsGroup {
    private final double boxSize;
    private final double dotDiameter;
    private final Color linkColorWhenNotSelected;
    private final Color linkColorWhenHover;
    private final Color dotColor;
    private final double linkThickness;

    private final GraphicsGroup dots = new GraphicsGroup();
    private final GraphicsGroup edges = new GraphicsGroup();
    private final GraphicsGroup boxes = new GraphicsGroup();
    private final Board board;
    private List<Edge> currentlyHoveredLinks;

    

    public BoardGUI(int rows, int cols, 
                 double boxSize, double dotDiameter,
                 Color linkColorWhenNotSelected, Color linkColorWhenHover,
                 Color dotColor,
                 double linkThinknessPropotionalToDotDiameter) {
        this.boxSize = boxSize;
        this.dotDiameter = dotDiameter;
        this.linkColorWhenNotSelected = linkColorWhenNotSelected;
        this.linkColorWhenHover = linkColorWhenHover;
        this.dotColor = dotColor;
        this.linkThickness = dotDiameter*linkThinknessPropotionalToDotDiameter;

        add(boxes);
        add(edges);
        add(dots);
        board = new Board(rows, cols);
        currentlyHoveredLinks = new LinkedList<>();

        addHorizontalEdges();
        addVerticalEdges();
        addDots();
    }


    private void addHorizontalEdges() {
        for(int row=0; row<=board.getNumRows(); row++)
            for(int col=0; col<board.getNumCols(); col++)
                edges.add(new HorizontalEdge(row, col));
    }


    private void addVerticalEdges() {
        for(int col=0; col<=board.getNumCols(); col++)
            for(int row=0; row<board.getNumRows(); row++)
                edges.add(new VerticalEdge(row, col));
    }


    private void addDots() {
        for(int row=0; row<=board.getNumRows(); row++)
            for(int col=0; col<=board.getNumCols(); col++)
                dots.add(new Dot(row, col));
    }


    public void onHoverEdge(Point mousePosition) {
        GraphicsObject obj = getElementAt(mousePosition);

        if(obj instanceof Edge) {
            Edge edge = (Edge) obj;
            if(edge.isClicked()) return;
            if(edge.isHovered()) return;
            resetHoveredEdges();
            edge.hovered();
            currentlyHoveredLinks.add(edge);
            return;
        }

        resetHoveredEdges();
    }

    public Edge onClickEdge(Point mousePosition, Color color) {
        GraphicsObject obj = getElementAt(mousePosition);

        if(!(obj instanceof Edge)) return null;
        
        Edge edge = (Edge) obj;
        if(edge.isHovered()) {
            edge.setColor(color);
            currentlyHoveredLinks.remove(edge);
            resetHoveredEdges();
            addBoxesIf(edge, color);
            return edge;
        }

        return null;
    }

    public void addBoxesIf(Edge justClickedEdge, Color color) {
        int col = justClickedEdge.column0;
        int row = justClickedEdge.row0;

        Set<BoxPos> labelledBoxes = new HashSet<>();
        if(justClickedEdge instanceof VerticalEdge) {
            labelledBoxes = board.addVerticalLine(col, row, color.toString());
        }
        else if(justClickedEdge instanceof HorizontalEdge) {
            labelledBoxes = board.addHorizontalLine(col, row, color.toString());
        }

        for(BoxPos boxPos : labelledBoxes) {
            boxes.add(new Box(boxPos.topLeftX, boxPos.topLeftY, color));
        }
    }

    private void resetHoveredEdges() {
        for(Edge e : currentlyHoveredLinks) {
            e.changeToUnclickedColor();
        }
        this.currentlyHoveredLinks.clear();
    }

    public boolean isEdgeUnclicked(Edge edge) {
        return edge.getFillColor() == this.linkColorWhenNotSelected;
    }

    class Dot extends Ellipse {
        public Dot(int row, int column) {
            super(row*boxSize, column*boxSize, dotDiameter, dotDiameter);
            this.setFilled(true);
            this.setColor(dotColor);
        }

        public void setColor(Color color) {
            this.setFillColor(color);
            this.setStrokeColor(color);
        }
    }

    class Box extends Rectangle {
        public Box(int column, int row, Color color) {
            super(column*boxSize + dotDiameter/2, row*boxSize + dotDiameter/2, boxSize, boxSize);
            this.setFilled(true);
            this.setColor(color);
        }

        public void setColor(Color color) {
            this.setFillColor(color);
            this.setStrokeColor(color);
        }
    }

    abstract class Edge extends Rectangle {
        private int row0;
        private int column0;

        public Edge(double x, double y, double width, double height) {
            super(x, y, width, height);
            this.setFilled(true);
            this.changeToUnclickedColor();
        }

        public boolean isClicked() {
            if(this.getColor() != linkColorWhenNotSelected) {
                return true;
            }
            return false;
        }

        public void changeToUnclickedColor() {
            this.setColor(linkColorWhenNotSelected);
        }

        public void hovered() {
            this.setColor(linkColorWhenHover);
        }

        public boolean isHovered() {
            return this.getFillColor() == linkColorWhenHover;
        }

        private void setColor(Color color) {
            this.setFillColor(color);
            this.setStrokeColor(color);
        }

        public Paint getColor() {
            return this.getFillColor();
        }

        @Override
        public String toString() {
            String msg = "";
            if(this instanceof VerticalEdge) {
                msg = "Vertical";
            }
            else if(this instanceof HorizontalEdge) {
                msg = "Horizontal";
            }

            return msg + " edge stars at (" + row0 + "," + column0 +")";
        }
    }

    class HorizontalEdge extends Edge {
        public HorizontalEdge(int row, int column) {
            super(column*boxSize + dotDiameter, 
                  row*boxSize + dotDiameter/2 - linkThickness/2, 
                  boxSize - dotDiameter, 
                  linkThickness);
            super.row0 = row;
            super.column0 = column;
        }
    }

    class VerticalEdge extends Edge {
        public VerticalEdge(int row, int column) {
            super(column*boxSize + dotDiameter/2 - linkThickness/2, 
                  row*boxSize + dotDiameter, 
                  linkThickness,
                  boxSize - dotDiameter);
            super.row0 = row;
            super.column0 = column;
        }
    }
}