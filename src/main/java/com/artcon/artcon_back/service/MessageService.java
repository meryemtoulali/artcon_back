package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.Message;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public List<Message> getChatsForUser(Integer userId) {
        User user = new User();
        user.setId((int) userId.longValue());
        return messageRepository.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestamp(user, user, user, user);
    }

    public Message startChat(Integer userId, Integer receiverId, String content) {
        User sender = new User();
        sender.setId((int) userId.longValue());

        User receiver = new User();
        receiver.setId((int) receiverId.longValue());

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        // Set timestamp using LocalDateTime.now() or similar

        return messageRepository.save(message);
    }
}
