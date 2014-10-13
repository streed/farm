import csv
import sys
import datetime
import time

with open(sys.argv[1], 'r') as f:
  reader = csv.reader(f)
  for r in reader:
    timestamp = time.mktime(datetime.datetime.strptime(r[0], "%m/%d/%Y %H:%M").timetuple())
    r[0] = str(timestamp)
    r = map(lambda a: float(a), r)
    if len(r) < 4:
      r.insert(2, -1)
    print "%f %f %f %f" % ( timestamp, r[1], r[2], r[3])
