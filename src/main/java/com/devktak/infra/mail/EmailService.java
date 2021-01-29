package com.devktak.infra.mail;

import com.devktak.infra.mail.form.EmailMessageForm;

public interface EmailService {

    void sendEmail(EmailMessageForm emailMessageForm);
}
