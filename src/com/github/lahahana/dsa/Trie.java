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
		addNode(word, 0, word.length() - 1, this.root, true);
		return this;
	}

	private void addNode(String word, int pos, int end, TrieNode node, boolean isOld) {
		char c = word.charAt(pos);
		TrieNode[] childs = node.childs;
		boolean isPrefixExists = false;
		int index = -1;
		if(isOld) {
			for (int i = 0; i < childs.length; i++) {
				TrieNode childNode = childs[i];
				if (childNode != null) {
					index = i;
					if (childNode.prefix == c) {
						isPrefixExists = true;
						break;
					}
				} else {
					break;
				}
			}
		}

		if (!isPrefixExists) {
			if (pos < end) {
				TrieNode newNode = new TrieNode(c);
				childs[index + 1] = newNode;
				addNode(word, ++pos, end, newNode, false);
			} else {
				TrieNode newNode = new TrieNode(c, true);
				childs[index + 1] = newNode;
				counter++;
				return;
			}
		} else {
			if (pos < end) {
				addNode(word, ++pos, end, childs[index], true);
			} else {
				if(!childs[index].exists){
					childs[index].exists = true;
					counter++;
					return;
				}
			}
		}
	}

	public boolean isWordExists(String word) {
		TrieNode[] childs = root.childs;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			for (int j = 0; j < childs.length; j++) {
				if(childs[j] == null)
					return false;
				else if(childs[j].prefix == c){
					if(i == word.length() - 1){
						if(childs[j].exists)
							return true;
						else
							return false;
					}
					childs = childs[j].childs;
					break;
				}
			}
		}
		return false;
	}
	
	private class TrieNode {
		
		private char prefix;
		private boolean exists;
		private TrieNode[] childs;

		public TrieNode() {
			super();
			this.exists = false;
			this.childs = new TrieNode[SYMBOLSIZE];
		}

		public TrieNode(char prefix) {
			super();
			this.prefix = prefix;
			this.exists = false;
			this.childs = new TrieNode[SYMBOLSIZE];
		}

		public TrieNode(char prefix, boolean exists) {
			super();
			this.prefix = prefix;
			this.exists = exists;
			this.childs = new TrieNode[SYMBOLSIZE];
		}
	}
}
