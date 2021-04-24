import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSAUtil {

    public boolean isPrimeMillerRabin(BigInteger number, int round){
        boolean isComposite = false;
        BigInteger p = number.add(BigInteger.valueOf(-1));
        BigInteger d = p;
        int S = 0;

        while(d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.TWO);
            S++;
        }

        for (int i = 0; i < round; i++){
           BigInteger a;
           do {
                a = new BigInteger(String.valueOf(2 + (int) (Math.random() * (p.intValue()))));
           }
            while (!p.gcd(new BigInteger(String.valueOf(a))).equals(BigInteger.ONE));

            if(a.pow(d.intValue()).mod(number).equals(BigInteger.ONE)) {
                continue;
            }
            for (int s = 0 ; s < S ; s++){

                if(!a.pow(BigInteger.TWO.pow(s).multiply(d).intValue()).mod(number).equals(p) &&
                        !a.pow(BigInteger.TWO.pow(s).multiply(d).intValue()).mod(number).equals(BigInteger.ONE)){
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

    public BigInteger fastModularExp(BigInteger base, BigInteger exp, BigInteger modNumber){
        BigInteger finalResult = BigInteger.ONE;

        if(exp.equals(1)){
            return  base.pow(exp.intValue()).mod(modNumber);
        }

        String binaryRep = Integer.toBinaryString(exp.intValue());
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

    public List<BigInteger> extendedEuclidean(BigInteger a, BigInteger b){
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
}

