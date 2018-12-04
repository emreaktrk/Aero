package com.kocsistem.aero.io;

class Post {

  long id;
  String title;
  String body;
  long userId;

  public Post(String title, String body, long userId) {
    this.title = title;
    this.body = body;
    this.userId = userId;
  }
}
