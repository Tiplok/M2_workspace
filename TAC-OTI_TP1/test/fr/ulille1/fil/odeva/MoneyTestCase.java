package fr.ulille1.fil.odeva;

import static org.junit.Assert.*;
import org.junit.*;

// Classe des tests pour la classe Money.java, on vérifie ses différentes méthodes
public class MoneyTestCase {
	private MoneyFactory mf;
	private Money m1, m2, m3, m4;
	    
    @Before
    public void init() throws UnexistingCurrencyException
    {
      mf=MoneyFactory.getDefaultFactory();
      m1 = mf.createMoney(64, "EUR");
      m2 = mf.createMoney(64, "EUR");
      m3 = mf.createMoney(128, "EUR");
      m4 = mf.createMoney(64, "DOL");
    }
    
    @Test
    public void testGetValue() throws UnexistingCurrencyException
    {
        Money m = mf.createMoney(32, "EUR");
        assertEquals(m.getValue(), 32);
    }
    
    @Test
    public void testGetCurrency() throws UnexistingCurrencyException
    {
        Money m = mf.createMoney(8, "EUR");
        assertEquals(m.getCurrency(), "EUR");
    }
    
    @Test
    public void testToString() throws UnexistingCurrencyException
    {
        Money m = mf.createMoney(64, "EUR");
        assertEquals(m.toString(), "64 (EUR)");
    }
    
    @Test
    public void testEqualsSameValues() throws UnexistingCurrencyException
    {
        assertTrue(m1.equals(m2));
        assertTrue(m2.equals(m1));
    }
    
    @Test
    public void testEqualsDifferentsValues() throws UnexistingCurrencyException
    { 
        assertFalse(m1.equals(m3));
        assertFalse(m2.equals(m3));
    }
    
    @Test
    public void testEqualsDifferentsDevises() throws UnexistingCurrencyException
    { 
        assertFalse(m1.equals(m4)); // Même valeur mais devise différente
        assertFalse(m3.equals(m4)); // Valeur et devise différente
    }
    
    @Test
    public void testEquals3() throws UnexistingCurrencyException
    {
        assertEquals(m1.equals(m2), m2.equals(m1)); // Test utile ?
    }
}
