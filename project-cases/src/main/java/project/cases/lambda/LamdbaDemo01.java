package project.cases.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * lambda 案例一
 */
public class LamdbaDemo01 {

	public static void main(String[] args) {

		String[] atp = {"Rafael Nadal", "Novak Djokovic",
				"Stanislas Wawrinka",
				"David Ferrer", "Roger Federer",
				"Andy Murray", "Tomas Berdych",
				"Juan Martin Del Potro"};
		List<String> players = Arrays.asList(atp);

		// 以前的循环方式
		for (String player : players) {
			System.out.println(player + "; ");
		}
		System.out.println("-----------------------------");

		// 使用 lambda 表达式以及函数操作(functional operation)
		System.out.println("使用 lambda 表达式以及函数操作:");
		players.forEach((player) -> System.out.println(player + "; "));
		System.out.println("-----------------------------");

		// 在 Java 8 中使用双冒号操作符(double colon operator)
		System.out.println("在 Java 8 中使用双冒号操作符:");
		players.forEach(System.out::println);

	}
}
