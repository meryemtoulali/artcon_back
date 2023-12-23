package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Message;
import com.artcon.artcon_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiverOrReceiverAndSenderOrderByTimestamp(
            User sender1, User receiver1, User sender2, User receiver2
    );
}

