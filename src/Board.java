import java.awt.Color;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Line; 

public class Board extends GraphicsGroup {
    private final int numRows;
    private final int numColumns;
    private final double boxSize;
    private final double dotDiameter;
    private final Color EDGE_COLOR = Color.LIGHT_GRAY;
    private final double EDGE_THICKNESS;

    public Board(int numRows, int numColumns, double boxSize, double dotDiameter) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.boxSize = boxSize;
        this.dotDiameter = dotDiameter;
        this.EDGE_THICKNESS = dotDiameter/4;

        this.addHorizontalEdges();
        this.addVerticalEdges();
        this.addDots();
    }

    private void addDots() {
        for(int row=0; row<=this.numRows; row++) {
            for(int col=0; col<=this.numColumns; col++) {
                // create a dot at a particular location and add to board
                Dot dot = new Dot(row*boxSize, col*boxSize, dotDiameter);
                this.add(dot);
            }
        }
    }

    private void addHorizontalEdges() {
        for(int row=0; row<=this.numRows; row++) {
            double lineY = row*this.boxSize + this.dotDiameter/2;

            for(int col=0; col<this.numColumns; col++) {
                // create edge at particulate location and add to board
                double lineX0 = col*this.boxSize + this.dotDiameter;
                double lineX1 = (col+1)*this.boxSize;
                Line line = new Edge(lineX0, lineY, lineX1, lineY);
                this.add(line);
            }
        }
    }

    private void addVerticalEdges() {
        for(int col=0; col<=this.numColumns; col++) {
            double lineX = col*this.boxSize + this.dotDiameter/2;
            for(int row=0; row<this.numRows; row++) {
                // create edge at particulate location and add to board
                double lineY0 = row*this.boxSize + this.dotDiameter;
                double lineY1 = (row+1)*this.boxSize;
                Line line = new Edge(lineX, lineY0, lineX, lineY1);
                this.add(line);
            }
        }
    }

    private class Dot extends Ellipse {
        private Dot(double x, double y, double width, double height) {
            super(x, y, width, height);
            this.setFilled(true);
        }
        
        public Dot(double x, double y, double diameter) {
            this(x, y, diameter, diameter);
        }
    }

    private class Edge extends Line {
        public Edge(double x1, double y1, double x2, double y2) {
            super(x1, y1, x2, y2);
            this.setStrokeWidth(EDGE_THICKNESS);
            this.setStrokeColor(EDGE_COLOR);
        }
    }
}