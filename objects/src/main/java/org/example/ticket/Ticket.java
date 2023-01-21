package org.example.ticket;

public class Ticket {

    private Long fee = 0L;

    Ticket(Long fee) {
        this.fee = fee;
    }

    public Long getFee() {
        return fee;
    }

}
