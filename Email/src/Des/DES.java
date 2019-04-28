package Des;

public class DES {
	
	/**
	 * 加密
	 * @param plainText  64位明文
	 * @param sk       64位密钥
	 * @return           64位密文
	 */
	static String encrypt(String plainText, String sk) {
		

		System.out.println("-----加密------");
		long t1=System.currentTimeMillis();
		System.out.println("密钥：");
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

		System.out.println("密文");
		String s = DESUtil.BytetoString(ciphertext);
		long t2=System.currentTimeMillis();
        System.out.println("加密时间："+(t2-t1)+"ms");
        
		return s;
	}

	/**
	 * 解密
	 * @param ciphertext  64位密文
	 * @param sKey        64位密钥
	 * @return            64位明文
	 */
	static String decrypt(String cipherText, String sk) {
		

		System.out.println("-----解密------");
        long t3=System.currentTimeMillis();
		System.out.println("密钥：");
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
		
		byte[] ciphertext = cipherText.getBytes();
		for (int i = 0; i < ciphertext.length; i++) 
			ciphertext[i] -= '0';
		
		
		byte[][] L = new byte[17][32];
		byte[][] R = new byte[17][32];
		byte[] plaintext = new byte[64];
		//子密钥的产生
		byte[][] K = DESUtil.generateKeys(sKey);
		//初始置换IP
		ciphertext = DESUtil.IP(ciphertext);
		//将密文分成左半部分R16和右半部分L16
		for (int i = 0; i < 32; i++) {
			R[16][i] = ciphertext[i];
			L[16][i] = ciphertext[i + 32];
		}
		//解密迭代
		for (int i = 16; i >= 1; i--) {
			L[i - 1] = xor(R[i], DESUtil.f(L[i], K[i - 1]));
			R[i - 1] = L[i];
			R[i] = xor(L[i - 1], DESUtil.f(R[i - 1], K[i - 1]));
		}
		//以L0为左半部分，R0为右半部分合并
		for (int i = 0; i < 32; i++) {
			plaintext[i] = L[0][i];
			plaintext[i + 32] = R[0][i];
		}
		//逆初始置换IP^-1
		plaintext = DESUtil.rIP(plaintext);
		
		System.out.println("明文：");
		String s = DESUtil.BytetoString(plaintext);
		
        long t4=System.currentTimeMillis();
        System.out.println("解密时间："+(t4-t3)+"ms");
        
		return s;
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
