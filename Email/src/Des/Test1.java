package Des;

public class Test1 {
	public static void main(String[] args) {
		String sKey = "0011000100110010001100110011010000110101001101100011011100111000";

		String plaintext = "0011000000110001001100100011001100110100001101010011011000110111";
		String ciphertext = DES.encrypt(plaintext, sKey);
		
		String plainText = DES.decrypt(ciphertext, sKey);
	}
}
