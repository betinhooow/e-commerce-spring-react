package com.roberto.curscomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.roberto.curscomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
