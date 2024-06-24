package dev.check;

import dev.check.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class CheckApplication {
	private static Initializer initiator;

	@Autowired
	public void setInitiatorLoader(Initializer initiator) {
		CheckApplication.initiator = initiator;
	}

	public static void main(String[] args) {
		SpringApplication.run(CheckApplication.class, args);
		initiator.initial();
		//initiator.initialUser();
	}

}
