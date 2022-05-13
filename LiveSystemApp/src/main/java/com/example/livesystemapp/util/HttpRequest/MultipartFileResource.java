package com.example.livesystemapp.util.HttpRequest;

import org.springframework.core.io.InputStreamResource;
import java.io.IOException;
import java.io.InputStream;

public class MultipartFileResource extends InputStreamResource {
    private String filename;

    public MultipartFileResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }
    @Override
    public String getFilename() {
        return this.filename;
    }
    @Override
    public long contentLength() throws IOException {
        // we do not want to generally read the whole stream into memory ...
        return -1;
    }
}