package com.algaworks.algaposts.textprocessorservice.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_POST = "text-processor-service.post-processing.v1.q";
    public static final String EXCHANGE_POST = "text-processor-service.post-processing.v1.e";
    public static final String EXCHANGE_PROCESSING = "text-processor-service.text-processing.v1.e";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper mapper){
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){

        return new RabbitAdmin(connectionFactory);

    }

    @Bean
    public Queue queue(){
        return QueueBuilder.durable(QUEUE_POST).build();
    }

    public FanoutExchange exchange(){
        return ExchangeBuilder.fanoutExchange(EXCHANGE_POST).build();
    }

    @Bean
    public FanoutExchange exchangeProcessing(){
        return ExchangeBuilder.fanoutExchange(EXCHANGE_PROCESSING).build();
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange());
    }

}
