import java.math.BigInteger;

public class POICproof {
    BigInteger A,B,C,Z1,Z2,Z3,e;


    public POICproof(BigInteger a, BigInteger b, BigInteger c, BigInteger z1, BigInteger z2, BigInteger z3, BigInteger e) {
        A = a;
        B = b;
        C = c;
        Z1 = z1;
        Z2 = z2;
        Z3 = z3;
        this.e = e;
    }

    public BigInteger getA() {
        return A;
    }

    public BigInteger getB() {
        return B;
    }

    public BigInteger getC() {
        return C;
    }

    public BigInteger getZ1() {
        return Z1;
    }

    public BigInteger getZ2() {
        return Z2;
    }

    public BigInteger getZ3() {
        return Z3;
    }

    public BigInteger getE() {
        return e;
    }
}
