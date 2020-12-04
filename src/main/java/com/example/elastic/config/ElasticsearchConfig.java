package com.example.elastic.config;

import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

/**
 * @author xyw
 * @date 2020/12/02
 */
@Configuration
public class ElasticsearchConfig {
    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client, ElasticsearchConverter converter) {
            return new ElasticsearchTemplate(client,converter);
    }
}
