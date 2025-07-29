package com.maqoor.telegram_bot.cleancloud.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maqoor.telegram_bot.cleancloud.login.CleanCloudWebClient;
import com.maqoor.telegram_bot.entity.customer.Customer;

import com.maqoor.telegram_bot.exceptions.CleanCloudClientException;
import com.maqoor.telegram_bot.security.MyDecryptor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.maqoor.telegram_bot.util.Constants.*;

@Slf4j
@Component
public class CleanCloudAPIClient {

    @Value("${cleancloud.api.token}")
    private String token;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CleanCloudWebClient cleanCloudWebClient;

    @Value("${cleancloud.api.url}")
    private String url;

    @Value("${cleancloud.merge.url}")
    private String mergeURL;

    private final String decKey = "bowe";

    private String decToken;


    @PostConstruct
    public void init() {
        decToken = MyDecryptor.decrypt(token, decKey);
    }

    public Customer getCustomer(String phoneNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> body = Map.of(
                API_CC_TOKEN, decToken,
                SEARCH_BY_PHONE, "1",
                CUSTOMER_API_TELEPHONE, phoneNumber,
                DEACTIVATED_CUSTOMERS, "1"
        );

        try {
            String jsonBody = objectMapper.writeValueAsString(body);
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url + "/getCustomer", request, String.class);
            if(!response.getStatusCode().is2xxSuccessful()){
                throw new CleanCloudClientException("Failed to get customer " + phoneNumber);
            }
            return objectMapper.readValue(response.getBody(), Customer.class);
        } catch (JsonProcessingException e) {
            throw new CleanCloudClientException("Failed to parse response of this customer with following phone  " + phoneNumber);
        }
    }


    public void mergeCustomers(String customerId1, String customerId2) {
        var cookies = cleanCloudWebClient.logIn();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(HttpHeaders.COOKIE, cookies.getFirst());
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add(MERGE_DISABLE_ID, customerId1);
        form.add(MERGE_MASTER_ID, customerId2);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(mergeURL, request, String.class);
            if(result.getStatusCode() == HttpStatusCode.valueOf(200)){
                log.info("Merged Customer ID {} and Customer ID2 {}", customerId1, customerId2);
            }else {
                log.error("Failed to merge customer with id " + customerId1 + " and " + customerId2 +
                        " with {}, message : {} ",result.getStatusCode(),result.getBody());
            }
        } catch (RestClientException e) {
            log.error("Merge failed", e);
            throw new CleanCloudClientException("Failed to merge customer " + customerId1);
        }
    }
}