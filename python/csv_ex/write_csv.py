lunch = {
    '고갯마루' : '02-123-4567',
    '세븐브릭스' : '02-456-3121',
    '아랑졸돈까스' : '02-1234-1123'
}

# 1. sorting formating
# with open('lunch.csv', 'w', encoding='utf-8') as f:
#     for item in lunch.items():
#         f.write(f'{item[0]}, {item[1]}\n')

# 2. join
# with open('lunch.csv', 'w', encoding='utf-8') as f:
#     for item in lunch.items():
#         f.write(','.join(item) + '\n')

# 3. csv.writer
import csv
with open('lunch.csv', 'w', encoding='utf-8') as f:
    csv_writer = csv.writer(f)

    for item in lunch.items():
        csv_writer.writerow(item)
