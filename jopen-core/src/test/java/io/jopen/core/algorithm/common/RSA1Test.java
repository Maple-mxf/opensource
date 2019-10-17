package io.jopen.core.algorithm.common;

import io.jopen.core.common.code.RSA1;
import org.junit.Test;

import java.util.Map;

import static io.jopen.core.common.code.RSA1.*;

/**
 * @author maxuefeng
 */

public class RSA1Test {

    @Test
    public void getKey() {
        Map<String, Object> map = RSA1.init();


        System.err.println(RSA1.getPublicKey(map));
        System.err.println(RSA1.getPrivateKey(map));
    }

    public static void main(String[] args) {

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdVUemCpFbHk4qQDK3P/FOs5oHZJsNg75Dy6JKqktG1QJgwQ2o5giA+GxGYYQxMhdRzZ4FxDGEQHe2GE/CQjei/orSY8qhzwTM3fDuOh2J4YHZYzRha2swPoD9ZZHqVMw/dBmlsgwVsnwxkvO6so9j7bW4kKIcm+s3qMglJk4zFQIDAQAB";
        String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ1VR6YKkVseTipAMrc/8U6zmgdkmw2DvkPLokqqS0bVAmDBDajmCID4bEZhhDEyF1HNngXEMYRAd7YYT8JCN6L+itJjyqHPBMzd8O46HYnhgdljNGFrazA+gP1lkepUzD90GaWyDBWyfDGS87qyj2PttbiQohyb6zeoyCUmTjMVAgMBAAECgYABZkvP31IxtFBPseiBltEHuTNKk4uJkD12Du6vqVooFCcEQ1NiyEEVHKCh86YgZeitMp4QQRGLsqMd/JNhEAO8rNGOFCR6a+VLXPbE2T4gk4encOZz4ul2/7bM/b43zSKaJLti5JWbRf0KJDjJUAXoXxvKVsfX1sP+W4OR3GARwQJBAPPNoqbmpi3k1p+MamegnWtolsiy9CdfujhNSfK4vQ8oz/DmKsM95SCvAGKbULRrdIzbiHF6m4LoSoezO3kzacUCQQClNDrZMCPc9+OwEEna13WdFADu3Gp4lWFOviZBk48JvhlM9Uqk0OMGd4JPHVbBcly1h3Breqmq3m0gPESYf0kRAkBDcfW3GkOKmq3NAXlGJaam2g4sg2ULp23KuYblgLm8gdvUlrfpvyutuPvwS2suHjPl6615RyPSORS1KZ4gT1rJAkBwKXDW6k8/A/JLJBPwh54rolwrz+xdqBXjz0fL9/ICTFWDIXqnQccRKeyH6ERxnEKDYMwbvLEa+F6gpypvWuPxAkB4dco6o5wF1FGte3XkzFJbPh4eoEfwNiWwk/vgHa0Ggdk2TMAZM3xXcDMThchqRjO9so4OokFAtEw3Z5xMShFw";
        String str = "{\"code\":1,\"data\":\"数据转换成功\",\"msg\":\"OK\",\"success\":true}";


        // 加密
        String enStr1 = encryptByPublic(str, publicKey);

        System.err.println(enStr1);

        //
        String source = decryptByPrivate(enStr1, privateKey);

        System.err.println(source);

        String sign = sign(enStr1, privateKey);

        System.err.println(sign);


        System.out.println(verify(enStr1, publicKey, sign));
    }

