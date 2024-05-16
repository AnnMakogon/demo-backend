package dev.check;

import dev.check.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class СheckApplication {
	private static Initializer initiator;

	@Autowired
	public void setInitiatorLoader(Initializer initiator) {
		СheckApplication.initiator = initiator;
	}

	static void checkSize(Iterable<Student> i) {
		long count = 0;
		for (Student iter : i){
		System.out.println(iter);
		count ++;
		}
		System.out.println(count);
	}
	static void checkSize(Collection<Student> c) {
		System.out.println(c.size());
	}

	public static void main(String[] args) {
		SpringApplication.run(СheckApplication.class, args);
		initiator.initial();
		initiator.initialUser();
	}

}
