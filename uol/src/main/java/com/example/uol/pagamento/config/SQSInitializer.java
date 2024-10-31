package com.example.uol.pagamento.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.example.uol.pagamento.constants.SqsQueueConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import java.util.List;

@Component
public class SQSInitializer {

    private final AmazonSQS sqsClient;
    private final String awsAccessKeyId="test";
    private final String awsSecretAccessKey="test";


    public SQSInitializer() {

        this.sqsClient = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey)))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        "http://localhost:4566", "us-east-1"))
                .build();
    }

    @PostConstruct
    public void createQueues() {
        List<String> queuesToCreate = List.of(SqsQueueConstants.PAGAMENTOS_PARCIAIS_QUEUE,
                SqsQueueConstants.PAGAMENTOS_TOTAIS_QUEUE, SqsQueueConstants.PAGAMENTOS_EXCEDENTES_QUEUE);

        try {
            queuesToCreate.forEach(queue -> sqsClient.createQueue(new CreateQueueRequest(queue)));
            System.out.println("Queues created successfully!");
        } catch (Exception e) {
            System.out.println("Failed to create queues: " + e.getMessage());
        }
    }

    @Bean
    public AmazonSQS amazonSQS() {
        return this.sqsClient;
    }
}
