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
    }
}
