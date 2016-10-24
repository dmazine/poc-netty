/**
 * @(#)UnixTime.java
 */
package br.com.advance.netty.example.time;

import java.util.Date;

/**
 * UnixTime.
 * 
 * @author diegorm
 * @version 1.0, Mar 29, 2012
 */
public class UnixTime {
	/** Value. */
	private final int value;

	/**
	 * Constructs a UnixTime object.
	 * 
	 * @param value
	 *            the number of seconds since 00:00 (midnight) 1 January 1900
	 *            GMT.
	 */
	public UnixTime(int value) {
		this.value = value;
	}

	/**
	 * Gets the number of seconds since 00:00 (midnight) 1 January 1900 GMT.
	 * 
	 * @return the number of seconds since 00:00 (midnight) 1 January 1900 GMT.
	 */
	public int getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new Date(value * 1000L).toString();
	}

}
