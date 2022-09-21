package com.jitterted.quizdown.adapter.port.notifier;

import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.port.QuizCompletedNotifier;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Profile("prod")
@Service
@Slf4j
public class QuizCompletedSendGridEmailNotifier implements QuizCompletedNotifier {

    private final SendGrid sendGrid;

    @Autowired
    public QuizCompletedSendGridEmailNotifier(SendGrid sendGrid) {
        log.info("SendGrid Email Notifier is active.");
        this.sendGrid = sendGrid;
    }

    @Override
    public void quizCompleted(User user) {
        String name = user.name().name();
        Email from = new Email("quizdown@tedmyoung.com");
        String subject = "A Quiz has been completed by " + name;
        Email to = new Email("ted@tedmyoung.com");
        Content content = new Content("text/plain", "Hey, " + name + " just finished taking the quiz. " +
                "Results can be found at http://localhost:8080/results?username=" + name);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        try {
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (IOException e) {
            log.warn("Failed to send via email notification", e);
        }
    }
}
