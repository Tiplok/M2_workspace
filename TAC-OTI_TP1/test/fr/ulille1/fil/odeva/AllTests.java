package fr.ulille1.fil.odeva;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MoneyAddTestCase.class, MoneyTestCase.class })

// Permet d'avoir un meilleur contrôle sur les tests à éxecuter, on choisit les classes de tests (avec le @SuiteClasses) qui seront à valider
public class AllTests {
	
}