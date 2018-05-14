package org.baeldung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Created by harsjaya on 5/5/18.
 */
public class CustomFilter extends GenericFilterBean{

    private JwtTokenStore tokenStore;

    public final static String OATH2_HEADER = "Authorization";

    /**
     * this is the bearer key that is used to identify this a s a bearer token
     */
    public final static String OATH2_TOKEN_TYPE = "Bearer";

    public CustomFilter(JwtTokenStore tokenStore){
        this.tokenStore = tokenStore;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        System.out.println("CustomFilter After Auth");
        String accessToken = getBearerToken((HttpServletRequest)request);
        OAuth2Authentication auth = tokenStore.readAuthentication(accessToken);
        Map<String, Object> details = (Map<String, Object>) auth.getDetails();
        System.out.println("CustomFilter AccessToken size:"+accessToken.getBytes().length);
        System.out.println("CustomFilter AccessToken deatils:"+details);
        System.out.println("CustomFilter AccessToken Authorities:"+auth.getAuthorities());
        chain.doFilter(request, response);
    }

    protected String getBearerToken(HttpServletRequest request) {
        String payload = request.getHeader(OATH2_HEADER);
        String parts[] = payload.split(" ");

        if (payload != null) {
            if((null != parts[0]) && parts[0].trim().equalsIgnoreCase(OATH2_TOKEN_TYPE)) {
                String token = payload.substring(OATH2_TOKEN_TYPE.length() + 1);
                return token.trim();
            }
        }
        return null;
    }
}