    @Test
    public void testSimpleAPI() {

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLNbmKl9/gLn7Bef/xtUkshC1WyrLZLRpXCcFYR1gQi0isWsZBTicC4efBOkkNG3r+1ue0gvtuU/tjREFGf4Y7HaKHGb5tNCOlMNeNjM5YLRwLFqrUSsQyD4rj4eua1ltearr24R0HilnTvnQm6Z/UY0s21vdOUFQBPY0GNAa+0wIDAQAB";
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIs1uYqX3+AufsF5//G1SSyELVbKstktGlcJwVhHWBCLSKxaxkFOJwLh58E6SQ0bev7W57SC+25T+2NEQUZ/hjsdoocZvm00I6Uw142MzlgtHAsWqtRKxDIPiuPh65rWW15quvbhHQeKWdO+dCbpn9RjSzbW905QVAE9jQY0Br7TAgMBAAECgYBcYhbzpr5no/Nyqmf0G/6nkEAWbQYrogbs5AhvcUk8EXL1DnirNhYlj42hafC4xhflrvCtlo8NNKaLxewbwN1uuzG8A2jd+ROEXlx5HDh2ZluhtHzL/SmNcJXo684xAl2pCNVBjDcW48PcIBijke/sTVHTDsDCukLKDPUOM/mKIQJBAL96k4+jBscazsJiuZ6C3RFDVtRRDpf1dMgLgxcx63bAXkA2Arau0J49IAYmSVJoDXqDoJKWdXJVh9vHSkhN/48CQQC6Hk1/G0Y0nOylf6NOp0oMgc0A+etnwxHKqwtctPKjEYcJx2fzALzTtCoySLYXX7gLnPIQXpQBTUysG5skBKp9AkEAiSQm6fqu0Q4fRlRlc+VwpnufhgPkOuw/z0OHiaZkajJPjxfgC63bl2paNG1ZmJ8UAEqkSDlhNxmRa9UqG+1ZewJASaQxz6gwCCNLM1SkfjuM/hPh1JAOh9jUUleJQF5MXx9RSho/VBQnorB3vbutaOQzw0yPLtDtSPKX8sVdhkveVQJAIDsJP5X8Tey6zXTUISor7PF0TSiKdE4k0IwKoy9y8HmQ+AU8+xyr/iOt5lvaGxKlBK8N/7yCw5H4qHnJaHT+Bg==";


        String str = "你好goldlone, RSA!";

        // 公钥加密，私钥解密
        String enStr1 = encryptByPublic(str, publicKey);
        System.out.println("公钥加密后：" + enStr1);

        //
        String deStr1 = decryptByPrivate(enStr1, privateKey);
        System.out.println("私钥解密后：" + deStr1);

        // 私钥加密，公钥解密
        String enStr2 = encryptByPrivate(str, privateKey);
        System.out.println("私钥加密后：" + enStr2);


        //
        String deStr2 = decryptByPublic(enStr2, publicKey);
        System.out.println("公钥解密后：" + deStr2);

        // 产生签名
        String sign = sign(enStr2, privateKey);
        System.out.println("签名:" + sign);

        // 验证签名
        boolean status = verify(enStr2, publicKey, sign);
        System.out.println("状态:" + status);
    }


