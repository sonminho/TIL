# f = open('mulcam.txt', 'w')
# for i in range(10):
#     f.write(f'This is line {i+1}.\n')
# f.close()

with open('mulcam.txt', 'a') as f:
    for i in range(10):
        f.write(f'This is line {i+1}\n')