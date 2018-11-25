
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Compute_a_posteriori {


	private static double h1 = 0.1;
	private static double h2 = 0.2;
	private static double h3 = 0.4;
	private static double h4 = 0.2;
	private static double h5 = 0.1;
	public static String otpt = "result.txt";

	public static void main(String[] args) {

		Scanner val = new Scanner(System.in);
		String ips = args[0];
		char ip[] = ips.toCharArray();
		int cc = 0, lc = 0;
		for (int i = 0; i < ip.length; i++) {
			if (ip[i] == 'C' || ip[i] == 'c') {
				cc++;
			} else if (ip[i] == 'L' || ip[i] == 'l') {
				lc++;
			} else {
				System.out.println("enter correct input\n");
				System.exit(0);
			}
		}
		double prob[] = new double[6];
		prob[1] = Math.pow(0, lc) * Math.pow(1, cc) * h1;
		prob[2] = Math.pow(0.25, lc) * Math.pow(0.75, cc) * h2;
		prob[3] = Math.pow(0.5, lc) * Math.pow(0.5, cc) * h3;
		prob[4] = Math.pow(0.75, lc) * Math.pow(0.25, cc) * h4;
		prob[5] = Math.pow(1, lc) * Math.pow(0, cc) * h5;

		double a = 0;
		for (int i = 1; i < 6; i++) {
			a = a + prob[i];

		}
		a = 1 / a;
		for (int i = 1; i < 6; i++) {
			prob[i] = prob[i] * a;
			prob[i] = Math.round(prob[i] * 100000);
			prob[i] = prob[i] / 100000;

		}
		double ca_p[] = { 0, 0.25, 0.5, 0.75, 1 };
		double lp = 0, ch_p = 0;
		for (int i = 1; i < 6; i++) {
			lp = lp + (ca_p[i - 1] * prob[i]);
			ch_p = ch_p + ((1 - ca_p[i - 1]) * prob[i]);
		}

		lp = Math.round(lp * 100000);
		lp = lp / 100000;
		ch_p = Math.round(ch_p * 100000);
		ch_p = ch_p / 100000;


		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(otpt));
			output.write("Observation sequence Q : " + ips);
			output.write("\r\nLength of Q : " + ips.length());

			for (int j = 0; j < 5; j++) {
				output.write("\r\nP(h" + (j + 1) + "|Q) = " + prob[j + 1]);
			}
			output.write("\r\nProbability that the next candy we pick will be C, given Q : " + ch_p);
			output.write("\r\nProbability that the next candy we pick will be L, given Q : " + lp);
			output.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}