package com.example.kayuho.coen390.Model;

/**
 * Created by ryanpoitras on 16-04-11.
 */
public class BlockedContactInvalidLetters extends Exception {

    BlockedContactInvalidLetters(String blockedContactNum, Throwable throwable){
        super(blockedContactNum,throwable);
    }

    BlockedContactInvalidLetters(){
        super();
    }
}
