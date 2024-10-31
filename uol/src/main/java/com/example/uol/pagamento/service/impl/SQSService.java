package com.example.uol.pagamento.service.impl;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.uol.pagamento.constants.SqsQueueConstants;
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

    public void sendMessageToExdentQueue(ItemPagamentoDTO itemPagamentoDTO) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(sqsClient.getQueueUrl(SqsQueueConstants.PAGAMENTOS_EXCEDENTES_QUEUE).getQueueUrl())
                .withMessageBody(itemPagamentoDTO.toString());
        sendMessage(sendMessageRequest);
    }

    public void sendMessageToPartialQueue(ItemPagamentoDTO itemPagamentoDTO) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(sqsClient.getQueueUrl(SqsQueueConstants.PAGAMENTOS_PARCIAIS_QUEUE).getQueueUrl())
                .withMessageBody(itemPagamentoDTO.toString());
        sendMessage(sendMessageRequest);
    }

    public void sendMessageTotalQueue(ItemPagamentoDTO itemPagamentoDTO) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(sqsClient.getQueueUrl(SqsQueueConstants.PAGAMENTOS_TOTAIS_QUEUE).getQueueUrl())
                .withMessageBody(itemPagamentoDTO.toString());
        sendMessage(sendMessageRequest);

    }

    private void sendMessage(SendMessageRequest sendMessageRequest) {
        try {
            sqsClient.sendMessage(sendMessageRequest);
        }catch(Exception e){
            System.out.println("Failed to send message: " + e.getMessage());
        }

    }

}

