# ECE465-Cloud_Computing

Repo for Cooper Union ECE-465 Cloud Computing. Our main project is CooperCoin, a blockchain app. 
The goal of this project is to explore blockchain applications and distributed computing.

## Features
Rev 1a implements a single-node, multithreaded version that generates transactions between two wallets and creates a blockchain. Broken due to a race condition.
Rev 1b fixed the race condition and implements mining and transactions. We still need to work on implementing Markle tries and blockchain validation, as well as code refactoring. 

## Requirements
+ Java 
+ If building from source, [Gradle](https://gradle.org)

## Installation
To build from source:

	git clone https://github.com/tsar-daniel/ECE465-Cloud_Computing
    cd coopercoin
    gradle build
    
Otherwise, download and run the .jar file.

## Usage
If building from source, execute `gradle run` in `/coopercoin`. Optionally, `gradle shadowjar` will make a fat jar in `/coopercoin/app/build/libs`.
If using the .jar file, run `java -jar coopercoin-0.XX.jar`, where XX is the version. The jar file's permissions may need to be changed.
    
## Reference
+ [Bitcoin whitepaper](https://bitcoin.org/bitcoin.pdf)
+ [Bitcoin wiki](https://en.bitcoin.it/wiki/Main_Page)
+ [Shadow Gradle plugin](https://imperceptiblethoughts.com/shadow/)
+ [Bouncy Castle API](https://www.bouncycastle.org/)
+ [Programmers Blockchain](https://medium.com/programmers-blockchain/blockchain-development-mega-guide-5a316e6d10df) - [Part 1](https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa) and [Part 2](https://medium.com/programmers-blockchain/creating-your-first-blockchain-with-java-part-2-transactions-2cdac335e0ce)
+ [Baeldung Blockchain Tutorial](https://www.baeldung.com/java-blockchain)
+ [Naivecoin](https://lhartikk.github.io/)
+ [ScroogeCoin](https://github.com/zhaohuabing/ScroogeCoin)

## License
This software is licensed under GNU GPLv3.

## Authors
Daniel Tsarev and Jon Lu, Spring 2021.
