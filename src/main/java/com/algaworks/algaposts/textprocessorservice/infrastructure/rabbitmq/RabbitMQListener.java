package com.algaworks.algaposts.textprocessorservice.infrastructure.rabbitmq;

import com.algaworks.algaposts.textprocessorservice.model.PostData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

import static com.algaworks.algaposts.textprocessorservice.infrastructure.rabbitmq.RabbitMQConfig.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQListener {

    private final RabbitTemplate template;

    @RabbitListener(queues = QUEUE_POST)
    @SneakyThrows
    public void handle(@Payload PostData postData, @Headers Map<String, Object> headers){

        log.info("Body {} ", postData.getBody());
        log.info("Headers {} ", headers.toString());

        int length = postData.getBody().split(" ").length;
        Double valorEstimado = length * 0.1;

        postData.setWordCount(length);
        postData.setCalculatedValue(valorEstimado);

        String exchange = EXCHANGE_PROCESSING;
        String routenKey = "";

        template.convertAndSend(exchange, routenKey, postData);


//        Thread.sleep(Duration.ofSeconds(2));

    }
}
