package com.algoTrader.types;

/**
 * 
 * @author abh1sh3k47
 *
 */
public class Bet implements Comparable
{	
	private BetType betType;
	
	private int orderCount;
	private int quantity;
	private double price;
	private Double averagePrice;
	
	public Bet(BetType betType, int orderCount, int quantity, double price) 
	{
		this.betType = betType;
		this.orderCount = orderCount;
		this.quantity = quantity;
		this.price = price;
	}

	public Bet(BetType betType, int orderCount, int quantity, double price, double averagePrice) 
	{
		this(betType,orderCount,quantity,price);
		this.averagePrice = averagePrice;
	}
	public static enum BetType
	{
		BID,ASK
	};
	
	public BetType getBetType() {
		return betType;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public double getAveragePrice() {
		return averagePrice;
	}
	
	@Override
	public int compareTo(Object o) {
		if(!(o instanceof Bet))
			return -1;
		
		Bet other = (Bet)o;
		int betTypeCpmparison = this.betType.compareTo(other.betType);
		if(betTypeCpmparison!=0)
		{
			return betTypeCpmparison;
		}
		if(this.betType.equals(BetType.ASK))
		{
			return new Double(this.price).compareTo(other.price);
		}
		else
		{
			return new Double(other.price).compareTo(this.price);
		}
	}
}
