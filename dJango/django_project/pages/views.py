from django.shortcuts import render
import random
from pprint import pprint
import math
from datetime import datetime
import requests

# Create your views here.
def index(request):
    pprint(request.META)
    return render(request, 'pages/index.html')


def dinner(request):
    menus = ['족발','햄버거','치킨','초밥']
    pick = random.choice(menus)
    context = dict()
    context['pick'] = pick
    return render(request, 'pages/dinner.html', context)


def hello(request, name):
    context = dict()
    context['name'] = name

    return render(request, 'pages/hello.html', context)


# 자기소개 / 이름과 나이를 url 로 받아서 추력
def introduce(request, name, age):
    context = dict()
    context['name'] = name
    context['age'] = age
    return render(request, 'pages/introduce.html', context)


# 숫자 2개를 variable routing 으로 받아 곱셈 결과를 출력
def times(request, num1, num2):
    context = dict()
    num1 = int(num1)
    num2 = int(num2)
    context['num1'] = num1
    context['num2'] = num2
    context['result'] = num1 * num2

    return render(request, 'pages/times.html', context)


# 원의 반지름 값을 variable routing 으로 받아 원의 넓이를 출력
def area(request, radius):
    context = dict()
    radius = int(radius)
    context['radius'] = radius
    context['area'] = radius * radius * math.pi

    return render(request, 'pages/area.html', context)


def dtl_example(request):
    menus = ['짜장면', '탕수육', '짬뽕', '양장피']
    my_sentence = 'Lifs is short, You need python'
    messages = ['apple', 'banana', 'cucumber', 'mango']
    datetimenow = datetime.now()
    empty_list = []

    context = dict()
    context['menus'] = menus
    context['my_sentence'] = my_sentence
    context['messages'] = messages
    context['datetimenow'] = datetimenow
    context['empty_list'] = empty_list

    return render(request, 'pages/dtl_example.html', context)


def throw(request):
    return render(request, 'pages/throw.html')


def catch(request):
    #print(request.GET)
    message = request.GET.get('message')
    context = {'message': message,}
    return render(request, 'pages/catch.html', context)


def artii(request):
    return render(request, 'pages/artii.html')


def result(request):
    # 1. form 에서 날아온 데이터를 받는다.
    text = request.GET.get('text')
    font = request.GET.get('font')
    context = dict()

    # 2. artii api 로 요청을 보내 응답 결과를 .text 로 저장한다.
    url = 'http://artii.herokuapp.com/fonts_list'
    response = requests.get(url).text
    print(type(response))

    # 3. 저장한 데이터를 list 로 바꾼다.
    list = response.split("\n")

    # 4. list 안에 들어 있는 요소(font) 하나를 선택해서 저장한다.
    font = random.choice(list)

    # 5. 우리가 전달한 데이터와 list 안에 font 를 가지고 다시 요청을 보내  해당 응답 결과를 저장한다.
    context['text'] = text
    context['font'] = font

    url = f'http://artii.herokuapp.com/make?text={text}&font={font}'
    response = requests.get(url).text
    print(response)
    list = response.split("\n")
    context['list'] = list

    # 결과를 저장한다.
    # 6. 최종적으로 저장한 데이터를 template 으로 넘겨준다.
    return render(request, 'pages/result.html', context)


def user_new(request):
    return render(request, 'pages/user_new.html')


def user_create(request):
    name = request.POST.get('name')
    pwd = request.POST.get('pwd')
    context = {
        'name': name,
        'pwd': pwd,
    }

    return render(request, 'pages/user_create.html', context)

def static_example(request):
    return render(request, 'pages/static_example.html')
