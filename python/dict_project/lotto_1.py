import requests
import random
from bs4 import BeautifulSoup
from pprint import pprint

numbers = random.sample(range(800,861), 5)
for num in numbers:
    url = f'https://dhlottery.co.kr/gameResult.do?method=byWin&drwNo={num}'
    res = requests.get(url).text
    soup = BeautifulSoup(res, 'html.parser')
    lottery = soup.select('.ball_645')
    print(f'{num}회차')
    count = 0
    for lotto in lottery:
        if count == 5:
            print(lotto.text, end=' + ')
        else:
            print(lotto.text, end=' ')
        count += 1
    print()
