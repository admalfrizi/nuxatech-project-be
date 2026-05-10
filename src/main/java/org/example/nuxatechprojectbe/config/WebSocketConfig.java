package org.example.nuxatechprojectbe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple memory-based message broker
        // /topic is used for broadcasting (one-to-many)
        config.enableSimpleBroker("/topic");

        // Prefix for messages bound for methods annotated with @MessageMapping
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // The endpoint where the Angular app will connect
        registry.addEndpoint("/ws-monitoring")
                .setAllowedOriginPatterns("*") // In production, restrict this to your FE domain
                .withSockJS(); // Enable fallback options for browsers that don't support WebSockets
    }
}