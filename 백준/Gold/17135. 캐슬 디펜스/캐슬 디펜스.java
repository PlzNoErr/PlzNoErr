import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[] dr = {0, -1, 0};
    static int[] dc = {-1, 0, 1};
    static int[][] map;
    static int[] record;
    static int N;
    static int M;
    static int D;
    static int max = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        map = new int[N + 1][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // Combination 으로 3자리를 뽑고 게임을 시뮬레이션 한다.
        record = new int[3];
        Combination(0, 0);
        System.out.println(max);
    }

    static void Combination(int depth, int idx) {
        if (depth == 3) {
            int[][] copy_map = new int[N + 1][M];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    copy_map[i][j] = map[i][j];
                }
            }
            Simulation(copy_map);
            return;
        }
        for (int i = idx; i < M; i++) {
            record[depth] = i;
            Combination(depth + 1, i + 1);
        }
    }

    static void Simulation(int[][] map) {
        int kill = 0;
        int[] archer1 = {N, record[0]};
        int[] archer2 = {N, record[1]};
        int[] archer3 = {N, record[2]};
        while (true) {
            // 몹이 다 죽었는지 확인한다.
            boolean no_enemy = true;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (map[i][j] == 1) {
                        no_enemy = false;
                    }
                }
            }
            if (no_enemy)
                break;
            // bfs를 돌려서 궁수가 공격할 대상을 지정한다.
            int[] target1 = bfs_find_target(archer1, map);
            int[] target2 = bfs_find_target(archer2, map);
            int[] target3 = bfs_find_target(archer3, map);
            if (target1 != null && map[target1[0]][target1[1]] == 1) {
                kill++;
                map[target1[0]][target1[1]] = 0;
            }
            if (target2 != null && map[target2[0]][target2[1]] == 1) {
                kill++;
                map[target2[0]][target2[1]] = 0;
            }
            if (target3 != null && map[target3[0]][target3[1]] == 1) {
                kill++;
                map[target3[0]][target3[1]] = 0;
            }

            // 몬스터가 한칸씩 앞으로 이동한다.
            for (int j = 0; j < M; j++) {
                for (int i = N; i > 0; i--) {
                    map[i][j] = map[i - 1][j];
                }
            }
            // 첫칸 막칸을 0으로 변경.
            for (int j = 0; j < M; j++) {
                map[0][j] = 0;
                map[N][j] = 0;
            }
        }//
        max = Math.max(max, kill);
    }//

    static int[] bfs_find_target(int[] archer, int[][] map) {
        Queue<int[]> Q = new LinkedList<>();
        boolean[][] check = new boolean[N][M];
        Q.add(new int[]{archer[0], archer[1], 0});
        while (!Q.isEmpty()) {
            int[] vt = Q.poll();
            if (vt[2] > D)
                continue;
            if (map[vt[0]][vt[1]] == 1)
                return vt;
            for (int i = 0; i < 3; i++) {
                int R = vt[0] + dr[i];
                int C = vt[1] + dc[i];
                if (0 <= R && R < N && 0 <= C && C < M && !check[R][C]) {
                    check[R][C] = true;
                    Q.add(new int[]{R, C, vt[2] + 1});
                }
            }
        }
        return null;
    }//
}