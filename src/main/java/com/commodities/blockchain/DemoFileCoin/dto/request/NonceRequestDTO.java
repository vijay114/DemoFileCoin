package com.commodities.blockchain.DemoFileCoin.dto.request;

import lombok.Getter;

@Getter
public class NonceRequestDTO extends BaseRequestDTO{
    String[] params;

    public NonceRequestDTO(String jsonrpc, Integer id, String method, String[] params) {
        super(jsonrpc, id, method);
        this.params = params;
    }
}
