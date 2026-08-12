package com.gunitha.springai.tool;

import com.gunitha.springai.entity.Ticket;
import com.gunitha.springai.mode.request.TicketRequest;
import com.gunitha.springai.service.TicketService;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketTool {

    @Autowired
    TicketService ticketService;

    @Tool(name = "createTicket",description = "It creates the support or helpdesk ticket")
    public String createTicket(@ToolParam(description = "Details to create the support ticket")TicketRequest ticketRequest
    , ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("username");
        ticketService.create(username,ticketRequest);
        return "Ticket # : "+toolContext.toString();
    }

    @Tool(name = "tickets", description = "fetch the tickets on username", returnDirect = true)
    public List<Ticket> tickets(ToolContext toolContext) {
        return ticketService.findByUserName((String) toolContext.getContext().get("username"));
    }
}
