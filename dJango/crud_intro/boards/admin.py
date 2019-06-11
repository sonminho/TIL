from django.contrib import admin
from .models import Board, Comment


class BoardAdmin(admin.ModelAdmin):
    list_display = ['id', 'title', 'content', 'created_at', 'updated_at', ]


class CommentAdmin(admin.ModelAdmin):
    list_display = ('pk', 'content', 'board_id',)


admin.site.register(Board, BoardAdmin)
admin.site.register(Comment, CommentAdmin)
