package model;

public class EccResponse {
    private String publicKey1;
    private String privateKey1;

    private String publicKey2;
    private String privateKey2;

    public EccResponse() {
    }

    public EccResponse(String publicKey1, String privateKey1, String publicKey2, String privateKey2) {
        this.publicKey1 = publicKey1;
        this.privateKey1 = privateKey1;
        this.publicKey2 = publicKey2;
        this.privateKey2 = privateKey2;
    }

    public String getPublicKey1() {
        return publicKey1;
    }

    public void setPublicKey1(String publicKey1) {
        this.publicKey1 = publicKey1;
    }

    public String getPrivateKey1() {
        return privateKey1;
    }

    public void setPrivateKey1(String privateKey1) {
        this.privateKey1 = privateKey1;
    }

    public String getPublicKey2() {
        return publicKey2;
    }

    public void setPublicKey2(String publicKey2) {
        this.publicKey2 = publicKey2;
    }

    public String getPrivateKey2() {
        return privateKey2;
    }

    public void setPrivateKey2(String privateKey2) {
        this.privateKey2 = privateKey2;
    }
}
