# 1. split (l,r)strip
with open('lunch.csv', 'r', encoding='utf-8') as f:
    lines = f.readlines()

    for line in lines:
        print(line.strip().split(','))

# 2. csv.reader
import csv
with open('lunch.csv', 'r', encoding='utf-8') as f:
    items = csv.reader(f)

    for item in items:
        print(item)