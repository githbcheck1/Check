import java.util.Scanner;

class crc {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        int m, n;
        int g[], d[], r[], z[];
        int msb, i, j, k;

        System.out.print("Enter no. of data bits : ");
        n = sc.nextInt();

        System.out.print("Enter no. of generator bits : ");
        m = sc.nextInt();

        d = new int[n + m];
        g = new int[m];

        System.out.print("Enter data bits : ");
        for (i = 0; i < n; i++)
            d[i] = sc.nextInt();

        System.out.print("Enter generator bits : ");
        for (i = 0; i < m; i++)
            g[i] = sc.nextInt();

        // Append zero bits (m-1 zeros)
        for (i = n; i < n + m - 1; i++)
            d[i] = 0;

        r = new int[n + m];
        for (i = 0; i < n + m; i++)
            r[i] = d[i];

        z = new int[m];
        for (i = 0; i < m; i++)
            z[i] = 0;

        // Division (CRC calculation)
        for (i = 0; i < n; i++) {
            msb = r[i];
            k = 0;

            for (j = i; j < i + m; j++) {
                if (msb == 0)
                    r[j] = xor(r[j], z[k]);
                else
                    r[j] = xor(r[j], g[k]);
                k++;
            }
        }

        System.out.print("The code bits added are : ");
        for (i = n; i < n + m - 1; i++) {
            d[i] = r[i];
            System.out.print(d[i]);
        }

        System.out.print("\nThe code data is : ");
        for (i = 0; i < n + m - 1; i++) {
            System.out.print(d[i]);
        }
    }

    public static int xor(int x, int y) {
        return (x == y) ? 0 : 1;
    }
}
