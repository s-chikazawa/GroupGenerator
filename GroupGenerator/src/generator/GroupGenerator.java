package generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupGenerator {

	private static final String FILE_PATH = ".\\resources\\members.txt";
	private static final int TEAM_NUM = 5;
	private static final String[] TEAM_NAME = new String[]{"A", "B", "C", "D", "E", "F"};
	private static final String REQUEST_URL = "";

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			File file = new File(FILE_PATH);
			br = new BufferedReader(new FileReader(file));
			String line;

			List<String> memberList = new ArrayList<>();

			StringBuilder sb = new StringBuilder();

			int teamNameCount = 0;
			sb.append(getTeamName(teamNameCount++));

			while ((line = br.readLine()) != null) {
				memberList.add(line);
			}

			Collections.shuffle(memberList);

			for(int i = 0; i < memberList.size(); i++) {
				if (i >= TEAM_NUM && i % TEAM_NUM == 0) {
					sb.append(getTeamName(teamNameCount++));
				}
				sb.append(memberList.get(i)).append("\n");
			}
			sendToSlack(sb.toString());
		} catch (Exception e) {
			showError(e);
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				showError(e);
			}
		}
	}

	static String getTeamName(int index) {
		return new StringBuilder()
			.append("----------------------------------")
			.append("\n")
			.append(TEAM_NAME[index])
			.append("\n")
			.toString();
	}

	static void sendToSlack(String body) throws Exception {
		URL url = new URL(REQUEST_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		String text = new StringBuilder()
				.append("{text:\""+ body + "\"}")
				.toString();

		OutputStream os = connection.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(text);
		writer.flush();
		writer.close();
		os.close();
		connection.getResponseCode();
	}

	static void showError(Exception e) {
		System.out.println("##### ERROR #####");
		System.out.println(e.getMessage());
	}

}
