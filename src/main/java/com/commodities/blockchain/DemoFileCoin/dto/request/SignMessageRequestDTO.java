package com.commodities.blockchain.DemoFileCoin.dto.request;

import lombok.Getter;

@Getter
public class SignMessageRequestDTO extends BaseRequestDTO{
    Object[] params;

    public SignMessageRequestDTO(String jsonrpc, Integer id, String method, Object[] params) {
        super(jsonrpc, id, method);
        this.params = params;
    }
}
