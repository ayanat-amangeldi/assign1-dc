package edu.aitu.dsa.geometry;

import java.util.Arrays;
import java.util.Comparator;

public final class ClosestPair {
    private ClosestPair() {}

    public static double dist(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }

    public static double closest(Point[] pts) {
        Point[] px = pts.clone();
        Point[] py = pts.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));
        return rec(px, py, 0, px.length - 1);
    }

    private static double rec(Point[] px, Point[] py, int lo, int hi) {
        int n = hi - lo + 1;
        if (n <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = lo; i <= hi; i++)
                for (int j = i + 1; j <= hi; j++)
                    best = Math.min(best, dist(px[i], px[j]));
            return best;
        }
        int mid = (lo + hi) >>> 1;
        double midx = px[mid].x;

        Point[] pyl = new Point[mid - lo + 1];
        Point[] pyr = new Point[hi - mid];
        int il = 0, ir = 0;
        for (Point p : py) {
            if (p.x <= midx) pyl[il++] = p; else pyr[ir++] = p;
        }

        double dl = rec(px, pyl, lo, mid);
        double dr = rec(px, pyr, mid + 1, hi);
        double d = Math.min(dl, dr);

        Point[] strip = new Point[py.length];
        int s = 0;
        for (Point p : py) if (Math.abs(p.x - midx) < d) strip[s++] = p;

        for (int i = 0; i < s; i++) {
            for (int j = i + 1; j < s && (strip[j].y - strip[i].y) < d; j++) {
                d = Math.min(d, dist(strip[i], strip[j]));
            }
        }
        return d;
    }

    public static double brute(Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++)
            for (int j = i + 1; j < pts.length; j++)
                best = Math.min(best, dist(pts[i], pts[j]));
        return best;
    }
}
