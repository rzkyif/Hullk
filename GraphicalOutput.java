import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

public class GraphicalOutput extends Canvas {

    private static final long serialVersionUID = 1L;

    private double screenWidth;
    private double screenHeight;
    private int[][] points;
    private ArrayList<int[]> lines;
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
        this.points = pointList;
    }

    public void setLines(ArrayList<int[]> lineList) {
        this.lines = lineList;
    }

    public void paint(Graphics g) {
        g.drawLine(this.zeroX, this.zeroY, this.zeroX, Constants.OFFSET);
        g.drawLine(this.zeroX, this.zeroY, (int)this.screenWidth-Constants.OFFSET, this.zeroY);
        
        for (int y = this.zeroY+15; y > Constants.OFFSET; y -= 15) {
            g.drawLine(this.zeroX, y, this.zeroX+5, y);
        }
        for (int x = this.zeroX+15; x < this.screenWidth-Constants.OFFSET; x += 15) {
            g.drawLine(x, this.zeroY, x, this.zeroY-5);
        }

        g.setFont(g.getFont().deriveFont(Font.BOLD));
        for (int i = 0; i < this.points.length; i++) {
            g.setColor(Color.BLUE);
            g.drawString(i+"", this.points[i][0]+Constants.TEXT_OFFSET, this.points[i][1]+Constants.TEXT_OFFSET);
            g.setColor(Color.BLACK);
            g.drawOval(this.points[i][0], this.points[i][1], Constants.POINT_SIZE, Constants.POINT_SIZE);
        }

        var offset = Constants.POINT_SIZE / 2;
        var offset_mark = -(Constants.MARK_POINT_SIZE - Constants.POINT_SIZE)/2;
        for (int[] line : this.lines) {
            g.setColor(Color.RED);
            g.drawOval(points[line[0]][0]+offset_mark, points[line[0]][1]+offset_mark, Constants.MARK_POINT_SIZE, Constants.MARK_POINT_SIZE);
            g.drawOval(points[line[1]][0]+offset_mark, points[line[1]][1]+offset_mark, Constants.MARK_POINT_SIZE, Constants.MARK_POINT_SIZE);
            g.drawLine(points[line[0]][0]+offset, points[line[0]][1]+offset, points[line[1]][0]+offset, points[line[1]][1]+offset);
        }
    }

}