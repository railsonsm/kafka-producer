package com.example.kafka;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class Controller {
	
	@Autowired
	private KafkaTemplate<String, String> template;
	
	@PostMapping
	public String send(@RequestBody String msg) {
		template.send("estudo-kafka" , 0, UUID.randomUUID().toString(), msg);
		return "Enviado";
	}
}
