package fr.ulille1.fil.odeva;

import static org.junit.Assert.*;
import org.junit.*;

import fr.ulille1.fil.odeva.Money;
import fr.ulille1.fil.odeva.MoneyFactory;
import fr.ulille1.fil.odeva.MoneyOps;
import fr.ulille1.fil.odeva.UnexistingCurrencyException;

/*
 * Unit test for simple App.
 */
public class MoneyAddTestCase
{
    private Money f12EUR,  f14EUR;
    private MoneyFactory mf;
    
    @Before
    public void init() throws UnexistingCurrencyException
    {
      mf=MoneyFactory.getDefaultFactory();
      f12EUR=mf.createMoney(12, "EUR");
      f14EUR=mf.createMoney(14, "EUR");;
    }

    /**
     * simpleAdd
     */
    @Test
    public void simpleAdd() throws UnexistingCurrencyException
    {
        Money expected=mf.createMoney(26, "EUR");
        Money result=MoneyOps.simpleAdd(f12EUR,f14EUR);
        //assertEquals(expected,result); -> assertTrue(expected.equals(result));
        assertTrue(expected.equals(result));
    }
    
    @Test
    public void simpleAdd2() throws UnexistingCurrencyException
    {
        Money m1 = mf.createMoney(13, "EUR");
        Money m2 = mf.createMoney(27, "EUR");
        //m1 = mock(Money.class);
        assertTrue(MoneyOps.simpleAdd(m1, m2).equals(mf.createMoney(40, "EUR")));
    }
}
