package com.biswa1045.quizgame;

public class scoreData {
    String name;
  //  String image;
    Long score;

    public scoreData() {
    }

    public scoreData(String name, Long score) {
        this.name = name;
      //  this.image = image;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

  //  public String getImage() {
   //     return image;
 //   }

  //  public void setImage(String image) {
    //    this.image = image;
  //  }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
