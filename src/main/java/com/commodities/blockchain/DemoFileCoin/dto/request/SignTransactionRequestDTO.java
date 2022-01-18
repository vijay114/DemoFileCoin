package com.commodities.blockchain.DemoFileCoin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignTransactionRequestDTO {

    String senderAddress;
    String receiverAddress;
    String message;
    String attoFil;

}
