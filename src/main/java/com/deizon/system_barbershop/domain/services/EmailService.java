package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.models.Email;
import com.deizon.system_barbershop.domain.repositories.BarbeariaRepository;
import com.deizon.system_barbershop.domain.repositories.ClienteRepository;
import com.deizon.system_barbershop.domain.repositories.HorarioRepository;
import com.deizon.system_barbershop.domain.services.exceptions.SendEmailException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private BarbeariaRepository barbeariaRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;


    public void sendEmail(UUID id, Email email) {

        ArrayList<String> getClienteData = clienteRepository.getDataByReservas(id);
        ArrayList<Instant> getHorarioData = horarioRepository.getDataByReservas(id);
        String getBarbeariaData = barbeariaRepository.getDataByReserva(id);
        var dateTime = LocalDateTime.ofInstant(getHorarioData.get(0), ZoneOffset.UTC);
        email.setSendDateEmail(Instant.now());
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);

            var text = new StringBuilder();
            text.append("Olá ");
            text.append(getClienteData.get(0));
            text.append(", sua reserva na ");
            text.append(getBarbeariaData);
            text.append(". Foi agendada para o dia: ");
            text.append(dateTime.getDayOfMonth());
            text.append(" às ");
            text.append(dateTime.getHour());
            text.append(" horas.");

            helper.setFrom(emailFrom);
            helper.setTo(getClienteData.get(1));
            helper.setSubject("Reserva confirmada");
            helper.setText(text.toString());

            mailSender.send(message);
        } catch (MessagingException error) {
            throw new SendEmailException(error.getMessage());
        }
    }
}



