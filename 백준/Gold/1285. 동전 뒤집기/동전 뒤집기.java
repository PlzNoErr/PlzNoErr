import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	static int min;
	static int n;
	static int[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		min = n * n;
		map = new int[n][n];
		for (int i = 0; i < map.length; i++) {
			String s = br.readLine();
			for (int j = 0; j < map.length; j++) {
				if (s.charAt(j) == 'H') {
					map[i][j] = 0;
				} else {
					map[i][j] = 1;
				}
			}
		}
		dfs(0);
		System.out.println(min);

	}// main

	static void dfs(int depth) {
		if (depth == n) {
			//세로열을 비교해주자. 만약 뒤집는게 더 이득이면 뒤집어서 더하고 아니면 그냥 더한다.
			int count = 0;
			for (int i = 0; i < map.length; i++) {
				int check = 0;
				for (int k = 0; k < map.length; k++) {
					check += map[k][i];
				}
				if (n - check < check) {
					count += n - check;
				} else {
					count += check;
				}
			}
			min = Math.min(min, count);
			return;
		}

		//행을 뒤집고 넘어간다.
		for (int i = 0; i < map.length; i++) {
			map[depth][i] = (map[depth][i] + 1) % 2;
		}
		dfs(depth + 1);
		for (int i = 0; i < map.length; i++) {
			map[depth][i] = (map[depth][i] + 1) % 2;
		}

		//행을 뒤집지 않고 넘어간다.
		dfs(depth + 1);

	}// dfs

}