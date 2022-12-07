package by.itacademy.center.setup;

import java.io.IOException;
import java.io.Writer;

final class TeeWriter extends Writer {

    private final Writer first;
    private final Writer second;

    TeeWriter(Writer first, Writer second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        first.write(cbuf, off, len);
        second.write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        first.flush();
        second.flush();
    }

    @Override
    public void close() throws IOException {
        try {
            first.close();
        } finally {
            second.close();
        }
    }
}
