package de.daniel.dengler.getRichBot;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.jfree.util.Log;

public class ApiKeySecretPair implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7643170329660725210L;
	public String apiKey = "un2D7K2wubnk1cEsDQsCWYB1YWkzodJ3RLsj4QPnowK",
			apiSecret = "b9SdFF2Kx9VmRMz0qQB31DyWFYARHszTycldjohl1nF";

	public ApiKeySecretPair(String apiKey2, String apiSecret2) {
		this.apiKey = apiKey2;
		this.apiSecret = apiSecret2;
		}

	public String getApiKey() {
		return apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}
	
	public String getEncoded(String payload){
		return encode(apiSecret,payload);
		
	}

	public static String encode(String key, String data) {
		Mac hMAC = null;
		try {
			hMAC = Mac.getInstance("HmacSHA384");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SecretKeySpec secret_key = null;
		try {
			secret_key = new SecretKeySpec(key.getBytes("UTF-8"),
					"HmacSHA384");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			hMAC.init(secret_key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		byte[] doFinal = null;
		try {
			doFinal = hMAC.doFinal(data.getBytes("ASCII"));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (byte b : doFinal) {
			//sb.append(String.format("%02x", b));
			String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
            	sb.append('0');
            }
            sb.append(hex);
		}
		return sb.toString();
	}

	public static String hmacDigest(String msg, String keyString, String algo) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return digest;
    }
	
}
