from django.db import models


class Movie(models.Model):
    title = models.CharField(max_length=100)
    title_en = models.CharField(max_length=100)
    audience = models.IntegerField()
    open_date = models.DateTimeField()
    genre = models.CharField(max_length=100)
    watch_grade = models.CharField(max_length=50)
    score = models.FloatField()
    poster_url = models.CharField(max_length=200)
    description = models.TextField()


