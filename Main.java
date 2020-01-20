import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.lang.System;

public class Main {

    private static JFrame frame;
    private static GraphicalOutput go;
    private static Scanner s;
    private static Random r;
    private static Dimension screenSize;

    public static void main(String[] args) {
        frame = new JFrame("Convex Hull yang Ditemukan");
        screenSize = new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        go = new GraphicalOutput(screenSize);
        s = new Scanner(System.in);
        r = new Random();

        System.out.print("Masukkan nilai N: ");
        int n = 0;
        while (n < 3) {
            n = s.nextInt();
            if (n < 3) {
                System.out.println("Masukan tidak valid!");
                System.out.print("Masukkan nilai N: ");
            }
        }

        System.out.println("\nMemulai kode pencarian convex hull... (0ns)");
        var t = System.nanoTime();

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

        var lineList = new ArrayList<int[]>();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                var point1 = pointList[i];
                var point2 = pointList[j];

                var a = point2[1]-point1[1];
                var b = point1[0]-point2[0];
                var c = point1[0]*point2[1]-point1[1]*point2[0];
                
                var result = true;

                var upside = 0;
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) continue;

                    var cc = a*pointList[k][0]+b*pointList[k][1];
                    if (cc < c) {
                        if (upside == -1) {
                            continue;
                        } else if (upside == 0) {
                            upside = -1;
                        } else {
                            result = false;
                            break;
                        }
                    } else if (cc > c) {
                        if (upside == -1) {
                            result = false;
                            break;
                        } else if (upside == 0) {
                            upside = 1;
                        } else {
                            continue;
                        }
                    }
                }

                if (result) {
                    int[] p = {i, j};
                    lineList.add(p);
                }
            }
        }
        go.setLines(lineList);
        int l = lineList.size();

        var d1 = System.nanoTime() - t;
        System.out.println("Selesai mencari convex hull! (" + d1 + "ns)\n");
        System.out.println("Mencetak daftar garis... (" + d1 + "ns + 0ns)");
        t = System.nanoTime();

        System.out.print("Daftar garis secara acak: {");
        for (int i = 0; i < l; i++) {
            System.out.printf("(%d, %d)", lineList.get(i)[0], lineList.get(i)[1]);
            if (i < l-1) System.out.print(", ");
        }
        System.out.print("}\n");

        System.out.print("Daftar garis secara terurut: {");
        int current = 0;
        boolean normal = true;
        var checkList = new HashSet<>();
        for (int count = 0; count < l; count++) {
            System.out.printf("(%d, %d), ", lineList.get(current)[normal?0:1], lineList.get(current)[normal?1:0]);
            checkList.add(current);

            for (int search = 0; search < l; search++) {
                if (checkList.contains(search)) continue;

                if (lineList.get(current)[normal ? 1 : 0] == lineList.get(search)[0]) {
                    current = search;
                    normal = true;
                    break;
                } else if (lineList.get(current)[normal ? 1 : 0] == lineList.get(search)[1]) {
                    current = search;
                    normal = false;
                    break;
                }
            }
        }
        System.out.printf("(%d, %d)}\n", lineList.get(0)[0], lineList.get(0)[1]);

        var d2 = System.nanoTime() - t;
        System.out.println("Berhasil menemukan convex hull! (" + d1 + "ns + " + d2 + "ns)\n");
        System.out.println("Total waktu yang digunakan: "+ (d1 + d2) +"ns = "+(d1+d2)/1000000+"ms");

        frame.add(go);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}