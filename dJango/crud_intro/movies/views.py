from django.shortcuts import render, redirect
from movies.models import Movie


def index(request):
    movies = Movie.objects.all()
    context = {
        'movies': movies,
    }
    return render(request, 'movies/index.html', context)


def create(request):
    if request.method == 'POST':
        print('POST REQ')
        title = request.POST.get('title')
        title_en = request.POST.get('title_en')
        audience = request.POST.get('audience')
        open_date = request.POST.get('open_date')
        genre = request.POST.get('genre')
        watch_grade = request.POST.get('watch_grade')
        score = request.POST.get('score')
        poster_url = request.POST.get('poster_url')
        description = request.POST.get('description')

        movie = Movie(title=title,
                      title_en=title_en,
                      audience=audience,
                      open_date=open_date,
                      genre=genre,
                      watch_grade=watch_grade,
                      score=score,
                      poster_url=poster_url,
                      description=description)
        movie.save()

        context = {
            'title': title,
            'title_en': title_en,
            'audience': audience,
            'open_date': open_date,
            'genre': genre,
            'watch_grade': watch_grade,
            'score': score,
            'poster_url': poster_url,
            'description': description,
        }
        print(context)
        return redirect('movies:index')
    else:
        print('GET REQ')
        return render(request, 'movies/create.html')
