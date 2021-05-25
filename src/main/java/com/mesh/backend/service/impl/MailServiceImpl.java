package com.mesh.backend.service.impl;

import com.mesh.backend.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements IMailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(String receiver, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("meshproject@163.com");
        message.setTo(receiver);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }
}
