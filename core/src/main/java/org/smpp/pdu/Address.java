/*
 * Copyright (c) 1996-2001
 * Logica Mobile Networks Limited
 * All rights reserved.
 *
 * This software is distributed under Logica Open Source License Version 1.0
 * ("Licence Agreement"). You shall use it and distribute only in accordance
 * with the terms of the License Agreement.
 *
 */
package org.smpp.pdu;

import java.io.UnsupportedEncodingException;

import org.smpp.Data;
import org.smpp.util.*;

/**
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version $Id: Address.java 72 2008-07-15 19:43:00Z sverkera $
 */
public class Address extends ByteData {
	private byte ton = Data.DFLT_GSM_TON;
	private byte npi = Data.DFLT_GSM_NPI;
	private String address = Data.DFLT_ADDR;
	private static int defaultMaxAddressLength = Data.SM_ADDR_LEN;
	private int maxAddressLength = defaultMaxAddressLength;

	/**
	 * Construct Address with default ton, npi, address, and max address length.
	 */
	public Address() {
		this(Data.getDefaultTon(), Data.getDefaultNpi(), defaultMaxAddressLength);
	}

	/**
	 * Construct Address with default ton, npi, address, and given max address length.
	 * @param maxAddressLength maximum address length
	 */
	public Address(int maxAddressLength) {
		this(Data.getDefaultTon(), Data.getDefaultNpi(), maxAddressLength);
	}

	/**
	 * Construct Address with default address and given ton, npi and max length.
	 * @param ton the ton to set
	 * @param npi the npi to set
	 * @param maxAddressLength maximum address lengh
	 */
	public Address(byte ton, byte npi, int maxAddressLength) {
		setTon(ton);
		setNpi(npi);
		try {
			setAddress(Data.DFLT_ADDR, maxAddressLength);
		} catch (WrongLengthOfStringException e) {
			throw new Error("Default address value was longer than default max address length.");
		}
	}

	/**
	 * Construct Address with default ton, npi and maximum length.
	 * @param address string to set address to
	 * @throws WrongLengthOfStringException when invalid address length
	 */
	public Address(String address) throws WrongLengthOfStringException {
		this(Data.getDefaultTon(), Data.getDefaultNpi(), address, defaultMaxAddressLength);
	}

	/**
	 * Construct Address with default ton, npi and given maximum length.
	 * @param address string to set address to
	 * @param maxAddressLength maximum length for address
	 * @throws WrongLengthOfStringException when invalid address length
	 */
	public Address(String address, int maxAddressLength) throws WrongLengthOfStringException {
		this(Data.getDefaultTon(), Data.getDefaultNpi(), address, maxAddressLength);
	}

	/**
	 * Construct Address with given ton and npi.
	 * @param ton the ton to set
	 * @param npi the npi to set
	 * @param address the address
	 * @throws WrongLengthOfStringException when address is too long
	 */
	public Address(byte ton, byte npi, String address) throws WrongLengthOfStringException {
		this(ton, npi, address, defaultMaxAddressLength);
	}

	/**
	 * Construct Address with given ton, npi, and maximum length.
	 * @param ton the ton to set
	 * @param npi the npi to set
	 * @param address the address
	 * @param maxAddressLength the maximum length to set
	 * @throws WrongLengthOfStringException when address is too long
	 */
	public Address(byte ton, byte npi, String address, int maxAddressLength) throws WrongLengthOfStringException {
		setTon(ton);
		setNpi(npi);
		setAddress(address, maxAddressLength);
	}

	public void setData(ByteBuffer buffer)
	throws NotEnoughDataInByteBufferException, TerminatingZeroNotFoundException, WrongLengthOfStringException {
		byte ton = buffer.removeByte();
		byte npi = buffer.removeByte();
		String address = buffer.removeCString();
		setAddress(address);
		setTon(ton);
		setNpi(npi);
	}

	public ByteBuffer getData() {
		ByteBuffer addressBuf = new ByteBuffer();
		addressBuf.appendByte(getTon());
		addressBuf.appendByte(getNpi());
		try {
			addressBuf.appendCString(getAddress(), Data.ENC_ASCII);
		} catch(UnsupportedEncodingException e) {
			// can't happen, ascii is always supported
		}
		
		return addressBuf;
	}

	/**
	 * Set the TON
	 * @param ton the ton to set
	 */
	public void setTon(byte ton) {
		this.ton = ton;
	}
	/**
	 * Set the NPI
	 * @param npi the NPI to set
	 */
	public void setNpi(byte npi) {
		this.npi = npi;
	}
	/**
	 * Set the address
	 * @param address to set
	 * @throws WrongLengthOfStringException if the address exceeds the maximum length
	 */
	public void setAddress(String address) throws WrongLengthOfStringException {
		setAddress(address, maxAddressLength);
	}
	/**
	 * Set the address
	 * @param address the address to set
	 * @param maxAddressLength the maximum length
	 * @throws WrongLengthOfStringException if length exceeds the maximum length
	 */
	public void setAddress(String address, int maxAddressLength) throws WrongLengthOfStringException {
		checkCString(address, maxAddressLength);
		this.maxAddressLength = maxAddressLength;
		this.address = address;
	}

	/**
	 * Get the ton
	 * @return the ton
	 */
	public byte getTon() {
		return ton;
	}
	/** 
	 * Get the NPI
	 * @return the npi
	 */
	public byte getNpi() {
		return npi;
	}

	/**
	 * Get the address string.
	 * @return the address string
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Get the address string in the given encoding.
	 * @param encoding the encoding
	 * @return the address string in the given encoding
	 */
	public String getAddress(String encoding) {
		// The address is by default encoded with Data.ENC_ASCII
		// Create a new string with the given encoding
		try {
			byte[] bytes = address.getBytes(Data.ENC_ASCII);
			String newAddress = new String(bytes, encoding);
			return newAddress;
		} catch(UnsupportedEncodingException uee) {
			// just return the address as it is
			return address;
		}
	}

	public String debugString() {
		String dbgs = "(addr: ";
		dbgs += super.debugString();
		dbgs += Integer.toString(getTon());
		dbgs += " ";
		dbgs += Integer.toString(getNpi());
		dbgs += " ";
		dbgs += getAddress();
		dbgs += ") ";
		return dbgs;
	}

}
/*
 * $Log: not supported by cvs2svn $
 * Revision 1.2  2003/09/30 09:04:07  sverkera
 * Use GSM 7Bit as default but fall back to Ascii if it doesn't exsist
 *
 * Revision 1.1  2003/07/23 00:28:39  sverkera
 * Imported
 *
 *
 * Old changelog:
 * 11-10-01 ticp@logica.com max address length configurable per instance
 */
