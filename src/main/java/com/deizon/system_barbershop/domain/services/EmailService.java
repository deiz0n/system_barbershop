package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.models.Email;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.repositories.EmailRepository;
import com.deizon.system_barbershop.domain.services.exceptions.SendEmailException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public Email sendEmail(Reserva reserva, Email email)  {


        email.setSendDateEmail(Instant.now());
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);

            var text = new StringBuilder();
            text.append("Olá ");
            text.append(reserva.getCliente().getNome());
            text.append(", sua reserva na ");
            text.append(reserva.getHorario().getBarbearia().getNome());
            text.append("foi agendada para o dia: ");
            text.append(reserva.getHorario().getHorarioInicial());
            text.append(" às ");
            text.append(reserva.getHorario().getHorarioFinal());

            helper.setFrom(emailFrom);
            helper.setTo((reserva.getCliente().getEmail()));
            helper.setSubject("Reserva comfirmada");
            helper.setText(text.toString());

            mailSender.send(message);
        } catch (MessagingException error) {
            throw new SendEmailException(error.getMessage());
        }
        return emailRepository.save(email);
    }
}
