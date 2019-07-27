package communicationObjects;

import java.io.Serializable;

public class BasketContent implements Serializable {
	 private static final long serialVersionUID = 1L;

	 public byte[] getRawContent() {
		  return rawContent;
	 }

	 public BasketContent(byte[] rawContent) {
		  this.rawContent = rawContent;
	 }

	 private byte[] rawContent;

}
