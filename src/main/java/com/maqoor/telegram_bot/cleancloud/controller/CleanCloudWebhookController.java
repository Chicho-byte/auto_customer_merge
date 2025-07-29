package com.maqoor.telegram_bot.cleancloud.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maqoor.telegram_bot.cleancloud.client.CleanCloudAPIClient;
import com.maqoor.telegram_bot.entity.customer.WebhookEventProcessor;
import com.maqoor.telegram_bot.exceptions.CustomerNotFoundException;
import com.maqoor.telegram_bot.service.CustomerService;
import com.maqoor.telegram_bot.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CleanCloudWebhookController {


    @Autowired
    public CustomerService customerService;

    @Autowired
    private CleanCloudAPIClient cleanCloudApiClient;

    @Autowired
    private ObjectMapper objectMapper;


    @RequestMapping("/webhook/receiver")
    public ResponseEntity<String> webhookReceiver(@RequestBody String payload) throws JsonProcessingException {

        log.info("Webhook received: {}", payload);
        WebhookEventProcessor webhookEventProcessor = objectMapper.readValue(payload, WebhookEventProcessor.class);
        if (webhookEventProcessor == null) {
            log.error("WebhookEventProcessor is null");
            throw new IllegalArgumentException("WebhookEventProcessor is null");
        }

        if (webhookEventProcessor.getEvent().equals(Constants.EVENT_SOURCE_CUSTOMER_CREATED)) {
            if (Constants.CUSTOMER_SOURCE.equals(webhookEventProcessor.getSource())) {
                try {
                    customerService.checkForMerge(webhookEventProcessor);
                    log.info("App Customer has been created");
                    return ResponseEntity.ok("Customer has been created");
                } catch (CustomerNotFoundException e) {
                    log.error(e.getMessage());
                }
            }

        }
            log.info("Pos Customer has been created");
            return ResponseEntity.ok("Pos Customer has been created");
        }
    }



