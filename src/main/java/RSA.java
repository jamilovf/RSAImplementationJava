import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phiN;
    private BigInteger e;
    private BigInteger d;

    public RSA(){


        p = Util.getRandomBigInteger();
        q = Util.getRandomBigInteger();
        n = p.multiply(q);
        phiN = Util.phiForPrimes(p,q);
        e = Util.isCoPrime(phiN);
        d = Util.modularInverse(e,phiN).mod(phiN);

    }




    public List<BigInteger> encrypt(BigInteger n, BigInteger e,String message){
        List<BigInteger> encryptedMessage = new ArrayList<>();

        for(int i = 0; i < message.length(); i++){
            BigInteger m = BigInteger.valueOf(message.charAt(i));
            encryptedMessage.add(Util.fastModularExp(m,e,n));
        }

        return encryptedMessage;
    }

    public String decryptWithFME(List<BigInteger> ciphertext, BigInteger d, BigInteger n){
        StringBuilder messageBuilder = new StringBuilder();

        for(int i = 0; i < ciphertext.size(); i++){
            BigInteger c = ciphertext.get(i);
            messageBuilder.append((char) Util.fastModularExp(c,d,n).intValue());
        }

        return messageBuilder.toString();
    }

    public  String decryptWithCRT(List<BigInteger> ciphertext, BigInteger d, BigInteger p, BigInteger q) {
        StringBuilder messageBuilder = new StringBuilder();
        BigInteger n = p.multiply(q);
        BigInteger dp = d.mod(p.subtract(BigInteger.ONE));
        BigInteger dq = d.mod(q.subtract(BigInteger.ONE));

        for (int i = 0; i < ciphertext.size(); i++) {
            BigInteger mp,mq;
            BigInteger c = ciphertext.get(i);
            mp = Util.fastModularExp(c,dp,p);
            mq = Util.fastModularExp(c,dq,q);
            List<BigInteger> mList = new ArrayList<>(List.of(mp,mq));
            List<BigInteger> nList = new ArrayList<>(List.of(p,q));

            messageBuilder.append((char) Util.chineseRemainderTheorem(mList, nList, 2).intValue());
        }

        return messageBuilder.toString();
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getPhiN() {
        return phiN;
    }

    public void setPhiN(BigInteger phiN) {
        this.phiN = phiN;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }
}
