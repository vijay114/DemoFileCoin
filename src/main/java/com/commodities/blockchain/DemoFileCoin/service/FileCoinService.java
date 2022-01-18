package com.commodities.blockchain.DemoFileCoin.service;

import com.commodities.blockchain.DemoFileCoin.dto.request.*;
import com.commodities.blockchain.DemoFileCoin.dto.response.NonceResponseDTO;
import com.commodities.blockchain.DemoFileCoin.dto.response.SignMessageResponseDTO;
import com.commodities.blockchain.DemoFileCoin.dto.response.WalletBalanceResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

/**
 * Service class for file coin wallet functionality
 * @author Vijay
 */
@Slf4j
@Service
public class FileCoinService {

    @Autowired
    private Environment env;

    /**
     * Method to create a new wallet on file coin
     * @return
     * @throws JsonProcessingException
     */
    public String createNewWallet() throws JsonProcessingException {

        // Initializing rest template for calling rest api
        var restTemplate = new RestTemplate();
        // Initializing Wallet request DTO for creating new wallet
        var newWalletRequestDTO = new NewWalletRequestDTO(
                env.getProperty("fileCoin.jsonrpc"),
                Integer.parseInt(env.getProperty("fileCoin.id")),
                "Filecoin.WalletNew",
                new String[]{env.getProperty("fileCoin.new.wallet.key.type")});

        // Mapping DTO object to create a string to pass into the rest template body
        var requestBody = new ObjectMapper().writeValueAsString(newWalletRequestDTO);

        // Create new http entity
        HttpEntity<NewWalletRequestDTO> entity = new HttpEntity(requestBody, this.setHeadersForRPCRequest());
        // Send post request to create a new wallet
        var newWalletCreateResponse = restTemplate.postForObject(
                env.getProperty("fileCoin.rpc.endPoint.liteNode"), entity, String.class);

        return newWalletCreateResponse;
    }

    /**
     * Method to get wallet balance
     * @param address
     * @return walletBalanceResponseDTO
     * @throws JsonProcessingException
     */
    public WalletBalanceResponseDTO getWalletBalance(String address) throws JsonProcessingException {
        // Initializing rest template for calling rest api
        var restTemplate = new RestTemplate();
        // Initializing Wallet balance request DTO for getting wallet balance
        var walletBalanceRequestDTO  = new WalletBalanceRequestDTO(
                env.getProperty("fileCoin.jsonrpc"),
                Integer.parseInt(env.getProperty("fileCoin.id")),
                "Filecoin.WalletBalance",
                new String[]{address});

        // Mapping DTO object to create a string to pass into the rest template body
        var requestBody = new ObjectMapper().writeValueAsString(walletBalanceRequestDTO);

        // Create new http entity
        HttpEntity<NewWalletRequestDTO> entity = new HttpEntity(requestBody, this.setHeadersForRPCRequest());
        // Send post request to get wallet balance
        var  walletBalanceResponseDTO = restTemplate.postForObject(
                env.getProperty("fileCoin.rpc.endPoint.fullNode"), entity, WalletBalanceResponseDTO.class);

        return walletBalanceResponseDTO;
    }

    /**
     * Method to get nonce for an address
     * @param address
     * @return nonceResponseDTO
     * @throws JsonProcessingException
     */
    public NonceResponseDTO getNonceForAddress(String address) throws JsonProcessingException {
        // Initializing rest template for calling rest api
        var restTemplate = new RestTemplate();
        // Initializing nonce request DTO for getting nonce of sender's address
        var nonceRequestDTO  = new NonceRequestDTO(
                env.getProperty("fileCoin.jsonrpc"),
                Integer.parseInt(env.getProperty("fileCoin.id")),
                "Filecoin.MpoolGetNonce",
                new String[]{address});

        // Mapping DTO object to create a string to pass into the rest template body
        var requestBody = new ObjectMapper().writeValueAsString(nonceRequestDTO);

        // Create new http entity
        HttpEntity<NewWalletRequestDTO> entity = new HttpEntity(requestBody, this.setHeadersForRPCRequest());
        // Send post request to get address's nonce
        var  nonceResponseDTO = restTemplate.postForObject(
                env.getProperty("fileCoin.rpc.endPoint.fullNode"), entity, NonceResponseDTO.class);

        return nonceResponseDTO;
    }

