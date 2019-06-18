from django.shortcuts import render, redirect, get_object_or_404
from IPython import embed
from django.contrib.auth.decorators import login_required
from .models import Board, Comment
from .forms import BoardForm, CommentForm
from django.views.decorators.http import require_POST

# Create your views here.
def index(request):
    boards = Board.objects.order_by('-pk')
    context = {'boards': boards,}
    return render(request, 'boards/index.html', context)


@login_required
def create(request):
    if request.method == 'POST':
        form = BoardForm(request.POST)
        if form.is_valid():
            board = form.save(commit=False)
            board.user = request.user
            board.save()
            return redirect('boards:detail', board.pk)
    else:
        form = BoardForm()
    # context 에 넘어가는 2가지 form
    # 1. GET : 기본 FORM 모습으로 넘겨짐
    # 2. POST : 요청에서 검증에 실패한 form 이 오류메세지를 포함한 상태로 넘겨짐.
    context = {'form': form}
    return render(request, 'boards/form.html', context)


def detail(request, board_pk):
    # board = Board.objects.get(pk=board_pk)
    board = get_object_or_404(Board, pk=board_pk)
    comment_form = CommentForm()
    comments = board.comment_set.all()

    context = {
        'board': board,
        'comment_form': comment_form,
        'comments': comments,
    }
    return render(request, 'boards/detail.html', context)


def delete(request, board_pk):
    board = get_object_or_404(Board, pk=board_pk)

    if board.user == request.user:
        if request.method == 'POST':
            board.delete()
            return redirect('boards:index')
        else:
            return redirect('boards:detail', board.pk)
    else:
        return redirect('boards:index')


@login_required
def update(request, board_pk):
    board = get_object_or_404(Board, pk=board_pk)
    
    if board.user == request.user:
        if request.method == 'POST':
           form = BoardForm(request.POST, instance=board)
           if form.is_valid():
               board = form.save()
               return redirect('boards:detail', board.pk)
    
        else:
            form = BoardForm(instance=board)
    else:
        return redirect('boards:index')

    context = {
        'form': form,
        'board': board,
    }
    return render(request, 'boards/form.html', context)


# 로그인된 유저만 작성 가능
# POST 요청으로만 작성 가능
@login_required
@require_POST
def comments_create(request, board_pk):
    comment_form = CommentForm(request.POST)
    if comment_form.is_valid():
        comment = comment_form.save(commit=False)
        comment.user = request.user
        comment.board_id = board_pk
        comment.save()
    return redirect('boards:detail', board_pk)


@login_required
@require_POST
def comments_delete(request, board_pk, comment_pk):
    comment = get_object_or_404(Comment, pk=comment_pk)
    
    if comment.user == request.user:
        comment.delete()
    return redirect('boards:detail', board_pk)


@login_required
def like(request, board_pk):
    board = get_object_or_404(Board, pk=board_pk)
    user = request.user

    # 해당 게시글에 좋아요를 누른 사용자 중에 현재 요청을 한 사용자가 존재한다면
    if board.like_users.filter(pk=user.pk).exists():
        # 좋아요를 취소
        board.like_users.remove(user)
        
    else:
        # 좋아요를 누름
        board.like_users.add(user)
    return redirect("boards:index")