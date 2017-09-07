package com.github.lahahana.dsa;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-Safe Trie, performence optimization
 * 
 * @author lahahana
 * */
public class SyncTrie { 
	
	private TrieNode root;
	private final int SYMBOLSIZE;

	private SyncTrie(int symbolSize) {
		SYMBOLSIZE = symbolSize;
	}

	public static SyncTrie build() {
		SyncTrie trie = new SyncTrie(52);
		trie.root = trie.new TrieNode();
		return trie;
	}
	
	public static SyncTrie build(int symbolSize) {
		SyncTrie trie = new SyncTrie(symbolSize);
		trie.root = trie.new TrieNode();
		return trie;
	}

	public long getCount() {
		return sum0(root, 0);
	}

	public SyncTrie indexWord(String word) {
		addNode(word, 0, word.length() - 1, this.root, true);
		return this;
	}

	private void addNode(final String word, int pos, final int end, final TrieNode node, final boolean isOld) {
		char c = word.charAt(pos);
		TrieNode[] childs = node.childs;
		boolean isSymbolExists = false;
		int index = -1;
		if(isOld) {
			for (int i = 0; i <= node.pos; i++) {
				TrieNode childNode = childs[i];
				if (childNode != null) {
					index = i;
					if (childNode.symbol == c) {
						isSymbolExists = true;
						break;
					}
				} else {
					break;
				}
			}
		}

		if (!isSymbolExists) {
			if (pos < end) {
				TrieNode newNode = new TrieNode(c);
				newNode = node.addChildNode(index + 1, newNode);
				addNode(word, ++pos, end, newNode, false);
			} else {
				TrieNode newNode = new TrieNode(c, word);
				node.addChildNode(index + 1, newNode);
				return;
			}
		} else {
			if (pos < end) {
				addNode(word, ++pos, end, childs[index], true);
			} else {
				if(!childs[index].exists){
					childs[index].exists = true;
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
				else if(childs[j].symbol == c){
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
		
		private char symbol;
		private boolean exists;
		private TrieNode[] childs;
		private ReentrantLock lock = new ReentrantLock(false);
		private volatile int pos = 0;
		
		public TrieNode() {
			super();
			this.exists = false;
			this.childs = new TrieNode[SYMBOLSIZE];
		}

		public TrieNode(char prefix) {
			super();
			this.symbol = prefix;
			this.exists = false;
			this.childs = new TrieNode[SYMBOLSIZE];
		}

		public TrieNode(char prefix, String val) {
			super();
			this.symbol = prefix;
			this.exists = true;
			this.childs = new TrieNode[SYMBOLSIZE];
		}
		
		public TrieNode addChildNode(int pos, TrieNode node) {
			lock.lock();
			TrieNode extactNode;
			final int position = this.pos;
			OutLable:for(;;) {
				if(childs[pos] == null) {
					childs[pos] = node;
					this.pos = pos;
					break;
				}else {
					for(;pos <= position;) {
						if(childs[pos].symbol == node.symbol) {
							childs[pos].exists = node.exists || childs[pos].exists; 
							break OutLable;
						}else {
							pos++;
						}
					}
				}
			}
			extactNode = childs[pos];
			lock.unlock();
			return extactNode;
		}
	}
	
	private int sum0(TrieNode node, int count) {
		TrieNode[] nodes = node.childs;
		for (TrieNode trieNode : nodes) {
			if(trieNode != null) {
				if(trieNode.exists) {
					count++;
				}
				count=sum0(trieNode, count);
			}
		}
		return count;
	}

}
