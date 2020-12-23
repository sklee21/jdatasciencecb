package net.uxl21.jdatasciencecb.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class BitArrayMaker {
	
	
	public static final int SINGLE_PRECISION_BIAS = 127;
	
	public static final int DOUBLE_PRECISION_BIAS = 1023;
	
	
	public static final int SINGLE_PRECISION_MANTISSA_BITS = 23;
	
	public static final int DOUBLE_PRECISION_MANTISSA_BITS = 52;
	
	
	
	public static ArrayList<String> toBinaryList(long longValue) throws Exception {
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
	
	
	public static String[] toBinaryArray(long longValue) throws Exception {
		return toBinaryList(longValue).toArray(new String[0]);
	}
	
	
	public static String toBinaryString(long longValue) throws Exception {
		StringBuffer binStr = new StringBuffer();
		
		toBinaryList(longValue).forEach(value -> {
			binStr.append(value);
		});
		
		return binStr.toString();
	}
	
	
	
	public static ArrayList<String> toBinaryList(int intValue) throws Exception {
		return toBinaryList(Integer.valueOf(intValue).longValue());
	}
	
	
	public static String[] toBinaryArray(int intValue) throws Exception {
		return toBinaryArray(Integer.valueOf(intValue).longValue());
	}
	
	
	public static String toBinaryString(int intValue) throws Exception {
		return toBinaryString(Integer.valueOf(intValue).longValue());
	}
	
	
	
	
	public static ArrayList<String> toBinaryList(float floatValue) throws Exception {
		int intValue = (int)floatValue;
		
		ArrayList<String> bits = toBinaryList(intValue);
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
	
	
	public static String[] toBinaryArray(float floatValue) throws Exception {
		return toBinaryList(floatValue).toArray(new String[0]);
	}
	
	
	public static String toBinaryString(float floatValue) throws Exception {
		StringBuffer binStr = new StringBuffer();
		
		toBinaryList(floatValue).forEach(value -> {
			binStr.append(value);
		});
		
		return binStr.toString();
	}
	
	
	
	public static ArrayList<String> toFloatingPointBinaryList(float floatValue) throws Exception {
		int intValue = (int)floatValue;
		
		ArrayList<String> mantissaBits = toBinaryList(intValue);
		
		String signBit = floatValue > 0 ? "0" : "1";
		ArrayList<String> exponentBits = toBinaryList(SINGLE_PRECISION_BIAS + mantissaBits.size() - 1);
		
		float tmpValue = floatValue - intValue;
		
		if( tmpValue == 0.0f ) {
			mantissaBits.add("0");
		
		} else {
			HashSet<Integer> historySet = new HashSet<>();
			int historyInt;
			
			while( true ) {
				tmpValue *= 2.0f;
				
				if( tmpValue < 1.0f ) {
					mantissaBits.add("0");
				
				} else {
					mantissaBits.add("1");
					
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
		
		mantissaBits.remove(0);
		int emptyBitCount = SINGLE_PRECISION_MANTISSA_BITS - mantissaBits.size();
		
		for(int i=0; i<emptyBitCount; i++) {
			mantissaBits.add("0");
		}
		
		ArrayList<String> floatingPointBits = new ArrayList<>();
		floatingPointBits.add(signBit);
		floatingPointBits.addAll(exponentBits);
		mantissaBits.forEach(bit -> {
			floatingPointBits.add(bit);
		});
		
		return floatingPointBits;
	}
	
	
	public static String[] toFloatingPointBinaryArray(float floatValue) throws Exception {
		return toFloatingPointBinaryList(floatValue).toArray(new String[0]);
	}
	
	
	public static String toFloatingPointBinaryString(float floatValue) throws Exception {
		StringBuffer binStr = new StringBuffer();
		
		toFloatingPointBinaryList(floatValue).forEach(value -> {
			binStr.append(value);
		});
		
		return binStr.toString();
	}
	
		
	
	public static ArrayList<String> toFloatingPointBinaryList(double doubleValue) throws Exception {
		long longValue = (long)doubleValue;
		
		ArrayList<String> mantissaBits = toBinaryList(longValue);
		
		String signBit = doubleValue > 0 ? "0" : "1";
		ArrayList<String> exponentBits = toBinaryList(DOUBLE_PRECISION_BIAS + mantissaBits.size() - 1);
		
		double tmpValue = doubleValue - longValue;
		
		if( tmpValue == 0.0f ) {
			mantissaBits.add("0");
		
		} else {
			HashSet<Integer> historySet = new HashSet<>();
			int historyInt;
			
			while( true ) {
				tmpValue *= 2.0f;
				
				if( tmpValue < 1.0f ) {
					mantissaBits.add("0");
				
				} else {
					mantissaBits.add("1");
					
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
		
		mantissaBits.remove(0);
		int emptyBits = DOUBLE_PRECISION_MANTISSA_BITS - mantissaBits.size();
		
		for(int i=0; i<emptyBits; i++) {
			mantissaBits.add("0");
		}
		
		ArrayList<String> floatingPointBits = new ArrayList<>();
		floatingPointBits.add(signBit);
		floatingPointBits.addAll(exponentBits);
		mantissaBits.forEach(bit -> {
			floatingPointBits.add(bit);
		});
		
		return floatingPointBits;
	}
	
	
	public static String[] toFloatingPointBinaryArray(double doubleValue) throws Exception {
		return toFloatingPointBinaryList(doubleValue).toArray(new String[0]);
	}
	
	
	public static String toFloatingPointBinaryString(double doubleValue) throws Exception {
		StringBuffer binStr = new StringBuffer();
		
		toFloatingPointBinaryList(doubleValue).forEach(value -> {
			binStr.append(value);
		});
		
		return binStr.toString();
	}
	
	
	

	
	public static void main(String[] args) {
		try {
			System.out.println();
			System.out.println("baMaker => " + BitArrayMaker.toBinaryString(127));
			System.out.println("Integer => " + Integer.toBinaryString(128));
			
			System.out.println();
			System.out.println("baMaker => " + BitArrayMaker.toBinaryString(69.6875f));
			System.out.println("baMaker => " + BitArrayMaker.toFloatingPointBinaryString(69.6875f));
			System.out.println("baMaker => " + BitArrayMaker.toFloatingPointBinaryString(69.6875d));
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
