package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Programm {

	public static void main(String[] args) {
		String[] arr2 = { "S", "Shrinath", "Shree", "Shrikant", "Mhohan", "Shrinath" };

		Arrays.stream(arr2).filter(s -> s.startsWith("S")).collect(Collectors.groupingBy(s -> s, Collectors.counting()))
				.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEach((k) -> System.out.println(k.getKey() + ":" + k.getValue()));

		int[] arr = { 12, 34, 2, 12, 44, 25, 1 };

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}

		System.out.println(Arrays.toString(arr));

		int[] arr3 = { 1, 2, 5, 6 };

		Set<Integer> set = new HashSet<>();

		for (int i = 0; i < arr3.length; i++) {
			set.add(arr3[i]);
		}

		List<Integer> list = new ArrayList<>();

		for (int i = 1; i < arr3[arr3.length-1]; i++) {
			if (!set.contains(i)) {
				list.add(i);
			}
		}

		System.out.println(list);

	}

}
