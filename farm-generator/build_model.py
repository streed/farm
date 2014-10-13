import numpy as np
from scipy.stats import norm
#import matplotlib.mlab as mlab
import matplotlib.pyplot as plt
import sys
import csv

twelve = []
twentyfour = []
thirtysix = []

with open(sys.argv[1],'r') as f:
  reader = csv.reader(f, delimiter=' ')

  for r in reader:
    r = map(lambda a: float(a), r)
    twelve.append(r[1])
    twentyfour.append(r[2])
    thirtysix.append(r[3])

mu, sigma = norm.fit(twelve)
print "12in", mu, sigma

mu, sigma = norm.fit(twentyfour)
print "24in", mu, sigma

mu, sigma = norm.fit(thirtysix)
print "36in", mu, sigma
