package com.commodities.blockchain.DemoFileCoin.controller;

import com.commodities.blockchain.DemoFileCoin.dto.request.SendSignedTransactionRequestDTO;
import com.commodities.blockchain.DemoFileCoin.dto.request.SignTransactionRequestDTO;
import com.commodities.blockchain.DemoFileCoin.service.FileCoinService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for wallet functionality
 */
@Controller
public class FileCoinController {

    FileCoinService fileCoinService;

    public FileCoinController(FileCoinService fileCoinService) {
        this.fileCoinService = fileCoinService;
    }

    /**
     * POST API method to create a new wallet
     * @return responseEntity
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/wallet/new", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity createNewWallet() throws JsonProcessingException {
        ResponseEntity responseEntity = ResponseEntity.ok().body(fileCoinService.createNewWallet());
        return responseEntity;
    }

    /**
     * GET API method to get a wallet balance
     * @param address
     * @return responseEntity
     * @throws JsonProcessingException
     */
    @GetMapping(value = "/wallet/balance/{address}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getWalletBalance(@PathVariable String address) throws JsonProcessingException {
        ResponseEntity responseEntity = ResponseEntity.ok().body(fileCoinService.getWalletBalance(address));
        return responseEntity;
    }

    /**
     * GET API Method to get Nonce for an address
     * @param address
     * @return responseEntity
     * @throws JsonProcessingException
     */
    @GetMapping(value = "/wallet/nonce/{address}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity getNonce(@PathVariable String address) throws JsonProcessingException {
        ResponseEntity responseEntity = ResponseEntity.ok().body(fileCoinService.getNonceForAddress(address));
        return responseEntity;
    }

    /**
     * GET API method to get chain head
     * @return responseEntity
     * @throws JsonProcessingException
     */
    @GetMapping(value = "/chainHead", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getChainHead() throws JsonProcessingException {
        ResponseEntity responseEntity = ResponseEntity.ok().body(fileCoinService.getChainHead());
        return responseEntity;
    }

    /**
     * POST API method to sign a transaction
     * @param signTransactionRequestDTO
     * @return responseEntity
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/transaction/sign", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity signTransaction(@RequestBody SignTransactionRequestDTO signTransactionRequestDTO)
            throws JsonProcessingException {
        ResponseEntity responseEntity = ResponseEntity.ok().body(fileCoinService.signTransaction(
                signTransactionRequestDTO.getSenderAddress(),
                signTransactionRequestDTO.getReceiverAddress(),
                signTransactionRequestDTO.getMessage(),
                signTransactionRequestDTO.getAttoFil()
        ));
        return responseEntity;
    }

    /**
     * POST API method to send signed transaction request
     * @param sendSignedTransactionRequestDTO
     * @return responseEntity
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/transaction/send", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity sendSignedTransaction(
            @RequestBody SendSignedTransactionRequestDTO sendSignedTransactionRequestDTO) throws JsonProcessingException {
        ResponseEntity responseEntity = ResponseEntity.ok().body(fileCoinService.sendTransaction(sendSignedTransactionRequestDTO.getSenderAddress(),
                sendSignedTransactionRequestDTO.getReceiverAddress(), sendSignedTransactionRequestDTO.getMessage(),
                sendSignedTransactionRequestDTO.getAttoFil(), sendSignedTransactionRequestDTO.getSignatureType(),
                sendSignedTransactionRequestDTO.getSignature()));
        return responseEntity;
    }

}
