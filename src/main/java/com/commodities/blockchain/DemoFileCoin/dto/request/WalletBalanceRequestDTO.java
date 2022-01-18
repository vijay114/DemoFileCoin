package com.commodities.blockchain.DemoFileCoin.dto.request;

import lombok.Getter;

@Getter
public class WalletBalanceRequestDTO extends BaseRequestDTO{
    String[] params;

    public WalletBalanceRequestDTO(String jsonrpc, Integer id, String method, String[] params) {
        super(jsonrpc, id, method);
        this.params = params;
    }
}
