package literAllura.vargas.api;

import literAllura.vargas.api.model.principal.Principal;
import literAllura.vargas.api.repository.IAutorRepository;
import literAllura.vargas.api.repository.ILibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private ILibroRepository libroRepository;
	@Autowired
	private IAutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			Principal principal = new Principal(libroRepository, autorRepository);
			principal.muestraElMenu();
		} catch (Exception e) {
			System.err.println("Error starting the application: " + e.getMessage());
			e.printStackTrace();
		}
	}
}

