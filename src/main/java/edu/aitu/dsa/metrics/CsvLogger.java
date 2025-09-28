package edu.aitu.dsa.metrics;

import java.io.*;

public final class CsvLogger implements Closeable, Flushable {
    private final PrintWriter out;
    private boolean header;

    public CsvLogger(String filename, boolean append) {
        try {
            File f = new File(filename);
            this.out = new PrintWriter(new BufferedWriter(new FileWriter(f, append)));
            header = !(append && f.exists() && f.length()>0);
            if (header) writeHeader();
        } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    private void writeHeader() {
        out.println("algo,n,time_ns,depth,comparisons,swaps,allocs");
        out.flush();
    }

    public void write(String algo, int n, Metrics m) {
        out.printf("%s,%d,%d,%d,%d,%d,%d%n",
                algo, n, m.timeNanos, m.maxDepth, m.comparisons, m.swaps, m.allocs);
    }

    @Override public void flush(){ out.flush(); }
    @Override public void close(){ out.flush(); out.close(); }
}
