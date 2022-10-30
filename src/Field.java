import java.util.ArrayList;
import java.util.List;

public class Field {
    int X;
    int Y;
    public List<ArrayList<Boolean>> alive;
    List<ArrayList<Boolean>> newAlive;
    List<ArrayList<Boolean>> killed;

    public Field(int X, int Y) {
        this.X = X;
        this.Y = Y;

        alive = new ArrayList<>();
        newAlive = new ArrayList<>();
        killed = new ArrayList<>();

        iniArrayList(alive);
        iniArrayList(newAlive);
        iniArrayList(killed);

        fetchSeed();
    }

    private void iniArrayList(List<ArrayList<Boolean>> arrayList) {
        for (int i = 0; i < this.X; i++) {
            arrayList.add(new ArrayList<>());
            for (int j = 0; j < this.Y; j++) {
                arrayList.get(i).add(false);
            }
        }
    }

    private boolean updateRound(int I, int J, boolean cAlive) {
        int count = 0;
        for (int i = I - 1; i <= I + 1; i++) {
            for (int j = J - 1; j <= J + 1; j++) {
                if ((i == I && j == J) || i < 0 || j < 0 || i >= this.X || j >= this.Y) continue;
                if (alive.get(i).get(j)) {
                    count++;
                }
            }
        }

        if (cAlive && (2 == count || 3 == count)) {
            return true;
        }
        else return !cAlive && count == 3;
    }

    private void aliveCount() {
        for (int i = 0; i < this.X; i++) {
            for (int j = 0; j < this.Y; j++) {
                newAlive.get(i).set(j, updateRound(i, j, alive.get(i).get(j)));
                if (newAlive.get(i).get(j)) killed.get(i).set(j, true);
            }
        }
    }

    private void printC(List<ArrayList<Boolean>> arrayList) {
        for (int i = 0; i < this.X; i++) {
            for (int j = 0; j < this.Y; j++) {
                if (arrayList.get(i).get(j)) System.out.print("+");
                if (!arrayList.get(i).get(j)) System.out.print("·");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void updateField() {
        newAlive = new ArrayList<>();
        iniArrayList(newAlive);

        aliveCount();

        alive = newAlive;
    }

    public void fetchSeed() {
        ArrayList<Logic.Point> p = SeedGenerator.build();
        for (Logic.Point ps: p) {
            alive.get(ps.x).set(ps.y, true);
        }
    }
}
