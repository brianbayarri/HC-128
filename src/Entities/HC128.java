package Entities;

public class HC128 {
	
	private byte[] key, iv;
	private int[] p = new int[512];
	private int[] q = new int[512];
	private byte[] buffer = new byte[4];
	
	public HC128(byte[] iv, byte[] key) {
		this.iv = iv;
		this.key = key;
	}
	
	public void initialization(){
		int[] w = new int[1280];

		byte firstByteKey = key[0];

		String[] s1 = String.format("%8s", Integer.toBinaryString(firstByteKey & 0xFF)).replace(' ', '0').split("");

		for (int i = 0; i <= 7; i++) {
			w[i] = Integer.parseInt(s1[i]);
		}

		byte primerByteIV = iv[0];
		String[] s2 = String.format("%8s", Integer.toBinaryString(primerByteIV & 0xFF)).replace(' ', '0').split("");

		for (int i = 8; i <= 15; i++) {
			w[i] = Integer.parseInt(s2[i - 8]);
		}

		for (int i = 16; i <= 1279; i++) {
			w[i] = f2(w[i - 2]) + w[i - 7] + f1(w[i - 15]) + w[i - 16] + i;
		}

		for (int i = 0; i <= 511; i++) {
			p[i] = w[i + 256];
			q[i] = w[i + 768];
		}

		for (int i = 0; i <= 511; i++) {
			p[i] = (p[i] + g1(p[mod512(i - 3)], p[mod512(i - 511)], p[mod512(i - 511)]) ^ h1(p[mod512(i - 12)]));
			q[i] = (q[i] + g2(p[mod512(i - 3)], p[mod512(i - 511)], p[mod512(i - 511)]) ^ h2(p[mod512(i - 12)]));
		}
	}
	
	public int f1(int x) {
		return Integer.rotateRight(x, 7) ^ Integer.rotateRight(x, 18) ^ (x >>> 3);
	}

	public int f2(int x) {
		return Integer.rotateRight(x, 17) ^ Integer.rotateRight(x, 19) ^ (x >>> 10);
	}

	public int g1(int x, int y, int z) {
		return (Integer.rotateRight(x, 10) ^ Integer.rotateRight(z, 23)) + Integer.rotateRight(y, 8);
	}

	public int g2(int x, int y, int z) {
		return (Integer.rotateLeft(x, 10) ^ Integer.rotateLeft(z, 23)) + Integer.rotateLeft(y, 8);
	}

	public int h1(int x) {
		return q[x & 0xFF] + q[((x >> 16) & 0xFF) + 256];
	}

	public int h2(int x) {
		return p[x & 0xFF] + p[((x >> 16) & 0xFF) + 256];
	}

	private static int mod512(int x) {
		return x & 0x1FF;
	}

	private int mod1024(int x) {
		return x & 0x3FF;
	}

	public byte[] encryption(byte[] data) {
		int index = 0;
		byte[] dataEncrypted = new byte[data.length];
		int newInteg;
		for (int i = 0; i < data.length; i++) {
			if (index == 0) {
				int j = mod512(i);
				if (mod1024(i) < 512) {
					p[j] = (p[j] + g1(p[mod512(j - 3)], p[mod512(j - 10)], p[mod512(j - 511)]));
					newInteg = h1(p[mod512(j - 12)] ^ p[j]);
				} else {
					q[j] = (q[j] + g1(q[mod512(j - 3)], q[mod512(j - 10)], q[mod512(j - 511)]));
					newInteg = h2(q[mod512(j - 12)] ^ q[j]);
				}
				buffer[3] = (byte) (newInteg & 0xFF);
				newInteg >>= 8;
				buffer[2] = (byte) (newInteg & 0xFF);
				newInteg >>= 8;
				buffer[1] = (byte) (newInteg & 0xFF);
				newInteg >>= 8;
				buffer[0] = (byte) (newInteg & 0xFF);
			}
			byte feedback = buffer[index];
			index = index + 1 & 0x3;
			dataEncrypted[i] = (byte) (data[i] ^ feedback);
		}
		return dataEncrypted;
	}
}
