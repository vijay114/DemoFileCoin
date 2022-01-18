package com.commodities.blockchain.DemoFileCoin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseResponseDTO {
    String jsonrpc;
    Integer id;
}
