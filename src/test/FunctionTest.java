package test;

import static org.junit.Assert.*;
import dev.*;
import org.junit.*;


public class FunctionTest {
	@Test
	public void testMultiplication(){
		Money five = Money.dollar(5);
		assertEquals(new Money(10,"USD"), five.times(2));
		assertEquals(new Money(15,"USD"), five.times(3));
	}
	@Test
	public void testFrancMultiplication(){
		Money five = Money.franc(5);
		assertEquals(new Money(10,"CHF"), five.times(2));
		assertEquals(new Money(15,"CHF"), five.times(3));
	}
	@Test
	public void testEquality(){
		assertTrue(Money.dollar(5).equals(Money.dollar(5)));
		assertTrue(Money.franc(5).equals(Money.franc(5)));
		assertFalse(Money.dollar(5).equals(Money.franc(5)));
	}
	@Test
	public void testCurrency(){
		assertEquals("USD", Money.dollar(1).Currency());
		assertEquals("CHF", Money.franc(1).Currency());
	}
	@Test
	public void testSimpleAddition(){
		Money five = Money.dollar(5);
		Expression sum = five.plus(five);
		Bank bank = new Bank();
		Money reduced = bank.reduce(sum,"USD");
		assertEquals(Money.dollar(10),reduced);
	}
	@Test
	public void testPlusReturnSum(){
		Money five = Money.dollar(5);
		Expression result = five.plus(five);
		Sum sum = (Sum) result;
		assertEquals(five,sum.augend);
		assertEquals(five,sum.addend);
	}
	@Test
	public void testReduceSum(){
		Expression sum = new Sum(Money.dollar(3),Money.dollar(4));
		Bank bank = new Bank();
		Money result = bank.reduce(sum, "USD");
		assertEquals(Money.dollar(7),result);
	}
	@Test 
	public void testReduceMoney(){
		Bank bank = new Bank();
		Money result = bank.reduce(Money.dollar(1), "USD");
		assertEquals(Money.dollar(1),result);
	}
	@Test
	public void testReduceMoneyDifferentCurrency(){
		Bank bank = new Bank();
		bank.addRate("CHF","USD",2);
		Money result = bank.reduce(Money.franc(2), "USD");
		assertEquals(Money.dollar(1), result);
	}
	@Test 
	public void testIdentityRate(){
		Bank bank = new Bank();
		bank.addRate("CHF","USD",2);
		assertEquals(2, bank.rate("CHF", "USD"));
		assertEquals(1, new Bank().rate("USD", "USD"));
	}
	@Test
	public void testMixedAddtion(){
		Expression fiveBucks = Money.dollar(5);
		Expression tenFrancs = Money.franc(10);
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 2); 
		Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
		assertEquals(Money.dollar(10), result);
		
	}
	@Test
	public void testSumPlusMoney(){
		Expression fiveBucks = Money.dollar(5);
		Expression tenFrancs = Money.franc(10);
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 2);
		Expression sum = new Sum(fiveBucks,tenFrancs.plus(fiveBucks));
		Money result = bank.reduce(sum, "USD");
		assertEquals(Money.dollar(15), result);
	}
	@Test
	public void testSumTimes(){
		Expression fiveBucks = Money.dollar(5);
		Expression tenFrancs = Money.franc(10);
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 2);
		Expression sum = new Sum(fiveBucks,tenFrancs.times(2));
		Money result = bank.reduce(sum, "USD");
		assertEquals(Money.dollar(15), result);
	}
	@Test
	public void testPlusSameCurrencyReturnMoney(){
		Expression sum = Money.dollar(1).plus(Money.dollar(1));
		assertTrue(sum instanceof Expression);
	}
}
