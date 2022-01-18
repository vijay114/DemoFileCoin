package com.commodities.blockchain.DemoFileCoin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignMessageResponseDTO {
    int type;
    String signature;
}
