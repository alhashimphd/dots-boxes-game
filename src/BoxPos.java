public final class BoxPos {
    final int topLeftX;
    final int topLeftY;
    
    public BoxPos(int topLeftX, int topLeftY) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
    }

    @Override
    public boolean equals(Object obj) {
        BoxPos boxPos = (BoxPos) obj;
        return this.topLeftX == boxPos.topLeftX && this.topLeftY == boxPos.topLeftY;
    }

    @Override
    public String toString() {
        return "(" + topLeftX + "," + topLeftY + ")";
    }
}
