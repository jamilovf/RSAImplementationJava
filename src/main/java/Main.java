import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

     /*  if(Util.isPrimeMillerRabin(BigInteger.valueOf(561),1)){
           System.out.println("possibly prime");
       }
       else{
           System.out.println("is composite");
       }

        System.out.println(Util.fastModularExp(BigInteger.valueOf(14546),
                BigInteger.valueOf(14356), BigInteger.valueOf(45645)));

        System.out.println(Util.extendedEuclidean(BigInteger.valueOf(12), BigInteger.valueOf(125)));

        System.out.println(
                Util.chineseRemainderTheorem(
                        new ArrayList<BigInteger>(
                                List.of(BigInteger.valueOf(3),
                                BigInteger.valueOf(1),
                                BigInteger.valueOf(6))),
                        new ArrayList<BigInteger>(
                                List.of(BigInteger.valueOf(5),
                                        BigInteger.valueOf(7),
                                        BigInteger.valueOf(8))),
                        3));*/

        RSA rsa = new RSA();
        List<BigInteger> list = rsa.encrypt(rsa.getN(),rsa.getE(),"Fuad");
        System.out.println(list);
        System.out.println(rsa.decryptWithFME(list,rsa.getD(),rsa.getN()));
        System.out.println(rsa.decryptWithCRT(list,rsa.getD(),rsa.getP(),rsa.getQ()));

    }
}
