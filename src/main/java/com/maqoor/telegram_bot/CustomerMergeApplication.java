package com.maqoor.telegram_bot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
@SpringBootApplication()
public class CustomerMergeApplication {

	public static void main(String[] args) throws TelegramApiException {
		ApplicationContext context = SpringApplication.run(CustomerMergeApplication.class, args);

	}
}
