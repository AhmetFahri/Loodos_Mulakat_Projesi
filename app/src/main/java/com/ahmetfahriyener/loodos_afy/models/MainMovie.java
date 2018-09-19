package com.ahmetfahriyener.loodos_afy.models;

public class MainMovie {

    private String title;
    private String year;
    private String imdbID;
    private String type;
    private String poster;

        public MainMovie(){}

        public MainMovie(String title, String year, String imdbID, String type, String poster) {

            this.title = title;
            this.year = year;
            this.imdbID = imdbID;
            this.type = type;
            this.poster = poster;
        }

        public String getTitle() {
            return title;
        }

        public String getYear() {
            return year;
        }

        public String getImdbID() {
            return imdbID;
        }

        public String getType() {
            return type;
        }

        public String getPoster() {
            return poster;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setImdbID(String imdbID) {
            this.imdbID = imdbID;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }
}
