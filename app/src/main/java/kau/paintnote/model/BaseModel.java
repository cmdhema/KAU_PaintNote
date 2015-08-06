package kau.paintnote.model;

public abstract class BaseModel {
	
	public String messageType;
	
	public BaseModel(String type) {
		this.messageType = type;
	}
	
	public void setMessageType(String type) {
		this.messageType = type;
	}
}
