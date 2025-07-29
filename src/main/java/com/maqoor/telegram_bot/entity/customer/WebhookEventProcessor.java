package com.maqoor.telegram_bot.entity.customer;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

import static com.maqoor.telegram_bot.util.Constants.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebhookEventProcessor {

    @Getter
    @NotBlank(message = "Event must be no blank")
    private String event; // should be customer.created
    @Getter
    @NotBlank(message = "Source must be no blank")
    private String source; // should be customer store
    @NotBlank(message = "Id must be no blank")
    private String id;
    @NotNull
    private Map<String, String> data;


    public String getName() {
        return data.get(CUSTOMER_NAME);
    }
    public String getEmail(){
        return data.get(CUSTOMER_EMAIL);
    }
    public String getPhone() {
        return data.get(CUSTOMER_TEL);
    }
    public String getStatusNumber(String status){
        return data.get(status);
    }

}
