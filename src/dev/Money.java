package dev;

public class Money implements Expression{
	protected int amount;
	protected String currency;
	
	public Money(int amount,String currency){
		this.amount = amount;
		this.currency = currency;
	}
	
	public int getAmount(){
		return this.amount;
	}
	
	public String getCurrency(){
		return this.currency;
	}
	
	public static Money dollar(int amount){
		return new Money(amount,"USD");
	}
	
	public static Money franc(int amount){
		return new Money(amount,"CHF");
	}
	
	public boolean equals(Object object){
		Money money = (Money) object;
		return amount == money.amount && this.Currency().equals(money.Currency());
	}
	
	public String Currency(){
		return currency;
	}
	
	public Expression plus(Expression addend){
		return new Sum(this,addend);
	}
	
	public Money times(int multiplier){
		return new Money(amount*multiplier,currency);
	}

	@Override
	public Money reduce(Bank bank,String to) {
		int rate = bank.rate(currency, to);
		return new Money(amount/rate,to);
	}
}
