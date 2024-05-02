import java.util.HashSet;
import java.util.Set;

public class Board {
    private final String[][] boxesLabels;
    private final String[][] verticalLinesLabels;
    private final String[][] horizontalLinesLabels;

    
    public Board(int rows, int cols) {
        boxesLabels = new String[rows][cols];
        verticalLinesLabels = new String[rows][cols+1];
        horizontalLinesLabels = new String[rows+1][cols];
    }

    public int getNumRows() {
        return boxesLabels.length;
    }

    public int getNumCols() {
        return boxesLabels[0].length;
    }


    public Set<BoxPos> addVerticalLine(int topXPos, int topYPos, String label) {
        isPosValid(topXPos, topYPos, verticalLinesLabels);
        isLabelValid(label);
        canVerticalLineBeLabelled(topXPos, topYPos);
        
        verticalLinesLabels[topYPos][topXPos] = label;

        Set<BoxPos> completedBoxes = new HashSet<>();
        int boxTopLeftX;

        if(topXPos != verticalLinesLabels[0].length-1) {
            boxTopLeftX = topXPos;
            if(labelBoxIfPossible(boxTopLeftX, topYPos, label)) {
                completedBoxes.add(new BoxPos(boxTopLeftX, topYPos));
            }
        }

        if(topXPos != 0) {
            boxTopLeftX = topXPos-1;
            if(labelBoxIfPossible(boxTopLeftX, topYPos, label)) {
                completedBoxes.add(new BoxPos(boxTopLeftX, topYPos));
            }
        }

        return completedBoxes;
    }

    
    public Set<BoxPos> addHorizontalLine(int leftXPos, int leftYPos, String label) {
        isPosValid(leftXPos, leftYPos, horizontalLinesLabels);
        isLabelValid(label);
        canHorizontalLineBeLabelled(leftXPos, leftYPos);

        horizontalLinesLabels[leftYPos][leftXPos] = label;
        
        Set<BoxPos> completedBoxes = new HashSet<>();
        int boxTopLeftY;

        if(leftYPos != 0) {
            boxTopLeftY = leftYPos-1;
            if(labelBoxIfPossible(leftXPos, boxTopLeftY, label)) {
                completedBoxes.add(new BoxPos(leftXPos, boxTopLeftY));
            }
        }

        if(leftYPos != horizontalLinesLabels.length-1) {
            boxTopLeftY = leftYPos;
            if(labelBoxIfPossible(leftXPos, boxTopLeftY, label)) {
                completedBoxes.add(new BoxPos(leftXPos, boxTopLeftY));
            }
        }

        return completedBoxes;
    }        
    
    private void isPosValid(int posX, int posY, Object[][] array) {
        String xPositionErrorMsg = "X position must be ";
        if(posX < 0) 
            throw new IllegalArgumentException(xPositionErrorMsg + " >= 0");

        int maxX = array[0].length; 
        if(posX >= maxX) 
            throw new IllegalArgumentException(xPositionErrorMsg + " < " + maxX);

        String yPositionErrorMsg = "Y position must be ";
        if(posY < 0)
            throw new IllegalArgumentException(yPositionErrorMsg + " >= 0");
        
        int maxY = array.length;
        if(posY >= maxY)
            throw new IllegalArgumentException(yPositionErrorMsg + " < " + maxY);
    }


    public void canVerticalLineBeLabelled(int topXPos, int topYPos) {
        if(verticalLinesLabels[topYPos][topXPos] != null) {
            throw new IllegalStateException("Vertical edge starting at (" + topXPos + "," + topYPos + ") is already labelled");
        }
    }

    public void canHorizontalLineBeLabelled(int leftXPos, int leftYPos) {
        if(horizontalLinesLabels[leftYPos][leftXPos] != null) {
            throw new IllegalStateException("Horizontal edge starting at (" + leftXPos + "," + leftYPos + ") is already labelled");
        }
    }

    private void isLabelValid(String label) {
        if(label.strip().length() == 0) {
            throw new IllegalArgumentException("'" + label + "'' is invalid label, must be non-empty");
        }
    }

    private boolean labelBoxIfPossible(int topLeftXPos, int topLeftYPos, String label) {
        if(boxesLabels[topLeftYPos][topLeftXPos] != null)
            throw new IllegalStateException("Box at position (" + topLeftXPos + "," + topLeftYPos + ") is already labelled");

        // check the 4 sides of the box
        if(verticalLinesLabels[topLeftYPos][topLeftXPos] != null && 
           verticalLinesLabels[topLeftYPos][topLeftXPos+1] != null &&
           horizontalLinesLabels[topLeftYPos][topLeftXPos] != null &&
           horizontalLinesLabels[topLeftYPos+1][topLeftXPos] != null) {
            boxesLabels[topLeftYPos][topLeftXPos] = label;
            return true;
        }
        
        return false;
    }
}
