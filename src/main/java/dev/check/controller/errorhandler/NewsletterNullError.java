package dev.check.controller.errorhandler;

import dev.check.DTO.Newsletter;

public class NewsletterNullError extends Exception{
    public NewsletterNullError(){
        super("Newsletter is null");
    }

     NewsletterNullError(Throwable throwable){
        super(throwable);
     }
}
