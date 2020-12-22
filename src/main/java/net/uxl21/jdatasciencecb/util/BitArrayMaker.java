package net.uxl21.jdatasciencecb.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
	
	
	
	protected ArrayList<String> toBinaryList(long longValue) throws Exception {
		long quotient = longValue;
		long rest;
		
		ArrayList<String> bits = new ArrayList<>();
		
		while( true ) {
			rest = Math.floorMod(quotient, 2);
			quotient = Math.floorDiv(quotient, 2);
			
			bits.add(Long.toString(rest));
			
			if( quotient == 0 ) {
				break;
			}
		}
		
		Collections.reverse(bits);
		
		return bits;
	}
	
	
	protected String[] toBinaryArray(long longValue) throws Exception {
		return this.toBinaryList(longValue).toArray(new String[0]);
	}
	
	
	protected String toBinaryString(long longValue) throws Exception {
		StringBuffer binStr = new StringBuffer();
		
		this.toBinaryList(longValue).forEach(value -> {
			binStr.append(value);
		});
		
		return binStr.toString();
	}
	
	
	
	protected ArrayList<String> toBinaryList(int intValue) throws Exception {
		return this.toBinaryList(Integer.valueOf(intValue).longValue());
	}
	
	
	protected String[] toBinaryArray(int intValue) throws Exception {
		return this.toBinaryArray(Integer.valueOf(intValue).longValue());
	}
	
	
	protected String toBinaryString(int intValue) throws Exception {
		return this.toBinaryString(Integer.valueOf(intValue).longValue());
	}
	
	
	
	
	protected ArrayList<String> toBinaryList(float floatValue) throws Exception {
		int intValue = (int)floatValue;
		
		ArrayList<String> bits = this.toBinaryList(intValue);
		bits.add(".");
		
		float tmpValue = floatValue - intValue;
		
		if( tmpValue == 0.0f ) {
			bits.add("0");
		
		} else {
			HashSet<Integer> historySet = new HashSet<>();
			int historyInt;
			
			while( true ) {
				tmpValue *= 2.0f;
				
				if( tmpValue < 1.0f ) {
					bits.add("0");
				
				} else {
					bits.add("1");
					
					if( tmpValue == 1.0f ) {
						break;
						
					} else {
						tmpValue -= 1.0f;
					}
				}
				
				historyInt = (int)(tmpValue * 10);
				
				if( historySet.contains(Integer.valueOf(historyInt)) ) {
					break;
					
				} else {
					historySet.add(historyInt);
				}
			}
		}
		
		return bits;
	}
	
	
	protected String[] toBinaryArrays(float floatValue) throws Exception {
		return this.toBinaryList(floatValue).toArray(new String[0]);
	}
	
	
	protected String toBinaryString(float floatValue) throws Exception {
		StringBuffer binStr = new StringBuffer();
		
		this.toBinaryList(floatValue).forEach(value -> {
			binStr.append(value);
		});
		
		return binStr.toString();
	}
	
	
	
	protected ArrayList<String> toFloatingPointBinaryList(float floatValue) throws Exception {
		int intValue = (int)floatValue;
		
		ArrayList<String> mantissa = this.toBinaryList(intValue);
		
		String signBit = floatValue > 0 ? "0" : "1";
		ArrayList<String> exponentBits = this.toBinaryList(127 + mantissa.size() - 1);
		
		float tmpValue = floatValue - intValue;
		
		if( tmpValue == 0.0f ) {
			mantissa.add("0");
		
		} else {
			HashSet<Integer> historySet = new HashSet<>();
			int historyInt;
			
			while( true ) {
				tmpValue *= 2.0f;
				
				if( tmpValue < 1.0f ) {
					mantissa.add("0");
				
				} else {
					mantissa.add("1");
					
					if( tmpValue == 1.0f ) {
						break;
						
					} else {
						tmpValue -= 1.0f;
					}
				}
				
				historyInt = (int)(tmpValue * 10);
				
				if( historySet.contains(Integer.valueOf(historyInt)) ) {
					break;
					
				} else {
					historySet.add(historyInt);
				}
			}
		}
		
		mantissa.remove(0);
		int emptyBits = 23 - mantissa.size();
		
		for(int i=0; i<emptyBits; i++) {
			mantissa.add("0");
		}
		
		ArrayList<String> allBits = new ArrayList<>();
		allBits.add(signBit);
		allBits.add("/");
		allBits.addAll(exponentBits);
		allBits.add("/");
		
		for(int i=0; i<mantissa.size(); i++) {
			allBits.add(mantissa.get(i));
		}
		
		return allBits;
	}
	
	
	protected String toFloatingPointBinaryString(float floatValue) throws Exception {
		StringBuffer binStr = new StringBuffer();
		
		this.toFloatingPointBinaryList(floatValue).forEach(value -> {
			binStr.append(value);
		});
		
		return binStr.toString();
	}
	
		
	
	protected ArrayList<String> toFloatingPointBinaryList(double doubleValue) throws Exception {
		long longValue = (int)doubleValue;
		
		ArrayList<String> mantissa = this.toBinaryList(longValue);
		
		String signBit = doubleValue > 0 ? "0" : "1";
		ArrayList<String> exponentBits = this.toBinaryList(1023 + mantissa.size() - 1);
		
		double tmpValue = doubleValue - longValue;
		
		if( tmpValue == 0.0f ) {
			mantissa.add("0");
		
		} else {
			HashSet<Integer> historySet = new HashSet<>();
			int historyInt;
			
			while( true ) {
				tmpValue *= 2.0f;
				
				if( tmpValue < 1.0f ) {
					mantissa.add("0");
				
				} else {
					mantissa.add("1");
					
					if( tmpValue == 1.0f ) {
						break;
						
					} else {
						tmpValue -= 1.0f;
					}
				}
				
				historyInt = (int)(tmpValue * 10);
				
				if( historySet.contains(Integer.valueOf(historyInt)) ) {
					break;
					
				} else {
					historySet.add(historyInt);
				}
			}
		}
		
		mantissa.remove(0);
		int emptyBits = 52 - mantissa.size();
		
		for(int i=0; i<emptyBits; i++) {
			mantissa.add("0");
		}
		
		ArrayList<String> allBits = new ArrayList<>();
		allBits.add(signBit);
		allBits.add("/");
		allBits.addAll(exponentBits);
		allBits.add("/");
		
		for(int i=0; i<mantissa.size(); i++) {
			allBits.add(mantissa.get(i));
		}
		
		return allBits;
	}
	
	
	protected String toFloatingPointBinaryString(double doubleValue) throws Exception {
		StringBuffer binStr = new StringBuffer();
		
		this.toFloatingPointBinaryList(doubleValue).forEach(value -> {
			binStr.append(value);
		});
		
		return binStr.toString();
	}
	
	
	

	
	public static void main(String[] args) {
		BitArrayMaker baMaker = new BitArrayMaker();
		
		try {
			System.out.println();
			System.out.println("baMaker => " + baMaker.toBinaryString(127));
			System.out.println("Integer => " + Integer.toBinaryString(128));
			
			System.out.println();
			System.out.println("baMaker => " + baMaker.toBinaryString(69.6875f));
			System.out.println("baMaker => " + baMaker.toFloatingPointBinaryString(69.6875f));
			System.out.println("baMaker => " + baMaker.toFloatingPointBinaryString(69.6875d));
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
