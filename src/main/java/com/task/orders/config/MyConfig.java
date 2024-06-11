
package com.task.orders.config;


import com.task.orders.helpers.Constants;
import com.task.orders.helpers.Crypto;
import com.task.orders.redis.RedisHelper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {
    @Autowired
    private Crypto crypto;
    @Autowired
    private RedisHelper redisHelper;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    public String generateRedisToken(String id, String email,String name) {
        var key=crypto.encrypt(id+"//"+email+"//"+name);
        redisHelper.set(Constants.REDIS_KEY+id,key);
        return key;
    }
}
