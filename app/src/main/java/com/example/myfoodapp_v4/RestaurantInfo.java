package com.example.myfoodapp_v4;

public class RestaurantInfo {

        private String name;
        private String set_number;
        private String address;
        private String rating;
        private String distance;
        private String open_or_closed;
        private String lat;
        private String lon;

        public RestaurantInfo() {};
//these are the Setter and Getter functions that return Name, Phone, Address, Distance, Rating, Latitud, and Longitude
//this is where City will send the informationa and Display Activity will get information from
//Each of the names describe what it will return
        public void setName(String name) {
            this.name = name;
        }
        public void setPhone(String number) {
            this.set_number = number;
        }

        public void setAddress(String t) {
            this.address = t;
        }

        public void setDistance(String c) {
            this.distance = c;
        }

        public void setRating(String t) {
            this.rating = t;
        }


        public void setLat(String l) {
            this.lat = l;
        }

        public void setLon(String l) {
            this.lon = l;
        }

        public String getName() {
            return this.name;
        }
        public String getNumber() {
            return this.set_number;
        }

        public String getAddress() {
            return this.address;
        }

        public String getDistance() {
            return this.distance;
        }

        public String getRating() {
            return this.rating;
        }

        public String getLat() {
            return this.lat;
        }

        public String getLon() {
            return this.lon;
        }


    }

