package com.github.lahahana.dsa;
/**
 * Not Thread-Safe Trie
 * 
 * @author lahahana
 * */
public class Trie { 
	
	private TrieNode root;
	private final int SYMBOLSIZE;
	private long counter = 0;

	private Trie(int symbolSize) {
		SYMBOLSIZE = symbolSize;
	}

	public static Trie build() {
		Trie trie = new Trie(52);
		trie.root = trie.new TrieNode();
		return trie;
	}
	
	public static Trie build(int symbolSize) {
		Trie trie = new Trie(symbolSize);
		trie.root = trie.new TrieNode();
		return trie;
	}

	public long getCount() {
		return counter;
	}

	public Trie indexWord(String word) {
		addNode(word, 0, word.length() - 1, this.root);
		return this;
	}

	private void addNode(String word, int pos, int end, TrieNode node) {
		char c = word.charAt(pos);
		TrieNode[] childs = node.getChilds();
		boolean isPrefixExists = false;
		int index = -1;
		for (int i = 0; i < childs.length; i++) {
			TrieNode childNode = childs[i];
			if (childNode != null) {
				index = i;
				if (childNode.getPrefix() == c) {
					isPrefixExists = true;
					break;
				}
			} else {
				break;
			}
		}

		if (!isPrefixExists) {
			if (pos < end) {
				TrieNode newNode = new TrieNode(c);
				childs[index + 1] = newNode;
				addNode(word, ++pos, end, newNode);
			} else {
				TrieNode node2 = new TrieNode(c, word);
				childs[index + 1] = node2;
				counter++;
				return;
			}
		} else {
			if (pos < end) {
				addNode(word, ++pos, end, childs[index]);
			} else {
				if(!word.equals(childs[index].getVal())){
					childs[index].setVal(word);
					counter++;
					return;
				}
			}
		}
	}

	public boolean isWordExists(String word) {
		TrieNode[] childs = root.getChilds();
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			for (int j = 0; j < childs.length; j++) {
				if(childs[j] == null)
					return false;
				else if(childs[j].getPrefix() == c){
					if(i == word.length() - 1){
						if(word.equals(childs[j].getVal()))
							return true;
						else
							return false;
					}
					childs = childs[j].getChilds();
					break;
				}
			}
		}
		return false;
	}
	
	private class TrieNode {
		
		private char prefix;
		private String val;
		private TrieNode[] childs;

		public TrieNode() {
			super();
			val = null;
			childs = new TrieNode[SYMBOLSIZE];
		}

		public TrieNode(char prefix) {
			super();
			this.prefix = prefix;
			val = null;
			childs = new TrieNode[SYMBOLSIZE];
		}

		public TrieNode(char prefix, String val) {
			super();
			this.prefix = prefix;
			this.val = val;
			childs = new TrieNode[SYMBOLSIZE];
		}

		public char getPrefix() {
			return prefix;
		}

		public String getVal() {
			return val;
		}

		public void setVal(String val) {
			this.val = val;
		}

		public TrieNode[] getChilds() {
			return childs;
		}
	}
}
