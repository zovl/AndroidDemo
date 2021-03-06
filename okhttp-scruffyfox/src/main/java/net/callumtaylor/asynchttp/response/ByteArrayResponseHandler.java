package net.callumtaylor.asynchttp.response;

import java.io.ByteArrayOutputStream;

public class ByteArrayResponseHandler extends ResponseHandler<byte[]> {
    private ByteArrayOutputStream byteBuffer;
    private byte[] bytes;

    @Override
    public void onByteChunkReceived(byte[] chunk, long chunkLength, long totalProcessed, long totalLength) {
        if (byteBuffer == null) {
            int total = (int) (totalLength > Integer.MAX_VALUE ? Integer.MAX_VALUE : totalLength);
            byteBuffer = new ByteArrayOutputStream(Math.max(8192, total));
        }

        if (chunk != null) {
            byteBuffer.write(chunk, 0, (int) chunkLength);
        }
    }

    /**
     * Generate the bitmap from the buffer and remove it to allow the GC to clean up properly
     */
    @Override
    public void generateContent() {
        if (byteBuffer.size() > 0) {
            this.bytes = byteBuffer.toByteArray();
        }

        this.byteBuffer = null;
    }

    /**
     * @return The data represented as a byte array
     */
    @Override
    public byte[] getContent() {
        return bytes;
    }
}
