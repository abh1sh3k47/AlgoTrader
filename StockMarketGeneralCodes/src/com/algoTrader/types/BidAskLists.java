package com.algoTrader.types;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BidAskLists 
{
	protected Set<Bet> bids;
	protected Set<Bet> asks;

	public BidAskLists()
	{
		bids = new TreeSet<Bet>();
		asks = new TreeSet<Bet>();
	}

	//Bids METHODS
	public int getBidsSize() {
		return bids.size();
	}

	public Iterator<Bet> getBidsIterator() {
		return bids.iterator();
	}

	public boolean addBid(Bet bid) {
		return bids.add(bid);
	}

	public void clearBids() {
		bids.clear();
	}
	
	//Asks METHODS
	public int getAsksSize() {
		return asks.size();
	}

	public Iterator<Bet> getAsksIterator() {
		return asks.iterator();
	}

	public boolean addAsk(Bet ask) {
		return asks.add(ask);
	}

	public void clearAsks() {
		asks.clear();
	}
}
