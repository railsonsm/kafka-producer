package com.example.kafka;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.producer.ProducerRecord;
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
	private KafkaTemplate<String, User> template;
	
	@PostMapping
	public String send(@RequestBody User msg) {
		
		final String TOPIC = "kafka-estudo";
      
        Runnable task1 = () -> sendTradeToTopic(TOPIC, "ABCD", 1, 5, msg);
        Runnable task2 = () -> sendTradeToTopic(TOPIC, "PQ1234@1211111111111", 6, 10, msg);
        Runnable task3 = () -> sendTradeToTopic(TOPIC, "ZX12345OOO", 11, 15, msg);
 
        
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(task1);
        executorService.submit(task2);
        executorService.submit(task3);
 
        executorService.shutdown();

		//template.send(new ProducerRecord<String, String>("kafka", "teste", msg));
		return "Enviado";
	}
	
	private void sendTradeToTopic(String topic, String securityId, int idStart, int idEnd,User  user) {
        for (int i = idStart; i <= idEnd; i++) {
            try {
                template.send(new ProducerRecord<String, User>(topic, securityId, new User(user.getNome(), user.getEmail())));
                System.out.println("Sending to " + topic + "msg : " + user + " to: " +securityId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
