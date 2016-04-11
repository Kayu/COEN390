package com.example.kayuho.coen390.Model;

/**
 * Created by ryanpoitras on 16-04-11.
 */
public class PhoneNumberWrongLengthException extends Exception {
    PhoneNumberWrongLengthException(){
        super();
    }

    PhoneNumberWrongLengthException(String string, Throwable throwable){
        super(string,throwable);
    }
}
