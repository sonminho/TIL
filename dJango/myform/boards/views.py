from django.shortcuts import render, redirect, get_object_or_404
from IPython import embed
from django.contrib.auth.decorators import login_required
from django.views.decorators.http import require_POST
from django.contrib.auth import get_user_model
from django.http import JsonResponse, HttpResponseBadRequest
from .models import Board, Comment
from .forms import BoardForm, CommentForm


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
            # board 를 바로 db 에 저장하지 않고
            board = form.save(commit=False)
            # 요청 user 정보를 작성자에 반영 후 저장
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
    board = get_object_or_404(Board, pk=board_pk)
    comment_form = CommentForm()
    comments = board.comment_set.all()
    person = get_object_or_404(get_user_model(), pk=board.user.pk)
    context = {
        'board': board,
        'comment_form': comment_form,
        'comments': comments,
        'person': person,
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
    # 해당 게시글에 좋아요 누른 사용자 중에 현재 요청을 한 사용자가 존재한다면(이미 좋아요를 누른 상태라면)
    if board.like_users.filter(pk=user.pk).exists():
    # if user in board.like_users.all():
        # 좋아요를 취소하고
        board.like_users.remove(user)
        liked = False
    # 아니면 (좋아요를 누르지 않았다면)
    else:
    # 좋아요를 누름
        board.like_users.add(user)
        liked = True
    context = {
        'liked': liked,
        'count': board.like_users.count(),
    }
    return JsonResponse(context)


def follow(request, board_pk, user_pk):
    person = get_object_or_404(get_user_model(), pk=user_pk)
    user = request.user
    if person.followers.filter(pk=user.pk).exists():
        person.followers.remove(user)
    else:
        person.followers.add(user)
    return redirect('boards:detail', board_pk)
