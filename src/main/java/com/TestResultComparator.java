package com;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestResultComparator implements Comparator<String>{
	Pattern pattern;
	Matcher match;
	int greppedKey1, greppedKey2;

	public int compare(String testCase1, String testCase2) {
		pattern = Pattern.compile("-TC-[\\d]+");
		match = pattern.matcher(testCase1);
		if (match.find()) {
			greppedKey1 = Integer.parseInt(match.group().toString().substring(4));
		}
		match = pattern.matcher(testCase2);
		if (match.find()) {
			greppedKey2 = Integer.parseInt(match.group().toString().substring(4));
		}
		if(greppedKey1>greppedKey2) {
			return 1;
		}else if(greppedKey1<greppedKey2) {
			return -1;
		}else {
			return 0;
		}
	}

}
