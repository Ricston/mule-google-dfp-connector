/**
 * (c) 2003-2015 Ricston, Ltd. The software in this package is published under the terms of the CPAL v1.0 license,
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.google.dfp.exceptions;

public class TooManyAdvertisersFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6842034221308676994L;


	public TooManyAdvertisersFoundException(int companiesFound){
		super(companiesFound + " company results found for your query, should have been only 1");
	}
}
