package com.commodities.blockchain.DemoFileCoin.dto.request;

import lombok.Getter;

@Getter
public class NewWalletRequestDTO extends BaseRequestDTO{
    String[] params;

    public NewWalletRequestDTO(String jsonrpc, Integer id, String method, String[] params) {
        super(jsonrpc, id, method);
        this.params = params;
    }
}
