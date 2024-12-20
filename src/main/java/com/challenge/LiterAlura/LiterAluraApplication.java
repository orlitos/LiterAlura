package com.challenge.LiterAlura;

import com.challenge.LiterAlura.principal.Principal;
import com.challenge.LiterAlura.repositorio.IEscritoresRepository;
import com.challenge.LiterAlura.repositorio.ILibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private IEscritoresRepository escritoresRepository;
	@Autowired
	private ILibrosRepository librosRepository;
	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(escritoresRepository, librosRepository);
		principal.mostrarMenu();
	}
}