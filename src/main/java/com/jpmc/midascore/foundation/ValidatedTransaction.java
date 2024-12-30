package com.jpmc.midascore.foundation;

import com.jpmc.midascore.entity.UserRecord;

public class ValidatedTransaction {
    private final UserRecord sender;
    private final UserRecord recipient;

    public ValidatedTransaction(UserRecord sender, UserRecord recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }

    public UserRecord getSender() {
        return sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }
}
