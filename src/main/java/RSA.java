import java.math.BigInteger;
import java.util.Random;

public class RSA {
    private static BigInteger p;
    private static BigInteger q;

    static {
        p = RSAUtil.getRandomBigInteger();
        q = RSAUtil.getRandomBigInteger();
    }

    public static BigInteger getP() {
        return p;
    }

    public static void setP(BigInteger p) {
        RSA.p = p;
    }

    public static BigInteger getQ() {
        return q;
    }

    public static void setQ(BigInteger q) {
        RSA.q = q;
    }


}
