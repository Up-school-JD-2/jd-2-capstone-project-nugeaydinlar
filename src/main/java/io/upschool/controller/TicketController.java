package io.upschool.controller;

import io.upschool.dto.ticketDTO.TicketRequestDto;
import io.upschool.entity.Ticket;
import io.upschool.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/buy")
    public ResponseEntity<Ticket> buyTicket(@RequestBody @Valid TicketRequestDto ticketRequestDto) {
        Ticket ticket = ticketService.buyTicket(ticketRequestDto);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Ticket> findTicketByTicketNumber(@PathVariable("ticketNumber") String ticketNumber) {
        Ticket ticket = ticketService.findTicketByTicketNumber(ticketNumber);
        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable("ticketId") Long ticketId, @RequestBody @Valid TicketRequestDto ticketRequestDto) {
        Ticket updatedTicket = ticketService.updateTicket(ticketId, ticketRequestDto);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<Void> cancelTicket(@PathVariable("ticketId") Long ticketId) {
        ticketService.cancelTicket(ticketId);
        return ResponseEntity.noContent().build();
    }
}