package RSA;
import java.math.BigInteger;

public class rsa {

	public static String StringToBinary(String string) 
	{
		char M[] = string.toCharArray();
		//System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
		int M1[] = new int[M.length];
		String tmp = new String(); 

		String s ="";  //���ж����Ƶ��ۼ�
		for(int i=0;i<M.length;i++)
		{
			if (Character.isDigit(M[i])){  // �ж��Ƿ�������
			    M1[i] = Integer.parseInt(String.valueOf(M[i]));
			}
			else {
				System.err.println("StringתBinary��������������");
			}
	
			tmp = supplement(4, Integer.toBinaryString(M1[i]));
			//ÿһλ��ת���˶�����
			s = s + tmp; //����string��
		}
 		return s;		
	}
	
/**
 * ��str���뵽nλ����λд0
 * @param n 
 * @param str Ҫ������ַ���
 * @return
 */
public static String supplement(int n,String str){ 
	if(n>str.length()) {
		int sl=str.length();//stringԭ����
		for(int i=0;i<(n-sl);i++) {
			str="0"+str;
		}
	}
	return str;
}
public static String StringToBinary2(String string) 
{
	char M[] = string.toCharArray();
	//System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
	int M1[] = new int[M.length];
	String tmp = new String(); 

	String s ="";  //���ж����Ƶ��ۼ�
	for(int i=0;i<M.length;i++)
	{
		if (Character.isDigit(M[i])){  // �ж��Ƿ�������
		    M1[i] = Integer.parseInt(String.valueOf(M[i]));
		}
		else {
			System.err.println("StringתBinary��������������");
		}

		tmp = supplement(3, Integer.toBinaryString(M1[i]));
		//ÿһλ��ת���˶�����
		s = s + tmp; //����string��
	}
		return s;		
}
public static String BinaryToString2(String string) 
{
	int length = string.length();
	String str = string;
	if(length%3 != 0) {
		str = supplement((length+3-length%3), string);
		length = str.length();
	}
	char C[] = str.toCharArray();
	
	String M[] = new String[length/3];
	for(int i=0;i<M.length;i++){
		M[i] = "";
	}
	//System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
	int M1[] = new int[length/3];

	String s ="";  //���ж����Ƶ��ۼ�
	for(int i1=0;i1 < length;i1++)
	{
		M[i1/3] = M[i1/3]+C[i1];
		if(i1%3 == 2) {
			M1[i1/3] = Integer.parseInt(M[i1/3],2);
			s = s + M1[i1/3]; //����string��
		}
	}
		return s;		
}
public static String BinaryToString(String string) 
{
	int length = string.length();
	String str = string;
	if(length%4 != 0) {
		str = supplement((length+4-length%4), string);
		length = str.length();
	}
	char C[] = str.toCharArray();
	
	String M[] = new String[length/4];
	for(int i=0;i<M.length;i++){
		M[i] = "";
	}
	//System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
	int M1[] = new int[length/4];

	String s ="";  //���ж����Ƶ��ۼ�
	for(int i1=0;i1 < length;i1++)
	{
		M[i1/4] = M[i1/4]+C[i1];
		if(i1%4 == 3) {
			M1[i1/4] = Integer.parseInt(M[i1/4],2);
				
			s = s + M1[i1/4]; //����string��
		}
	}
		return s;		
}


/**
 * ����
 * @param plainText
 * @param pubkey
 * @return
 */
public String encrypt(String plainText, String[] pubkey){
	System.out.println("-----RSA��ʼ����-----");
	BigInteger n = new BigInteger(pubkey[0]) ;
	BigInteger e = new BigInteger(pubkey[1]) ;
	plainText = BinaryToString2("1"+plainText);
	//System.out.println(BinaryToString2(plainText));
	BigInteger m = new BigInteger(plainText);
	BigInteger c = m.modPow(e, n);
	String	C=StringToBinary(c.toString());
    return C.toString() ;
}
/**
 * ����
 * @param cipherText
 * @param selfkey
 * @return
 */
public String decrypt(String cipherText, String[] selfkey){
	System.out.println("-----RSA��ʼ����-----");
	BigInteger n = new BigInteger(selfkey[0]) ;
	BigInteger d = new BigInteger(selfkey[1]) ;
	BigInteger c = new BigInteger(BinaryToString(cipherText));
	BigInteger m = c.modPow(d, n);
	String m2 = String.valueOf(m);
	String	M = StringToBinary2(m2);
	while(M.charAt(0) == '0')
	M = M.replaceFirst("0", "");
	
	M = M.replaceFirst("1", "");
	
	return M.toString() ;
}

	
	

	
	
	
}
