package com.bank.credit_card;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CreditCreditCardApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		// Verifica que el contexto se carga correctamente
		assertNotNull(applicationContext, "El contexto de la aplicación no debería ser nulo");
	}

	@Test
	void methodValidationPostProcessorBeanExists() {
		// Verifica que el bean MethodValidationPostProcessor está presente en el contexto
		MethodValidationPostProcessor methodValidationPostProcessor =
				applicationContext.getBean(MethodValidationPostProcessor.class);
		assertNotNull(methodValidationPostProcessor,
				"El bean MethodValidationPostProcessor debería estar presente en el contexto");
	}
}