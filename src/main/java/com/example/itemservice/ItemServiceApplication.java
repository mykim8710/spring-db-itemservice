package com.example.itemservice;

import com.example.itemservice.config.JdbcTemplateV2Config;
import com.example.itemservice.config.JdbcTemplateV3Config;
import com.example.itemservice.repository.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Import(JdbcTemplateV3Config.class)
//@Import(JdbcTemplateV2Config.class)
//@Import(JdbcTemplateV1Config.class)
//@Import(MemoryConfig.class)
@SpringBootApplication(scanBasePackages = "com.example.itemservice.web")
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

}
