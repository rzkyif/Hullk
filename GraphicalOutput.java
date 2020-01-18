import java.awt.*;

public class GraphicalOutput extends Canvas {

    private double screenWidth;
    private double screenHeight;
    private int[][] points;
    private int zeroX;
    private int zeroY;

    GraphicalOutput(Dimension screenSize) {
        this.screenHeight = screenSize.getHeight();
        this.screenWidth = screenSize.getWidth();
        this.setSize(screenSize);

        this.zeroX = Constants.OFFSET;
        this.zeroY = (int)this.screenHeight - this.zeroX;
    }

    public void setPoints(int[][] pointList) {
        this.points = new int[pointList.length][2];
        for (int i = 0; i < pointList.length; i++) {
            this.points[i][0] = pointList[i][0];
            this.points[i][1] = pointList[i][1];
        }
    }

    public void paint(Graphics g) {
        g.drawLine(this.zeroX, this.zeroY, this.zeroX, Constants.OFFSET);
        g.drawLine(this.zeroX, this.zeroY, (int)this.screenWidth-Constants.OFFSET, this.zeroY);
        for (int y = this.zeroY+Constants.LINE_DISTANCE; y > Constants.OFFSET; y -= Constants.LINE_DISTANCE) {
            g.drawLine(this.zeroX, y, this.zeroX+Constants.LINE_LENGTH, y);
        }
        for (int x = this.zeroX+Constants.LINE_DISTANCE; x < this.screenWidth-Constants.OFFSET; x += Constants.LINE_DISTANCE) {
            g.drawLine(x, this.zeroY, x, this.zeroY-Constants.LINE_LENGTH);
        }
        for (int[] point : this.points) {
            g.drawOval(point[0], point[1], Constants.POINT_SIZE, Constants.POINT_SIZE);
        }
    }

}