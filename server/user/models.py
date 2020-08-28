from django.db import models
from django.contrib.postgres.fields import ArrayField

# Create your models here.
class User(models.Model):
    uid = models.TextField()
    nickname = models.CharField(max_length=16)
    profileImageUrl = models.TextField()
    # region = ArrayField(models.CharField(max_length=10, null=True), size=2, default=list)
    # savedSentences = ArrayField(models.TextField(null=True), size=10, default=list, null=True)
    # favoriteUsersId = ArrayField(models.IntegerField(null=True), default=list, null=True)