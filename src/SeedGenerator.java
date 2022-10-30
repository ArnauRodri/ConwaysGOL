import java.util.*;

public class SeedGenerator {

    private final static int D = 10;

    private static class P {
        int x;
        int y;

        int turn;

        private P(int x, int y, int turn) {
            this.x = x;
            this.y = y;
            this.turn = turn;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            P p = (P) o;
            return this.x == p.x && this.y == p.y;
        }
    }
    static public class Dir {

        public static Dir d0 = new Dir(0, 0, 1);
        public static Dir d1 = new Dir(1, 1, 0);
        public static Dir d2 = new Dir(2, 0, -1);
        public static Dir d3 = new Dir(3, -1, 0);
        int d;

        int dx;
        int dy;
        private Dir(int d, int dx, int dy) {
            this.d = d;
            this.dx = dx;
            this.dy = dy;
        }

        static public Dir rotate(Dir d, boolean left) {
            if (left) {
                if (d.d == 0) return Dir.d1;
                else if (d.d == 1) return Dir.d2;
                else if (d.d == 2) return Dir.d3;
                else return Dir.d0;
            } else {
                if (d.d == 0) return Dir.d3;
                else if (d.d == 3) return Dir.d2;
                else if (d.d == 2) return Dir.d1;
                else return Dir.d0;
            }
        }

        static P step(P p, Dir d, int turn) {
            return new P(p.x + d.dx, p.y + d.dy, turn);
        }
    }

    private static LinkedList<P> getSnake() {

        LinkedList<P> path = new LinkedList<>();


        Random r = new Random();

        int changeDir;

        P p;

        Dir d = Dir.d0;

        int newTurn;

        path.add(new P(r.nextInt(10), r.nextInt(10), 1));

        for (int i = 0; i < 3; i++) {
            newTurn = 1;
            changeDir = r.nextInt(3);
            if (changeDir == 0) {
                d = Dir.rotate(d, true);
                newTurn = 2;
            } else if (changeDir == 1) {
                d = Dir.rotate(d, false);
                newTurn = 0;
            }
            p = Dir.step(path.getLast(), d, newTurn);
            if (path.contains(p)) break;
            path.add(p);
        }
        return path;
    }

    public static ArrayList<Logic.Point> build() {
        ArrayList<Logic.Point> c = new ArrayList<>();
        LinkedList<P> path = getSnake();
        List<P> lp = new ArrayList<>(path);

        Logic.Dir tmpD;

        Random r = new Random();

        int tmpInt = r.nextInt(4);
        if (tmpInt == 0) tmpD = Logic.Dir.d0;
        else if (tmpInt == 1) tmpD = Logic.Dir.d1;
        else if (tmpInt == 2) tmpD = Logic.Dir.d2;
        else tmpD = Logic.Dir.d3;



        var o = new Logic.Join(tmpD, new Logic.Point(500, 500));

        var g = new Logic.Gen60(o);
        c.addAll(g.c);
        var s = new Logic.NStream(g.out, 2);

        Logic.TurnClocK tC;
        Logic.TurnCounterClocK tCC;

        Logic.Join lastPoint = s.out;

        for (P p: lp) {
            if (p.turn == 0) {
                tC = new Logic.TurnClocK(lastPoint);
                c.addAll(tC.c);
                s = new Logic.NStream(tC.out, 4);
                lastPoint = s.out;
            } else if (p.turn == 1) {
                tCC = new Logic.TurnCounterClocK(lastPoint);
                c.addAll(tCC.c);
                s = new Logic.NStream(tCC.out, 4);
                lastPoint = s.out;
            }
        }

        var end = new Logic.Stop(lastPoint);
        c.addAll(end.c);

        return c;
    }
}
