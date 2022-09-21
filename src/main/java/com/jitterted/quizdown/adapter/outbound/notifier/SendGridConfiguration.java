package com.jitterted.quizdown.adapter.outbound.notifier;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfiguration {

    @Value("${sendgrid.api.key}")
    String sendGridAPIKey;

    @Bean
    public SendGrid createSendGrid() {
        return new SendGrid(sendGridAPIKey);
    }

}