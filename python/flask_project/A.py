from flask import Flask
app = Flask(__name__)

def func():
    print('function A.py')

print('top-level A.py')


if __name__ == '__main__':
    print('A.py가 직접 실행')
else:
    print('A.py가 import 되어 사용됨')