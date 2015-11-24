package adf.component.extaction;

import adf.agent.action.Action;

abstract public class ExtAction {
	protected Action result;

	public ExtAction()
	{
		result = null;
	}

	public ExtAction calc() {
		return this;
	}

	public Action getAction() {
		return result;
	}
}
