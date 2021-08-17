package com.solver.conference;

import org.kurento.client.KurentoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@EnableWebSocket
@SpringBootApplication
public class SolverKurentoApplication implements WebSocketConfigurer {
	

	  @Bean
	  public UserRegistry registry() {
	    return new UserRegistry();
	  }

	  @Bean
	  public RoomManager roomManager() {
	    return new RoomManager();
	  }
	  

	  @Bean
	  public ScreenRegistry screenRegistry() {
	    return new ScreenRegistry();
	  }

	  @Bean
	  public CallHandler groupCallHandler() {
	    return new CallHandler();
	  }

	  @Bean
	  public KurentoClient kurentoClient() {
	    return KurentoClient.create();
	  }

	  @Bean
	  public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
	    ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
	    container.setMaxTextMessageBufferSize(32768);
	    return container;
	  }

	public static void main(String[] args) {
		SpringApplication.run(SolverKurentoApplication.class, args);
	}
	
	  @Override
	  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		  registry.addHandler(groupCallHandler(), "/groupcall").setAllowedOriginPatterns("http://localhost:8080");
		  registry.addHandler(groupCallHandler(), "/groupcall").setAllowedOriginPatterns("http://localhost:8081");
	  }

}
