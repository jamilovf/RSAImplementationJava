import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

        System.out.println(rsaUtil.extendedEuclidean(BigInteger.valueOf(12), BigInteger.valueOf(125)));

        System.out.println(
                rsaUtil.chineseRemainderTheorem(
                        new ArrayList<BigInteger>(
                                List.of(BigInteger.valueOf(3),
                                BigInteger.valueOf(1),
                                BigInteger.valueOf(6))),
                        new ArrayList<BigInteger>(
                                List.of(BigInteger.valueOf(5),
                                        BigInteger.valueOf(7),
                                        BigInteger.valueOf(8))),
                        3));

    }
}
