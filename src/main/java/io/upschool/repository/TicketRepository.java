package io.upschool.repository;

import io.upschool.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByIdAndStatus(Long id, Ticket.TicketStatus status);
    List<Ticket> findAllByFlight_IdAndStatus(Long flightId, Ticket.TicketStatus status);

    // Bilet numarasına göre bilet araması yapacak metot
    public Optional<Ticket> findByTicketNumber(String ticketNumber);
}