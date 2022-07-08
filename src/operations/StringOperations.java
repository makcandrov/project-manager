package operations;

import java.util.ArrayList;
import java.util.regex.*;

public abstract class StringOperations {

	public static int getIndexSequence(String line, String s) {
		if (line == null || !line.contains(s)) {
			return -1;
		} else {
			int i = 0;
			while (!line.startsWith(s, i)) {
				i++;
			}
			return i;
		}
	}

	public static String getSubstringAfter(String line, String s) {
		int i = getIndexSequence(line, s);

		if (i == -1) {
			return null;
		} else {
			return line.substring(i + s.length());
		}
	}

	public static String getSubstringBefore(String line, String s) {
		int i = getIndexSequence(line, s);

		if (i == -1) {
			return null;
		} else {

			return line.substring(0, i);
		}
	}

	public static String getSubstringFramedBy(String line, String beg, String end) {
		return getSubstringBefore(getSubstringAfter(line, beg), end);
	}

	public static String getSubstringAfter(String line, ArrayList<String> sList) {
		for (String s : sList) {
			String res = getSubstringAfter(line, s);
			if (res != null) {
				return res;
			}
		}
		return null;
	}

	public static String getSubstringBefore(String line, ArrayList<String> sList) {
		String res = null;

		for (String s : sList) {
			String nres = getSubstringBefore(line, s);
			if (nres != null && (res == null || nres.length() < res.length())) {
				res = nres;
			}
		}
		return res;
	}

	public static String getSubstringFramedBy(String line, ArrayList<String> begList, ArrayList<String> endList) {
		return getSubstringBefore(getSubstringAfter(line, begList), endList);
	}

	public static ArrayList<String> getAllSubstringsFramedBy(String line, String beg, String end) {
		ArrayList<String> res = new ArrayList<>();

		while ((line = getSubstringAfter(line, beg)) != null) {
			res.add(getSubstringBefore(line, end));
		}

		return res;

	}

	public static String getSubstringFramedByNotEmpty(String line, String beg, String end) {
		ArrayList<String> substrings = getAllSubstringsFramedBy(line, beg, end);

		for (String s : substrings) {
			if (s.length() != 0) {
				return s;
			}
		}

		return null;
	}

	public static ArrayList<String> getAllSubstringsWithHeader(String line, String header) {
		ArrayList<String> res = new ArrayList<>();

		while ((line = getSubstringAfter(line, header)) != null) {
			String substringBefore = getSubstringBefore(line, header);

			if (substringBefore == null) {
				res.add(header + line);
			} else {
				res.add(header + substringBefore);
			}
		}

		return res;

	}

	public static String deleteFirstSpace(String line) {
		int i = 0;

		if (line == null) {
			return null;
		} else {
			int n = line.length();
			while (i < n && ((int) line.charAt(i) == 32 || (int) line.charAt(i) == 9)) {
				i++;
			}
		}
		return line.substring(i);
	}

	public static String deleteLastSpace(String line) {
		int i = 0;
		if (line == null) {
			return null;
		} else {
			i = line.length() - 1;

			while (i >= 0 && ((int) line.charAt(i) == 32 || (int) line.charAt(i) == 9)) {
				i--;
			}
		}
		return line.substring(0, i + 1);
	}

	public static int firstCharCounter(String line, char c) {
		int i = 0;
		while (line.charAt(i) == c) {
			i++;
		}
		return i;
	}

	public static boolean regexMatching(String line, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		return m.matches();
	}

	public static int regexExtractNumber(String line, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		if (m.find()) {
			return Integer.parseInt(m.group(1));
		} else {
			return -1;
		}
	}

	public static String regexExtractString(String line, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(line);
		if (m.find()) {
			return m.group(1);
		} else {
			return null;
		}
	}

	public static String deleteCharAt(String line, int i) {
		return line.substring(0, i) + line.substring(i + 1);
	}

	public static String capitalize(String line) {
		line = line.substring(0, 1).toUpperCase() + line.substring(1, line.length());
		return line;
	}

	public static String capitalizeFully(String line) {
		line = line.toLowerCase();
		line = line.substring(0, 1).toUpperCase() + line.substring(1, line.length());
		return line;
	}

	public static String formatTime(Number time, String format) {
		long longTime = time.longValue();
		String res = "";
		long jours = longTime / 86400;
		long heures = (longTime % 86400) / 3600;
		long minutes = (longTime % 3600) / 60;

		if (format.equals("jh")) {
			if (jours > 1) {
				res += String.format("%d jours et ", jours);
			} else if (jours == 1) {
				res += "1 jour et ";
			}
			if (heures > 1) {
				res += String.format("%d heures", heures);
			} else {
				res += String.format("%d heure", heures);
			}
		} else if (format.equals("jhm")) {
			if (jours > 1) {
				res += String.format("%d jours et ", jours);
			} else if (jours == 1) {
				res += "1 jour et ";
			}
			if (heures > 1) {
				res += String.format("%d heures", heures);
			} else {
				res += String.format("%d heure", heures);
			}
			if (minutes > 1) {
				res += String.format(" et %d minutes", minutes);
			} else if (minutes == 1) {
				res += " et 1 minute";
			}
		}
		return res;

	}

}