import smtplib
from email.message import EmailMessage
from getpass import getpass
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

email_list = []
email_list.append('imsm2no@gmail.com')
email_list.append('smin2846@gmail.com')

password = getpass('Password : ')
msg = MIMEMultipart()
msg['Subject'] = '입사 지원 결과'
msg['From'] = 'als2gh@naver.com'
msg['To'] = ','.join(email_list)
#msg.set_content('삼성전자의 신입사원이 되신걸 축하드립니다.')

html = """
    <html>
        <body>
            <h2>삼성전자의 신입사원이 되신걸 축하드립니다.</h2>
        </body>
    </html>
"""
part = MIMEMultipart(html, 'html')
msg.attach(part)

s = smtplib.SMTP_SSL('smtp.naver.com', 465)
s.login('als2gh', password)
s.send_message(msg)
print('이메일 전송 완료!!')