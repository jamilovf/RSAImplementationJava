import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        RSAUtil rsaUtil = new RSAUtil();
       if(rsaUtil.isPrimeMillerRabin(BigInteger.valueOf(997),3)){
           System.out.println("possibly prime");
       }
       else{
           System.out.println("is composite");
       }

        System.out.println(rsaUtil.fastModularExp(BigInteger.valueOf(14546),
                BigInteger.valueOf(14356), BigInteger.valueOf(45645)));

        System.out.println(rsaUtil.extendedEuclidean(BigInteger.valueOf(125), BigInteger.valueOf(12)));
    }
}
