package jp.costlierrain464.ogiwara.java.niconicoapi.exception;

import java.io.Serializable;

/**
 * Created by ogiwara on 2017/03/08.
 */
public class NiconicoException extends Exception implements Serializable{
    private static final long serialVersionUID = 1L;

    private int code = -1;

    public NiconicoException(String message) {
        super(message);
    }

    public NiconicoException(Exception e) {
        super(e);
    }

    public NiconicoException(String message, Exception e) {
        super(message, e);
    }

    public NiconicoException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
