package com.map1;

public class TomorrowWeatherVO {
	
	String low;
	String high;
//	String icon;
	String condition;
	    
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	
	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public TomorrowWeatherVO(String low, String high, String condition) {
		super();
		this.low = low;
		this.high = high;
//		this.icon = icon;
		this.condition = condition;
	}

	public TomorrowWeatherVO() {

	}

}