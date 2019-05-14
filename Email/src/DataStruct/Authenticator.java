package DataStruct;

public class Authenticator {
	private String clientID;   //客户端ID
	private String clientIP;    //IP
	private String timeStamp;  //时间戳
	public Authenticator(String clientID, String clientIP, String timeStamp) {
		super();
		this.clientID = clientID;
		this.clientIP = clientIP;
		this.timeStamp = timeStamp;
	}
	public Authenticator() {
		this.clientID = "";
		this.clientIP = "";
		this.timeStamp = "";
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientID == null) ? 0 : clientID.hashCode());
		result = prime * result + ((clientIP == null) ? 0 : clientIP.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authenticator other = (Authenticator) obj;
		if (clientID == null) {
			if (other.clientID != null)
				return false;
		} else if (!clientID.equals(other.clientID))
			return false;
		if (clientIP == null) {
			if (other.clientIP != null)
				return false;
		} else if (!clientIP.equals(other.clientIP))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Authenticator [clientID=" + clientID + ", clientIP=" + clientIP + ", timeStamp=" + timeStamp + "]";
	}
	public String AuthOutput() {
		return clientID + clientIP + timeStamp;
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	
}
