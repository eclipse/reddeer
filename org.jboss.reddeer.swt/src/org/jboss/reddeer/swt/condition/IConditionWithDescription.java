package org.jboss.reddeer.swt.condition;

import org.eclipse.swtbot.swt.finder.waits.ICondition;

public interface IConditionWithDescription extends ICondition{
    public String getDescription();
}
