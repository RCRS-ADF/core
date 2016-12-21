package adf.component.communication;

import javax.annotation.Nonnull;
import java.util.List;

abstract public class MessageBundle
{
    @Nonnull
    abstract public List<Class<? extends CommunicationMessage>> getMessageClassList();
}
