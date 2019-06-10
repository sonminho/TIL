from django.shortcuts import render, redirect
from boards.models import Board
from IPython import embed


# Create your views here.
def index(request):
    boards = Board.objects.all()
    context = {
        'boards': boards,
    }
    return render(request, 'boards/index.html', context)


def create(request):
    # request 가 POST 로 왔을때는 create
    if request.method == 'POST':
        board = Board()
        board.title = request.POST.get('title')
        board.content = request.POST.get('content')
        board.save()
        return redirect('boards:detail', board.pk)
    # request 가 GET 으로 왔을때는 new
    else:
        return render(request, 'boards/create.html')


def detail(request, pk):
    board = Board.objects.get(pk=pk)
    context = {
        'board': board,
    }
    return render(request, 'boards/detail.html', context)


def delete(request, pk):
    board = Board.objects.get(pk=pk)
    if request.method == 'POST':
        board.delete()
        return redirect('boards:index')
    else:
        return redirect('boards:detail')


def update(request, pk):
    board = Board.objects.get(pk=pk)
    if request.method == 'POST':
        board.title = request.POST.get('title')
        board.content = request.POST.get('content')
        print(board.title)
        board.save()
        return redirect('boards:detail', board.pk)
    else:
        context = {'board': board}
        return render(request, 'boards/update.html', context)
