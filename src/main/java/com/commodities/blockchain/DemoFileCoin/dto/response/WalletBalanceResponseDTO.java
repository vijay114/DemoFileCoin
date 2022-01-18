package com.commodities.blockchain.DemoFileCoin.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WalletBalanceResponseDTO extends BaseResponseDTO {
    String result;

    public WalletBalanceResponseDTO(String jsonrpc, Integer id, String result) {
        super(jsonrpc, id);
        this.result = result;
    }
}
