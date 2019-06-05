from django.shortcuts import render, redirect
from boards.models import Board

# Create your views here.
def index(request):
    boards = Board.objects.all()
    context = {
        'boards': boards,
    }
    return render(request, 'boards/index.html', context)


def new(request):
    return render(request, 'boards/new.html')


def create(request):
    board = Board()

    board.title = request.POST.get('title')
    board.content = request.POST.get('content')
    board.save()
    print(board)

    return redirect(f'/boards/detail/{board.pk}')

def detail(request, pk):
    board = Board.objects.get(pk=pk)
    context = {
        'board': board,
    }
    return render(request, 'boards/detail.html', context)
