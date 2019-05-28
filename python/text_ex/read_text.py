# readlines() : 파일의 모든 라인을 읽어서 각각의 줄을 요소로 갖는
# 하나의 리스트로 return

with open('mulcam.txt', 'r') as f:
    lines = f.readlines()

    for line in lines:
        print(line)

# read() : 파일 내용 전체를 문자열로 return
with open('mulcam.txt', 'r') as f:
    all_text = f.read()
    print(all_text)