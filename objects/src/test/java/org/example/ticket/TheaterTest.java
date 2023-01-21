package org.example.ticket;

import org.junit.Assert;
import org.junit.Test;

public class TheaterTest {

    Theater theater;
    TicketOffice ticketOffice;
    TicketSeller ticketSeller;

    @Test
    public void testTheater() {
        // given
        Long ticketPrice = 10000L;
        Long initBagAmount = 100000L;
        ticketOffice = new TicketOffice(ticketPrice);
        ticketSeller = new TicketSeller(ticketOffice);

        // when
        Audience nonInvitationAudience = new Audience(new Bag(initBagAmount, null));
        Audience invitationAudience = new Audience(new Bag(initBagAmount, "invitation"));
        ticketSeller.sellTo(nonInvitationAudience);
        ticketSeller.sellTo(invitationAudience); // 초대장이 있으면 bag 금액 차감 안됨

        // then
        Long leftAmount = nonInvitationAudience.getBag().getAmount();
        Long invitedLeftAmount = invitationAudience.getBag().getAmount();

        Assert.assertEquals(initBagAmount - ticketPrice, leftAmount.longValue());
        Assert.assertEquals(initBagAmount.longValue(), invitedLeftAmount.longValue());
        Assert.assertEquals(ticketPrice.longValue(), ticketOffice.getAmount().longValue());
    }

}