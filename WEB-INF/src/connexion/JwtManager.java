// code pompé ici : https://developer.okta.com/blog/2018/10/31/jwts-with-java
// lui-même inspiré par : https://www.baeldung.com/java-json-web-tokens-jjwt
// et sinon la doc : https://github.com/jwtk/jjwt/blob/master/README.md
package connexion;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.*;

import io.jsonwebtoken.*;


public class JwtManager {
    // pour SHA256 : 256 bits mini

    public static String createJWT(String login, String password) {
        // The JWT signature algorithm we will be using to sign the token
        String pwd = DigestUtils.sha256Hex(password);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(pwd);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder token = Jwts.builder()
                .setId(UUID.randomUUID().toString().replace("-", ""))
                .setIssuedAt(now)
                .setSubject("SecretJWT")
                .setIssuer(login)
                .signWith(signingKey, signatureAlgorithm);

        // if it has been specified, let's add the expiration
        long ttlMillis = 1000 * 60 * 4; // 4mn
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            token.setExpiration(exp); // 20mn par defaut
        }
        // Builds the JWT and serializes it to a compact, URL-safe string
        return token.compact();
    }

    public static Claims decodeJWT(String jwt, String password) throws Exception {
        // This line will throw an exception if it is not a signed JWS (as expected)
        String pwd = DigestUtils.sha256Hex(password);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Base64.getDecoder().decode(pwd))
                .build()
                // verifie la signature et l'iat
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static String getlogin(String tokent) {
        String token[]=tokent.split("\\.");
        String decode=new String(java.util.Base64.getDecoder().decode(token[1]));
        String payload[]=decode.split(",");
        String iss[]=payload[1].split(":");
        return iss[1].replace("\"","").replace("}","");
    }
}
