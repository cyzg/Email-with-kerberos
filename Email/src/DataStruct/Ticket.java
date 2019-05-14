package DataStruct;

public class Ticket{
	private String sessionKey;
	private String ID;       //发送请求的ID
	private String IP;       //发送请求的IP
	private String requestID; //被请求方的ID
	private String timeStamp; //时间戳
	private String lifeTime;//ticket有效期
	
	public Ticket(String sessionKey, String iD, String iP, String requestID, String timeStamp, String lifeTime) {
		super();
		this.sessionKey = sessionKey;
		ID = iD;
		IP = iP;
		this.requestID = requestID;
		this.timeStamp = timeStamp;
		this.lifeTime = lifeTime;
	}
	
	public Ticket() {
		this.sessionKey = "";
		ID = "";
		IP = "";
		this.requestID = "";
		this.timeStamp = "";
		this.lifeTime = "";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((IP == null) ? 0 : IP.hashCode());
		result = prime * result + ((lifeTime == null) ? 0 : lifeTime.hashCode());
		result = prime * result + ((requestID == null) ? 0 : requestID.hashCode());
		result = prime * result + ((sessionKey == null) ? 0 : sessionKey.hashCode());
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
		Ticket other = (Ticket) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (IP == null) {
			if (other.IP != null)
				return false;
		} else if (!IP.equals(other.IP))
			return false;
		if (lifeTime == null) {
			if (other.lifeTime != null)
				return false;
		} else if (!lifeTime.equals(other.lifeTime))
			return false;
		if (requestID == null) {
			if (other.requestID != null)
				return false;
		} else if (!requestID.equals(other.requestID))
			return false;
		if (sessionKey == null) {
			if (other.sessionKey != null)
				return false;
		} else if (!sessionKey.equals(other.sessionKey))
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
		return "Ticket [sessionKey=" + sessionKey + ", ID=" + ID + ", IP=" + IP + ", requestID=" + requestID
				+ ", timeStamp=" + timeStamp + ", lifeTime=" + lifeTime + "]";
	}
	public String ticketOutput() {
		return  sessionKey + ID + IP + requestID + timeStamp + lifeTime;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(String lifeTime) {
		this.lifeTime = lifeTime;
	}
	
	
	
}

