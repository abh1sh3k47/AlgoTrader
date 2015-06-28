package com.algoTrader.types.shareKhan;

import com.algoTrader.types.BidAskLists;

public class ShareKhanBidAskLists
{
	private String scrip;
	
	BidAskLists nseList;
	BidAskLists bseList;
	
	private double nseLtp;
	private double bseLtp;
	
	private int nseLtq;
	private int nseTbq;
	private int nseTsq;
	private double nseWtAvg;
	private int nseTtq;
	private double nseTtv;
	
	private int bseLtq;
	private int bseTbq;
	private int bseTsq;
	private double bseWtAvg;
	private int bseTtq;
	private double bseTtv;
	
	String bseTimeStamp;
	String nseTimeStamp;
	
	public ShareKhanBidAskLists()
	{
		nseList = new BidAskLists();
		bseList = new BidAskLists();
	}

	public BidAskLists getNseList() {
		return nseList;
	}

	public void setNseList(BidAskLists nseList) {
		this.nseList = nseList;
	}

	public BidAskLists getBseList() {
		return bseList;
	}

	public void setBseList(BidAskLists bseList) {
		this.bseList = bseList;
	}

	public double getNseLtp() {
		return nseLtp;
	}

	public void setNseLtp(double nseLtp) {
		this.nseLtp = nseLtp;
	}

	public double getBseLtp() {
		return bseLtp;
	}

	public void setBseLtp(double bseLtp) {
		this.bseLtp = bseLtp;
	}

	public int getNseLtq() {
		return nseLtq;
	}

	public void setNseLtq(int nseLtq) {
		this.nseLtq = nseLtq;
	}

	public int getNseTbq() {
		return nseTbq;
	}

	public void setNseTbq(int nseTbq) {
		this.nseTbq = nseTbq;
	}

	public int getNseTsq() {
		return nseTsq;
	}

	public void setNseTsq(int nseTsq) {
		this.nseTsq = nseTsq;
	}

	public double getNseWtAvg() {
		return nseWtAvg;
	}

	public void setNseWtAvg(double nseWtAvg) {
		this.nseWtAvg = nseWtAvg;
	}

	public int getNseTtq() {
		return nseTtq;
	}

	public void setNseTtq(int nseTtq) {
		this.nseTtq = nseTtq;
	}

	public double getNseTtv() {
		return nseTtv;
	}

	public void setNseTtv(double nseTtv) {
		this.nseTtv = nseTtv;
	}

	public int getBseLtq() {
		return bseLtq;
	}

	public void setBseLtq(int bseLtq) {
		this.bseLtq = bseLtq;
	}

	public int getBseTbq() {
		return bseTbq;
	}

	public void setBseTbq(int bseTbq) {
		this.bseTbq = bseTbq;
	}

	public int getBseTsq() {
		return bseTsq;
	}

	public void setBseTsq(int bseTsq) {
		this.bseTsq = bseTsq;
	}

	public double getBseWtAvg() {
		return bseWtAvg;
	}

	public void setBseWtAvg(double bseWtAvg) {
		this.bseWtAvg = bseWtAvg;
	}

	public int getBseTtq() {
		return bseTtq;
	}

	public void setBseTtq(int bseTtq) {
		this.bseTtq = bseTtq;
	}

	public double getBseTtv() {
		return bseTtv;
	}

	public void setBseTtv(double bseTtv) {
		this.bseTtv = bseTtv;
	}

	public String getScrip() {
		return scrip;
	}

	public void setScrip(String scrip) {
		this.scrip = scrip;
	}

	public String getBseTimeStamp() {
		return bseTimeStamp;
	}

	public String getNseTimeStamp() {
		return nseTimeStamp;
	}

	public void setBseTimeStamp(String bseTimeStamp) {
		this.bseTimeStamp = bseTimeStamp;
	}

	public void setNseTimeStamp(String nseTimeStamp) {
		this.nseTimeStamp = nseTimeStamp;
	}
}
