import random
import requests
import json
from pprint import pprint

url = 'https://dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=860'
res = requests.get(url)
lottery = res.json()

winner = []

for i in range(1,7):
    winner.append(lottery[f'drwtNo{i}'])
bonus = lottery['bnusNo']

# 내가 자동으로 산 복권 번호와 당첨번호(winner) 교집합 개수 비교를 통해 등수 매기기
count = 0
while(True):
    #print(f'#### 860회 당첨번호 {winner} + {bonus} ####')
    my_lucky_numbers = random.sample(range(1,46), 6)
    hit_num = len(set(my_lucky_numbers).intersection(winner))

    if hit_num == 6:
        rank = 1
    elif hit_num == 5:
        if bonus in my_lucky_numbers:
            rank = 2
        else:
            rank = 3
    elif hit_num == 4:
        rank = 4
    elif hit_num == 3:
        rank = 5
    else:
        rank = 6

    count += 1
    #print(f'{count}회 시도중... 당신의 추첨 번호는 {my_lucky_numbers}, {hit_num}개 적중!!!! 등수는 {rank}등 입니다.\n')

    if(rank == 1):
        print(f'#### 860회 당첨번호 {winner} + {bonus} ####')
        print(f'{count}회 시도하였고 {count*1000}원 쓰셨네요. 당신의 추첨 번호는 {my_lucky_numbers}, {hit_num}개 적중!!!! 등수는 {rank}등 입니다.\n')
        break
