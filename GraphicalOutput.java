import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

public class GraphicalOutput extends Canvas {

    // Variabel wajib saat mengekstensi Canvas
    private static final long serialVersionUID = 1L;

    // Variabel global lainnya
    private double screenWidth;
    private double screenHeight;
    private int[][] points;
    private ArrayList<int[]> lines;
    private int zeroX;
    private int zeroY;

    // Konstruktor
    GraphicalOutput(Dimension screenSize) {
        this.screenHeight = screenSize.getHeight();
        this.screenWidth = screenSize.getWidth();
        this.setSize(screenSize);

        this.zeroX = Constants.OFFSET;
        this.zeroY = Constants.OFFSET;
    }

    // Prosedur pengaturan daftar titik
    public void setPoints(int[][] pointList) {
        this.points = pointList;
    }

    // Prosedur pengaturan daftar garis
    public void setLines(ArrayList<int[]> lineList) {
        this.lines = lineList;
    }

    // Prosedur penggambaran
    public void paint(Graphics g) {

        // Kode penggambaran dua sumbu utama
        g.drawLine(this.zeroX, this.zeroY, this.zeroX, (int)this.screenHeight-Constants.OFFSET);
        g.drawLine(this.zeroX, this.zeroY, (int)this.screenWidth-Constants.OFFSET, this.zeroY);
        
        // Kode penggambaran garis dan angka penanda pada dua sumbu utama
        var text_offset = Constants.OFFSET * 3 / 4;
        boolean big;
        for (int y = this.zeroY+15; y < this.screenHeight-Constants.OFFSET; y += 15) {
            big = (y % 2 != 0);
            g.drawLine(this.zeroX, y, this.zeroX+(big?5:3), y);
            if (big) {
                g.drawString(Integer.toString(y), this.zeroX-text_offset, y);
            }
        }
        text_offset /= 3;
        for (int x = this.zeroX+15; x < this.screenWidth-Constants.OFFSET; x += 15) {
            big = (x % 2 != 0);
            g.drawLine(x, this.zeroY, x, this.zeroY+(big?5:3));
            if (big) {
                g.drawString(Integer.toString(x), x, this.zeroY-text_offset);
            }
        }

        // Kode penggambaran titik dan nomor titik
        g.setFont(g.getFont().deriveFont(Font.BOLD));
        for (int i = 0; i < this.points.length; i++) {
            g.setColor(Color.BLUE);
            g.drawString((i+1)+"", this.points[i][0]+Constants.TEXT_OFFSET, this.points[i][1]+Constants.TEXT_OFFSET);
            g.setColor(Color.BLACK);
            g.drawOval(this.points[i][0], this.points[i][1], Constants.POINT_SIZE, Constants.POINT_SIZE);
        }

        // Kode penggambaran garis dan lingkaran penanda titik bagian dari convex hull
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