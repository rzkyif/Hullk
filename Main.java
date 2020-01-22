import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.lang.System;

public class Main {

    // Variabel global
    private static JFrame frame;
    private static GraphicalOutput go;
    private static Scanner s;
    private static Random r;
    private static Dimension screenSize;

    public static void main(String[] args) {

        // Deklarasi variabel global
        frame = new JFrame("Convex Hull yang Ditemukan");
        screenSize = new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        go = new GraphicalOutput(screenSize);
        s = new Scanner(System.in);
        r = new Random();

        // Sistem masukan
        System.out.print("Masukkan nilai N: ");
        int n = 0;
        while (n < 3) {
            n = s.nextInt();
            if (n < 3) {
                System.out.println("Masukan tidak valid!");
                System.out.print("Masukkan nilai N: ");
            }
        }

        // Timer dimulai: Waktu keseluruhan
        var tr = System.nanoTime();

        // Timer dimulai: Pembangkitan titik
        System.out.println("\nMemulai kode pembangkitan titik...");
        var t = System.nanoTime();

        // Kode pembangkitan titik
        var pointList = new int[n][2];
        int minX = 2 * Constants.OFFSET;
        int widthX = (int)screenSize.getWidth() - 2 * minX;
        int minY = minX;
        int heightY = (int)screenSize.getHeight() - 2 * minY;
        System.out.println("Titik hasil:");
        for (int i = 0; i < n; i++) {
            pointList[i][0] = r.nextInt(widthX)+minX;
            pointList[i][1] = r.nextInt(heightY)+minY;
            System.out.printf(" %d. (%d, %d)%n", i+1, pointList[i][0], pointList[i][1]);
        }
        go.setPoints(pointList);

        // Timer selesai: Pembangkitan titik
        var d = System.nanoTime() - t;
        System.out.printf("Selesai membangkitkan titik! (%dns/%dms)%n", d, d/1000000);

        // Timer dimulai: Pencarian convex hull
        System.out.println("\nMemulai kode pencarian convex hull...");
        t = System.nanoTime();

        // Kode pencarian convex hull
        var lineList = new ArrayList<int[]>();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                var a = pointList[j][1]-pointList[i][1];
                var b = pointList[i][0]-pointList[j][0];
                var c = pointList[i][0]*pointList[j][1]-pointList[i][1]*pointList[j][0];
                
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

        // Timer selesai: Pencarian convex hull
        d = System.nanoTime() - t;
        System.out.println("Selesai mencari convex hull! (" + d + "ns/" + d/1000000 + "ms)\n");

        // Timer dimulai: Pencetakan daftar garis
        System.out.println("Mencetak daftar garis...");
        t = System.nanoTime();

        // Kode pengurutan dan pencetakan daftar garis
        System.out.print("Daftar garis: {");
        int l = lineList.size();
        int current = 0;
        boolean normal = true;
        var checkList = new HashSet<>();
        for (int count = 0; count < l; count++) {
            System.out.printf("(%d, %d), ", lineList.get(current)[normal?0:1]+1, lineList.get(current)[normal?1:0]+1);
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

        // Timer selesai: Pencetakan daftar garis
        d = System.nanoTime() - t;
        System.out.println("Berhasil menemukan convex hull! (" + d + "ns/" + d/1000000 + "ms)\n");

        // Timer selesai: Waktu keseluruhan
        d = System.nanoTime() - tr;
        System.out.println("Total waktu yang digunakan sejak input nilai N: "+ d +"ns/"+ d/1000000+"ms");

        // Kode penampilan output grafis
        frame.add(go);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}