package generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupGenerator {

	private static final String FILE_PATH = ".\\resources\\members.txt";
	private static final int TEAM_NUM = 5;
	private static final String[] TEAM_NAME = new String[]{"A", "B", "C", "D", "E", "F"};

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			File file = new File(FILE_PATH);
			br = new BufferedReader(new FileReader(file));
			String line;

			List<String> memberList = new ArrayList<>();

			int teamNameCount = 0;
			printTeamName(teamNameCount++);

			while ((line = br.readLine()) != null) {
				memberList.add(line);
			}

			Collections.shuffle(memberList);

			for(int i = 0; i < memberList.size(); i++) {
				if (i >= TEAM_NUM && i % TEAM_NUM == 0) {
					printTeamName(teamNameCount++);
				}
				System.out.println(memberList.get(i));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				//
			}
		}
	}

	static void printTeamName(int index) {
		System.out.println("----------------------------------");
		System.out.println(TEAM_NAME[index]);
	}

}
