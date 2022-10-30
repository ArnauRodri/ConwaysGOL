import java.util.ArrayList;
import java.util.Arrays;

public class Logic {

    static public class Dir {

        public static Dir d0 = new Dir(0);
        public static Dir d1 = new Dir(1);
        public static Dir d2 = new Dir(2);
        public static Dir d3 = new Dir(3);
        int d;
        private Dir(int d) {
            this.d = d;
        }

        static public Dir flip(Dir d) {
            if (d.d == 0) return Dir.d3;
            else if (d.d == 1) return Dir.d2;
            else if (d.d == 2) return Dir.d1;
            else return Dir.d0;
        }

        static public Dir rotate(Dir d, Dir rotation) {
            return new Dir((d.d + rotation.d) % 4);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dir dir = (Dir) o;
            return d == dir.d;
        }
    }

    static public class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        static public Point offset(Point p, Point o) {
            return new Point(o.x + p.x, o.y + p.y);
        }

        static public void offsetM(ArrayList<Point> p, ArrayList<Point> s, Point o) {
            for (Point ps : s) {
                p.add(Point.offset(ps, o));
            }
        }

        static public void flip(Point p, Point o) {
            p.x = (-1 * p.x) + (2 * o.x);
        }

        static public void flipM(ArrayList<Point> p, Point o) {
            for (Point point : p) {
                Point.flip(point, o);
            }
        }

        static public void rotate(Point p, Point o, Dir d) {
            int py;
            for (int i = 0; i < d.d; i++) {
                py = (o.x - p.x) + o.y;
                p.x = -(o.y - p.y) + o.x;
                p.y = py;
            }

        }

        static public void rotateM(ArrayList<Point> p, Point o, Dir d) {
            for (Point point : p) {
                Point.rotate(point, o, d);
            }
        }

