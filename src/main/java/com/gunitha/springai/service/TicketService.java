package com.gunitha.springai.service;

import com.gunitha.springai.entity.Ticket;
import com.gunitha.springai.mode.request.TicketRequest;
import com.gunitha.springai.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    public List<Ticket> findByUserName(String userName) {
        return ticketRepository.findByUserName(userName);
    }

    public Ticket create(String userName, TicketRequest ticketRequest) {
        Ticket ticket = Ticket.builder()
                .userName(userName)
                .issue(ticketRequest.issue())
                .status("OPEN")
                .issueTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusDays(15))
                .build();
        return ticketRepository.save(ticket);
    }

}
