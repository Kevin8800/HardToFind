package dMining;

import java.util.HashMap;

public class FileBean {
	private String filename;
	private String classlabel;
	private HashMap<String,Integer> words;
	
	public FileBean(String filename, String classlabel, HashMap<String, Integer> words) {
		super();
		this.filename = filename;
		this.classlabel = classlabel;
		this.words = words;
	}
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getClasslabel() {
		return classlabel;
	}

	public void setClasslabel(String classlabel) {
		this.classlabel = classlabel;
	}

	public HashMap<String, Integer> getWords() {
		return words;
	}

	public void setWords(HashMap<String, Integer> words) {
		this.words = words;
	}

	public FileBean() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
