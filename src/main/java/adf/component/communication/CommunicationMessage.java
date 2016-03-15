package adf.component.communication;

import adf.component.communication.util.BitOutputStream;

abstract public class CommunicationMessage
{
    private boolean isRadio;

    public CommunicationMessage(boolean isRadio)
    {
        this.isRadio = isRadio;
    }

    public boolean isRadio()
    {
        return this.isRadio;
    }

    abstract public int getByteArraySize();
    abstract public byte[] toByteArray();

    abstract public BitOutputStream toBitOutputStream();
}
