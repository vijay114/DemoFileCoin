# FileCoin RPC API Implementation using Java & Spring boot
This repository demonstartes consuming filecoin blockchain RPC APIs using Java and Spring boot. Main Filecoin blockchain APIs consumed in this repo are:
1. Creating a new wallet
2. Getting FIL balance of a wallet
3. Getting nonce for a Filecoin address
4. Getting chain head of the node
5. Signing a transaction
6. Sending a transaction

##### Please note this is not a local/algo implementation of addresses and signing.


## File Coin Lotus Installation Steps (For macOS):
1. Check if you already have the XCode Command Line Tools installed via the CLI, run:
``xcode-select -p``
2. If this command returns a path, then you have Xcode already installed ``/Library/Developer/CommandLineTools
   ``
3. If the above command doesn’t return a path, install Xcode: ``xcode-select --install
   ``
4. Use the command brew install to install the following packages: ``brew install go bzr jq pkg-config rustup hwloc
   ``
5. Clone the repository:``git clone https://github.com/filecoin-project/lotus.git``
6. Navigate to cloned repository: ``cd lotus/``
7. Checkout repository to the latest release ``git checkout v1.13.2``. Latest release can be found here: https://github.com/filecoin-project/lotus/releases
8. Build and install Lotus:
   1. ``make clean && make all`` for Main network
   2. OR ``make clean calibnet`` for Test network
   3. ``sudo make install``
9. To start Lotus Daemon in Lite node ``FULLNODE_API_INFO=wss://api.chain.love lotus daemon --lite
   ``
10. To start Lotus Daemon in full node ``lotus daemon``

## Obtain token to call FileCoin lotus APIs

1. Execute: ``lotus auth create-token --perm <read,write,sign,admin>`` e.g.: For obtaining admin token ``lotus auth create-token --perm admin``
2. A token will be generated, use that token to call all subsequent APIs by passing in Authorization Header as Bearer Token.

## Obtain test FIL
https://faucet.calibration.fildev.network/

## Tools for Calibration Testnet
https://calibration.filscan.io/

## Using both lotus node and calibration rpc
Lotus node either installed as full node or lite node does not sync the FILs and wallets, so this project is using 
calibration glif node RPC URL ``https://calibration.node.glif.io/rpc/v0`` for few of the RPC API methods like get balance and push 
transaction/message to the pool. 

Please note that the calibration glif RPC URL supports only few of the methods which can be found at 
https://calibration.node.glif.io/. That is why local RPC API URL has been used with it.

## Important Lotus Commands
### To get the time stamp of the recently mined block on local lotus filecoin
``date -r $(lotus chain getblock $(lotus chain head) | jq .Timestamp)``

