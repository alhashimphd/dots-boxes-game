import java.awt.Color;
import java.awt.Paint;
import java.util.LinkedList;
import java.util.List;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle; 

public class Board extends GraphicsGroup {
    private final int numRows;
    private final int numColumns;
    private final double boxSize;
    private final double dotDiameter;
    private final Color edgeColorWhenNotSelected;
    private final Color edgeColorWhenHover;
    private final Color dotColor;
    private final double edgeThickness;

    private final HorizontalEdge[][] horizontalEdges;
    private final VerticalEdge[][] verticalEdges;
    private List<Edge> currentlyHoveredEdges;

    public Board(int numRows, int numColumns, 
                 double boxSize, double dotDiameter,
                 Color edgeColorWhenNotSelected, Color edgeColorWhenHover,
                 Color dotColor,
                 double edgeThinkness) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.boxSize = boxSize;
        this.dotDiameter = dotDiameter;
        this.edgeColorWhenNotSelected = edgeColorWhenNotSelected;
        this.edgeColorWhenHover = edgeColorWhenHover;
        this.dotColor = dotColor;
        this.edgeThickness = edgeThinkness;

        this.horizontalEdges = new HorizontalEdge[numRows+1][numColumns+1];
        this.verticalEdges = new VerticalEdge[numRows+1][numColumns+1];
        this.currentlyHoveredEdges = new LinkedList<>();

        this.addHorizontalEdges();
        this.addVerticalEdges();
        this.addDots();
    }

    public void highlightHoveredEdge(Point mousePosition) {
        GraphicsObject obj = this.getElementAt(mousePosition);

        if(obj instanceof Edge) {
            Edge edge = (Edge) obj;
            if(edge.isClicked()) return;
            if(edge.isHovered()) return;
            this.resetHoveredEdges();
            edge.hovered();
            this.currentlyHoveredEdges.add(edge);
        }
        else {
            if(this.currentlyHoveredEdges != null) {
                this.resetHoveredEdges();
            }
        }
    }

    public Edge highlightClickedEdge(Point mousePosition, Color color) {
        GraphicsObject obj = this.getElementAt(mousePosition);

        if(!(obj instanceof Edge)) return null;
        
        Edge edge = (Edge) obj;
        if(edge.isHovered()) {
            edge.setColor(color);
            this.currentlyHoveredEdges.remove(edge);
            this.resetHoveredEdges();
            highlightBox(edge);
            return edge;
        }

        return null;
    }

    public void highlightBox(Edge justClickedEdge) {
        if(justClickedEdge instanceof VerticalEdge) {
            // check box on right
            if(this.verticalEdges[justClickedEdge.row0][justClickedEdge.column0+1].isClicked() &&
            this.horizontalEdges[justClickedEdge.row0][justClickedEdge.column0].isClicked() &&
            this.horizontalEdges[justClickedEdge.row0+1][justClickedEdge.column0].isClicked()) {
                Box box = new Box(justClickedEdge.row0, justClickedEdge.column0);
                this.add(box);
            }

            // check box on the left
        }
        else if(justClickedEdge instanceof HorizontalEdge) {
            // check box on top


            // check box on bottom

        }
    }

    private void resetHoveredEdges() {
        for(Edge e : this.currentlyHoveredEdges) {
            e.setUnclickedColor();
        }
        this.currentlyHoveredEdges.clear();
    }

    public boolean isEdgeUnclicked(Edge edge) {
        return edge.getFillColor() == this.edgeColorWhenNotSelected;
    }

    private void addDots() {
        for(int row=0; row<=this.numRows; row++) {
            for(int col=0; col<=this.numColumns; col++) {
                Dot dot = new Dot(row, col);
                this.add(dot);
            }
        }
    }

    private void addHorizontalEdges() {
        for(int row=0; row<=this.numRows; row++) {
            for(int col=0; col<this.numColumns; col++) {
                HorizontalEdge horizontalEdge = new HorizontalEdge(row, col);
                this.add(horizontalEdge);

                this.horizontalEdges[row][col] = horizontalEdge;
            }
        }
    }

    private void addVerticalEdges() {
        for(int col=0; col<=this.numColumns; col++) {
            for(int row=0; row<this.numRows; row++) {
                VerticalEdge verticalEdge = new VerticalEdge(row, col);
                this.add(verticalEdge);

                this.verticalEdges[row][col] = verticalEdge;
            }
        }
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
        public Box(int row, int column) {
            super(column*boxSize+ dotDiameter/2, row*boxSize + dotDiameter/2, boxSize, boxSize);
        }
    }

    abstract class Edge extends Rectangle {
        private int row0;
        private int column0;
        // private int row1;
        // private int column1;

        public Edge(double x, double y, double width, double height) {
            super(x, y, width, height);
            this.setFilled(true);
            this.setUnclickedColor();
        }

        public boolean isClicked() {
            if(this.getColor() != edgeColorWhenNotSelected) {
                return true;
            }
            return false;
        }

        public void setUnclickedColor() {
            this.setColor(edgeColorWhenNotSelected);
        }

        public void hovered() {
            this.setColor(edgeColorWhenHover);
        }

        public boolean isHovered() {
            return this.getFillColor() == edgeColorWhenHover;
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
                  row*boxSize + dotDiameter/2 - edgeThickness/2, 
                  boxSize - dotDiameter, 
                  edgeThickness);
            super.row0 = row;
            super.column0 = column;
            // super.row1 = row;
            // super.column1 = column+1;

        }
    }

    class VerticalEdge extends Edge {
        public VerticalEdge(int row, int column) {
            super(column*boxSize + dotDiameter/2 - edgeThickness/2, 
                  row*boxSize + dotDiameter, 
                  edgeThickness,
                  boxSize - dotDiameter);
            super.row0 = row;
            super.column0 = column;
            // super.row1 = row+1;
            // super.column1 = column;
        }
    }
}