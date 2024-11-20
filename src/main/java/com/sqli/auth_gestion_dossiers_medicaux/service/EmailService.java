package com.sqli.auth_gestion_dossiers_medicaux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.MailMessage;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            // Créer un message MIME pour envoyer du contenu HTML
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);  // Le 'true' permet d'envoyer un email HTML

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);  // 'true' pour indiquer que le contenu est HTML

            // Envoi de l'email
            emailSender.send(message);
            System.out.println("Email envoyé avec succès à : " + to);
        } catch (MailException e) {
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
