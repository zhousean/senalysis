package com.wt.twitter;

public class AnalysisResult {

	private String text;
	
	private Float sentimentScore;
	
	public AnalysisResult(String t, Float s) {
		text = t;
		sentimentScore = s;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Float getSentimentScore() {
		return sentimentScore;
	}

	public void setSentimentScore(Float sentimentScore) {
		this.sentimentScore = sentimentScore;
	}
	
	
}
