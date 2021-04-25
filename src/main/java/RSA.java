import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {
    private static BigInteger p;
    private static BigInteger q;
    private static BigInteger n;
    private static BigInteger phiN;
    private static BigInteger e;
    private static BigInteger d;
    private static RSAUtil rsaUtil;

    static {
        rsaUtil = new RSAUtil();
        p = BigInteger.valueOf(13);
        q = BigInteger.valueOf(19);
        n = p.multiply(q);
        phiN = phiForPrimes(p,q);
        e = BigInteger.valueOf(47);
        d = modularInverse(e,phiN).mod(phiN);
    }



    public static BigInteger phiForPrimes(BigInteger p, BigInteger q){
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    public static BigInteger modularInverse(BigInteger e, BigInteger n){
        return rsaUtil.extendedEuclidean(e,n).get(1);
    }

    public static List<BigInteger> encrypt(BigInteger n, BigInteger e,String message){
        List<BigInteger> encryptedMessage = new ArrayList<>();

        for(int i = 0; i < message.length(); i++){
            BigInteger m = BigInteger.valueOf(message.charAt(i));
            encryptedMessage.add(rsaUtil.fastModularExp(m,e,n));
        }

        return encryptedMessage;
    }

    public static String decryptWithFME(List<BigInteger> ciphertext, BigInteger d, BigInteger n){
        StringBuilder messageBuilder = new StringBuilder();

        for(int i = 0; i < ciphertext.size(); i++){
            BigInteger c = ciphertext.get(i);
            messageBuilder.append((char)rsaUtil.fastModularExp(c,d,n).intValue());
        }

        return messageBuilder.toString();
    }

    public static String decryptWithCRT(List<BigInteger> ciphertext, BigInteger d, BigInteger p, BigInteger q) {
        StringBuilder messageBuilder = new StringBuilder();
        BigInteger n = p.multiply(q);
        BigInteger dp = d.mod(p.subtract(BigInteger.ONE));
        BigInteger dq = d.mod(q.subtract(BigInteger.ONE));

        for (int i = 0; i < ciphertext.size(); i++) {
            BigInteger mp,mq;
            BigInteger c = ciphertext.get(i);
            mp = rsaUtil.fastModularExp(c,dp,p);
            mq = rsaUtil.fastModularExp(c,dq,q);
            List<BigInteger> mList = new ArrayList<>(List.of(mp,mq));
            List<BigInteger> nList = new ArrayList<>(List.of(p,q));

            messageBuilder.append((char) rsaUtil.chineseRemainderTheorem(mList, nList, 2).intValue());
        }

        return messageBuilder.toString();
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

    public static BigInteger getN() {
        return n;
    }

    public static void setN(BigInteger n) {
        RSA.n = n;
    }

    public static BigInteger getE() {
        return e;
    }

    public static void setE(BigInteger e) {
        RSA.e = e;
    }

    public static BigInteger getD() {
        return d;
    }

    public static void setD(BigInteger d) {
        RSA.d = d;
    }
}
