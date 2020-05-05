package org.scu.srg.exceptions;

public class SRGException extends RuntimeException {
  public SRGException(String msg) {
    super(msg);
  }

  public SRGException(String msg, Throwable e) {
    super(msg,e);
  }
}
