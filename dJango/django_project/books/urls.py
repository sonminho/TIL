from django.urls import path
from . import views

urlpatterns = [
    path('today/', views.today),
    path('translated/', views.translated),
    path('original/', views.original),
    path('ascii_make/', views.ascii_make),
    path('ascii_new/', views.ascii_new),
    path('imagepick/', views.imagepick),
    path('graduation/', views.graduation),
    path('', views.index),
]