    @Test
    public void testVerify() {

        String publicKey1 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwy9uEhtIaRrYmG/LFUvrR15kREBuTc6Z+Vsj7JzwKda4hhyUCKq7lQh/vJlN8624jm09CO/FK0KI6W6RKwYDs0ZkQLtMoqJ+KQXpD0UPNa0HJM9Ej8ukNVlVlknj5NBKtcDQjlNOZxsFIwegpouqAJ+xEaK7ew3jRq1PaAQLyIwIDAQAB";
        String privateKey1 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALDL24SG0hpGtiYb8sVS+tHXmREQG5Nzpn5WyPsnPAp1riGHJQIqruVCH+8mU3zrbiObT0I78UrQojpbpErBgOzRmRAu0yion4pBekPRQ81rQckz0SPy6Q1WVWWSePk0Eq1wNCOU05nGwUjB6Cmi6oAn7ERort7DeNGrU9oBAvIjAgMBAAECgYAKaFXJ3W3s4hDC+Y43E4V0aO631GzrJzm74jv+nPap3vebPatuDzkwgc4DdAwiJHHdYr0ttv2jW9jQ+qgXRoDiVtf0sqv7mv91n8C2/A9fUk3aFpZdO4JC3ALA59tW60UpY1JgYViJewlh5C2pUpcXD0iVXlrLj5kWk7X54IxukQJBAO3ugZn6QQq3NeN2YYekdD11BKBrEo0dar2HfrFfXykDYJUacewLmR+cyazqxiOo4q7hNc9ym/xX0JZkI5fxu70CQQC+ONqVVTyeohWDrsRgStoyGfrTVeOe5RddYBa/YAUZyQ1jRxnbJpIh+dWjjSXnAlXIsZD90dT/4uI9YGlnMFNfAkBIxBeYysNXjp6JeSvsuuvd28rVcMmUpLl/S1AA2QUWWvUPxK2wnf9vfpjjLE36m+zvxOqbDhcMfqLJS8ji8EgVAkBTpoTZLsadrAFyCyTog4I4jhwzW43hjjUcJulZ90vG7xcoFVhVezzAHDHK3oNQd0JqI0vzkpbsgEWlYAR6EFJZAkEAuhI1xvZ+gXNJhOH5B0puW9qQo/qLNfYBu17Gr7WId9lF9p+5gKcvMiuRkAcvB1lzBnSKUihACH3KkMcugzpqfA==";

        String publicKey2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNV+eXI4XVIgDdC7dbe6CWPkkzPQ51tKGqXQJg88NsJS+6JTZkKm5nbr5QZR8U3wz3y4RVSZR03Dtl5e9xVyYMPWxPVXEcFh+PCgWdu79c5mWwLmIJ1zH71wxnMGtO2vD//SHbXGD6DIg83KP+l9WUdk4AF0Pt1V7lVJmdj7mMJQIDAQAB";
        String privateKey2 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI1X55cjhdUiAN0Lt1t7oJY+STM9DnW0oapdAmDzw2wlL7olNmQqbmduvlBlHxTfDPfLhFVJlHTcO2Xl73FXJgw9bE9VcRwWH48KBZ27v1zmZbAuYgnXMfvXDGcwa07a8P/9IdtcYPoMiDzco/6X1ZR2TgAXQ+3VXuVUmZ2PuYwlAgMBAAECgYEAhZMwg04H2Y8j/8Jlyuf/sF02slyeQLCvI6B4cgWMhrRx8VnfmDPdWJR4+ONHx2KwD7He4vcBfJrw63Cso5E3m2sYy7rLSo9L0LlrOJTyAWoA6CXwnUh+rGKAB2rAGV/foQG8zu6AbntBlzKURNtPWbLdQwX30PdkIAtrliDb9wECQQDNBdt+yY6UQDQvxAYN32NbE70oODX3oPOIg5wHKeKZJOo9pkWevzxBr2E4prVRY2W4mUmbi0T5rPEro+aixrBBAkEAsHy2HVSxoFmxtLzmKKIoqAIrrCf8w6VU97P5p3c7AyCJ0uKyfbyarCX2yr3tnpoB7RuFwhrdUFnJieJ3o0Vi5QJAF15pjGQQ+ljvGdqwDfi+bG0tjF4L2LPhzleh+zcM+wO4J1rCaLvHdH03CawG0sYPnCDswOv46rSJsL231inrgQJAUGy0bM9QlprpIUzHsZKb5lv+3NfoFpv8CVkJlqf9y2IDjIdkAWp2LaarPR6p7xGNeSiGnrqxSdFwxxXdoRiYhQJAA2H2EA470y4w8jWFVLlceH5YCJI1oKK9BluqfukcTY9YRUqInMPKqS5QRYghXjAHG3sbbMQfH6kSzexH8aBzJQ==";

        // 将公钥pubkey2通过公钥pubkey1加密，加密之后传输给服务端
        String decryptByPublic = decryptByPublic(publicKey2, publicKey1);

        //

    }

}
