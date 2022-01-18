package com.commodities.blockchain.DemoFileCoin.dto.request;

import lombok.Getter;

@Getter
public class ChainHeadRequestDTO extends BaseRequestDTO{
    String[] params;

    public ChainHeadRequestDTO(String jsonrpc, Integer id, String method, String[] params) {
        super(jsonrpc, id, method);
        this.params = params;
    }
}
