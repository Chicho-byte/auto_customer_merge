package com.maqoor.telegram_bot.entity.constant;


import lombok.Getter;

@Getter
public enum PaymentStatus {

    UNPAID("0","*\uD83D\uDFE0 Your order isn’t paid yet*"),
    PAID("1" ,"*✅ Order has been paid*") ;

    private final String number;
    private final String message;

    PaymentStatus(String number , String message) {
        this.number = number;
        this.message = message;
    }

    public static PaymentStatus fromNumber(String number) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getNumber().equals(number)) {
                return paymentStatus;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return  number ;
    }
}


