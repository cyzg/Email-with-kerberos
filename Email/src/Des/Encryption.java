package Des;

public class Encryption {
	/**
	 * 加密
	 * @param plainText  64位明文
	 * @param sk       64位密钥
	 * @return           64位密文
	 */
	static String encrypt(String plainText, String sk) {
		

		long t1=System.currentTimeMillis();
		byte[] sKey = sk.getBytes();
		for (int i = 0; i < sKey.length; i++) 
		{
			sKey[i] -= '0';
			System.out.print(sKey[i]);
			if(i%8 == 7)
			{
				System.out.print(" ");
			}
		}
		
		System.out.println();
		
		byte[] plaintext = plainText.getBytes();
		for (int i = 0; i < plaintext.length; i++) 
			plaintext[i] -= '0';
		
		byte[][] L = new byte[17][32];
		byte[][] R = new byte[17][32];
		byte[] ciphertext = new byte[64];
		//子密钥的产生
		byte[][] K = DESUtil.generateKeys(sKey);
		//初始置换IP
		plaintext = DESUtil.IP(plaintext);
		//将明文分成左半部分L0和右半部分R0
		for (int i = 0; i < 32; i++) {
			L[0][i] = plaintext[i];
			R[0][i] = plaintext[i + 32];
		}
		//加密迭代
		for (int i = 1; i <= 16; i++) {
			L[i] = R[i - 1];
			R[i] = xor(L[i - 1], DESUtil.f(R[i - 1], K[i - 1]));
		}
		//以R16为左半部分，L16为右半部分合并
		for (int i = 0; i < 32; i++) {
			ciphertext[i] = R[16][i];
			ciphertext[i + 32] = L[16][i];
		}
		//逆初始置换IP^-1
		ciphertext = DESUtil.rIP(ciphertext);

		long t2=System.currentTimeMillis();
        System.out.println("加密时间："+(t2-t1)+"ms");
		
		return ciphertext.toString();
	}

	/**
	 * 两数组异或
	 * @param a
	 * @param b
	 * @return
	 */
	static byte[] xor(byte[] a, byte[] b) {
		byte[] c = new byte[a.length];
		for (int i = 0; i < a.length; i++) 
			c[i] = (byte) (a[i] ^ b[i]);
		return c;
	}
}