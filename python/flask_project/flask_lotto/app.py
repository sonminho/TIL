from flask import Flask, render_template, request
import requests
import random
import json

app = Flask(__name__) # 인스턴스 생성

@app.route('/lotto_check')
def lotto_check():
    return render_template('lotto_check.html')


@app.route('/lotto_result', methods=['POST'])
def lotto_result():
    lotto_round = request.form.get('lotto_round')
    url = f'https://dhlottery.co.kr/common.do?method=getLottoNumber&drwNo={lotto_round}'
    response = requests.get(url)
    lotto = response.json()
    result = dict()

    # 1 번호 6개 가져오기
    # winner = []
    # for i in range(1,7):
    #     winner.append(lotto[f'drwtNo{i}'])
    ### list comprehension ###
    winner = [lotto[f'drwtNo{i}'] for i in range(1, 7)]

    # 2 내 번호 리스트 만들기
    my_number_list = [int(request.form.get(f'lotto_num_{i}')) for i in range(1, 7)]
    print(my_number_list)
    result["my_number_list"] = my_number_list

    # 3 당첨번호와의 교집합
    hit_list = list(set(my_number_list).intersection(set(winner)))
    result["hit_list"] = hit_list
    hit_num = len(hit_list)

    # 4 조건에 따라 1등부터 꽝까지 결과 값을 lotto_result로 보내준다
    if len(set(my_number_list)) != 6:
        rank = -2
    else:
        if hit_num == 6:
            rank = 1
        elif hit_num == 5:
            if lotto['bnusNo'] in my_number_list:
                rank = 2
                hit_num += 1
            else:
                rank = 3
        elif hit_num == 4:
            rank = 4
        elif hit_num == 3:
            rank = 5
        else:
            rank = -1

    result["rank"] = rank
    result["hit_num"] = hit_num
    return render_template('lotto_result.html', lotto_round=lotto_round, winner=f'{winner} + {lotto["bnusNo"]}', result=result)

if __name__ == '__main__':
    app.run(debug=True)