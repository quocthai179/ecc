package service;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class EccService {
    public static void main(String[] args) throws Exception {
        // 1. Thêm BouncyCastle Provider
        Security.addProvider(new BouncyCastleProvider());

        // 2. Tạo cặp khóa cho Alice
        KeyPair aliceKeyPair = generateECKeyPair("secp256k1"); // ECParam: secp256k1
        PrivateKey alicePrivateKey = aliceKeyPair.getPrivate();
        PublicKey alicePublicKey = aliceKeyPair.getPublic();

        // 3. Tạo cặp khóa cho Bob
        KeyPair bobKeyPair = generateECKeyPair("secp256k1"); // ECParam: secp256k1
        PrivateKey bobPrivateKey = bobKeyPair.getPrivate();
        PublicKey bobPublicKey = bobKeyPair.getPublic();

        // 4. Tạo Shared Secret cho Alice
        byte[] aliceSharedSecret = generateSharedSecret(alicePrivateKey, bobPublicKey);

        // 5. Tạo Shared Secret cho Bob
        byte[] bobSharedSecret = generateSharedSecret(bobPrivateKey, alicePublicKey);

        // 6. Kiểm tra tính đồng nhất của Shared Secret
        System.out.println("Shared Secret của Alice: " + bytesToHex(aliceSharedSecret));
        System.out.println("Shared Secret của Bob:   " + bytesToHex(bobSharedSecret));
        System.out.println("Shared Secret Match: " + MessageDigest.isEqual(aliceSharedSecret, bobSharedSecret));
    }

    // Tạo cặp khóa ECC
    public static KeyPair generateECKeyPair(String ecParam) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec(ecParam);
        keyPairGenerator.initialize(ecSpec, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    // Tạo Shared Secret
    public static byte[] generateSharedSecret(PrivateKey privateKey, PublicKey publicKey) throws Exception {
        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", "BC");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);
        return keyAgreement.generateSecret();
    }

    // Chuyển đổi byte[] sang chuỗi hex
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
