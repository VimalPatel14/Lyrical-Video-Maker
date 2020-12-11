package com.github.hiteshsondhi88.libffmpeg.utils;

import android.os.Handler;

import java.util.ArrayList;

public class VideoMaker {
	int frame_duration=3;
	ArrayList<String> inputImages=null;
	String audioPath=null;
	private String outputVideoPath=null;
	Handler mHandler;
	
	public VideoMaker(){
		
	}
	
	public VideoMaker(ArrayList<String> inputImages, int frame_duration, String audioPath, String outputVideoPath) {
		this.outputVideoPath=outputVideoPath;
		this.audioPath=audioPath;
		this.frame_duration=frame_duration;
		this.inputImages=inputImages;
	}
	
	public String getAudioPath() {
		return audioPath;
	}
	public ArrayList<String> getInputImages() {
		return inputImages;
	}
	public String getOutputVideoPath() {
		return outputVideoPath;
	}
	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}
	public void setFrame_duration(int frame_duration) {
		this.frame_duration = frame_duration;
	}
	public void setInputImages(ArrayList<String> inputImages) {
		this.inputImages = inputImages;
	}
	public void setOutputVideoPath(String outputVideoPath) {
		this.outputVideoPath = outputVideoPath;
	}
	
	
}
