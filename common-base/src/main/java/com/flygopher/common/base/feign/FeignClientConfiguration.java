package com.flygopher.common.base.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flygopher.common.base.feign.inventory.InventoryClient;
import com.flygopher.common.base.feign.order.OrderClient;
import com.flygopher.common.base.feign.payment.PaymentClient;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.services")
@ConditionalOnProperty(prefix = "feign.client", name = "enabled", havingValue = "true")
public class FeignClientConfiguration {

    private String orderUrl;
    private String inventoryUrl;
    private String paymentUrl;

    @Autowired
    private Client client;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public OrderClient orderClient() {
        return Feign.builder()
                .client(client)
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .contract(new SpringMvcContract())
                .logger(new FeignLogger(LoggerFactory.getLogger(OrderClient.class)))
                .logLevel(Logger.Level.BASIC)
                .target(OrderClient.class, orderUrl);
    }

    @Bean
    public InventoryClient inventoryClient() {
        return Feign.builder()
                .client(client)
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .contract(new SpringMvcContract())
                .logger(new FeignLogger(LoggerFactory.getLogger(InventoryClient.class)))
                .logLevel(Logger.Level.BASIC)
                .target(InventoryClient.class, inventoryUrl);
    }

    @Bean
    public PaymentClient paymentClient() {
        return Feign.builder()
                .client(client)
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .contract(new SpringMvcContract())
                .logger(new FeignLogger(LoggerFactory.getLogger(PaymentClient.class)))
                .logLevel(Logger.Level.BASIC)
                .target(PaymentClient.class, paymentUrl);
    }
}
