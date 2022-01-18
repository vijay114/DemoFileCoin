package com.commodities.blockchain.DemoFileCoin.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NonceResponseDTO extends BaseResponseDTO {
    Integer result;

    public NonceResponseDTO(String jsonrpc, Integer id, Integer result) {
        super(jsonrpc, id);
        this.result = result;
    }
}