    /**
     * Method to get chain head
     * @return string
     * @throws JsonProcessingException
     */
    public String getChainHead() throws JsonProcessingException {

        // Initializing rest template for calling rest api
        var restTemplate = new RestTemplate();
        // Initializing nonce request DTO for getting chain head
        var chainHeadRequestDTO  = new ChainHeadRequestDTO(
                env.getProperty("fileCoin.jsonrpc"),
                Integer.parseInt(env.getProperty("fileCoin.id")),
                "Filecoin.ChainHead",
                new String[]{});

        // Mapping DTO object to create a string to pass into the rest template body
        var requestBody = new ObjectMapper().writeValueAsString(chainHeadRequestDTO);

        // Create new http entity
        HttpEntity<NewWalletRequestDTO> entity = new HttpEntity(requestBody, this.setHeadersForRPCRequest());
        // Send post request to get chain head
        var  chainHeadResponseString = restTemplate.postForObject(
                env.getProperty("fileCoin.rpc.endPoint.fullNode"), entity, String.class);

        JsonNode jsonNode = new ObjectMapper().readTree(chainHeadResponseString);
        var chainHead = jsonNode.get("result").get("Cids").get(0).get("/").asText();

        return chainHead;

    }

    /**
     * Method to sign a transaction
     * @param senderAddress
     * @param receiverAddress
     * @param message
     * @param attoFIL
     * @return signMessageResponseDTO
     * @throws JsonProcessingException
     */
    public SignMessageResponseDTO signTransaction(String senderAddress, String receiverAddress, String message, String attoFIL)
            throws JsonProcessingException {
        // Initializing rest template for calling rest api
        var restTemplate = new RestTemplate();
        // initialize object array for sending request for signing transaction
        Object[] objects = new Object[2];
        // add sender address in the first object
        objects[0] = senderAddress;
        // add the object node to object array
        objects[1] =  getTransactionJsonNodes(senderAddress, receiverAddress, message, attoFIL);;

        // initialize the DTO required for signing the message
        var signMessageRequestDTO = new SignMessageRequestDTO(env.getProperty("fileCoin.jsonrpc"),
                Integer.parseInt(env.getProperty("fileCoin.id")),
                "Filecoin.WalletSignMessage",
                objects);

        // Mapping DTO object to create a string to pass into the rest template body
        var requestBody = new ObjectMapper().writeValueAsString(signMessageRequestDTO);

        log.info(requestBody);


        // Create new http entity
        HttpEntity<NewWalletRequestDTO> entity = new HttpEntity(requestBody, this.setHeadersForRPCRequest());
        // Send post request to get chain head
        var  signedTransactionResponse = restTemplate.postForObject(
                env.getProperty("fileCoin.rpc.endPoint.liteNode"), entity, String.class);


        // reading response and setting it into response dto
        JsonNode jsonNode = new ObjectMapper().readTree(signedTransactionResponse);
        var signature = jsonNode.get("result").get("Signature").get("Data").asText();
        var signatureType = jsonNode.get("result").get("Signature").get("Type").asInt();
        var signMessageResponseDTO = new SignMessageResponseDTO(signatureType, signature);

        return signMessageResponseDTO;

    }

    /**
     * Method to send sign transaction to file coin blockchain
     * @param senderAddress
     * @param receiverAddress
     * @param message
     * @param attoFIL
     * @param signatureType
     * @param signature
     * @return transactionResponse
     * @throws JsonProcessingException
     */
    public String sendTransaction(String senderAddress, String receiverAddress,
                                String message, String attoFIL, int signatureType, String signature)
            throws JsonProcessingException {
        // Initializing rest template for calling rest api
        var restTemplate = new RestTemplate();

        // initialize the DTO required for signing the message
        var signMessageRequestDTO = new SignMessageRequestDTO(env.getProperty("fileCoin.jsonrpc"),
                Integer.parseInt(env.getProperty("fileCoin.id")),
                "Filecoin.MpoolPush",
                new Object[]{this.getSignedTransactionJsonNodes(senderAddress, receiverAddress, message, attoFIL, signatureType, signature)});

        // Mapping DTO object to create a string to pass into the rest template body
        var requestBody = new ObjectMapper().writeValueAsString(signMessageRequestDTO);

        log.info(requestBody);


        // Create new http entity
        HttpEntity<NewWalletRequestDTO> entity = new HttpEntity(requestBody, this.setHeadersForRPCRequest());
        // Send post request to get chain head
        var  transactionResponse = restTemplate.postForObject(
                env.getProperty("fileCoin.rpc.endPoint.fullNode"), entity, String.class);

        return transactionResponse;


    }

