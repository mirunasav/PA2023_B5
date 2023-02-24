//Savin Miruna, A5

public class Compulsory {
    static int digitSum(int n)
    {
        if(n >= 10) {
            int sum = 0;
            while (n > 0) {
                sum += n % 10;
                n /= 10;
            }
            return digitSum(sum);
        }
        else return n;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        String [] languages = {"C", "C++","C#", "Python","Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};

        int n = (int) (Math.random() *1_000_000);

        System.out.println("n este " + n);

        n *= 3;

        n += Integer.parseInt("10101",2);

        n+=Integer.parseInt("FF",16);

        n*=6;

        System.out.println("n este " + n);

        System.out.println("suma cifrelor lui n este " + digitSum(n));

        System.out.println("Willy-nilly, this semester I will learn " + languages[digitSum(n)]);


    }
}
