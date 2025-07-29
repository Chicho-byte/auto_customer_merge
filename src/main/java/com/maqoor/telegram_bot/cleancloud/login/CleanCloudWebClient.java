package com.maqoor.telegram_bot.cleancloud.login;

import com.maqoor.telegram_bot.exceptions.LoginFailureAttemptException;
import com.maqoor.telegram_bot.security.MyDecryptor;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.maqoor.telegram_bot.util.Constants.*;


@Slf4j
@Component
public class CleanCloudWebClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${cleancloud.admin.email}")
    private String email;

    @Value("${cleancloud.admin.password}")
    private String password;

    @Value("${cleancloud.admin.station}")
    private String station;

    @Value("${cleancloud.admin.store.id}")
    private String storeId;

    @Value("${cleancloud.admin.login.url}")
    private String url;

    protected final String decKey = "callcenter";

    private String decPassword ;

    @PostConstruct
    public void init(){
       decPassword = MyDecryptor.decrypt(password,decKey);
    }


    @Getter
    @Setter
    private String cookies;

    public List<String> logIn() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.USER_AGENT, "PostmanRuntime/7.36.3");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(LOGIN_EMAIL, email);
        body.add(LOGIN_PASSWORD, decPassword);
        body.add(STATION, station);
        body.add(CHOOSE_STORE,storeId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            var cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
            log.info("Response headers: {}", response.getHeaders());
            var responseBody = response.getBody();
            if(responseBody != null && responseBody.startsWith("{\"login\"")){
                log.info("Login response: {}", response.getBody());
            }else{
                log.error("Login failed : {}", responseBody);
                throw new LoginFailureAttemptException("Login Failed");
            }
            if (CollectionUtils.isEmpty(cookies)) {
                log.warn("Set-Cookie header is empty or null");
                throw new LoginFailureAttemptException("Set-Cookie header is empty or null");
            }
                log.info("Successfully returned-Cookie header: {}", cookies.getFirst());
                return cookies;


        } catch (Exception e) {
            log.error("Failed to log in", e);
            throw new LoginFailureAttemptException(e.getMessage());
        }
    }
}
