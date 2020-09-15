package com.smpp.demo.payload.request;

public class ResponseDrive {

	 private String name;
	  private String url;
	  private String type,uploaded_at;
	  private long size;

	  public ResponseDrive(String name, String url, String type, long size, String uploaded_at) {
	    this.name = name;
	    this.url = url;
	    this.type = type;
	    this.size = size;
	    this.uploaded_at = uploaded_at;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public String getUrl() {
	    return url;
	  }

	  public void setUrl(String url) {
	    this.url = url;
	  }

	  public String getType() {
	    return type;
	  }

	  public void setType(String type) {
	    this.type = type;
	  }

	  public long getSize() {
	    return size;
	  }

	  public void setSize(long size) {
	    this.size = size;
	  }

	public String getUploaded_at() {
		return uploaded_at;
	}

	public void setUploaded_at(String uploaded_at) {
		this.uploaded_at = uploaded_at;
	}
	  
}
