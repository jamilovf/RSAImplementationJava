import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Util {
    private static List<Integer> primes;

    static {
        primes = new ArrayList<>(List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239,
                241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541
        ));
    }

    public static boolean isPrimeMillerRabin(BigInteger number, int round){
        if(number.compareTo(BigInteger.ONE) <= 0){
           throw new IllegalArgumentException();
        }

        if(number.equals(BigInteger.TWO) || number.equals(BigInteger.valueOf(3))){
            return true;
        }

        if(number.mod(BigInteger.TWO).equals(BigInteger.ZERO)){
            return false;
        }

        boolean isComposite = false;
        BigInteger p = number.subtract(BigInteger.ONE);
        BigInteger d = p;
        int S = 0;

        while(d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.TWO);
            S++;
        }

        for (int i = 0; i < round; i++){
           BigInteger a = BigInteger.TWO;

           if (!number.gcd(new BigInteger(String.valueOf(a))).equals(BigInteger.ONE)){
               return false;
           }

            if(fastModularExp(a,d,number).equals(BigInteger.ONE)) {
                continue;
            }
            for (int s = 0 ; s < S ; s++){

                if(!fastModularExp(a,BigInteger.TWO.pow(s).multiply(d),number).equals(p) &&
                        !fastModularExp(a,BigInteger.TWO.pow(s).multiply(d),number).equals(BigInteger.ONE)){
                    isComposite = true;
                }
                else {
                    isComposite = false;
                }
            }
        }
        if (isComposite == true){
            return false;
        }
        return true;
    }

    public static BigInteger fastModularExp(BigInteger base, BigInteger exp, BigInteger modNumber){
        if(exp.compareTo(BigInteger.ONE) < 0){
            throw new IllegalArgumentException();
        }
        BigInteger finalResult = BigInteger.ONE;

        if(exp.equals(1)){
            return  base.pow(exp.intValue()).mod(modNumber);
        }

        String binaryRep = exp.toString(2);
        List<BigInteger> binaryRes = new ArrayList<>(binaryRep.length());

        BigInteger result = base.mod(modNumber);
        binaryRes.add(result);

        for (int i = binaryRep.length() - 1; i >=1; i--){
            result = result.multiply(result).mod(modNumber);
            binaryRes.add(result);
        }

        for (int i = binaryRep.length() - 1; i >=0; i--){
            if(binaryRep.charAt(i) == '1'){
                finalResult = finalResult.multiply(binaryRes.get(binaryRep.length() - 1 - i));
            }
        }

        return finalResult.mod(modNumber);
    }

    public static List<BigInteger> extendedEuclidean(BigInteger a, BigInteger b){
        List<BigInteger> q = new ArrayList<>();
        q.add(BigInteger.ZERO);
        List<BigInteger> r = new ArrayList<>();
        r.add(a);
        r.add(b);
        List<BigInteger> x = new ArrayList<>();
        List<BigInteger> y = new ArrayList<>();
        x.add(BigInteger.ONE);
        x.add(BigInteger.ZERO);
        y.add(BigInteger.ZERO);
        y.add(BigInteger.ONE);


        int i = 1;
        while(!r.get(i).equals(BigInteger.ZERO)){
            i++;
            q.add(r.get(i-2).divide(r.get(i-1)));
            r.add(r.get(i-2).mod(r.get(i-1)));
            x.add(x.get(i-1).multiply(q.get(i-1)).add(x.get(i-2)));
            y.add(y.get(i-1).multiply(q.get(i-1)).add(y.get(i-2)));
        }

        List<BigInteger> result = new ArrayList<>();
        int n = i - 1;
        result.add(r.get(n));
        result.add(x.get(n).multiply(BigInteger.ONE.negate().pow(n)));
        result.add(y.get(n).multiply(BigInteger.ONE.negate().pow(n + 1)));

        return result;
    }

    public static BigInteger chineseRemainderTheorem(List<BigInteger> b, List<BigInteger> n, int k){
        BigInteger product = BigInteger.ONE;


        for (int i = 0; i < k; i++)
            product = product.multiply(n.get(i));

        BigInteger prod, x;
        BigInteger sum = BigInteger.ZERO;

        for (int i = 0; i < k; i++){
            prod = product.divide(n.get(i));
            x = extendedEuclidean(prod,n.get(i)).get(1);
            sum = sum.add(b.get(i).multiply(x).multiply(prod));
        }

        return sum.mod(product);
    }

    public static BigInteger modularInverse(BigInteger e, BigInteger n){
        return Util.extendedEuclidean(e,n).get(1);
    }

    public static BigInteger phiForPrimes(BigInteger p, BigInteger q){
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    public static BigInteger getRandomBigInteger() {
        Random random = new Random();
        BigInteger result = BigInteger.probablePrime(1024, random);
        return result;
    }

    public static BigInteger randomPrimeBigInteger() {
        boolean flagPrime = true;
        Random rand = new Random();
        BigInteger result = new BigInteger(1024, rand);

        while (flagPrime) {
            if (isPrimeMillerRabin(result, 1)) {
                flagPrime = false;
            }
            else{
                 result = new BigInteger(1024, rand);
            }
        }
        return result;
    }

    public static BigInteger isCoPrime(BigInteger phiN){
        Random rand = new Random();

        BigInteger result = new BigInteger(1024, rand);
        BigInteger tmp;
        while ((tmp = (result.gcd(phiN))).compareTo(BigInteger.ONE) > 0) {
                result = result.divide(tmp);
        }
        return result;
    }

}

