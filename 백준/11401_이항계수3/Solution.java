import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private int N, K;
    private long[] factorial;
    private static final int MOD = 1_000_000_007;

    public Solution(BufferedReader reader) throws IOException {
        String[] tokens = reader.readLine().split(" ");
        N = Integer.parseInt(tokens[0]);
        K = Integer.parseInt(tokens[1]);
        factorial = new long[N + 1];
        factorial[0] = 1;
    }

    private long pow(long n, int exp) {
        if ( exp == 0 ) {
            return 1;
        } 

        if ( exp == 1 ) {
            return n;
        }

        if ( exp % 2 == 0 ) {
            long half = pow(n, exp / 2);
            return (half % MOD * half % MOD) % MOD;
        }

        return n % MOD * pow(n, exp - 1) % MOD;
    }

    private long simulate() {
        // factorial 순열 미리 구하기
        for ( int i = 1 ; i <= N ; ++i ) {
            factorial[i] = (int) ( (long) (factorial[i-1] * i) % (long) MOD );
        }

        // nCr
        // n! / (r! * (n - r)!) 
        // a * b % p = (a % p * b % p) % p 
        // a^-1 % p = a^(p-2) % p  when p is prime and a is not divisible by p 
        // let x = r! * (n-r)! 
        // x^(p-2) % p = x^-1 % p
        long upper = factorial[N]; // 분자
        long lower = (factorial[K] * factorial[N - K])%MOD; // 분모
        long lowerInverse = pow(lower, MOD - 2); // 분모의 역수
                                                 //
        return (upper % MOD * lowerInverse % MOD) % MOD; // nCr
    }

    public void run () {
        StringBuilder sb = new StringBuilder();
        sb.append(simulate());
        System.out.println(sb.toString());
    }
}


