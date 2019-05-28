import os
os.chdir(r'C:\Users\student\PycharmProjects\manufacture_files\dummy_ex')
filenames = os.listdir('.')

for filename in filenames:
    text = os.path.splitext(filename)[-1].lower()

    if text == '.txt':
        os.rename(filename, filename.replace('지원자', '합격자'))