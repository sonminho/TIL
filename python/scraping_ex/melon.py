import requests
from bs4 import BeautifulSoup

headers = {
    'User-Agent' : 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36'
}

res = requests.get('https://www.melon.com/chart/index.htm', headers=headers).text
soup = BeautifulSoup(res, 'html.parser')
songs = soup.select('#lst50 > td:nth-child(6) > div > div > div.ellipsis.rank01 > span > a')

tags = soup.select('#frm > div > table > tbody > tr')

with open(f'melon.txt', 'w', encoding='utf-8') as f:
    for tag in tags:
        rank = tag.select_one('td:nth-of-type(2) .rank').text
        name = tag.select_one('td:nth-of-type(6) div.ellipsis.rank01 a').text
        singer = tag.select_one('td:nth-child(6) div.ellipsis.rank02 a').text
        line = f'{rank}/{name}/{singer}\n'
        f.write(f'{line}')
