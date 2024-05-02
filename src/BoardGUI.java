import java.awt.Color;
import java.awt.Paint;
import java.util.LinkedList;
import java.util.List;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle; 

public class BoardGUI extends GraphicsGroup {
    private final int rows;
    private final int cols;
    private final double boxSize;
    private final double dotDiameter;
    private final Color linkColorWhenNotSelected;
    private final Color linkColorWhenHover;
    private final Color dotColor;
    private final double linkThickness;

    private final HorizontalEdge[][] horizontalLinks;
    private final VerticalEdge[][] verticalLinks;
    private List<Edge> currentlyHoveredLinks;

    public BoardGUI(int rows, int cols, 
                 double boxSize, double dotDiameter,
                 Color linkColorWhenNotSelected, Color linkColorWhenHover,
                 Color dotColor,
                 double linkThinkness) {
        this.rows = rows;
        this.cols = cols;
        this.boxSize = boxSize;
        this.dotDiameter = dotDiameter;
        this.linkColorWhenNotSelected = linkColorWhenNotSelected;
        this.linkColorWhenHover = linkColorWhenHover;
        this.dotColor = dotColor;
        this.linkThickness = linkThinkness;

        this.horizontalLinks = new HorizontalEdge[rows+1][cols+1];
        this.verticalLinks = new VerticalEdge[rows+1][cols+1];
        this.currentlyHoveredLinks = new LinkedList<>();

        this.addHorizontalEdges();
        this.addVerticalEdges();
        this.addDots();
    }

    public void highlightIfHoveredEdge(Point mousePosition) {
        GraphicsObject obj = this.getElementAt(mousePosition);

        if(obj instanceof Edge) {
            Edge edge = (Edge) obj;
            if(edge.isClicked()) return;
            if(edge.isHovered()) return;
            this.resetHoveredEdges();
            edge.hovered();
            this.currentlyHoveredLinks.add(edge);
        }
        else {
            if(this.currentlyHoveredLinks != null) {
                this.resetHoveredEdges();
            }
        }
    }

    public Edge highlightIfClickedEdge(Point mousePosition, Color color) {
        GraphicsObject obj = this.getElementAt(mousePosition);

        if(!(obj instanceof Edge)) return null;
        
        Edge edge = (Edge) obj;
        if(edge.isHovered()) {
            edge.setColor(color);
            this.currentlyHoveredLinks.remove(edge);
            this.resetHoveredEdges();
            highlightBoxIf(edge, color);
            return edge;
        }

        return null;
    }

    public void highlightBoxIf(Edge justClickedEdge, Color color) {
        int col = justClickedEdge.column0;
        int row = justClickedEdge.row0;

        if(justClickedEdge instanceof VerticalEdge) {
            // check box on left
            if(col < cols) {
                highlightBoxIf(col, row, color);
            }

            // check box on right
            if(col > 0) {
                highlightBoxIf(col-1, row, color);
            }
        }
        else if(justClickedEdge instanceof HorizontalEdge) {
            // check box on top
            if(row > 0) {
                highlightBoxIf(col, row-1, color);
            }

            // check box on bottom
            if(row < rows) {
                highlightBoxIf(col, row, color);
            }
        }
    }

    private void highlightBoxIf(int column, int row, Color color) {
        if(this.verticalLinks[row][column].isClicked() &&
            this.verticalLinks[row][column+1].isClicked() &&
            this.horizontalLinks[row][column].isClicked() &&
            this.horizontalLinks[row+1][column].isClicked()) {
            Box box = new Box(row, column, color);
            this.add(box);
        }
    }

    private void resetHoveredEdges() {
        for(Edge e : this.currentlyHoveredLinks) {
            e.setUnclickedColor();
        }
        this.currentlyHoveredLinks.clear();
    }

    public boolean isEdgeUnclicked(Edge edge) {
        return edge.getFillColor() == this.linkColorWhenNotSelected;
    }

    private void addDots() {
        for(int row=0; row<=this.rows; row++) {
            for(int col=0; col<=this.cols; col++) {
                Dot dot = new Dot(row, col);
                this.add(dot);
            }
        }
    }

    private void addHorizontalEdges() {
        for(int row=0; row<=this.rows; row++) {
            for(int col=0; col<this.cols; col++) {
                HorizontalEdge horizontalEdge = new HorizontalEdge(row, col);
                this.add(horizontalEdge);

                this.horizontalLinks[row][col] = horizontalEdge;
            }
        }
    }

    private void addVerticalEdges() {
        for(int col=0; col<=this.cols; col++) {
            for(int row=0; row<this.rows; row++) {
                VerticalEdge verticalEdge = new VerticalEdge(row, col);
                this.add(verticalEdge);

                this.verticalLinks[row][col] = verticalEdge;
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
        public Box(int row, int column, Color color) {
            super(column*boxSize+ dotDiameter/2, row*boxSize + dotDiameter/2, boxSize, boxSize);
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
        // private int row1;
        // private int column1;

        public Edge(double x, double y, double width, double height) {
            super(x, y, width, height);
            this.setFilled(true);
            this.setUnclickedColor();
        }

        public boolean isClicked() {
            if(this.getColor() != linkColorWhenNotSelected) {
                return true;
            }
            return false;
        }

        public void setUnclickedColor() {
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
            // super.row1 = row;
            // super.column1 = column+1;

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
            // super.row1 = row+1;
            // super.column1 = column;
        }
    }
}