package org.lice.runtime.ast;

import org.lice.runtime.MetaData;

import javax.script.*;

/**
 * Created by Glavo on 17-9-21.
 *
 * @author Glavo
 * @since 4.0.0
 */
public abstract class Node extends CompiledScript {
	private ScriptEngine engine;

	public int scope = 0;
	public MetaData metaData = null;

	public Object eval() throws ScriptException {
		return this.eval(engine.getContext());
	}

	@Override
	public Object eval(ScriptContext bindings) throws ScriptException {
		return this.eval(bindings.getBindings(scope));
	}

	@Override
	public abstract Object eval(Bindings bindings) throws ScriptException;

	@Override
	public ScriptEngine getEngine() {
		return engine;
	}
}