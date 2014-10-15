import sys
import csv

twelve = {}
twentyfour = {}
thirtysix = {}

with open(sys.argv[1], 'r') as f:
  reader = csv.reader(f, delimiter=' ')

  for r in reader:
    if not r[0] in twelve:
      twelve[r[0]] = []
    if not r[0] in twentyfour:
      twentyfour[r[0]] = []
    if not r[0] in thirtysix:
      thirtysix[r[0]] = []

    twelve[r[0]].append(float(r[1]))
    twentyfour[r[0]].append(float(r[2]))
    thirtysix[r[0]].append(float(r[3]))

for k, v in twelve.iteritems():
  twelve[k] = sum(v) / len(v)
for k, v in twentyfour.iteritems():
  twentyfour[k] = sum(v) / len(v)
for k, v in thirtysix.iteritems():
  thirtysix[k] = sum(v) / len(v)

combined = []
for k, v in twelve.iteritems():
  combined.append((k, v, twentyfour[k], thirtysix[k],))

combined = sorted(combined, key=lambda a: a[0])

for c in combined:
  print " ".join(map(lambda a: str(a), c))
