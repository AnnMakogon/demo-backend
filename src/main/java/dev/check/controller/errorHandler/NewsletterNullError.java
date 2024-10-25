package dev.check.controller.errorHandler;

public class NewsletterNullError extends Exception {
    public NewsletterNullError() {
        super("Newsletter is null");
    }

}
