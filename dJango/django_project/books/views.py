import requests
import os
from django.shortcuts import render
from datetime import datetime

# 종합 메인 페이지
def index(request):
    return render(request, 'books/index.html')

# 우리의 수료날(190627)까지 남은 날짜 출력하기
def graduation(request):
    now_day = datetime.now()
    graduate_day = datetime(2019, 6, 27)
    diff_date = graduate_day - now_day
    context = dict()
    context['now_day'] = now_day
    context['graduate_day'] = graduate_day
    context['diff_date'] = diff_date.days
    return render(request, 'books/graduation.html', context)

# Lorem Picsum 활용하여 랜덤 이미지 출력하기
def imagepick(request):
    url = f'https://picsum.photos/500/500/?random'
    response = requests.get(url).text
    context = {
        'url': url,
    }
    return render(request, 'books/imagepick.html', context)

# 오늘 시간 및 날씨 정보 알려주기 (지금 살고 위치 기준으로)
def today(request):
    key = f'cc76034229b35fcb97bacaa535f10c78'
    url = "https://api.openweathermap.org/data/2.5/weather?q=Seoul,kr&lang=kr&APPID=" + key
    data = requests.get(url).json()
    print(data)
    weather = {
        'status': data['weather'][0]['description'],
        'temp': round(data['main']['temp'])-273.15,
        'temp_min': data['main']['temp_min']-273.15,
        'temp_max': data['main']['temp_max']-273.15,
    }
    context = {
        'weather': weather
    }

    return render(request, 'books/today.html', context)

# ascii art 를 변환을 위한 text, font 입력받기
def ascii_new(request):
    fonts = ['short', 'utopia', 'rounded', 'acrobatic', 'alligator']
    context = {
        'fonts': fonts,
    }

    return render(request, 'books/ascii_new.html', context)

# artii 를 활용하여 art 로 만들어서 출력해주기
def ascii_make(request):
    font = request.GET.get('font')
    text = request.GET.get('text')

    url = f'http://artii.herokuapp.com/make?text={text}&font={font}'
    response = requests.get(url).text
    context = {
        'font': font,
        'text': text,
        'list': response,
    }
    return render(request, 'books/ascii_make.html', context)

# 영어 번역을 위한 한국어 입력받기
def original(request):
    return render(request, 'books/original.html')

# papago 활용하여 한-영 번역 해주기
def translated(request):
    korean = request.GET.get("text")
    print(f'번역할 텍스트 > {korean}')

    papago_url = f'https://openapi.naver.com/v1/papago/n2mt'
    naver_client_id = os.getenv("NAVER_CLIENT_ID")
    naver_client_secret = os.getenv("NAVER_CLIENT_SECRET")

    # 네이버에 Post 요청을 위해서 필요한 내용들
    headers = {
        "X-Naver-Client-Id": naver_client_id,
        "X-Naver-Client-Secret": naver_client_secret
    }

    data = {
        "source": "ko",
        "target": "en",
        "text": korean,
    }

    papago_response = requests.post(papago_url, headers=headers, data=data).json()
    english = papago_response["message"]["result"]["translatedText"]

    context = {
        'korean': korean,
        'english': english,
    }

    return render(request, 'books/translated.html', context)
