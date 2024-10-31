package com.example.uol.pagamento.service.impl;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SQSService {
    private final AmazonSQS sqsClient;

    @Autowired
    public SQSService(AmazonSQS sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void sendMessage(ItemPagamentoDTO itemPagamentoDTO, String queue) {
        try {
            SendMessageRequest sendMessageRequest = new SendMessageRequest()
                    .withQueueUrl(sqsClient.getQueueUrl(queue).getQueueUrl())
                    .withMessageBody(itemPagamentoDTO.toString());
            sqsClient.sendMessage(sendMessageRequest);
        }catch(Exception e){
            System.out.println("Failed to send message: " + e.getMessage());
        }

    }

}

