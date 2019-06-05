from django.urls import path
from . import views

urlpatterns = [
    path('create/', views.create),
    path('new/', views.new),
    path('detail/<int:pk>', views.detail),
    path('', views.index),
]
