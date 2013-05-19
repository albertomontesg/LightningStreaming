package com.lightningstreaming.activity;

import com.lightningstreaming.playlist.MasterPlaylist;
import com.yixia.zi.utils.Log;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class DownloadService extends Service{

	private DownloaderListener downloaderListener;
	private final IBinder mBinder = new LocalBinder();
	private MasterPlaylist mPlaylist;
	private boolean mInitialized;
	private int mCurrentState;
	private String mDownloadPath;
	
	public static final int STATE_PREPARED = -1;
	public static final int STATE_DOWNLOADING = 0;
	
	public class LocalBinder extends Binder {
		public DownloadService getService() {
			return DownloadService.this;
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("CREATE Download Service OK");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInitialized = false;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("BIND OK : " + intent.getPackage());
		return mBinder;
	}

	public boolean isInitialized() {
		return mInitialized;
	}
	
	public void setState(int state) {
		setmCurrentState(state);
	}
	
	public void initialize(MasterPlaylist playlist, String downloadPath) {
		setmPlaylist(playlist);
		mDownloadPath = downloadPath;
		downloaderListener.onPrepared();
	}
	
	public DownloaderListener getDownloaderListener() {
		return downloaderListener;
	}

	public void setDownloaderListener(DownloaderListener downloaderListener) {
		this.downloaderListener = downloaderListener;
	}

	public MasterPlaylist getmPlaylist() {
		return mPlaylist;
	}

	public void setmPlaylist(MasterPlaylist mPlaylist) {
		this.mPlaylist = mPlaylist;
	}

	public int getmCurrentState() {
		return mCurrentState;
	}

	public void setmCurrentState(int mCurrentState) {
		this.mCurrentState = mCurrentState;
	}

	public static interface DownloaderListener {
		public void onPrepared();
		
		public void onPlay();
		
		public void onBuffering();
		
		public void onChangeSegment();

	}
}
