package com.github.hiteshsondhi88.libffmpeg;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class MakeMovie extends Service {
	 private long timeout = Long.MAX_VALUE;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	
	class TaskCreateSingleImageVideo extends AsyncTask<Void, String, Void>{
		 private Process process;
		private ShellCommand shellCommand;
		String[] cmdArr;
		private long startTime;
		private String output;
		public TaskCreateSingleImageVideo(int imgNo) {
			this.shellCommand = new ShellCommand();
				cmdArr = new String[]{
						
				};
		}
		@Override
		protected void onPreExecute() {
			startTime = System.currentTimeMillis();
		}
		@Override
		protected Void doInBackground(Void... params) {
			
			process = shellCommand.run(cmdArr);
			if(process==null){
				return null;
			}
			 try {
				checkAndUpdateProcess();
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		private void checkAndUpdateProcess() throws TimeoutException, InterruptedException {
	        while (!Util.isProcessCompleted(process)) {
	            // checking if process is completed
	            if (Util.isProcessCompleted(process)) {
	                return;
	            }
	            // Handling timeout
	            if (timeout != Long.MAX_VALUE && System.currentTimeMillis() > startTime + timeout) {
	                throw new TimeoutException("FFmpeg timed out");
	            }
	            try {
	                String line;
	                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
	                while ((line = reader.readLine()) != null) {
	                    if (isCancelled()) {
	                        return;
	                    }
	                    output += line+"\n";
	                    publishProgress(line);
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}

}
