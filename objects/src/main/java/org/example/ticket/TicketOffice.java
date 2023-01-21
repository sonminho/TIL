package org.example.ticket;

public class TicketOffice {

    private final Long price;
    private Long amount = 0L;

    public TicketOffice(Long price) {
        this.price = price;
    }

    public Ticket getTicket() {
        return new Ticket(price);
    }

    public Long getAmount() {
        return this.amount;
    }

    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount) {
        this.amount += amount;
    }

}
