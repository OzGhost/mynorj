package io.oz.mynorj;

public class ExecutionException extends RuntimeException {

    public ExecutionException(String msg) {
        super(msg);
    }

    public ExecutionException(Throwable e, String msg) {
        super(msg, e);
    }
}

