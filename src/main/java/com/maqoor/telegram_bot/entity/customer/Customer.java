package com.maqoor.telegram_bot.entity.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "customers")
public class Customer implements Persistable<String> {

    @Id
    @JsonProperty("ID")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Tel")
    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonProperty("usedApp")
    @Column(name = "app_customer")
    private String appCustomer;

    private String qr;

    @JsonProperty("Success")
    private String success;


    @Override
    public boolean isNew() {
        return false;
    }
}
