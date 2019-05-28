import requests
from bs4 import BeautifulSoup

# 1. 원하는 정보가 있는 URL로 요청을 보낸 후 응답을 저장
req = requests.get('https://www.naver.com').text

# 2. 정보를 조작하기 편하게 바꿈
soup = BeautifulSoup(req, 'html.parser')

# 3. 바꾼 정보 중 원하는 것만 뽑아서 출력
# kospi = soup.select_one('#KOSPI_now')
list = soup.select('.ah_item .ah_k')

print(list)

for item in list:
    print(item)