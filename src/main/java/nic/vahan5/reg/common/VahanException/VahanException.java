package nic.vahan5.reg.common.VahanException;

import org.springframework.http.HttpStatus;


/**
 * Top parent exception class for Vahan Server
 *
 * @author RCN
 */
public class VahanException extends Exception {

    HttpStatus code = HttpStatus.PRECONDITION_FAILED;

    public VahanException(String msg, HttpStatus code) {
        super(msg);
        this.code = code;
    }

    /**
     * Constructor
     */
    public VahanException() {
        super();
    }

    /**
     * Constructor
     */
    public VahanException(String msg) {
        super(msg);
    }

    /**
     * Override finalize()
     */
    @Override
    public void finalize() throws Throwable {
        super.finalize();
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}