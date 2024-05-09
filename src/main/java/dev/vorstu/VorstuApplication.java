package dev.vorstu;

import dev.vorstu.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class VorstuApplication {
	private static Initializer initiator;

	@Autowired
	public void setInitiatorLoader(Initializer initiator) {
		VorstuApplication.initiator = initiator;
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
		SpringApplication.run(VorstuApplication.class, args);
		initiator.initial();
	}

}
