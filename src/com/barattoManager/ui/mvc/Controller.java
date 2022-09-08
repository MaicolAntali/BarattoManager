package com.barattoManager.ui.mvc;

/**
 * Interface that represent an MVC controller <br>
 * This is a common interface for all controllers, it has two methods:
 * <ul>
 *		<li> {@link #getModel()} which returns a {@link Model} or null </li>
 *      <li> {@link #getView()} which returns a {@link View} or null  </li>
 * </ul>
 */
public interface Controller {

	Model getModel();

	View getView();
}
