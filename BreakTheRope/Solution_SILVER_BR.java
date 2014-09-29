// you can also use imports, for example:
// import java.util.*;

// you can use System.out.println for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Solution {
    public int solution(int[] A, int[] B, int[] C) {
        // write your code in Java SE 8
        // use the Brute Force to solve this problem and it gets an silver award
        // because of the worst case time complexity O(N*N)
        int N = A.length;
        int[] weights = new int[N];
        for (int i = 0; i < N; i++) {
            weights[i] = B[i];
            int rope = C[i];
            if (weights[i] > A[i])
                return i;
            while (rope != -1) {
                weights[rope] += B[i];
                if (weights[rope] > A[rope])
                    return i;
                rope = C[rope];
            }
            if (i == N - 1)
                return i + 1;
        }
        return 0;
    }
}
