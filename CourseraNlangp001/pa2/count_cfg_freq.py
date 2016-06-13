#! /usr/bin/python

__author__="Alexander Rush <srush@csail.mit.edu>"
__date__ ="$Sep 12, 2012"

import sys, json

"""
Count rule frequencies in a binarized CFG.
"""

class Counts:
  def __init__(self):
    self.unary = {}
    self.binary = {}
    self.nonterm = {}
    self.words = {}

  def show(self):
    for symbol, count in self.nonterm.iteritems():
      print count, "NONTERMINAL", symbol

    for (sym, word), count in self.unary.iteritems():
      print count, "UNARYRULE", sym, word

    for (sym, y1, y2), count in self.binary.iteritems():
      print count, "BINARYRULE", sym, y1, y2

  def clean_rare(self, tree):
    if isinstance(tree, basestring): return
    if len(tree) == 3:
      self.clean_rare(tree[1])
      self.clean_rare(tree[2])
    elif len(tree) == 2:
      if self.words[tree[1]] < 5:
        tree[1] = "_RARE_"

  def count(self, tree):
    """
    Count the frequencies of non-terminals and rules in the tree.
    """
    if isinstance(tree, basestring): return

    # Count the non-terminal symbol. 
    symbol = tree[0]
    self.nonterm.setdefault(symbol, 0)
    self.nonterm[symbol] += 1
    
    if len(tree) == 3:
      # It is a binary rule.
      y1, y2 = (tree[1][0], tree[2][0])
      key = (symbol, y1, y2)
      self.binary.setdefault(key, 0)
      self.binary[(symbol, y1, y2)] += 1
      
      # Recursively count the children.
      self.count(tree[1])
      self.count(tree[2])
    elif len(tree) == 2:
      # It is a unary rule.
      y1 = tree[1]
      key = (symbol, y1)
      self.unary.setdefault(key, 0)
      self.unary[key] += 1
      self.words.setdefault(y1, 0)
      self.words[y1] += 1

  def parse(self, words):
    pis = {}
    bps = {}
    maxval = 0.0
    maxkey = (1,1,"")
    for l in range(2, len(words)+1):
      for i in range (0, len(words)-l+1):
        j = i + l
        maxval = 0
        for n in self.nonterm.keys():
          compare = self.calpi(words, (i, j, n), pis)
          if compare >= maxval:
            maxval = compare
            maxkey = (i, j, n)
    self.print_tree(maxkey, pis, words)
    sys.stdout.write("\n")

  def print_tree(self, key, pis, words):
    i = key[0]
    j = key[1]
    x = key[2]
    if i == j-1:
      sys.stdout.write("[\""+x+"\", \""+words[i]+"\"]")
      return
    pi = pis[key]
    r = pi[1]
    y1 = r[1]
    y2 = r[2]
    s = pi[2]
    sys.stdout.write("[\""+x+"\", ")
    self.print_tree((i, s, y1), pis, words)
    sys.stdout.write(", ")
    self.print_tree((s, j, y2), pis, words)
    sys.stdout.write("]")


  # pis contains 0, val, 1, r, 2, split point
  # key contains, s, e, x
  def calpi(self, words, key, pis):
    if key in pis:
      return pis[key][0]

    i = key[0]
    j = key[1]
    x = key[2]
    if i == j-1:
      word = words[i]
      if not (word in self.words.keys()):
        word = "_RARE_"
      if (x, word) in self.unary.keys():
        return 1.0 * self.unary[(x, word)] / self.words[word]
      else:
        return 0.0

    maxpi = 0.0
    maxs = 0
    maxr = ("","","")
    for s in range(i+1, j):
      for r in self.binary.keys():
        if r[0] == x:
          compare = 1.0
          compare *= self.binary[r]
          compare /= self.nonterm[x]
          compare *= self.calpi(words, (i, s, r[1]), pis)
          compare *= self.calpi(words, (s, j, r[2]), pis)
          if compare > maxpi:
            maxpi = compare
            maxs = s
            maxr = r

    pis[key] = (maxpi, maxr, maxs)
    return maxpi

def main_org(parse_file):
  counter = Counts()
  for l in open(parse_file):
    t = json.loads(l)
    counter.count(t)
  counter.show()

def main_pa1(parse_file):
  counter = Counts()
  counter2 = Counts()
  for l in open(parse_file):
    t = json.loads(l)
    counter.count(t)
  for l in open(parse_file):
    t = json.loads(l)
    counter.clean_rare(t)
    counter2.count(t)
  return counter2

def main_pa2(parse_file):
  counter = main_pa1("parse_train.dat")
  for l in open(parse_file):
    words = l.split()
    counter.parse(words)



def usage():
    sys.stderr.write("""
    Usage: python count_cfg_freq.py [tree_file]
        Print the counts of a corpus of trees.\n""")

if __name__ == "__main__": 
  if len(sys.argv) != 2:
    usage()
    sys.exit(1)
  main_pa2(sys.argv[1])
  
