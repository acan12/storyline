package app.xzone.storyline.model;

import android.view.View;
import android.view.ViewGroup;

public class BaseModel extends Object {
	protected String todo;
	protected View view;
	protected Object lastSavedObject;
	

	/**
	 * @return the todo
	 */
	public String getTodo() {
		return todo;
	}

	/**
	 * @param todo the todo to set
	 */
	public void setTodo(String todo) {
		this.todo = todo;
	}
	
	
	/**
	 * @return the viewGroup
	 */
	public View getView() {
		return view;
	}

	/**
	 * @param viewGroup the viewGroup to set
	 */
	public void setView(View view) {
		this.view = view;
	}

	
	/**
	 * @return the lastSavedObject
	 */
	public Object getLastSavedObject() {
		return lastSavedObject;
	}

	/**
	 * @param the lastSavedObject to set
	 */
	public void setLastSavedObject(Object lastSavedObject) {
		this.lastSavedObject = lastSavedObject;
	}
}
