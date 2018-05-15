package org.baeldung.test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.baeldung.config.ResourceServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = ResourceServerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ResourceServerIntegrationTest {

   /* @Autowired
    private JwtTokenStore tokenStore;
  */
    @Autowired
    private RedisTokenStore tokenStore;

    @Test
    public void whenLoadApplication_thenSuccess() {

    }

    @Test
    public void whenResoureServerAuthenticates_thenSuccess(){
        final String tokenValue = obtainAccessToken("fooClientIdPassword", "superuser", "superuser");
        System.out.println("TokenValue:"+tokenValue);
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        //String tokenAttribute = authorizeToken(tokenValue);
        int responseCode = authorizeToken(tokenValue, "http://localhost:7082/spring-security-oauth-resource/users/extra1");
        assertEquals(200,responseCode);
        //System.out.println("tokenAdditionalAttribute:"+tokenAttribute);
        //assertNotNull(tokenAttribute);
    }

    @Test
    public void testLogout(){
        final String tokenValue = obtainAccessToken("fooClientIdPassword", "superuser", "superuser");
        System.out.println("TokenValue:"+tokenValue);
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        //String tokenAttribute = authorizeToken(tokenValue);
        int responseCode = authorizeToken(tokenValue,"http://localhost:7082/spring-security-oauth-resource/users/extra1");
        System.out.println("users/extra1:"+responseCode);
        assertEquals(200,responseCode);

        responseCode = authorizeToken(tokenValue,"http://localhost:7082/spring-security-oauth-resource/v1/logout");
        System.out.println("users/logout:"+responseCode);
        assertEquals(200,responseCode);

        responseCode = authorizeToken(tokenValue,"http://localhost:7082/spring-security-oauth-resource/users/extra1");
        System.out.println("v1/extra1:"+responseCode);
        assertEquals(401,responseCode);

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
                .post("http://localhost:7081/spring-security-oauth-server/v1/login");
        System.out.println("Getting token Status Code:"+response.getStatusCode());
        System.out.println("Getting token body:"+response.print());
        return response.jsonPath()
                .getString("access_token");
    }

    private int authorizeToken(String tokenValue, String url){
        final Map<String, String> params = new HashMap<String, String>();
        params.put("Authorization", "Bearer "+tokenValue);
        final Response response = RestAssured.given()
                .header("Authorization", "Bearer "+tokenValue)
                .when()
                .get(url);
        System.out.println("Accesing resource Status Code:"+response.getStatusCode());
        System.out.println("Accesing resource body:"+response.print());
        return response.getStatusCode();
      /* return response.jsonPath()
                .getString("organization"); */
    }
}
