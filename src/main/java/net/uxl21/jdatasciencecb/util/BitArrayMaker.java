package net.uxl21.jdatasciencecb.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BitArrayMaker {
	
	public enum Types {
		DEC_TO_BIN
	};
	
	
	public enum Bits {
		
		BITS_8 (8),
		BITS_16(16),
		BITS_32(32),
		BITS_64(64);
		
		
		private int value;
		
		
		Bits(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
		
	};
	
	
	private Types type;
	
	private Bits bits = Bits.BITS_32;
	
	//private boolean autoIncrement = true;
	
	
	
	
	public BitArrayMaker(Types type) {
		this.type = type;
	}
	
	
	/*
	public BitArrayMaker(Types type, Bits bits, boolean autoIncrement) {
		this.type = type;
		this.bits = bits;
		this.autoIncrement = autoIncrement;
	}
	*/
	
	
	
	protected ArrayList<String> toBinaryList(int intValue) throws Exception {
		int quotient = intValue;
		int rest;
		
		ArrayList<String> bits = new ArrayList<>();
		
		while( true ) {
			rest = Math.floorMod(quotient, 2);
			quotient = Math.floorDiv(quotient, 2);
			
			bits.add(Integer.toString(rest));
			
			if( quotient == 0 ) {
				break;
			}
		}
		
		if( bits.size() > Bits.BITS_32.value ) {
			throw new Exception(String.format("Bit overflow. (expected %d but %d)", Bits.BITS_32.value, bits.size()));
			
		} else {
			Collections.reverse(bits);
			
			int emptyBits = Bits.BITS_32.value - bits.size();
			
			for( int i=0; i<emptyBits; i++ ) {
				bits.add(0, "0");
			}
		}
		
		return bits;
	}
	
	
	protected String[] toBinaryArray(int intValue) throws Exception {
		return this.toBinaryList(intValue).toArray(new String[0]);
	}
	
	
	
	
	protected ArrayList[] toBinaryList(float floatValue) throws Exception {
		int intValue = (int)floatValue;
		float underZero = floatValue - intValue;
		
		ArrayList<String> intBinArray = this.toBinaryList(intValue);
		ArrayList<String> floatBinArray = new ArrayList<>();
		
		System.out.println(floatValue + " = " + intValue + " + " + underZero);
		float tmpValue;
		
		while( true ) {
			tmpValue = underZero * 2.0f;
			
			if( tmpValue > 1 ) {
				tmpValue -= 1.0f;
				floatBinArray.add("1");
				
			} else if( tmpValue == 1 ) {
				break;
			
			} else {
				floatBinArray.add("0");
			}
		}
		
		return new ArrayList[] {
			intBinArray, floatBinArray
		};
	}
	
	
	
	

	
	public static void main(String[] args) {
		BitArrayMaker baMaker = new BitArrayMaker(Types.DEC_TO_BIN);
		
		try {
			baMaker.toBinaryList(128).forEach(System.out::print);
			System.out.println("\n" + Integer.toBinaryString(128));
			
			baMaker.toBinaryList(118.625f);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
