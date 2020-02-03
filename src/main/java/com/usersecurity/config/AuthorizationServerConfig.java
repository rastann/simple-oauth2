package com.usersecurity.config;

import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.usersecurity.entities.Role;
import com.usersecurity.entities.User;
import com.usersecurity.services.UserService;

import lombok.SneakyThrows;

/**
 * Configures the authorization server.
 * The @EnableAuthorizationServer annotation is used to configure the OAuth 2.0 Authorization Server mechanism,
 * together with any @Beans that implement AuthorizationServerConfigurer (there is a handy adapter implementation with empty methods).
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private static final String RESOURCE_ID = "oauth2-resource";

    /**
     * Setting up the endpointsconfigurer authentication manager.
     * The AuthorizationServerEndpointsConfigurer defines the authorization and token endpoints and the token services.
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    /**
     * Setting up the clients with a clientId, a clientSecret, a scope, the grant types and the authorities.
     * @param clients
     * @throws Exception
     */

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        builder
            .withClient("fbiuser")
            .authorizedGrantTypes("client_credentials", "password")
            .authorities("ADMIN")
            .scopes("full", "public")
            .resourceIds(RESOURCE_ID)
            .accessTokenValiditySeconds(5000)
            .secret(passwordEncoder.encode("secret"));
    }

    /**
     * We here defines the security constraints on the token endpoint.
     * We set it up to isAuthenticated, which returns true if the user is not anonymous
     * @param oauthServer the AuthorizationServerSecurityConfigurer.
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()");
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

}
