package com.deizon.system_barbershop.domain.services;

import com.deizon.system_barbershop.domain.models.Barbearia;
import com.deizon.system_barbershop.domain.models.Cliente;
import com.deizon.system_barbershop.domain.models.Email;
import com.deizon.system_barbershop.domain.models.Reserva;
import com.deizon.system_barbershop.domain.repositories.BarbeariaRepository;
import com.deizon.system_barbershop.domain.repositories.ClienteRepository;
import com.deizon.system_barbershop.domain.repositories.HorarioRepository;
import com.deizon.system_barbershop.domain.services.exceptions.SendEmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmailServiceTest {

    public static final String FROM = "emailfrom@gmail.com";
    public static final UUID ID = UUID.randomUUID();
    public static final String TO = "emailto@gmail.com";
    public static final String SUBJECT = "TÍTULO TESTE";
    public static final String TEXT = "TESTE TESTE TESTE TESTE";
    public static final Instant INSTANT = Instant.now();
    @Value("${spring.mail.username}")
    private String emailFrom;

    @InjectMocks
    private EmailService service;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private HorarioRepository horarioRepository;
    @Mock
    private BarbeariaRepository barbeariaRepository;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private MimeMessageHelper helper;
    @Mock
    private MimeMessage mimeMessage;


    private MimeMailMessage message;

    private StringBuilder builder;
    private LocalDateTime dateTime;
    private Cliente cliente;
    private Email email;


    @BeforeEach
    void setUp() throws MessagingException {
        MockitoAnnotations.openMocks(this);
        startEmail();
    }

    void whenSendEmailThenSendEmail()  {
        ArrayList<String> mockListCliente = new ArrayList<>();
        mockListCliente.add(cliente.toString());

        ArrayList<Instant> mockListInstant = new ArrayList<>();
        mockListInstant.add(INSTANT);

        when(clienteRepository.getDataByReservas(any(UUID.class))).thenReturn(mockListCliente);
        when(horarioRepository.getDataByReservas(any(UUID.class))).thenReturn(mockListInstant);
        when(barbeariaRepository.getDataByReserva(any(UUID.class))).thenReturn("Teste");
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);


        service.sendEmail(ID, email);

    }

    private void startEmail() throws MessagingException {
        cliente = new Cliente(UUID.randomUUID()
                ,"Dudu"
                ,"60711339040"
                ,"88940028922"
                ,"dudu@gmail.com",
                List.of());
        email = new Email(UUID.randomUUID()
                ,""
                ,FROM
                ,TO
                ,SUBJECT
                ,TEXT
                ,INSTANT);
        helper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
        helper.setFrom(FROM);
        helper.setTo(TO);
        helper.setSubject(SUBJECT);
        helper.setText(TEXT);

        //        dateTime = LocalDateTime.ofInstant(horarioRepository.getDataByReservas(ID).get(0), ZoneOffset.UTC);
//
//        mimeMessage = mailSender.createMimeMessage();
//
//        helper = new MimeMessageHelper(message.getMimeMessage(), true);
//
//        helper.setFrom(FROM);
//        helper.setTo(TO);
//        helper.setSubject(SUBJECT);
//        helper.setText(TEXT);
//
//        mailSender.send((MimeMessagePreparator) message);

    }
}