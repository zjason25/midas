package com.jpmc.midascore.service;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.ValidatedTransaction;
import com.jpmc.midascore.entity.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public void processTransaction(Transaction transaction) {
        ValidatedTransaction validatedTransaction = validateAndFetchUsers(transaction);
        if (validatedTransaction == null) {
            logger.info("Transaction is not valid: {}", transaction);
            return;
        }

        UserRecord sender = validatedTransaction.getSender();
        UserRecord recipient = validatedTransaction.getRecipient();

        sender.setBalance(sender.getBalance() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getAmount());

        logger.info("Transaction from {} to {} for amount ${} processed successfully.",
                sender.getName(), recipient.getName(), transaction.getAmount());

        userService.updateUser(sender);
        userService.updateUser(recipient);

        logger.info("User waldorf balance: {}", userService.findUserByName("waldorf").getBalance());
    }

    private ValidatedTransaction validateAndFetchUsers(Transaction transaction) {
        UserRecord sender = userService.findUserById(transaction.getSenderId());
        UserRecord recipient = userService.findUserById(transaction.getRecipientId());

        // Check if both sender and recipient exist
        if (sender == null || recipient== null) {
            logger.info("Invalid IDs: Sender or Recipient not found.");
            return null;
        }

        if (sender.getBalance() < transaction.getAmount()) {
            logger.info("Insufficient balance for sender ID: {}.", transaction.getSenderId());
            return null;
        }

        return new ValidatedTransaction(sender, recipient);
    }
}