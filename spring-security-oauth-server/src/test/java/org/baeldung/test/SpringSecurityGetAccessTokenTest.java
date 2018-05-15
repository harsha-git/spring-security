package org.baeldung.test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.baeldung.AuthorizationServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Created by harsjaya on 5/14/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthorizationServerApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@Configuration
@ActiveProfiles("test")
public class SpringSecurityGetAccessTokenTest {

  /*  @Autowired
    private JwtTokenStore tokenStore;
  */

  @Autowired
  RedisTokenStore tokenStore;

    @Test
    public void testGetAccessToken(){
        //final String tokenValue = obtainAccessToken("fooClientIdPassword", "superuser", "superuser");
        final String tokenValue = obtainAccessToken("fooClientIdPassword", "c", "Password@1");
        System.out.println("TokenValue:"+tokenValue);
        assertNotNull(tokenValue);
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        assertNotNull(auth);
    }

    private String obtainAccessToken(String clientId, String username, String password) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("username", username);
        params.put("password", password);
        final Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic(clientId, "secret")
                .and()
                .with()
                .params(params)
                .when()
                .post("http://localhost:6081/spring-security-oauth-server/v1/login");
        return response.jsonPath()
                .getString("access_token");
    }


}
