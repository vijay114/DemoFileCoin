package com.commodities.blockchain.DemoFileCoin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BaseRequestDTO {

    String jsonrpc;
    Integer id;
    String method;

}
