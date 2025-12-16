module JoyMarket {
	requires javafx.graphics;
	requires javafx.controls;
	requires java.sql;
	
	opens main;
	opens view;
	opens controller;
	opens model;
}