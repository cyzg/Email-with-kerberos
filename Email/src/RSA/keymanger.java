package RSA;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;


//import RSA.EXP;


public class keymanger {
	
	private static BigInteger e;
	
	private static BigInteger n;
	
	private static BigInteger d;
	
	
	
	/**
	 * 默认的构造函数，尝试生成2048位的密钥
	 */
	 public static BigInteger getP() {
		 BigInteger bi;

	      // create and assign value to bitLength
	      int bitLength = 125;

	      // create a random object
	      Random rnd = new Random();

	      // assign probablePrime result to bi using bitLength and rnd
	      // static method is called using class name
	      bi = BigInteger.probablePrime(bitLength, rnd);

		return bi;
	}
    
	
	/**
	 * 带参数的构造函数，生成2*nbit位的密钥
	 * @param nbit 参数p和q的位数
	 */
	
	public static  String[][] keymanger(){
		BigInteger p=getP();
		BigInteger q=getP();
		n = q.multiply(p);
		BigInteger fai = q.subtract(new BigInteger("1")).multiply(p.subtract(new BigInteger("1")));
		e = new BigInteger("3889");
		d = e.modInverse(fai);
		return new String[][]{{String.valueOf(n) , String.valueOf(e)}, {String.valueOf(n) ,String.valueOf(d)}};
	}


}