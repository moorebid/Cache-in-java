import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * This class runs tests on the cache class
 * 
 * @author Jeffrey Moore
 *
 */
public class Test {
	private static int testChoice, argsLength;
	private static double size1, size2;
	// ratio variables
	private static double HR, HR1, HR2, NH, NH1, NH2, NR, NR1, NR2;
	private static String fileName;

	/**
	 * main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		// so methods all don't have to be static
		Test tester = new Test(args);
		tester.runTests(testChoice);
	}

	/**
	 * Constructor
	 * 
	 * @param cmds
	 *            to parse
	 */
	public Test(String[] cmds) {
		argsLength = cmds.length;
		testChoice = Integer.parseInt(cmds[0]);
		// hit and reference variables
		HR = HR1 = HR2 = NH = NH1 = NH2 = NR = NR1 = NR2 = 0;

		if (argsLength < 3 || argsLength > 4) {
			System.err.println("usage: \n" + "java Test 1 <cache size> <input textfile name>\n"
					+ "java Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>\n");
			System.exit(1);
		}
		if (argsLength == 3 && testChoice != 1) {
			System.err.println("usage: \n" + "java Test 1 <cache size> <input textfile name>\n"
					+ "java Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>\n");
			System.exit(1);
		}
		if (argsLength == 4 && testChoice != 2) {
			System.err.println("usage: \n" + "java Test 1 <cache size> <input textfile name>\n"
					+ "java Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>\n");
			System.exit(1);
		}
		if (testChoice == 1) {
			size1 = Double.parseDouble(cmds[1]);
			fileName = cmds[2];
		}
		if (testChoice == 2) {
			size1 = Double.parseDouble(cmds[1]);
			size2 = Double.parseDouble(cmds[2]);
			fileName = cmds[3];
			
			if(size2 < size1) {
				System.err.println("The second level Cache should be larger than the first level.");
				System.exit(1);
			}
		}
	}

	/**
	 * runs tests on cache class
	 * 
	 * @param choice
	 *            test1 or test2
	 */
	private void runTests(int choice) {

		switch (choice) {
		case 1:
			Cache<String> single = new Cache<>();

			try {
				Scanner scan = new Scanner(new File(fileName));
				String s;

				while (scan.hasNext()) {
					NR++;
					// set s to next and trim punctuation, letters all lower case
					s = scan.next().replaceAll("[^a-zA-Z]+$", "").toLowerCase();
					// hit
					if (single.cacheContains(s)) {
						single.movetoFront(s);
						NH++;
					}
					// miss
					else {
						if (single.size() == size1) {
							single.removeLast();
							single.addObject(s);
						} else {
							single.addObject(s);
						}
					}
				}
				scan.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			// ratio calculations
			HR = (NH / NR);
			printTestResults();
			break;

		case 2:
			// create both caches
			Cache<String> first = new Cache<>();
			Cache<String> second = new Cache<>();

			try {
				Scanner scan = new Scanner(new File(fileName));
				String s;

				while (scan.hasNext()) {
					NR++;
					// s = scan.next().replaceAll("[^a-zA-Z]+$", "").toLowerCase();
					s = scan.next();
					if (first.cacheContains(s)) {
						first.movetoFront(s);
						second.movetoFront(s);
						NR1++;
						NH1++;
					} else if (second.cacheContains(s)) {
						second.movetoFront(s);
						if (first.size() == size1) {
							first.removeLast();
							first.addObject(s);
						} else {
							first.addObject(s);
						}
						NR1++;
						NH2++;

					} else {
						NR1++;
						if (first.size() == size1) {
							first.removeLast();
							first.addObject(s);
						} else {
							first.addObject(s);
						}
						if (second.size() == size2) {
							second.removeLast();
							second.addObject(s);
						} else {
							second.addObject(s);
						}
					}
				}

				scan.close();

			} catch (Exception e) {
				// System.out.println(e.getMessage());
			}
			// hit ratio calculations
			NH = NH1 + NH2;
			NR = NR1;
			NR2 = NR1 - NH1;
			HR1 = (NH1 / NR1);
			HR2 = (NH2 / NR2);
			HR = ((NH1 + NH2) / NR1);

			printTestResults();
			break;
		}
	}

	/**
	 * sends results to the console
	 */
	private void printTestResults() {
		StringBuilder sb = new StringBuilder();
		NumberFormat df = new DecimalFormat("#.#################");

		if (testChoice == 1) {
			String s1 = "\nFirst level cache with " + df.format(size1) + " entries has been created\n"
					+ "..............................\n";
			String s2 = "The number of global references: " + df.format(NR) + "\n" + "The number of global cache hits: "
					+ df.format(NH) + "\n" + "The global hit ratio                  : " + df.format(HR) + "\n\n";
			sb.append(s1 + s2);
			System.out.println(sb.toString());
		} else {
			String s1 = "\nFirst level cache with " + df.format(size1) + " entries has been created\n"
					+ "Second level cache with " + df.format(size2) + " entries has been created\n"
					+ "..............................\n";
			String s2 = "The number of global references: " + df.format(NR) + "\n" + "The number of global cache hits: "
					+ df.format(NH) + "\n" + "The global hit ratio                  : " + df.format(HR) + "\n\n";
			String s3 = "The number of 1st-level references: " + df.format(NR1) + "\n"
					+ "The number of 1st-level cache hits: " + df.format(NH1) + "\n"
					+ "The 1st-level cache hit ratio             : " + df.format(HR1) + "\n\n";
			String s4 = "The number of 2nd-level references: " + df.format(NR2) + "\n"
					+ "The number of 2nd-level cache hits: " + df.format(NH2) + "\n"
					+ "The 2nd-level cache hit ratio             : " + df.format(HR2) + "\n";

			sb.append(s1 + s2 + s3 + s4);
			System.out.println(sb.toString());
		}
	}
}// end
