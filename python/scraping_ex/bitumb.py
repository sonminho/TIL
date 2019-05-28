import requests
from bs4 import BeautifulSoup

req = requests.get('https://www.bithumb.com/').text

soup = BeautifulSoup(req, 'html.parser')
coin_name_tags = soup.select('#tableAsset > tbody > tr > td:nth-child(1) > p > a > strong')
coin_price_tags = soup.select('#tableAsset > tbody > tr > td:nth-child(2) > strong')

print(coin_price_tags)

for i, item in enumerate(coin_name_tags):
    line = coin_name_tags[i].text.split()[0] + "/" + coin_price_tags[i].text + "\n"
    print(line)

    with open(f'coin.txt', 'a', encoding='utf-8') as f:
        f.write(f'{line}')

tags = soup.select('.coin_list tr')
for tag in tags:
    name = tag.select_one('td:nth-of-type(1) a strong').text.replace(' NEW', '').strip()
    price = tag.select_one('td:nth-of-type(2) strong').text
    print(f'{name}/{price}')