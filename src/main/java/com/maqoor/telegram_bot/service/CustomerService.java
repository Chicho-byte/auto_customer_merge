package com.maqoor.telegram_bot.service;

import com.maqoor.telegram_bot.entity.customer.WebhookEventProcessor;

public interface CustomerService {
     void checkForMerge(WebhookEventProcessor webhookEventProcessor);
}
