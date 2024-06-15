import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Floyd {
    private int[][] cost;
    private int cityCount;
    private static int INF = Integer.MAX_VALUE;
    
    public Floyd(int[][] cost, int count) {
        this.cost = cost;
        this.cityCount = count;
    }

    public void printPath(int start, int end, int[][] next) {
        if (next[start][end] == -1) {
            System.out.println("No path");
            return;
        }
        System.out.print("Path: " + start);
        while (start != end) {
            start = next[start][end];
            System.out.print(" -> " + start);
        }
        System.out.println();
    }
    
    public static int[][] fileScanner(String fileName) throws FileNotFoundException {
        int[][] cost;
        File file = new File(fileName);
        Scanner fileScanner1 = new Scanner(file);
        Scanner fileScanner2 = new Scanner(file);
        int x = 0;
        int count = 0;
        while (fileScanner1.hasNextLine()) {
            fileScanner1.nextLine();
            count++;
        }
        cost = new int[count][count];
        while (fileScanner2.hasNextLine()) {
            int y = 0;
            String data = fileScanner2.nextLine();
            String[] split = data.split(" ");
            for (String item : split) {
                int num = Integer.parseInt(item);
                cost[x][y] = num;
                y++;
            }
            x++;
        }
        fileScanner1.close();
        fileScanner2.close();
        return cost;
    }
    public void nextMaker(int[][] next) {
        for (int i = 0; i < this.cityCount; i++) {
            for (int j = 0; j < this.cityCount; j++) {
                if (cost[i][j] != INF) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }
    }
    public void changeToInf(int[][] cost) {
        for (int i = 0; i < cost.length; i++) {
            for (int j = 0; j < cost.length; j++) {
                if (cost[i][j] == -1) {
                    cost[i][j] = INF;
                }
            }
        }
    }
    public void compute(int[][] next) {
        for (int k = 0; k < cityCount; k++) {
            for (int i = 0; i < cityCount; i++) {
                for (int j = 0; j < cityCount; j++) {
                    if (cost[i][k] != INF && cost[k][j] != INF && cost[i][k] + cost[k][j] < cost[i][j]) {
                        cost[i][j] = cost[i][k] + cost[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Masukkan file harga: ");
            String fileName = scanner.nextLine();
            int[][] cost = Floyd.fileScanner(fileName);
            int cityCount = cost.length;
            
            System.out.println("Cost(Before)");
            for (int i = 0; i < cityCount; i++) {
                for (int j = 0; j < cityCount; j++) {
                    System.out.print(cost[i][j] + " ");
                }
                System.out.println();
            }

            int[][] next = new int[cityCount][cityCount];
            Floyd tes1 = new Floyd(cost, cityCount);
            tes1.changeToInf(cost);
            tes1.nextMaker(next);

            System.out.println("Next(Before)");
            for (int i = 0; i < cityCount; i++) {
                for (int j = 0; j < cityCount; j++) {
                    System.out.print(next[i][j] + " ");
                }
                System.out.println("");
            }

            tes1.compute(next);

            System.out.println("Cost(After)");
            for (int i = 0; i < cityCount; i++) {
                for (int j = 0; j < cityCount; j++) {
                    cost[i][j] = (cost[i][j] == INF ? -1 : cost[i][j]);
                    System.out.print(cost[i][j] + " ");
                }
                System.out.println("");
            }


            System.out.println("Next(After)");
            for (int i = 0; i < cityCount; i++) {
                for (int j = 0; j < cityCount; j++) {
                    System.out.print(next[i][j] + " ");
                }
                System.out.println("");
            }
            int start = 0;
            int end = 6;
            System.out.println("Minimum cost from city " + start + " to city " + end + " is: " + cost[start][end]);

            tes1.printPath(start, end, next);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