        static public void add(ArrayList<Point> src, ArrayList<Point> dest) {
            dest.addAll(src);
        }
    }

    static public class Join {
        Dir d;
        Point p;

        public Join(Dir d, Point p) {
            this.d = d;
            this.p = p;
        }

        public Join(Join o) {
            this(o.d, o.p);
        }

        public void flip(Point o) {
            this.d = Dir.flip(this.d);
            Point.flip(this.p, o);
        }

        public void rotate(Point o, Dir d) {
            this.d = Dir.rotate(this.d, d);
            Point.rotate(this.p, o, d);
        }
    }

    // << REAL OBJECTS >>

    static public class Stream {
        static int CHUNK = 15;
        static Join OUT = new Join(Dir.d0, new Point(Stream.CHUNK, Stream.CHUNK));
        Dir d;
        Point o;
        Join in;
        Join out;

        public Stream(Join o) {
            this.d = o.d;
            this.o = o.p;
            this.in = new Join(Dir.d0, o.p);
            this.out = new Join(Dir.d0, Point.offset(Stream.OUT.p, o.p));
            this.rotate(o.d);
        }

        public void rotate(Dir d) {
            this.d = Dir.rotate(this.d, d);
            this.in.rotate(this.o, d);
            this.out.rotate(this.o, d);
        }

        public static Join futureStream(Join e) {
            Join tmpJoin = new Join(Dir.d0, new Point(e.p.x - Stream.CHUNK, e.p.y - Stream.CHUNK));
            tmpJoin.rotate(e.p, e.d);
            return tmpJoin;
        }
    }

    static public class NStream {
        static int CHUNK = 15;
        static Join OUT = new Join(Dir.d0, new Point(NStream.CHUNK, NStream.CHUNK));
        Dir d;
        Point o;
        Join in;
        Join out;

        public NStream(Join o, int n) {
            this.d = o.d;
            this.o = o.p;
            this.in = new Join(Dir.d0, o.p);
            this.out = new Join(Dir.d0, Point.offset(new Point(n * NStream.CHUNK, n * NStream.CHUNK), o.p));
            this.rotate(o.d);
        }

        public void rotate(Dir d) {
            this.d = Dir.rotate(this.d, d);
            this.in.rotate(this.o, d);
            this.out.rotate(this.o, d);
        }
    }

    static public class Stop {
        static final ArrayList<Point> C = new ArrayList<>(Arrays.asList(
                new Point(2, 1), new Point(2, 2), new Point(3, 1),
                new Point(4, 2), new Point(4, 3), new Point(4, 4), new Point(5, 4)
        ));

        Dir d;
        Point o;
        ArrayList<Point> c;
        Join in;

        public Stop(Join o, boolean flip) {
            this.o = o.p;
            this.d = o.d;
            this.c = new ArrayList<>();
            Point.offsetM(this.c, Stop.C, this.o);
            this.in = new Join(Dir.d0, o.p);
            this.rotate(o.d);
            if (flip) this.flip();
        }

        public Stop(Join o) {
            this(o, false);
        }

        public void flip() {
            this.d = Dir.flip(this.d);
            Point.flipM(this.c, this.o);
            this.in.flip(this.o);
        }

        public void rotate(Dir d) {
            this.d = Dir.rotate(this.d, d);
            Point.rotateM(this.c, this.o, d);
            this.in.rotate(this.o, d);
        }

        public static ArrayList<Point> getPoints(Join o, boolean flipBool, Point of) {
            if (of == null) {
                of = new Point(0, 0);
            }
            Stop stop = new Stop(o);
            if (flipBool) stop.flip();
            ArrayList<Point> rp = new ArrayList<>();
            Point.offsetM(rp, stop.c, of);
            return rp;
        }
    }

    static public class Gun {
        static final ArrayList<Point> C = new ArrayList<>(Arrays.asList(
                new Point(-3, -21), new Point(-2, -21),
                new Point(-3, -20),new Point(-2, -20),
                new Point(-2, -17),
                new Point(-2, -16), new Point(-3, -16), new Point(-1, -16),
                new Point(-4, -15), new Point(0, -15),
                new Point(-2, -14),
                new Point(-5, -13), new Point(1, -13),
                new Point(-5, -12), new Point(1, -12),
                new Point(-4, -11), new Point(0, -11),
                new Point(-3, -10), new Point(-2, -10), new Point(-1, -10),
                new Point(-7, 4), new Point(-6, 4), new Point(-2, 4), new Point(-1, 4),
                new Point(-6, 6), new Point(-2, 6),
                new Point(-5, 7), new Point(-4, 7), new Point(-3, 7),
                new Point(-5, 8), new Point(-4, 8), new Point(-3, 8),
                new Point(-3, 13), new Point(-4, 13),
                new Point(-3, 14), new Point(-4, 14)
        ));

        Dir d;
        Point o;
        ArrayList<Point> c;
        Join out;

        public Gun(Join o, boolean flip) {
            this.o = o.p;
            this.d = o.d;
            this.c = new ArrayList<>();
            Point.offsetM(this.c, Gun.C, this.o);
            this.out = new Join(Dir.d0, o.p);
            this.rotate(o.d);
            if (flip) this.flip();
        }

        public Gun(Join o) {
            this(o, false);
        }

        public void flip() {
            this.d = Dir.flip(this.d);
            Point.flipM(this.c, this.o);
            this.out.flip(this.o);
        }

        public void rotate(Dir d) {
            this.d = Dir.rotate(this.d, d);
            Point.rotateM(this.c, this.o, d);
            this.out.rotate(this.o, d);
        }

        public static ArrayList<Point> getPoints(Join o, boolean flipBool, Point of) {
            if (of == null) {
                of = new Point(0, 0);
            }
            Gun gun = new Gun(o);
            if (flipBool) gun.flip();
            ArrayList<Point> rp = new ArrayList<>();
            Point.offsetM(rp, gun.c, of);
            return rp;
        }
    }

    static public class LWSSAuxGun {
        static final ArrayList<Point> C = new ArrayList<>(Arrays.asList(
                new Point(-3, -20), new Point(-2, -20),
                new Point(-3, -21),new Point(-2, -21),
                new Point(-1, -12), new Point(-2, -12), new Point(-3, -12),
                new Point(0, -11), new Point(-4, -11),
                new Point(1, -10), new Point(-5, -10),
                new Point(1, -9), new Point(0, -9), new Point(-4, -9), new Point(-5, -9),
                new Point(-2, -9),
                new Point(-2, -6),
                new Point(-1, -5), new Point(-3, -5),
                new Point(-1, -4), new Point(-3, -4),
                new Point(-2, -3), new Point(-3, -3),
                new Point(-4, -2),
                new Point(-3, -1), new Point(-4, -1), new Point(-5, -1),
                new Point(-2, 0), new Point(-6, 0),
                new Point(-1, 1), new Point(-7, 1),
                new Point(-3, 1), new Point(-4, 1), new Point(-5, 1),
                new Point(-2, 2), new Point(-3, 2),
                new Point(-4, 2), new Point(-5, 2), new Point(-6, 2),
                new Point(-3, 13), new Point(-4, 13),
                new Point(-3, 14), new Point(-4, 14)
        ));

        Dir d;
        Point o;
        ArrayList<Point> c;
        Join out;

        public LWSSAuxGun(Join o, boolean flip) {
            this.o = o.p;
            this.d = o.d;
            this.c = new ArrayList<>();
            Point.offsetM(this.c, LWSSAuxGun.C, this.o);
            this.out = new Join(Dir.d0, o.p);
            this.rotate(o.d);
            if (flip) this.flip();
        }

        public LWSSAuxGun(Join o) {
            this(o, false);
        }

        public void flip() {
            this.d = Dir.flip(this.d);
            Point.flipM(this.c, this.o);
            this.out.flip(this.o);
        }

        public void rotate(Dir d) {
            this.d = Dir.rotate(this.d, d);
            Point.rotateM(this.c, this.o, d);
            this.out.rotate(this.o, d);
        }

        public static ArrayList<Point> getPoints(Join o, boolean flipBool, Point of) {
            if (of == null) {
                of = new Point(0, 0);
            }
            LWSSAuxGun lwssAuxGun = new LWSSAuxGun(o);
            if (flipBool) lwssAuxGun.flip();
            ArrayList<Point> rp = new ArrayList<>();
            Point.offsetM(rp, lwssAuxGun.c, of);
            return rp;
        }
    }

    static public class LWSSKillerFilter {
        static final ArrayList<Point> C = new ArrayList<>(Arrays.asList(
                new Point(4, 1),
                new Point(1, 2), new Point(4, 2),
                new Point(3, 3),
                new Point(1, 4), new Point(3, 4), new Point(5, 4),
                new Point(1, 5), new Point(2, 5), new Point(4, 5), new Point(6, 5),
                new Point(4, 6), new Point(6, 6),
                new Point(4, 7), new Point(5, 7),
                new Point(0, 0), new Point(4, 0),
                new Point(4, -1),
                new Point(1, -2), new Point(4, -2),
                new Point(3, -3),
                new Point(1, -4), new Point(3, -4), new Point(5, -4),
                new Point(1, -5), new Point(2, -5), new Point(4, -5), new Point(6, -5),
                new Point(4, -6), new Point(6, -6),
                new Point(4, -7), new Point(5, -7)
        ));

        Dir d;
        Point o;
        ArrayList<Point> c;
        Join out;

        public LWSSKillerFilter(Join o, boolean flip) {
            this.o = o.p;
            this.d = o.d;
            this.c = new ArrayList<>();
            Point.offsetM(this.c, LWSSKillerFilter.C, this.o);
            this.out = new Join(Dir.d0, o.p);
            this.rotate(o.d);
            if (flip) this.flip();
        }

        public LWSSKillerFilter(Join o) {
            this(o, false);
        }

        public void flip() {
            this.d = Dir.flip(this.d);
            Point.flipM(this.c, this.o);
            this.out.flip(this.o);
        }

        public void rotate(Dir d) {
            this.d = Dir.rotate(this.d, d);
            Point.rotateM(this.c, this.o, d);
            this.out.rotate(this.o, d);
        }

        public static ArrayList<Point> getPoints(Join o, boolean flipBool, Point of) {
            if (of == null) {
                of = new Point(0, 0);
            }
            LWSSKillerFilter lwssKillerFilter = new LWSSKillerFilter(o);
            if (flipBool) lwssKillerFilter.flip();
            ArrayList<Point> rp = new ArrayList<>();
            Point.offsetM(rp, lwssKillerFilter.c, of);
            return rp;
        }
    }

    static public class LWSSKillerFinal {
        static final ArrayList<Point> C = new ArrayList<>(Arrays.asList(
                new Point(2, 1),
                new Point(5, 2), new Point(2, 2),
                new Point(3, 3),
                new Point(1, 4), new Point(3, 4), new Point(5, 4),
                new Point(1, 5), new Point(2, 5), new Point(4, 5), new Point(6, 5),
                new Point(4, 6), new Point(6, 6),
                new Point(4, 7), new Point(5, 7),
                new Point(6, 0), new Point(2, 0),
                new Point(2, -1),
                new Point(5, -2), new Point(2, -2),
                new Point(3, -3),
                new Point(1, -4), new Point(3, -4), new Point(5, -4),
                new Point(1, -5), new Point(2, -5), new Point(4, -5), new Point(6, -5),
                new Point(4, -6), new Point(6, -6),
                new Point(4, -7), new Point(5, -7)
        ));

        Dir d;
        Point o;
        ArrayList<Point> c;
        Join out;

        public LWSSKillerFinal(Join o, boolean flip) {
            this.o = o.p;
            this.d = o.d;
            this.c = new ArrayList<>();
            Point.offsetM(this.c, LWSSKillerFinal.C, this.o);
            this.out = new Join(Dir.d0, o.p);
            this.rotate(o.d);
            if (flip) this.flip();
        }

        public LWSSKillerFinal(Join o) {
            this(o, false);
        }

        public void flip() {
            this.d = Dir.flip(this.d);
            Point.flipM(this.c, this.o);
            this.out.flip(this.o);
        }

        public void rotate(Dir d) {
            this.d = Dir.rotate(this.d, d);
            Point.rotateM(this.c, this.o, d);
            this.out.rotate(this.o, d);
        }

        public static ArrayList<Point> getPoints(Join o, boolean flipBool, Point of) {
            if (of == null) {
                of = new Point(0, 0);
            }
            LWSSKillerFinal lwssKillerFinal = new LWSSKillerFinal(o);
            if (flipBool) lwssKillerFinal.flip();
            ArrayList<Point> rp = new ArrayList<>();
            Point.offsetM(rp, lwssKillerFinal.c, of);
            return rp;
        }
    }

    static public class Turn180 { // 180 Turn
        static final ArrayList<Point> C = new ArrayList<>(Arrays.asList(
                new Point(-14, -1), new Point(-14, -2), new Point(-14, -3),
                new Point(-13, 0), new Point(-13, -4),
                new Point(-12, 1), new Point(-12, -5),
                new Point(-10, 2), new Point(-10, -6),
                new Point(-5, -1), new Point(-5, -2), new Point(-5, -3),
                new Point(-6, 0), new Point(-6, -4),
                new Point(-7, 1), new Point(-7, -5),
                new Point(-9, 2), new Point(-9, -6)
        ));

        Dir d;
        Point o;
        ArrayList<Point> c;
        Join out;

        public Turn180(Join o, boolean flip) {
            this.o = o.p;
            this.d = o.d;
            this.c = new ArrayList<>();
            Point.offsetM(this.c, Turn180.C, this.o);
            this.out = new Join(Dir.d0, o.p);
            this.rotate(o.d);
            if (flip) this.flip();
        }

        public Turn180(Join o) {
            this(o, false);
        }

        public void flip() {
            this.d = Dir.flip(this.d);
            Point.flipM(this.c, this.o);
            this.out.flip(this.o);
        }

        public void rotate(Dir d) {
            this.d = Dir.rotate(this.d, d);
            Point.rotateM(this.c, this.o, d);
            this.out.rotate(this.o, d);
        }

        public static ArrayList<Point> getPoints(Join o, boolean flipBool, Point of) {
            if (of == null) {
                of = new Point(0, 0);
            }
            Turn180 turn180 = new Turn180(o);
            if (flipBool) turn180.flip();
            ArrayList<Point> rp = new ArrayList<>();
            Point.offsetM(rp, turn180.c, of);
            return rp;
        }
    }

    static public class TurnClocK { // LightWeight SpaceShip (L.W.S.S.)
        static final ArrayList<Point> C = new ArrayList<>();

        static final Join OUT = new Join(Dir.d1, new Point(22, -27)); // 20, -27 == 35, -42
        static final Join FILTER_KILLER = new Join(Dir.d0, new Point(3, 10));
        static final Join FINAL_KILLER = new Join(Dir.d0, new Point(3, -26));
        static final Join LWSS_AUX_GUN = new Join(Dir.d2, new Point(-21, 46));
        static final Join LEFT_GUN = new Join(Dir.d0, new Point(18, 16));
        static final Join RIGHT_GUN = new Join(Dir.d2, new Point(14, 46));
        static final Join TURN_180 = new Join(Dir.d0, new Point(-5, 2));

        Dir d;
        Point o;
        ArrayList<Point> c;
        Join out;

        public TurnClocK(Join o, boolean flip) {
            this.o = o.p;
            this.d = o.d;
            this.c = new ArrayList<>();
            Point.offsetM(this.c, TurnClocK.C, this.o);
            Point.offsetM(this.c, LWSSKillerFilter.getPoints(TurnClocK.FILTER_KILLER, false, null), this.o);
            Point.offsetM(this.c, LWSSKillerFinal.getPoints(TurnClocK.FINAL_KILLER, false, null), this.o);
            Point.offsetM(this.c, LWSSAuxGun.getPoints(TurnClocK.LWSS_AUX_GUN, true, null), this.o);
            Point.offsetM(this.c, Gun.getPoints(TurnClocK.LEFT_GUN, true, null), this.o);
            Point.offsetM(this.c, Gun.getPoints(TurnClocK.RIGHT_GUN, false, null), this.o);
            Point.offsetM(this.c, Turn180.getPoints(TurnClocK.TURN_180, false, null), this.o);
            this.out = new Join(TurnClocK.OUT.d, Point.offset(TurnClocK.OUT.p, this.o));
            this.rotate(o.d);
            if (flip) this.flip();
        }

        public TurnClocK(Join o) {
            this(o, false);
        }

        public void flip() {
            this.d = Dir.flip(this.d);
            Point.flipM(this.c, this.o);
            this.out.flip(this.o);
        }

        public void rotate(Dir d) {
            this.d = Dir.rotate(this.d, d);
            Point.rotateM(this.c, this.o, d);
            this.out.rotate(this.o, d);
        }

        public static ArrayList<Point> getPoints(Join o, boolean flipBool, Point of) {
            if (of == null) {
                of = new Point(0, 0);
            }
            TurnClocK turn = new TurnClocK(o);
            if (flipBool) turn.flip();
            ArrayList<Point> rp = new ArrayList<>();
            Point.offsetM(rp, turn.c, of);
            return rp;
        }
    }

    static public class TurnCounterClocK { // LightWeight SpaceShip (L.W.S.S.)
        static final ArrayList<Point> C = new ArrayList<>();

        static final Join OUT = new Join(Dir.d3, new Point(-12, 21)); // -27, 29
        static final Join FINAL_KILLER = new Join(Dir.d3, new Point(18, 10));
        static final Join FILTER_KILLER = new Join(Dir.d3, new Point(-18, 10));
        static final Join LWSS_AUX_GUN = new Join(Dir.d3, new Point(54, -14));
        static final Join LEFT_GUN = new Join(Dir.d1, new Point(24, 25));
        static final Join RIGHT_GUN = new Join(Dir.d1, new Point(54, 21));
        static final Join TURN_180 = new Join(Dir.d3, new Point(10, 2));

        Dir d;
        Point o;
        ArrayList<Point> c;
        Join out;

        public TurnCounterClocK(Join o, boolean flip) {
            //this.o = Point.offset(o.p, new Point(8, 7));
            this.o = o.p;
            this.d = o.d;
            this.c = new ArrayList<>();
            Point.offsetM(this.c, TurnCounterClocK.C, this.o);
            Point.offsetM(this.c, LWSSKillerFilter.getPoints(TurnCounterClocK.FILTER_KILLER, false, null), this.o);
            Point.offsetM(this.c, LWSSKillerFinal.getPoints(TurnCounterClocK.FINAL_KILLER, true, null), this.o);
            Point.offsetM(this.c, LWSSAuxGun.getPoints(TurnCounterClocK.LWSS_AUX_GUN, false, null), this.o);
            Point.offsetM(this.c, Gun.getPoints(TurnCounterClocK.LEFT_GUN, false, null), this.o);
            Point.offsetM(this.c, Gun.getPoints(TurnCounterClocK.RIGHT_GUN, true, null), this.o);
            Point.offsetM(this.c, Turn180.getPoints(TurnCounterClocK.TURN_180, true, null), this.o);
            this.out = new Join(TurnCounterClocK.OUT.d, Point.offset(TurnCounterClocK.OUT.p, this.o));
            this.rotate(o.d);
            if (flip) this.flip();
        }

        public TurnCounterClocK(Join o) {
            this(o, false);
        }

        public void flip() {
            this.d = Dir.flip(this.d);
            Point.flipM(this.c, this.o);
            this.out.flip(this.o);
        }

        public void rotate(Dir d) {
            this.d = Dir.rotate(this.d, d);
            Point.rotateM(this.c, this.o, d);
            this.out.rotate(this.o, d);
        }

        public static ArrayList<Point> getPoints(Join o, boolean flipBool, Point of) {
            if (of == null) {
                of = new Point(0, 0);
            }
            TurnCounterClocK turn = new TurnCounterClocK(o);
            if (flipBool) turn.flip();
            ArrayList<Point> rp = new ArrayList<>();
            Point.offsetM(rp, turn.c, of);
            return rp;
        }
    }

    static public class Gen60 { // Glider Gun (60 period) // 2 Guns used but time shifted
        static final ArrayList<Point> C = new ArrayList<>(Arrays.asList(
                new Point(-7, 5), new Point(-6, 5), new Point(-6, 7), new Point(-5, -12),
                new Point(-5, 6), new Point(-5, 8), new Point(-4, -12), new Point(-4, -11),
                new Point(-4, 6), new Point(-4, 9), new Point(-4, 13), new Point(-4, 14),
                new Point(-3, -21), new Point(-3, -20), new Point(-3, -17), new Point(-3, -16),
                new Point(-3, -11), new Point(-3, -10), new Point(-3, 6), new Point(-3, 8),
                new Point(-3, 13), new Point(-3, 14), new Point(-2, -21), new Point(-2, -20),
                new Point(-2, -17), new Point(-2, -16), new Point(-2, -11), new Point(-2, -10),
                new Point(-2, -9), new Point(-2, 5), new Point(-2, 7), new Point(-1, -17),
                new Point(-1, -16), new Point(-1, -11), new Point(-1, -10), new Point(-1, 5),
                new Point(0, -12), new Point(0, -11), new Point(1, -12), new Point(13, -13),
                new Point(14, -16), new Point(14, -15), new Point(14, -14), new Point(14, -13),
                new Point(15, -24), new Point(15, -23), new Point(15, -17), new Point(15, -16),
                new Point(15, -15), new Point(15, -14), new Point(15, -1), new Point(15, 0),
                new Point(16, -24), new Point(16, -23), new Point(16, -17), new Point(16, -14),
                new Point(16, -1), new Point(16, 1), new Point(17, -17), new Point(17, -16),
                new Point(17, -15), new Point(17, -14), new Point(17, 0), new Point(17, 1),
                new Point(17, 2), new Point(18, -16), new Point(18, -15), new Point(18, -14),
                new Point(18, -13), new Point(18, 1), new Point(18, 2), new Point(18, 3),
                new Point(19, -13), new Point(19, 0), new Point(19, 1), new Point(19, 2),
                new Point(20, -1), new Point(20, 1), new Point(20, 9), new Point(20, 10),
                new Point(21, -1), new Point(21, 0), new Point(21, 9), new Point(21, 11),
                new Point(22, 11), new Point(23, 11), new Point(23, 12)
        ));

        Dir d;
        Point o;
        ArrayList<Point> c;
        Join out;

        public Gen60(Join o, boolean flip) {
            this.o = o.p;
            this.d = o.d;
            this.c = new ArrayList<>();
            Point.offsetM(this.c, Gen60.C, this.o);
            this.out = new Join(Dir.d0, o.p);
            this.rotate(o.d);
            if (flip) this.flip();
        }

        public Gen60(Join o) {
            this(o, false);
        }

        public void flip() {
            this.d = Dir.flip(this.d);
            Point.flipM(this.c, this.o);
            this.out.flip(this.o);
        }

        public void rotate(Dir d) {
            this.d = Dir.rotate(this.d, d);
            Point.rotateM(this.c, this.o, d);
            this.out.rotate(this.o, d);
        }

        public static ArrayList<Point> getPoints(Join o, boolean flipBool, Point of) {
            if (of == null) {
                of = new Point(0, 0);
            }
            Gen60 gen60 = new Gen60(o);
            if (flipBool) gen60.flip();
            ArrayList<Point> rp = new ArrayList<>();
            Point.offsetM(rp, gen60.c, of);
            return rp;
        }
    }
}