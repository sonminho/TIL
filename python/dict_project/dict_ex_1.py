# 1. 평균을 구하시오.
score = {
    '수학': 80,
    '국어': 90,
    '음악': 100
}

# 아래에 코드를 작성해 주세요.
print('==== Q1 ====')
sum = 0.0
for value in score.values():
    sum = sum + float(value)
print(sum / len(score))

# 2. 반 평균을 구하시오. -> 전체 평균
scores = {
    'a': {
        '수학': 80,
        '국어': 90,
        '음악': 100
    },
    'b': {
        '수학': 80,
        '국어': 90,
        '음악': 100
    }
}

# 아래에 코드를 작성해 주세요.
print('==== Q2 ====')
sum = 0.0
count = 0
for student, subjects in scores.items():
    for subject in subjects.keys():
        sum = sum + scores[student][subject]
        count += 1
print(sum/count)

# 3. 도시별 최근 3일의 온도입니다.
cities = {
    '서울': [-6, -10, 5],
    '대전': [-3, -5, 2],
    '광주': [0, -2, 10],
    '부산': [2, -2, 9],
}

# 3-1. 도시별 최근 3일의 온도 평균은?
# 아래에 코드를 작성해 주세요.
print('==== Q3-1 ====')
for city, values in cities.items():
    sum = 0.0
    for i in range(len(values)):
        #print(values[i])
        sum = sum + values[i];
    print(f'{city} : {sum/len(values)}')
'''
출력 예시)
서울 : 값
대전 : 값
광주 : 값
부산 : 값
'''

# 3-2. 도시 중에 최근 3일 중에 가장 추웠던 곳, 가장 더웠던 곳은?

# 아래에 코드를 작성해 주세요.
print('==== Q3-2 ====')
hotday = -99999
coolday = 99999
for city, values in cities.items():
    for i in range(len(values)):
        if values[i] > hotday :
            hotday = values[i]
            hotcity = city
        if values[i] < coolday :
            coolday = values[i]
            coolcity = city
print(f'{hotcity} : {hotday}')
print(f'{coolcity} : {coolday}')

# 3-3. 위에서 서울은 영상 2도였던 적이 있나요?

# 아래에 코드를 작성해 주세요.
print('==== Q3-3 ====')
seoul = cities['서울']
for i in seoul:
    if i >= 2:
        print(True)
        break;
print(False)