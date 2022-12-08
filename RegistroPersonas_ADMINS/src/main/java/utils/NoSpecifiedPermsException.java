package utils;

public class NoSpecifiedPermsException extends Exception {
    
    public NoSpecifiedPermsException(String msg) {
        super(msg);
    }
    
    public NoSpecifiedPermsException(String msg, Throwable err) {
        super(msg, err);
    }
}
