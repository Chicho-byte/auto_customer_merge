package com.maqoor.telegram_bot.service.impl;

import com.maqoor.telegram_bot.cleancloud.client.CleanCloudAPIClient;
import com.maqoor.telegram_bot.entity.customer.WebhookEventProcessor;
import com.maqoor.telegram_bot.entity.customer.Customer;
import com.maqoor.telegram_bot.exceptions.CustomerNotFoundException;
import com.maqoor.telegram_bot.service.CustomerService;
import com.maqoor.telegram_bot.util.PhoneUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.maqoor.telegram_bot.util.Constants.EVENT_SOURCE_CUSTOMER;
import static com.maqoor.telegram_bot.util.Constants.EVENT_SOURCE_CUSTOMER_CREATED;


@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CleanCloudAPIClient cleanCloudApiClient;


    @Override
    public void checkForMerge(WebhookEventProcessor webhookEventProcessor) {
        try {
            if (webhookEventProcessor.getEvent().equals(EVENT_SOURCE_CUSTOMER_CREATED)) {
                if (webhookEventProcessor.getSource().equals(EVENT_SOURCE_CUSTOMER)) {
                    Customer registeredCustomer = cleanCloudApiClient.getCustomer(webhookEventProcessor.getPhone());
                    if (!registeredCustomer.getSuccess().equals("True")) {
                        throw new CustomerNotFoundException("Customer with phone number - " + webhookEventProcessor.getPhone() + " was not found");
                    }
                    if (registeredCustomer.getAppCustomer().equals("1")) {
                        Customer posCustomer = cleanCloudApiClient.getCustomer(PhoneUtil.formatPhoneNumber(registeredCustomer.getPhoneNumber()));
                        if (!posCustomer.getSuccess().equals("True")) {
                            log.error("Customer with phone number - {} was not found", posCustomer.getPhoneNumber());
                            throw new CustomerNotFoundException(String.format("Customer with phone number - %s was not found", posCustomer.getPhoneNumber()));
                        } else if (posCustomer.getAppCustomer().equals("0")) {
                            log.info("App Customer , {} ,  {}", registeredCustomer.getPhoneNumber(), registeredCustomer.getQr());
                            log.info("Pos Customer , {} , {}", posCustomer.getPhoneNumber(), posCustomer.getQr());
                            cleanCloudApiClient.mergeCustomers(posCustomer.getId(), registeredCustomer.getId());
                            log.info("Merged Customer , {} ,  {}", posCustomer.getQr(), registeredCustomer.getQr());
                        } else {
                            log.info("The POS customer is the only customer in the system {} ", posCustomer);
                        }
                    }

                }
            }
        } catch (NullPointerException e) {
            log.error("Customer was not found during check for merge process");
            throw new CustomerNotFoundException("Customer was not found");
        }
    }
}

