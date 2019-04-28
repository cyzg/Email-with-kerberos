package Des;

public class DES {
	
	/**
	 * ����
	 * @param plainText  64λ����
	 * @param sk       64λ��Կ
	 * @return           64λ����
	 */
	static String encrypt(String plainText, String sk) {
		

		System.out.println("-----����------");
		long t1=System.currentTimeMillis();
		System.out.println("��Կ��");
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
		//����Կ�Ĳ���
		byte[][] K = DESUtil.generateKeys(sKey);
		//��ʼ�û�IP
		plaintext = DESUtil.IP(plaintext);
		//�����ķֳ���벿��L0���Ұ벿��R0
		for (int i = 0; i < 32; i++) {
			L[0][i] = plaintext[i];
			R[0][i] = plaintext[i + 32];
		}
		//���ܵ���
		for (int i = 1; i <= 16; i++) {
			L[i] = R[i - 1];
			R[i] = xor(L[i - 1], DESUtil.f(R[i - 1], K[i - 1]));
		}
		//��R16Ϊ��벿�֣�L16Ϊ�Ұ벿�ֺϲ�
		for (int i = 0; i < 32; i++) {
			ciphertext[i] = R[16][i];
			ciphertext[i + 32] = L[16][i];
		}
		//���ʼ�û�IP^-1
		ciphertext = DESUtil.rIP(ciphertext);

		System.out.println("����");
		String s = DESUtil.BytetoString(ciphertext);
		long t2=System.currentTimeMillis();
        System.out.println("����ʱ�䣺"+(t2-t1)+"ms");
        
		return s;
	}

	/**
	 * ����
	 * @param ciphertext  64λ����
	 * @param sKey        64λ��Կ
	 * @return            64λ����
	 */
	static String decrypt(String cipherText, String sk) {
		

		System.out.println("-----����------");
        long t3=System.currentTimeMillis();
		System.out.println("��Կ��");
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
		//����Կ�Ĳ���
		byte[][] K = DESUtil.generateKeys(sKey);
		//��ʼ�û�IP
		ciphertext = DESUtil.IP(ciphertext);
		//�����ķֳ���벿��R16���Ұ벿��L16
		for (int i = 0; i < 32; i++) {
			R[16][i] = ciphertext[i];
			L[16][i] = ciphertext[i + 32];
		}
		//���ܵ���
		for (int i = 16; i >= 1; i--) {
			L[i - 1] = xor(R[i], DESUtil.f(L[i], K[i - 1]));
			R[i - 1] = L[i];
			R[i] = xor(L[i - 1], DESUtil.f(R[i - 1], K[i - 1]));
		}
		//��L0Ϊ��벿�֣�R0Ϊ�Ұ벿�ֺϲ�
		for (int i = 0; i < 32; i++) {
			plaintext[i] = L[0][i];
			plaintext[i + 32] = R[0][i];
		}
		//���ʼ�û�IP^-1
		plaintext = DESUtil.rIP(plaintext);
		
		System.out.println("���ģ�");
		String s = DESUtil.BytetoString(plaintext);
		
        long t4=System.currentTimeMillis();
        System.out.println("����ʱ�䣺"+(t4-t3)+"ms");
        
		return s;
	}
	
	/**
	 * ���������
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
