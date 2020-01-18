import java.awt.Dimension;
import javax.swing.JFrame;
import java.util.Vector;
import java.util.Scanner;
import java.util.Random;

public class Main {

    private static JFrame frame;
    private static GraphicalOutput go;
    private static Scanner s;
    private static Random r;
    private static Dimension screenSize;

    public static void main(String[] args) {
        frame = new JFrame("Hullk");
        screenSize = new Dimension(250, 250);
        go = new GraphicalOutput(screenSize);
        s = new Scanner(System.in);
        r = new Random();

        System.out.print("Masukkan nilai N: ");
        int n = s.nextInt();

        var pointList = new int[n][2];
        int minX = 2 * Constants.OFFSET;
        int widthX = (int)screenSize.getWidth() - 2 * minX;
        int minY = minX;
        int heightY = (int)screenSize.getHeight() - 2 * minY;
        for (int i = 0; i < n; i++) {
            pointList[i][0] = r.nextInt(widthX)+minX;
            pointList[i][1] = r.nextInt(heightY)+minY;
        }

        go.setPoints(pointList);

        frame.add(go);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

}