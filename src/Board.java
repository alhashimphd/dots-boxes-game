import java.awt.Color;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle; 

public class Board extends GraphicsGroup {
    private final int numRows;
    private final int numColumns;
    private final double boxSize;
    private final double dotDiameter;
    private final Color EDGE_UNCLICKED_COLOR = Color.LIGHT_GRAY;
    private final Color EDGE_HOVER_COLOR = Color.RED;
    private final Color DOT_COLOR = Color.BLACK;
    private final double EDGE_THICKNESS;

    public Board(int numRows, int numColumns, double boxSize, double dotDiameter) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.boxSize = boxSize;
        this.dotDiameter = dotDiameter;
        this.EDGE_THICKNESS = dotDiameter/2;

        this.addHorizontalEdges();
        this.addVerticalEdges();
        this.addDots();
    }

    public boolean isEdgeUnclicked(Edge edge) {
        return edge.getFillColor() == this.EDGE_UNCLICKED_COLOR;
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
            }
        }
    }

    private void addVerticalEdges() {
        for(int col=0; col<=this.numColumns; col++) {
            for(int row=0; row<this.numRows; row++) {
                VerticalEdge verticalEdge = new VerticalEdge(row, col);
                this.add(verticalEdge);
            }
        }
    }

    class Dot extends Ellipse {
        public Dot(int row, int column) {
            super(row*boxSize, column*boxSize, dotDiameter, dotDiameter);
            this.setFilled(true);
            this.setColor(DOT_COLOR);
        }

        public void setColor(Color color) {
            this.setFillColor(color);
            this.setStrokeColor(color);
        }
    }

    abstract class Edge extends Rectangle {
        public Edge(double x, double y, double width, double height) {
            super(x, y, width, height);
            this.setFilled(true);
            this.unclicked();
        }

        public boolean isClicked() {
            if(this.getFillColor() != EDGE_UNCLICKED_COLOR &&
               this.getFillColor() != EDGE_HOVER_COLOR) {
                return true;
               }
            return false;
        }

        public void unclicked() {
            this.setColor(EDGE_UNCLICKED_COLOR);
        }

        public void hovered() {
            this.setColor(EDGE_HOVER_COLOR);
        }

        public boolean isHovered() {
            return this.getFillColor() == EDGE_HOVER_COLOR;
        }

        private void setColor(Color color) {
            this.setFillColor(color);
            this.setStrokeColor(color);
        }
    }

    class HorizontalEdge extends Edge {
        public HorizontalEdge(int row, int column) {
            super(column*boxSize + dotDiameter, 
                  row*boxSize + dotDiameter/2 - EDGE_THICKNESS/2, 
                  boxSize - dotDiameter, 
                  EDGE_THICKNESS);
        }
    }

    class VerticalEdge extends Edge {
        public VerticalEdge(int row, int column) {
            super(column*boxSize + dotDiameter/2 - EDGE_THICKNESS/2, 
                  row*boxSize + dotDiameter, 
                  EDGE_THICKNESS,
                  boxSize - dotDiameter);
        }
    }
}