    /**
     * Method to set signed message transaction data and return the json object node for the same
     * @param senderAddress
     * @param receiverAddress
     * @param message
     * @param attoFIL
     * @param signatureType
     * @param signature
     * @return signedTransactionObjectNode
     * @throws JsonProcessingException
     */
    private ObjectNode getSignedTransactionJsonNodes(String senderAddress, String receiverAddress,
                                                     String message, String attoFIL, int signatureType, String signature) throws JsonProcessingException {
        // Create an object node using jackson to add key value pair for the request
        ObjectNode signedTransactionObjectNode = new ObjectMapper().createObjectNode();
        // Add the required key value pairs
        signedTransactionObjectNode.set("Message", this.getTransactionJsonNodes(senderAddress, receiverAddress, message, attoFIL));
        // Create another object node for signature and set in the signed transaction node
        ObjectNode signatureObjectNode = new ObjectMapper().createObjectNode();
        signatureObjectNode.put("Type", signatureType);
        signatureObjectNode.put("Data", signature);
        signedTransactionObjectNode.set("Signature", signatureObjectNode);
        // Create another object node for CID and set in the signed transaction node
        ObjectNode cidObjectNode = new ObjectMapper().createObjectNode();
        cidObjectNode.put("/", this.getChainHead());
        // add the above object node to the initial object node
        signedTransactionObjectNode.set("CID", cidObjectNode);

        return signedTransactionObjectNode;

    }

    /**
     * Method to set message transaction data and return the json object node for the same
     * @param senderAddress
     * @param receiverAddress
     * @param message
     * @param attoFIL
     * @return signTransactionParam
     * @throws JsonProcessingException
     */
    private ObjectNode getTransactionJsonNodes(String senderAddress, String receiverAddress,
                                               String message, String attoFIL) throws JsonProcessingException {
        // Create an object node using jackson to add key value pair for the request
        ObjectNode signTransactionParam = new ObjectMapper().createObjectNode();
        // Add the required key value pairs
        signTransactionParam.put("Version", Integer.parseInt(env.getProperty("fileCoin.sign.message.version")));
        signTransactionParam.put("To", receiverAddress);
        signTransactionParam.put("From", senderAddress);
        signTransactionParam.put("Nonce", this.getNonceForAddress(senderAddress).getResult());
        signTransactionParam.put("Value", attoFIL);
        signTransactionParam.put("GasLimit", Integer.parseInt(env.getProperty("fileCoin.gas.limit")));
        signTransactionParam.put("GasFeeCap", env.getProperty("fileCoin.gas.fee.cap"));
        signTransactionParam.put("GasPremium", env.getProperty("fileCoin.gas.premium"));
        signTransactionParam.put("Method", Integer.parseInt(env.getProperty("fileCoin.sign.method")));
        signTransactionParam.put("Params", Base64.getEncoder().encodeToString(message.getBytes()));

        // Create another object node which will go inside the above object node
        ObjectNode cidParam = new ObjectMapper().createObjectNode();
        cidParam.put("/", this.getChainHead());
        // add the above object node to the initial object node
        signTransactionParam.set("CID", cidParam);
        return signTransactionParam;
    }

    /**
     * Method to set http headers for rpc request
     * @return httpHeaders
     */
    private HttpHeaders setHeadersForRPCRequest() {
        // Setting http headers with authentication token and media type
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(env.getProperty("fileCoin.auth.token"));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

}
