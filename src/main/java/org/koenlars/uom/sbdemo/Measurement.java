package org.koenlars.uom.sbdemo;

public class Measurement {
	private Number value;
	private String unit;
	private String prefix;
	public Number getValue() {
		return value;
	}
	public void setValue(Number value) {
		this.value = value;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	} 
	
	
}
