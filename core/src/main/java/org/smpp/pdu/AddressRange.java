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

import org.smpp.Data;
import org.smpp.util.*;

/**
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version $Revision: 1.1 $
 */
public class AddressRange extends ByteData {
	private byte ton = Data.getDefaultTon();
	private byte npi = Data.getDefaultNpi();
	private String addressRange = Data.DFLT_ADDR_RANGE;

	/**
	 * Construct an AddressRange with default ton, npi and range.
	 */
	public AddressRange() {
		super();
		setTon(Data.getDefaultTon());
		setNpi(Data.getDefaultNpi());
	}

	/**
	 * Construct an AddressRange with default ton, npi and given address range.
	 * @param addressRange the address range to set
	 * @throws WrongLengthOfStringException if length is invalid
	 */
	public AddressRange(String addressRange) throws WrongLengthOfStringException {
		super();
		setTon(Data.getDefaultTon());
		setNpi(Data.getDefaultNpi());
		setAddressRange(addressRange);
	}

	/**
	 * Construct an address range with given ton and npi
	 * @param ton the ton to set
	 * @param npi the npi to set
	 * @param addressRange the address range
	 * @throws WrongLengthOfStringException if length is invalid
	 */
	public AddressRange(byte ton, byte npi, String addressRange) throws WrongLengthOfStringException {
		setTon(ton);
		setNpi(npi);
		setAddressRange(addressRange);
	}

	public void setData(ByteBuffer buffer)
		throws NotEnoughDataInByteBufferException, TerminatingZeroNotFoundException, WrongLengthOfStringException {
		byte ton = buffer.removeByte();
		byte npi = buffer.removeByte();
		String addressRange = buffer.removeCString();
		setAddressRange(addressRange);
		setTon(ton);
		setNpi(npi);
	}

	public ByteBuffer getData() {
		ByteBuffer addressBuf = new ByteBuffer();
		addressBuf.appendByte(getTon());
		addressBuf.appendByte(getNpi());
		addressBuf.appendCString(getAddressRange());
		return addressBuf;
	}

	/**
	 * Set the ton
	 * @param t the ton to set
	 */
	public void setTon(byte t) {
		ton = t;
	}
	/**
	 * Set the npi
	 * @param n the npi to set
	 */
	public void setNpi(byte n) {
		npi = n;
	}
	/**
	 * Set the address range
	 * @param a the address ramge to set
	 * @throws WrongLengthOfStringException if address range length is invalid
	 */
	public void setAddressRange(String a) throws WrongLengthOfStringException {
		checkCString(a, Data.SM_ADDR_RANGE_LEN);
		addressRange = a;
	}

	/**
	 * Get the ton.
	 * @return the ton value
	 */
	public byte getTon() {
		return ton;
	}
	/**
	 * Get the npi.
	 * @return the npi value
	 */
	public byte getNpi() {
		return npi;
	}
	/**
	 * Get the address range string.
	 * @return the address range string
	 */
	public String getAddressRange() {
		return addressRange;
	}

	public String debugString() {
		String dbgs = "(addrrang: ";
		dbgs += super.debugString();
		dbgs += Integer.toString(getTon());
		dbgs += " ";
		dbgs += Integer.toString(getNpi());
		dbgs += " ";
		dbgs += getAddressRange();
		dbgs += ") ";
		return dbgs;
	}
}
/*
 * $Log: not supported by cvs2svn $
 */