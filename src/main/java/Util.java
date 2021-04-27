import java.math.BigInteger;
import java.util.*;

public class Util {

    public static boolean isPrimeMillerRabin(BigInteger number, int round){
        BigInteger tmp;

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
           BigInteger a = getRandomBigInteger(number.bitLength());
            while(!(a.compareTo(BigInteger.TWO)>=0 && a.compareTo(p) < 0)){
                a = getRandomBigInteger(number.bitLength());
            }

           if (!extendedEuclidean(number,a).get(0).equals(BigInteger.ONE)){
               return false;
           }

            if(fastModularExp(a,d,number).equals(BigInteger.ONE)) {
                continue;
            }
            for (int s = 0 ; s < S ; s++){
                tmp = fastModularExp(a, BigInteger.TWO.pow(s).multiply(d), number);
                isComposite = !tmp.equals(p) && !tmp.equals(BigInteger.ONE);
            }
        }
        return isComposite != true;
    }

    public static BigInteger fastModularExp(BigInteger base, BigInteger exp, BigInteger modNumber){
        if(exp.compareTo(BigInteger.ONE) < 0){
            throw new IllegalArgumentException();
        }
        BigInteger finalResult = BigInteger.ONE;

        if(exp.equals(BigInteger.ONE)){
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

    public static BigInteger getRandomBigInteger(int bitLength){
        Random random = new Random();
        return new BigInteger(bitLength,random);
    }

    public static BigInteger getRandomBigIntegerInRange(int bitLength,BigInteger min, BigInteger max){

        BigInteger result = getRandomBigInteger(bitLength);
        while(!(result.compareTo(min) > 0 && result.compareTo(max) < 0)){
            result = getRandomBigInteger(bitLength);
        }
        return result;
    }

    public static BigInteger randomPrimeBigInteger() {
        BigInteger result = getRandomBigInteger(1024);

        while (!isPrimeMillerRabin(result, 3)) {
                 result = getRandomBigInteger(1024);
        }
        return result;
    }

    public static BigInteger isCoPrime(BigInteger phiN){
        BigInteger e = getRandomBigIntegerInRange(1024,BigInteger.ONE,phiN);
        while(!(extendedEuclidean(e,phiN).get(0).equals(BigInteger.ONE))){
            e = getRandomBigIntegerInRange(1024,BigInteger.ONE,phiN);
        }

        return e;
    }

}

