package com.algoTrader.types.shareKhan;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class ShareKhanOrderReportEntry {
	private String orderID = "";
	private String exch = "";
	private String scripCode = "";
	private String seg = "";
	private String bs = "";
	private String orderDateTime = "";
	private String orderStatus = "";
	private String OrderQty = "";
	private String DisQty = "";
	private String orderPrice = "";
	private String execQty = "";
	private String execPrice = "";
	private String ltp = "";
	private String trigPrice = "";
	private String validity = "";
	private String validityDate = "";
	private String rmsCode = "";

	public String toString() {
		return "";
	}

	public String toCsvString() {
		return orderID + "," + exch + "," + scripCode + "," + seg + "," + bs
				+ "," + orderDateTime + "," + orderStatus + "," + OrderQty
				+ "," + DisQty + "," + orderPrice + "," + execQty + ","
				+ execPrice + "," + ltp + "," + trigPrice + "," + validity
				+ "," + rmsCode;
	}

	public void set(int index, String val) {
		switch (index) {
		case 0:
			orderID = val;
			break;
		case 1:
			exch = val;
			break;
		case 2:
			scripCode = val;
			break;
		case 3:
			seg = val;
			break;
		case 4:
			bs = val;
			break;
		case 5:
			orderDateTime = val;
			break;
		case 6:
			orderStatus = val;
			break;
		case 7:
			OrderQty = val;
			break;
		case 8:
			DisQty = val;
			break;
		case 9:
			orderPrice = val;
			break;
		case 10:
			execQty = val;
			break;
		case 11:
			execPrice = val;
			break;
		case 12:
			ltp = val;
			break;
		case 13:
			trigPrice = val;
			break;
		case 14:
			validity = val;
			break;
		case 15:
			validityDate = val;
			break;
		case 16:
			rmsCode = val;
			break;
		default:
			break;
		}
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getExch() {
		return exch;
	}

	public void setExch(String exch) {
		this.exch = exch;
	}

	public String getScripCode() {
		return scripCode;
	}

	public void setScripCode(String scripCode) {
		this.scripCode = scripCode;
	}

	public String getSeg() {
		return seg;
	}

	public void setSeg(String seg) {
		this.seg = seg;
	}

	public String getBs() {
		return bs;
	}

	public void setBs(String bs) {
		this.bs = bs;
	}

	public String getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(String orderDateTime) {
		this.orderDateTime = orderDateTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderQty() {
		return OrderQty;
	}

	public void setOrderQty(String orderQty) {
		OrderQty = orderQty;
	}

	public String getDisQty() {
		return DisQty;
	}

	public void setDisQty(String disQty) {
		DisQty = disQty;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getExecQty() {
		return execQty;
	}

	public void setExecQty(String execQty) {
		this.execQty = execQty;
	}

	public String getExecPrice() {
		return execPrice;
	}

	public void setExecPrice(String execPrice) {
		this.execPrice = execPrice;
	}

	public String getLtp() {
		return ltp;
	}

	public void setLtp(String ltp) {
		this.ltp = ltp;
	}

	public String getTrigPrice() {
		return trigPrice;
	}

	public void setTrigPrice(String trigPrice) {
		this.trigPrice = trigPrice;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCalidityDate() {
		return validityDate;
	}

	public void setCalidityDate(String validityDate) {
		this.validityDate = validityDate;
	}

	public String getRmsCode() {
		return rmsCode;
	}

	public void setRmsCode(String rmsCode) {
		this.rmsCode = rmsCode;
	}
}
