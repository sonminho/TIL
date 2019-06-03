from django.urls import path
from . import views

urlpatterns = [
    path('', views.index),
    path('dinner/', views.dinner),
    path('hello/<name>/', views.hello),
    path('introduce/<name>/<age>/', views.introduce),
    path('times/<num1>/<num2>/', views.times),
    path('radius/<radius>/', views.area),
    path('dtl_example/', views.dtl_example),
    path('throw/', views.throw),
    path('catch/', views.catch),
    path('artii/', views.artii),
    path('result/', views.result),
    path('user_new/', views.user_new),
    path('user_create/', views.user_create),
    path('static-example/', views.static_example),
]
