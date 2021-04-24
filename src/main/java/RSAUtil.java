import java.math.BigInteger;

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
}
