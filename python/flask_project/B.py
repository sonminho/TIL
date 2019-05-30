from flask import Flask
import A # import 동시에 A.py 코드 실행
app = Flask(__name__)

print('top-level B.py')
A.func()

if __name__ == '__main__':
    print('B.py가 직접 실행')
else:
    print('B.py가 import 되어 사용됨')