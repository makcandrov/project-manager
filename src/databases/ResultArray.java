package databases;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultArray {
	public ArrayList<HashMap<String, Object>> rar;
	public int size;

	public ResultArray() {
		this.size = 0;
		this.rar = new ArrayList<>();
	}

	public ResultArray(ArrayList<HashMap<String, Object>> rar) {
		this.rar = rar;
		this.size = rar.size();
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public boolean hasRow(int i) {
		return this.size > i;
	}

	public boolean has(int i, String label) {
		return hasRow(i) && rar.get(i).containsKey(label);
	}

	public Object get(int i, String label) {
		if (!hasRow(i)) {
			System.out.println(String.format("ResultArray/ Ligne %d inexistante", i));
			return null;
		}
		if (!has(i, label)) {
			System.out.println(String.format("ResultArray/ label %s � la ligne %d inexistant", label, i));
			return null;
		}
		return rar.get(i).get(label);
	}

	public HashMap<String, Object> getRow(int i) {
		if (hasRow(i)) {
			return rar.get(i);
		}
		return new HashMap<>();
	}

	public void addRow() {
		rar.add(new HashMap<>());
		this.size++;
	}

	public void addRow(HashMap<String, Object> row) {
		rar.add(row);
		this.size++;
	}

	public void put(int i, String label, Object obj) {
		if (hasRow(i)) {
			rar.get(i).put(label, obj);
		}
	}

	public void removeRow(int i) {
		if (hasRow(i)) {
			rar.remove(i);
		}
		this.size--;
	}

	public ResultArray rowTruncate(int i) {
		ArrayList<HashMap<String, Object>> nrar = new ArrayList<>();
		for (int j = 0; j < i; j++) {
			if (hasRow(j)) {
				nrar.add(getRow(j));
			}
		}
		return new ResultArray(nrar);

	}

	public ResultArray columnTruncate(String... labels) {
		ResultArray res = new ResultArray();
		for (int i = 0; i < this.size; i++) {
			res.addRow();
			for (int j = 0; j < labels.length; j++) {
				String label = labels[j];
				if (has(i, label)) {
					res.put(i, label, get(i, label));
				}
			}
		}
		return res;
	}

	public void swap(int i, int j) {
		if (hasRow(i) && hasRow(j)) {
			HashMap<String, Object> tmp = getRow(i);
			this.rar.set(i, getRow(j));
			this.rar.set(j, tmp);
		}

	}

	public String toStringPresentation() {
		String res = "";
		for (int i = 0; i < this.size; i++) {
			res += String.format("(%d)\n", i);
			HashMap<String, Object> row = this.rar.get(i);
			for (String key : row.keySet()) {
				Object elt = row.get(key);
				if (elt == null) {
					elt = "null";
				}
				res += String.format("%s:%s\n", key, elt.toString());
			}
			res += "\n";
		}
		return res;
	}

	public String toString() {
		String res = "";
		for (int i = 0; i < this.size; i++) {
			HashMap<String, Object> row = getRow(i);
			int j = 0;
			int n = row.keySet().size();
			for (String label : row.keySet()) {
				res += label + "=";
				res += row.get(label);
				if (j != n - 1) {
					res += "||";
				}
				j++;
			}
			if (i != this.size - 1) {
				res += "|||";
			}
		}
		return res;
	}

}