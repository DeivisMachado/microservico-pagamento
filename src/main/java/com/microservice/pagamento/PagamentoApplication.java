package com.microservice.pagamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD

@SpringBootApplication
public class PagamentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PagamentoApplication.class, args);
    }
=======
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class PagamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagamentoApplication.class, args);
	}

>>>>>>> f31beeb7fd84831ad96247f917a0da568559b9b7
}
