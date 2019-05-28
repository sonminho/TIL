with open('number.txt', 'r') as f:
    # 1. line 불러오기
    lines = f.readlines()

# 2. 뒤집기
lines.reverse()
print(lines)

# 3. 다시 작성하기
with open('number.txt', 'w') as f:
    for line in lines:
        f.write(line)