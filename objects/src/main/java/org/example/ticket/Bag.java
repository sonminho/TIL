package org.example.ticket;

public class Bag {

    private Ticket ticket;
    private Long amount;
    private Object invitation; // 초대권

    public Bag(Long amount, Object invitation) {
        this.amount = amount;
        this.invitation = invitation;
    }

    public Long hold(Ticket ticket) {
        if(hasInvitation()) {
            this.ticket = ticket;
            return 0L;
        }

        this.ticket = ticket;
        minusAmount(ticket.getFee()); // 티켓의 비용만큼 차감

        return ticket.getFee();
    }

    public Long getAmount() {
        return this.amount;
    }
    public boolean hasInvitation() {
        return invitation != null;
    }

    public void minusAmount(Long amount) {
        this.amount -= amount;
    }

    public void plusAmount(Long amount) {
        this.amount += amount;
    }

}
