package adf.agent.communication.standard.bundle.centralized;


import adf.agent.communication.standard.bundle.StandardMessage;
import adf.component.communication.util.BitStreamReader;

import javax.annotation.Nonnull;

public abstract class MessageCommand extends StandardMessage {

    public MessageCommand(boolean isRadio) {
        super(isRadio);
    }

    public MessageCommand(boolean isRadio, int from, int ttl, @Nonnull BitStreamReader bitStreamReader) {
        super(isRadio, from, ttl, bitStreamReader);
    }
}
