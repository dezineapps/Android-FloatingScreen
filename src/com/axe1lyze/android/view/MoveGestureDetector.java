package com.axe1lyze.android.view;

import android.content.Context;
import android.view.MotionEvent;

public class MoveGestureDetector {
	
	private Context context;
	private OnMoveGestureListener listener;

	private float touchStartPositionX;
	private float touchStartPositionY;
	private float movedPositionFactorX;
	private float movedPositionFactorY;
	private double statusBarHeight;
	public float getTouchStartPositionX() {
		return touchStartPositionX;
	}

	public float getTouchStartPositionY() {
		return touchStartPositionY;
	}

	public float getMovedPositionFactorX() {
		return movedPositionFactorX;
	}

	public float getMovedPositionFactorY() {
		return movedPositionFactorY;
	}
	
	public MoveGestureDetector(Context context,OnMoveGestureListener listener) {
		this.context=context;
		this.listener = listener;
		this.statusBarHeight = Math.ceil(25 * context.getResources().getDisplayMetrics().density);
		reset();
	}
	
	public boolean  onTouch(MotionEvent event){
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			touchStartPositionX = event.getX();
			touchStartPositionY = event.getY();
			return listener.onMoveBegin(this);
		case MotionEvent.ACTION_MOVE:
			movedPositionFactorX=event.getRawX()-touchStartPositionX;
			movedPositionFactorY=(float) (event.getRawY()-touchStartPositionY-statusBarHeight);
			return listener.onMove(this);
		case MotionEvent.ACTION_UP:
			listener.onMoveEnd(this);
			break;
		case MotionEvent.ACTION_OUTSIDE:
			listener.onMoveOutside(this);
			reset();
			break;
		default:break;
		}

		return true;
	}
	
	private void reset(){
		touchStartPositionX=touchStartPositionY=movedPositionFactorX=movedPositionFactorY=0;
	}
	
	public static interface OnMoveGestureListener{
		public boolean onMoveBegin(MoveGestureDetector detector);
		public boolean onMove(MoveGestureDetector detector);
		public void onMoveOutside(MoveGestureDetector detector);
		public void onMoveEnd(MoveGestureDetector detector);
	}
	
	public static class SimpleOnMoveGestureListener implements OnMoveGestureListener{

		@Override
		public boolean onMoveBegin(MoveGestureDetector detector) {return false;}

		@Override
		public boolean onMove(MoveGestureDetector detector) {return false;}

		@Override
		public void onMoveOutside(MoveGestureDetector detector) {}
		
		@Override
		public void onMoveEnd(MoveGestureDetector detector) {}
		
	}
}
