/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.ulille1.fil.odeva;

/**
 *
 * @author marius
 */
public class UnexistingCurrencyException extends Exception {
  UnexistingCurrencyException(String curr)
  {
    super("Currency "+curr+" is unknown !");
  }
}
